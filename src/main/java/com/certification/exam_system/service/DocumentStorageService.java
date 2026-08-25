package com.certification.exam_system.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class DocumentStorageService {

    private final Path storageLocation;

    public DocumentStorageService(
            @Value("${app.file.upload-dir}")
            String uploadDirectory
    ) {

        this.storageLocation =
                Paths.get(uploadDirectory)
                        .toAbsolutePath()
                        .normalize();

        try {

            Files.createDirectories(
                    this.storageLocation
            );

        } catch (IOException exception) {

            throw new RuntimeException(
                    "Could not create document storage directory",
                    exception
            );
        }
    }

    public String storeFile(
            MultipartFile file
    ) {

        if (file == null || file.isEmpty()) {

            throw new IllegalArgumentException(
                    "Uploaded file is empty"
            );
        }

        String originalFileName =
                file.getOriginalFilename();

        if (originalFileName == null
                || originalFileName.isBlank()) {

            throw new IllegalArgumentException(
                    "Invalid file name"
            );
        }

        String extension = "";

        int lastDot =
                originalFileName.lastIndexOf('.');

        if (lastDot >= 0) {
            extension =
                    originalFileName.substring(lastDot);
        }

        String storedFileName =
                UUID.randomUUID()
                        + extension;

        Path targetLocation =
                storageLocation.resolve(
                        storedFileName
                ).normalize();

        if (!targetLocation.startsWith(
                storageLocation
        )) {

            throw new IllegalArgumentException(
                    "Invalid file path"
            );
        }

        try {

            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return targetLocation.toString();

        } catch (IOException exception) {

            throw new RuntimeException(
                    "Could not store uploaded file",
                    exception
            );
        }
    }

    public byte[] loadFile(String documentReference) {

        try {

            Path filePath = Paths
                    .get(documentReference)
                    .toAbsolutePath()
                    .normalize();

            if (!filePath.startsWith(storageLocation)) {
                throw new IllegalArgumentException(
                        "Invalid document path"
                );
            }

            if (!Files.exists(filePath)) {
                throw new IllegalArgumentException(
                        "Document file not found"
                );
            }

            return Files.readAllBytes(filePath);

        } catch (IOException exception) {

            throw new RuntimeException(
                    "Could not read document file",
                    exception
            );
        }
    }
}