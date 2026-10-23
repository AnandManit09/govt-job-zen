package com.govt_job_zen.controller;

import com.govt_job_zen.dto.CategoryResponse;
import com.govt_job_zen.dto.RecruitmentResponse;
import com.govt_job_zen.dto.StateResponse;
import com.govt_job_zen.service.RecruitmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> categories() {
        return ResponseEntity.ok(recruitmentService.getPublicCategories());
    }

    @GetMapping("/states")
    public ResponseEntity<List<StateResponse>> states() {
        return ResponseEntity.ok(recruitmentService.getPublicStates());
    }

    @GetMapping("/recruitments")
    public ResponseEntity<List<RecruitmentResponse>> search(
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(recruitmentService.getPublicRecruitments(state, category, limit));
    }

    @GetMapping("/recruitments/latest")
    public ResponseEntity<List<RecruitmentResponse>> latest(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(recruitmentService.getLatestRecruitments(limit));
    }

    @GetMapping("/recruitments/{slug}")
    public ResponseEntity<RecruitmentResponse> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(recruitmentService.getPublicRecruitmentBySlug(slug));
    }
}
