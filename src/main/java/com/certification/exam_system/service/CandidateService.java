package com.certification.exam_system.service;

import com.certification.exam_system.dto.candidate.CandidateCreateRequest;
import com.certification.exam_system.dto.candidate.CandidateResponse;
import com.certification.exam_system.entity.Candidate;
import com.certification.exam_system.entity.User;
import com.certification.exam_system.entity.VerificationStatus;
import com.certification.exam_system.repository.CandidateRepository;
import com.certification.exam_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.certification.exam_system.dto.candidate.CandidateUpdateRequest;

import java.util.List;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;

    public CandidateService(
            CandidateRepository candidateRepository,
            UserRepository userRepository
    ) {
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
    }

    public CandidateResponse createCandidate(
            CandidateCreateRequest request
    ) {

        if (candidateRepository.existsByCandidateCode(
                request.getCandidateCode())) {

            throw new IllegalArgumentException(
                    "Candidate code is already registered"
            );
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        )
                );

        if (candidateRepository.existsByUserId(user.getId())) {
            throw new IllegalArgumentException(
                    "Candidate profile already exists for this user"
            );
        }

        Candidate candidate = new Candidate();

        candidate.setUser(user);
        candidate.setCandidateCode(
                request.getCandidateCode()
        );
        candidate.setFirstName(request.getFirstName());
        candidate.setLastName(request.getLastName());
        candidate.setDateOfBirth(
                request.getDateOfBirth()
        );
        candidate.setPhone(request.getPhone());
        candidate.setAddress(request.getAddress());

        Candidate savedCandidate =
                candidateRepository.save(candidate);

        return mapToResponse(savedCandidate);
    }

    public CandidateResponse getCandidate(Long id) {

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Candidate not found"
                        )
                );

        return mapToResponse(candidate);
    }

    private CandidateResponse mapToResponse(
            Candidate candidate
    ) {

        return new CandidateResponse(
                candidate.getId(),
                candidate.getUser().getId(),
                candidate.getCandidateCode(),
                candidate.getFirstName(),
                candidate.getLastName(),
                candidate.getDateOfBirth(),
                candidate.getPhone(),
                candidate.getAddress(),
                candidate.getVerificationStatus()
        );
    }

    public CandidateResponse updateCandidate(
            Long id,
            CandidateUpdateRequest request
    ) {

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Candidate not found"
                        )
                );

        if (request.getFirstName() != null) {
            candidate.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            candidate.setLastName(request.getLastName());
        }

        if (request.getDateOfBirth() != null) {
            candidate.setDateOfBirth(
                    request.getDateOfBirth()
            );
        }

        if (request.getPhone() != null) {
            candidate.setPhone(request.getPhone());
        }

        if (request.getAddress() != null) {
            candidate.setAddress(request.getAddress());
        }

        Candidate updatedCandidate =
                candidateRepository.save(candidate);

        return mapToResponse(updatedCandidate);
    }

    public void deleteCandidate(Long id) {

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Candidate not found"
                        )
                );

        candidateRepository.delete(candidate);
    }

    public List<CandidateResponse> searchCandidates(
            String search,
            VerificationStatus verificationStatus
    ) {

        if (search != null && search.isBlank()) {
            search = null;
        }

        List<Candidate> candidates =
                candidateRepository.searchCandidates(
                        search,
                        verificationStatus
                );

        return candidates.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CandidateResponse updateVerificationStatus(
            Long id,
            VerificationStatus verificationStatus
    ) {

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Candidate not found"
                        )
                );

        candidate.setVerificationStatus(
                verificationStatus
        );

        Candidate updatedCandidate =
                candidateRepository.save(candidate);

        return mapToResponse(updatedCandidate);
    }
}