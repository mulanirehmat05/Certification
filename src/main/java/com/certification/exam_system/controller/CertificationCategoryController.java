package com.certification.exam_system.controller;

import com.certification.exam_system.dto.certification.CertificationCategoryRequest;
import com.certification.exam_system.dto.certification.CertificationCategoryResponse;
import com.certification.exam_system.service.CertificationCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certification-categories")
public class CertificationCategoryController {

    private final CertificationCategoryService
            certificationCategoryService;

    public CertificationCategoryController(
            CertificationCategoryService
                    certificationCategoryService
    ) {
        this.certificationCategoryService =
                certificationCategoryService;
    }

    @PostMapping
    public ResponseEntity<CertificationCategoryResponse>
    createCategory(
            @Valid @RequestBody
            CertificationCategoryRequest request
    ) {

        CertificationCategoryResponse response =
                certificationCategoryService
                        .createCategory(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<
            List<CertificationCategoryResponse>>
    getAllCategories() {

        return ResponseEntity.ok(
                certificationCategoryService
                        .getAllCategories()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationCategoryResponse>
    getCategoryById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                certificationCategoryService
                        .getCategoryById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificationCategoryResponse>
    updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody
            CertificationCategoryRequest request
    ) {

        return ResponseEntity.ok(
                certificationCategoryService
                        .updateCategory(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long id
    ) {

        certificationCategoryService
                .deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}