package com.certification.exam_system.service;

import com.certification.exam_system.dto.certification.CertificationRequest;
import com.certification.exam_system.dto.certification.CertificationResponse;
import com.certification.exam_system.entity.Certification;
import com.certification.exam_system.entity.CertificationCategory;
import com.certification.exam_system.repository.CertificationCategoryRepository;
import com.certification.exam_system.repository.CertificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CertificationService {

    private final CertificationRepository certificationRepository;

    private final CertificationCategoryRepository
            certificationCategoryRepository;

    public CertificationService(
            CertificationRepository certificationRepository,
            CertificationCategoryRepository certificationCategoryRepository
    ) {
        this.certificationRepository =
                certificationRepository;

        this.certificationCategoryRepository =
                certificationCategoryRepository;
    }

    @Transactional
    public CertificationResponse createCertification(
            CertificationRequest request
    ) {

        if (certificationRepository
                .existsByCodeIgnoreCase(request.getCode())) {

            throw new IllegalArgumentException(
                    "Certification code already exists"
            );
        }

        CertificationCategory category =
                certificationCategoryRepository
                        .findById(request.getCategoryId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Certification category not found"
                                )
                        );

        Certification certification =
                new Certification();

        certification.setCode(request.getCode());
        certification.setName(request.getName());
        certification.setDescription(
                request.getDescription()
        );
        certification.setCategory(category);
        certification.setFee(request.getFee());
        certification.setValidityInMonths(
                request.getValidityInMonths()
        );
        certification.setPassingPercentage(
                request.getPassingPercentage()
        );
        certification.setExamDurationMinutes(
                request.getExamDurationMinutes()
        );
        certification.setActive(request.getActive());
        certification.setEligibilityCriteria(
                request.getEligibilityCriteria()
        );

        Certification savedCertification =
                certificationRepository.save(
                        certification
                );

        return mapToResponse(savedCertification);
    }

    @Transactional(readOnly = true)
    public List<CertificationResponse>
    getAllCertifications() {

        return certificationRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CertificationResponse getCertificationById(
            Long id
    ) {

        Certification certification =
                certificationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Certification not found"
                                )
                        );

        return mapToResponse(certification);
    }

    @Transactional(readOnly = true)
    public List<CertificationResponse>
    getCertificationsByCategory(
            Long categoryId
    ) {

        if (!certificationCategoryRepository
                .existsById(categoryId)) {

            throw new IllegalArgumentException(
                    "Certification category not found"
            );
        }

        return certificationRepository
                .findByCategoryId(categoryId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CertificationResponse>
    getActiveCertifications() {

        return certificationRepository
                .findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public CertificationResponse updateCertification(
            Long id,
            CertificationRequest request
    ) {

        Certification certification =
                certificationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Certification not found"
                                )
                        );

        if (!certification.getCode()
                .equalsIgnoreCase(request.getCode())
                && certificationRepository
                .existsByCodeIgnoreCase(
                        request.getCode()
                )) {

            throw new IllegalArgumentException(
                    "Certification code already exists"
            );
        }

        CertificationCategory category =
                certificationCategoryRepository
                        .findById(request.getCategoryId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Certification category not found"
                                )
                        );

        certification.setCode(request.getCode());
        certification.setName(request.getName());
        certification.setDescription(
                request.getDescription()
        );
        certification.setCategory(category);
        certification.setFee(request.getFee());
        certification.setValidityInMonths(
                request.getValidityInMonths()
        );
        certification.setPassingPercentage(
                request.getPassingPercentage()
        );
        certification.setExamDurationMinutes(
                request.getExamDurationMinutes()
        );
        certification.setActive(request.getActive());
        certification.setEligibilityCriteria(
                request.getEligibilityCriteria()
        );

        Certification updatedCertification =
                certificationRepository.save(
                        certification
                );

        return mapToResponse(updatedCertification);
    }

    @Transactional
    public void deleteCertification(Long id) {

        Certification certification =
                certificationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Certification not found"
                                )
                        );

        certificationRepository.delete(certification);
    }

    private CertificationResponse mapToResponse(
            Certification certification
    ) {

        CertificationCategory category =
                certification.getCategory();

        return new CertificationResponse(
                certification.getId(),
                certification.getCode(),
                certification.getName(),
                certification.getDescription(),
                category.getId(),
                category.getName(),
                certification.getFee(),
                certification.getValidityInMonths(),
                certification.getPassingPercentage(),
                certification.getExamDurationMinutes(),
                certification.getActive(),
                certification.getEligibilityCriteria(),
                certification.getCreatedAt(),
                certification.getUpdatedAt()
        );
    }
}