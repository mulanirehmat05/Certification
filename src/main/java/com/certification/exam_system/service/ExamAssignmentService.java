package com.certification.exam_system.service;

import com.certification.exam_system.dto.exam.ExamAssignmentRequest;
import com.certification.exam_system.dto.exam.ExamAssignmentResponse;
import com.certification.exam_system.entity.Candidate;
import com.certification.exam_system.entity.ExamAssignment;
import com.certification.exam_system.entity.ExamSession;
import com.certification.exam_system.entity.ExamSessionStatus;
import com.certification.exam_system.repository.CandidateRepository;
import com.certification.exam_system.repository.ExamAssignmentRepository;
import com.certification.exam_system.repository.ExamSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamAssignmentService {

    private final ExamAssignmentRepository examAssignmentRepository;
    private final CandidateRepository candidateRepository;
    private final ExamSessionRepository examSessionRepository;

    public ExamAssignmentService(
            ExamAssignmentRepository examAssignmentRepository,
            CandidateRepository candidateRepository,
            ExamSessionRepository examSessionRepository
    ) {
        this.examAssignmentRepository = examAssignmentRepository;
        this.candidateRepository = candidateRepository;
        this.examSessionRepository = examSessionRepository;
    }

    @Transactional
    public ExamAssignmentResponse createAssignment(
            ExamAssignmentRequest request
    ) {

        Candidate candidate =
                candidateRepository
                        .findById(request.getCandidateId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Candidate not found"
                                )
                        );

        ExamSession examSession =
                examSessionRepository
                        .findById(request.getExamSessionId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam session not found"
                                )
                        );

        if (examSession.getStatus()
                != ExamSessionStatus.SCHEDULED) {

            throw new IllegalArgumentException(
                    "Candidate can only be assigned to a scheduled exam session"
            );
        }

        if (examAssignmentRepository
                .existsByCandidateIdAndExamSessionId(
                        request.getCandidateId(),
                        request.getExamSessionId()
                )) {

            throw new IllegalArgumentException(
                    "Candidate is already assigned to this exam session"
            );
        }

        long assignedCandidates =
                examAssignmentRepository
                        .countByExamSessionId(
                                request.getExamSessionId()
                        );

        if (assignedCandidates
                >= examSession.getMaximumCandidates()) {

            throw new IllegalArgumentException(
                    "Exam session has reached maximum candidate capacity"
            );
        }

        ExamAssignment assignment =
                new ExamAssignment();

        assignment.setCandidate(candidate);
        assignment.setExamSession(examSession);

        ExamAssignment savedAssignment =
                examAssignmentRepository.save(
                        assignment
                );

        return mapToResponse(savedAssignment);
    }

    @Transactional(readOnly = true)
    public List<ExamAssignmentResponse>
    getAllAssignments() {

        return examAssignmentRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ExamAssignmentResponse getAssignmentById(
            Long id
    ) {

        ExamAssignment assignment =
                examAssignmentRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam assignment not found"
                                )
                        );

        return mapToResponse(assignment);
    }

    @Transactional(readOnly = true)
    public List<ExamAssignmentResponse>
    getAssignmentsByCandidate(
            Long candidateId
    ) {

        if (!candidateRepository.existsById(candidateId)) {

            throw new IllegalArgumentException(
                    "Candidate not found"
            );
        }

        return examAssignmentRepository
                .findByCandidateId(candidateId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ExamAssignmentResponse>
    getAssignmentsByExamSession(
            Long examSessionId
    ) {

        if (!examSessionRepository.existsById(examSessionId)) {

            throw new IllegalArgumentException(
                    "Exam session not found"
            );
        }

        return examAssignmentRepository
                .findByExamSessionId(examSessionId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public void deleteAssignment(Long id) {

        ExamAssignment assignment =
                examAssignmentRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Exam assignment not found"
                                )
                        );

        examAssignmentRepository.delete(assignment);
    }

    private ExamAssignmentResponse mapToResponse(
            ExamAssignment assignment
    ) {

        Candidate candidate =
                assignment.getCandidate();

        ExamSession examSession =
                assignment.getExamSession();

        return new ExamAssignmentResponse(
                assignment.getId(),
                candidate.getId(),
                candidate.getCandidateCode(),
                candidate.getFirstName()
                        + " "
                        + candidate.getLastName(),
                examSession.getId(),
                examSession.getSessionCode(),
                examSession.getCertification().getCode(),
                examSession.getCertification().getName(),
                assignment.getAssignedAt()
        );
    }
}