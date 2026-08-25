package com.certification.exam_system.repository;

import com.certification.exam_system.entity.Candidate;
import com.certification.exam_system.entity.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository
        extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByUserId(Long userId);

    Optional<Candidate> findByCandidateCode(String candidateCode);

    boolean existsByCandidateCode(String candidateCode);

    boolean existsByUserId(Long userId);

    @Query("""
            SELECT c
            FROM Candidate c
            WHERE
                (
                    :search IS NULL
                    OR LOWER(c.candidateCode)
                    LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(c.firstName)
                    LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(c.lastName)
                    LIKE LOWER(CONCAT('%', :search, '%'))
                )
                AND
                (
                    :verificationStatus IS NULL
                    OR c.verificationStatus = :verificationStatus
                )
            """)
    List<Candidate> searchCandidates(
            @Param("search") String search,
            @Param("verificationStatus")
            VerificationStatus verificationStatus
    );
}