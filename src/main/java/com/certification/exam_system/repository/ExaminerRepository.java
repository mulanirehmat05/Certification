package com.certification.exam_system.repository;

import com.certification.exam_system.entity.Role;
import com.certification.exam_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExaminerRepository extends JpaRepository<User, Long> {

    List<User> findByRole(Role role);

    List<User> findByRoleAndActiveTrue(Role role);
}