package com.govt_job_zen.controller;

import com.govt_job_zen.dto.RecruitmentAdmitCardResponse;
import com.govt_job_zen.dto.RecruitmentAnswerKeyResponse;
import com.govt_job_zen.dto.RecruitmentResultResponse;
import com.govt_job_zen.service.LifecycleContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/recruitments")
public class LifecycleContentController {

    private final LifecycleContentService lifecycleContentService;

    public LifecycleContentController(LifecycleContentService lifecycleContentService) {
        this.lifecycleContentService = lifecycleContentService;
    }

    @GetMapping("/{slug}/results")
    public ResponseEntity<List<RecruitmentResultResponse>> getResults(@PathVariable String slug) {
        return ResponseEntity.ok(lifecycleContentService.getPublishedResultsForRecruitmentSlug(slug));
    }

    @GetMapping("/{slug}/admit-cards")
    public ResponseEntity<List<RecruitmentAdmitCardResponse>> getAdmitCards(@PathVariable String slug) {
        return ResponseEntity.ok(lifecycleContentService.getPublishedAdmitCardsForRecruitmentSlug(slug));
    }

    @GetMapping("/{slug}/answer-keys")
    public ResponseEntity<List<RecruitmentAnswerKeyResponse>> getAnswerKeys(@PathVariable String slug) {
        return ResponseEntity.ok(lifecycleContentService.getPublishedAnswerKeysForRecruitmentSlug(slug));
    }
}
