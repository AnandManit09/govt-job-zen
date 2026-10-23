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

    @GetMapping("/recruitments")
    public ResponseEntity<java.util.List<RecruitmentResponse>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(recruitmentService.getRecruitments(page, size));
    }

    @GetMapping("/recruitments/{id}")
    public ResponseEntity<RecruitmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(recruitmentService.getRecruitmentById(id));
    }

    @PutMapping("/recruitments/{id}")
    public ResponseEntity<RecruitmentResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody RecruitmentRequest request) {
        return ResponseEntity.ok(recruitmentService.updateRecruitment(id, request));
    }

    @DeleteMapping("/recruitments/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recruitmentService.deleteRecruitment(id);
        return ResponseEntity.noContent().build();
    }
}
