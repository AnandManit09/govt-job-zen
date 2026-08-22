package com.govt_job_zen.controller;

import com.govt_job_zen.dto.RecruitmentResponse;
import com.govt_job_zen.service.RecruitmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicRecruitmentController {

    private final RecruitmentService recruitmentService;

    public PublicRecruitmentController(RecruitmentService recruitmentService) {
        this.recruitmentService = recruitmentService;
    }

    @GetMapping("/recruitments/latest")
    public ResponseEntity<List<RecruitmentResponse>> latest(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(recruitmentService.getLatestRecruitments(limit));
    }
}
