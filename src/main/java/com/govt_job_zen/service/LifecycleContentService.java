package com.govt_job_zen.service;

import com.govt_job_zen.domain.*;
import com.govt_job_zen.dto.*;
import com.govt_job_zen.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LifecycleContentService {

    private final RecruitmentResultRepository recruitmentResultRepository;
    private final RecruitmentAdmitCardRepository recruitmentAdmitCardRepository;
    private final RecruitmentAnswerKeyRepository recruitmentAnswerKeyRepository;
    private final RecruitmentRepository recruitmentRepository;

    public LifecycleContentService(
            RecruitmentResultRepository recruitmentResultRepository,
            RecruitmentAdmitCardRepository recruitmentAdmitCardRepository,
            RecruitmentAnswerKeyRepository recruitmentAnswerKeyRepository,
            RecruitmentRepository recruitmentRepository) {
        this.recruitmentResultRepository = recruitmentResultRepository;
        this.recruitmentAdmitCardRepository = recruitmentAdmitCardRepository;
        this.recruitmentAnswerKeyRepository = recruitmentAnswerKeyRepository;
        this.recruitmentRepository = recruitmentRepository;
    }

    @Transactional(readOnly = true)
    public List<RecruitmentResultResponse> getPublishedResultsForRecruitmentSlug(String recruitmentSlug) {
        List<RecruitmentResult> records = recruitmentResultRepository.findByRecruitment_SlugAndPublishedTrueOrderByPublishedAtDesc(recruitmentSlug);
        if (records.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No published results found for slug: " + recruitmentSlug);
        }
        return records.stream().map(this::toResultResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecruitmentAdmitCardResponse> getPublishedAdmitCardsForRecruitmentSlug(String recruitmentSlug) {
        List<RecruitmentAdmitCard> records = recruitmentAdmitCardRepository.findByRecruitment_SlugAndPublishedTrueOrderByPublishedAtDesc(recruitmentSlug);
        if (records.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No published admit cards found for slug: " + recruitmentSlug);
        }
        return records.stream().map(this::toAdmitCardResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecruitmentAnswerKeyResponse> getPublishedAnswerKeysForRecruitmentSlug(String recruitmentSlug) {
        List<RecruitmentAnswerKey> records = recruitmentAnswerKeyRepository.findByRecruitment_SlugAndPublishedTrueOrderByPublishedAtDesc(recruitmentSlug);
        if (records.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No published answer keys found for slug: " + recruitmentSlug);
        }
        return records.stream().map(this::toAnswerKeyResponse).collect(Collectors.toList());
    }

    private RecruitmentResultResponse toResultResponse(RecruitmentResult record) {
        RecruitmentResultResponse response = new RecruitmentResultResponse();
        response.setId(record.getId());
        response.setTitle(record.getTitle());
        response.setSlug(record.getSlug());
        response.setDescription(record.getDescription());
        response.setDocumentUrl(record.getDocumentUrl());
        response.setReleaseDate(record.getReleaseDate());
        response.setPublished(record.isPublished());
        response.setPublishedAt(record.getPublishedAt());
        return response;
    }

    private RecruitmentAdmitCardResponse toAdmitCardResponse(RecruitmentAdmitCard record) {
        RecruitmentAdmitCardResponse response = new RecruitmentAdmitCardResponse();
        response.setId(record.getId());
        response.setTitle(record.getTitle());
        response.setSlug(record.getSlug());
        response.setDescription(record.getDescription());
        response.setDocumentUrl(record.getDocumentUrl());
        response.setReleaseDate(record.getReleaseDate());
        response.setPublished(record.isPublished());
        response.setPublishedAt(record.getPublishedAt());
        return response;
    }

    private RecruitmentAnswerKeyResponse toAnswerKeyResponse(RecruitmentAnswerKey record) {
        RecruitmentAnswerKeyResponse response = new RecruitmentAnswerKeyResponse();
        response.setId(record.getId());
        response.setTitle(record.getTitle());
        response.setSlug(record.getSlug());
        response.setDescription(record.getDescription());
        response.setDocumentUrl(record.getDocumentUrl());
        response.setReleaseDate(record.getReleaseDate());
        response.setPublished(record.isPublished());
        response.setPublishedAt(record.getPublishedAt());
        return response;
    }

    public String generateSlug(String title) {
        return title.trim().toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "") + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public RecruitmentResult createResult(Long recruitmentId, String title, String description, String documentUrl, LocalDate releaseDate, boolean published) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recruitment not found with id: " + recruitmentId));

        RecruitmentResult record = new RecruitmentResult();
        record.setRecruitment(recruitment);
        record.setTitle(title);
        record.setSlug(generateSlug(title));
        record.setDescription(description);
        record.setDocumentUrl(documentUrl);
        record.setReleaseDate(releaseDate);
        record.setPublished(published);
        if (published) {
            record.setPublishedAt(LocalDateTime.now());
        }
        return recruitmentResultRepository.save(record);
    }
}
