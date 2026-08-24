package com.certification.exam_system.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/protected")
    public String protectedEndpoint(
            Authentication authentication
    ) {
        return "Authenticated as: "
                + authentication.getName();
    }

    @GetMapping("/admin")
    public String adminEndpoint(
            Authentication authentication
    ) {
        return "Admin access granted to: "
                + authentication.getName();
    }

    @GetMapping("/examiner")
    public String examinerEndpoint(
            Authentication authentication
    ) {
        return "Examiner access granted to: "
                + authentication.getName();
    }

    @GetMapping("/candidate")
    public String candidateEndpoint(
            Authentication authentication
    ) {
        return "Candidate access granted to: "
                + authentication.getName();
    }
}