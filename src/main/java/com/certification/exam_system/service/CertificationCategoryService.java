package com.certification.exam_system.service;

import com.certification.exam_system.dto.certification.CertificationCategoryRequest;
import com.certification.exam_system.dto.certification.CertificationCategoryResponse;
import com.certification.exam_system.entity.CertificationCategory;
import com.certification.exam_system.repository.CertificationCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationCategoryService {

    private final CertificationCategoryRepository
            certificationCategoryRepository;

    public CertificationCategoryService(
            CertificationCategoryRepository
                    certificationCategoryRepository
    ) {
        this.certificationCategoryRepository =
                certificationCategoryRepository;
    }

    public CertificationCategoryResponse createCategory(
            CertificationCategoryRequest request
    ) {

        if (certificationCategoryRepository
                .existsByNameIgnoreCase(request.getName())) {

            throw new IllegalArgumentException(
                    "Certification category already exists"
            );
        }

        CertificationCategory category =
                new CertificationCategory();

        category.setName(request.getName());
        category.setDescription(
                request.getDescription()
        );

        CertificationCategory savedCategory =
                certificationCategoryRepository.save(
                        category
                );

        return mapToResponse(savedCategory);
    }

    public List<CertificationCategoryResponse>
    getAllCategories() {

        return certificationCategoryRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CertificationCategoryResponse
    getCategoryById(Long id) {

        CertificationCategory category =
                certificationCategoryRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Certification category not found"
                                )
                        );

        return mapToResponse(category);
    }

    public CertificationCategoryResponse updateCategory(
            Long id,
            CertificationCategoryRequest request
    ) {

        CertificationCategory category =
                certificationCategoryRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Certification category not found"
                                )
                        );

        if (!category.getName()
                .equalsIgnoreCase(request.getName())
                && certificationCategoryRepository
                .existsByNameIgnoreCase(
                        request.getName()
                )) {

            throw new IllegalArgumentException(
                    "Certification category already exists"
            );
        }

        category.setName(request.getName());
        category.setDescription(
                request.getDescription()
        );

        CertificationCategory updatedCategory =
                certificationCategoryRepository.save(
                        category
                );

        return mapToResponse(updatedCategory);
    }

    public void deleteCategory(Long id) {

        CertificationCategory category =
                certificationCategoryRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Certification category not found"
                                )
                        );

        certificationCategoryRepository.delete(category);
    }

    private CertificationCategoryResponse mapToResponse(
            CertificationCategory category
    ) {

        return new CertificationCategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getActive(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}