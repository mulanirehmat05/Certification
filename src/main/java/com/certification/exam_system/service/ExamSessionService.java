package com.certification.exam_system.service;

import com.certification.exam_system.dto.exam.ExamSessionRequest;
import com.certification.exam_system.dto.exam.ExamSessionResponse;
import com.certification.exam_system.entity.Certification;
import com.certification.exam_system.entity.ExamSession;
import com.certification.exam_system.entity.ExamSessionStatus;
import com.certification.exam_system.repository.CertificationRepository;
import com.certification.exam_system.repository.ExamSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.certification.exam_system.dto.exam.UpdateExamSessionStatusRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ExamSessionService {

    private final ExamSessionRepository examSessionRepository;

    private final CertificationRepository certificationRepository;

    public ExamSessionService(
            ExamSessionRepository examSessionRepository,
            CertificationRepository certificationRepository
    ) {
        this.examSessionRepository =
                examSessionRepository;

        this.certificationRepository =
                certificationRepository;
    }

    @Transactional
    public ExamSessionResponse createExamSession(
            ExamSessionRequest request
    ) {

        if (examSessionRepository
                .existsBySessionCodeIgnoreCase(
                        request.getSessionCode()
                )) {

            throw new IllegalArgumentException(
                    "Exam session code already exists"
            );
        }

        validateExamDateAndTime(request);

        Certification certification =
                certificationRepository
                        .findById(
                                request.getCertificationId()
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Certification not found"
                                )
                        );

        ExamSession examSession =
                new ExamSession();

        examSession.setSessionCode(
                request.getSessionCode()
        );

        examSession.setCertification(
                certification
        );

        examSession.setExamDate(
                request.getExamDate()
        );

        examSession.setStartTime(
                request.getStartTime()
        );

        examSession.setEndTime(
                request.getEndTime()
        );

        examSession.setExamMode(
                request.getExamMode()
        );

        examSession.setStatus(
                ExamSessionStatus.DRAFT
        );

        examSession.setExamCenter(
                request.getExamCenter()
        );

        examSession.setExamCenterAddress(
                request.getExamCenterAddress()
        );

        examSession.setMaximumCandidates(
                request.getMaximumCandidates()
        );

        ExamSession savedSession =
                examSessionRepository.save(
                        examSession
                );

        return mapToResponse(savedSession);
    }

    @Transactional(readOnly = true)
    public List<ExamSessionResponse>
    getAllExamSessions() {

        return examSessionRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ExamSessionResponse getExamSessionById(
            Long id
    ) {

        ExamSession examSession =
                examSessionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam session not found"
                                )
                        );

        return mapToResponse(examSession);
    }

    @Transactional(readOnly = true)
    public List<ExamSessionResponse>
    getSessionsByCertification(
            Long certificationId
    ) {

        if (!certificationRepository
                .existsById(certificationId)) {

            throw new IllegalArgumentException(
                    "Certification not found"
            );
        }

        return examSessionRepository
                .findByCertificationId(
                        certificationId
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ExamSessionResponse>
    getSessionsByStatus(
            ExamSessionStatus status
    ) {

        return examSessionRepository
                .findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ExamSessionResponse>
    getSessionsByDate(
            LocalDate examDate
    ) {

        return examSessionRepository
                .findByExamDate(examDate)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public ExamSessionResponse updateExamSession(
            Long id,
            ExamSessionRequest request
    ) {

        ExamSession examSession =
                examSessionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam session not found"
                                )
                        );

        if (!examSession.getSessionCode()
                .equalsIgnoreCase(
                        request.getSessionCode()
                )
                && examSessionRepository
                .existsBySessionCodeIgnoreCase(
                        request.getSessionCode()
                )) {

            throw new IllegalArgumentException(
                    "Exam session code already exists"
            );
        }

        validateExamDateAndTime(request);

        Certification certification =
                certificationRepository
                        .findById(
                                request.getCertificationId()
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Certification not found"
                                )
                        );

        examSession.setSessionCode(
                request.getSessionCode()
        );

        examSession.setCertification(
                certification
        );

        examSession.setExamDate(
                request.getExamDate()
        );

        examSession.setStartTime(
                request.getStartTime()
        );

        examSession.setEndTime(
                request.getEndTime()
        );

        examSession.setExamMode(
                request.getExamMode()
        );

        examSession.setExamCenter(
                request.getExamCenter()
        );

        examSession.setExamCenterAddress(
                request.getExamCenterAddress()
        );

        examSession.setMaximumCandidates(
                request.getMaximumCandidates()
        );

        ExamSession updatedSession =
                examSessionRepository.save(
                        examSession
                );

        return mapToResponse(updatedSession);
    }

    @Transactional
    public ExamSessionResponse updateExamSessionStatus(
            Long id,
            UpdateExamSessionStatusRequest request
    ) {

        ExamSession examSession =
                examSessionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam session not found"
                                )
                        );

        ExamSessionStatus currentStatus =
                examSession.getStatus();

        ExamSessionStatus newStatus =
                request.getStatus();

        if (!isValidStatusTransition(
                currentStatus,
                newStatus
        )) {

            throw new IllegalArgumentException(
                    "Invalid exam session status transition from "
                            + currentStatus
                            + " to "
                            + newStatus
            );
        }

        examSession.setStatus(newStatus);

        ExamSession updatedSession =
                examSessionRepository.save(
                        examSession
                );

        return mapToResponse(updatedSession);
    }

    private boolean isValidStatusTransition(
            ExamSessionStatus currentStatus,
            ExamSessionStatus newStatus
    ) {

        if (currentStatus == newStatus) {
            return false;
        }

        return switch (currentStatus) {

            case DRAFT ->
                    newStatus == ExamSessionStatus.SCHEDULED
                            || newStatus == ExamSessionStatus.CANCELLED;

            case SCHEDULED ->
                    newStatus == ExamSessionStatus.ONGOING
                            || newStatus == ExamSessionStatus.CANCELLED;

            case ONGOING ->
                    newStatus == ExamSessionStatus.COMPLETED;

            case COMPLETED,
                 CANCELLED ->
                    false;
        };
    }

    @Transactional
    public void deleteExamSession(Long id) {

        ExamSession examSession =
                examSessionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam session not found"
                                )
                        );

        examSessionRepository.delete(examSession);
    }

    private void validateExamDateAndTime(
            ExamSessionRequest request
    ) {

        if (request.getExamDate()
                .isBefore(LocalDate.now())) {

            throw new IllegalArgumentException(
                    "Exam date cannot be in the past"
            );
        }

        if (!request.getStartTime()
                .isBefore(request.getEndTime())) {

            throw new IllegalArgumentException(
                    "Exam start time must be before end time"
            );
        }

        if (request.getExamMode()
                .name()
                .equals("OFFLINE")) {

            if (request.getExamCenter() == null
                    || request.getExamCenter()
                    .isBlank()) {

                throw new IllegalArgumentException(
                        "Exam center is required for offline exams"
                );
            }
        }
    }

    private ExamSessionResponse mapToResponse(
            ExamSession examSession
    ) {

        Certification certification =
                examSession.getCertification();

        return new ExamSessionResponse(
                examSession.getId(),
                examSession.getSessionCode(),
                certification.getId(),
                certification.getCode(),
                certification.getName(),
                examSession.getExamDate(),
                examSession.getStartTime(),
                examSession.getEndTime(),
                examSession.getExamMode(),
                examSession.getStatus(),
                examSession.getExamCenter(),
                examSession.getExamCenterAddress(),
                examSession.getMaximumCandidates(),
                examSession.getCreatedAt(),
                examSession.getUpdatedAt()
        );
    }
}