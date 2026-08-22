package com.govt_job_zen.controller;

import com.govt_job_zen.dto.RecruitmentRequest;
import com.govt_job_zen.dto.RecruitmentResponse;
import com.govt_job_zen.service.RecruitmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminRecruitmentController {

    private final RecruitmentService recruitmentService;

    public AdminRecruitmentController(RecruitmentService recruitmentService) {
        this.recruitmentService = recruitmentService;
    }

    @PostMapping("/recruitments")
    public ResponseEntity<RecruitmentResponse> create(@Valid @RequestBody RecruitmentRequest request) {
        RecruitmentResponse response = recruitmentService.createRecruitment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
