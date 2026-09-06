package com.certification.exam_system.service;

import com.certification.exam_system.dto.ExaminerResponse;
import com.certification.exam_system.entity.Role;
import com.certification.exam_system.entity.User;
import com.certification.exam_system.repository.ExaminerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExaminerService {

    private final ExaminerRepository examinerRepository;

    public ExaminerService(
            ExaminerRepository examinerRepository
    ) {
        this.examinerRepository = examinerRepository;
    }

    @Transactional(readOnly = true)
    public List<ExaminerResponse> getAllExaminers() {

        List<User> examiners =
                examinerRepository.findByRole(Role.EXAMINER);

        return examiners.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ExaminerResponse> getActiveExaminers() {

        List<User> examiners =
                examinerRepository.findByRoleAndActiveTrue(
                        Role.EXAMINER
                );

        return examiners.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ExaminerResponse mapToResponse(User user) {

        return new ExaminerResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getActive()
        );
    }
}