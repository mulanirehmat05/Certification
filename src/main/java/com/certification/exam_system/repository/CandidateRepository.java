package com.certification.exam_system.repository;

import com.certification.exam_system.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByUserId(Long userId);

    Optional<Candidate> findByCandidateCode(String candidateCode);

    boolean existsByCandidateCode(String candidateCode);

    boolean existsByUserId(Long userId);
}