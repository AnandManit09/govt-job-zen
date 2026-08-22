package com.govt_job_zen.service;

import com.govt_job_zen.domain.*;
import com.govt_job_zen.dto.RecruitmentRequest;
import com.govt_job_zen.dto.RecruitmentResponse;
import com.govt_job_zen.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final OrganizationRepository organizationRepository;
    private final StateRepository stateRepository;
    private final CategoryRepository categoryRepository;
    private final QualificationRepository qualificationRepository;

    public RecruitmentService(
            RecruitmentRepository recruitmentRepository,
            OrganizationRepository organizationRepository,
            StateRepository stateRepository,
            CategoryRepository categoryRepository,
            QualificationRepository qualificationRepository) {
        this.recruitmentRepository = recruitmentRepository;
        this.organizationRepository = organizationRepository;
        this.stateRepository = stateRepository;
        this.categoryRepository = categoryRepository;
        this.qualificationRepository = qualificationRepository;
    }

    @Transactional
    public RecruitmentResponse createRecruitment(RecruitmentRequest request) {
        String normalizedSlug = slugify(request.getSlug());

        Organization organization = organizationRepository.findByNameIgnoreCase(request.getOrganizationName())
                .orElseGet(() -> organizationRepository.save(new Organization(request.getOrganizationName(), slugify(request.getOrganizationName()))));

        State state = null;
        if (request.getStateName() != null && !request.getStateName().isBlank()) {
            state = stateRepository.findByNameIgnoreCase(request.getStateName())
                    .orElseGet(() -> stateRepository.save(new State(request.getStateName(), slugify(request.getStateName()))));
        }

        Recruitment recruitment = new Recruitment();
        recruitment.setTitle(request.getTitle());
        recruitment.setSlug(normalizedSlug);
        recruitment.setOrganization(organization);
        recruitment.setState(state);
        recruitment.setDescription(request.getDescription());
        recruitment.setNotificationDate(request.getNotificationDate());
        recruitment.setApplicationStartDate(request.getApplicationStartDate());
        recruitment.setApplicationEndDate(request.getApplicationEndDate());
        recruitment.setVacancies(request.getVacancies());
        recruitment.setQualificationDetails(request.getQualificationDetails());
        recruitment.setAgeLimit(request.getAgeLimit());
        recruitment.setSalary(request.getSalary());
        recruitment.setApplicationFee(request.getApplicationFee());
        recruitment.setSelectionProcess(request.getSelectionProcess());
        recruitment.setOfficialNotificationUrl(request.getOfficialNotificationUrl());
        recruitment.setOfficialWebsiteUrl(request.getOfficialWebsiteUrl());
        recruitment.setApplyUrl(request.getApplyUrl());
        recruitment.setStatus(request.getStatus() == null ? RecruitmentStatus.UPCOMING : request.getStatus());
        recruitment.setPublished(request.isPublished());
        recruitment.setFeatured(request.isFeatured());
        recruitment.setCategories(resolveCategories(request.getCategoryNames()));
        recruitment.setQualifications(resolveQualifications(request.getQualificationNames()));

        if (request.isPublished()) {
            recruitment.setPublishedAt(LocalDateTime.now());
        }

        Recruitment saved = recruitmentRepository.save(recruitment);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<RecruitmentResponse> getLatestRecruitments(int limit) {
        return recruitmentRepository.findByPublishedTrueOrderByPublishedAtDesc(PageRequest.of(0, limit)).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private Set<Category> resolveCategories(Set<String> categoryNames) {
        if (categoryNames == null || categoryNames.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Category> categories = new HashSet<>();
        for (String categoryName : categoryNames) {
            if (categoryName == null || categoryName.isBlank()) {
                continue;
            }

            Category category = categoryRepository.findByNameIgnoreCase(categoryName)
                    .orElseGet(() -> categoryRepository.save(new Category(categoryName, slugify(categoryName))));
            categories.add(category);
        }
        return categories;
    }

    private Set<Qualification> resolveQualifications(Set<String> qualificationNames) {
        if (qualificationNames == null || qualificationNames.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Qualification> qualifications = new HashSet<>();
        for (String qualificationName : qualificationNames) {
            if (qualificationName == null || qualificationName.isBlank()) {
                continue;
            }

            Qualification qualification = qualificationRepository.findByNameIgnoreCase(qualificationName)
                    .orElseGet(() -> qualificationRepository.save(new Qualification(qualificationName, slugify(qualificationName))));
            qualifications.add(qualification);
        }
        return qualifications;
    }

    private String slugify(String value) {
        if (value == null || value.isBlank()) {
            return UUID.randomUUID().toString();
        }
        return value.trim().toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }

    private RecruitmentResponse toResponse(Recruitment recruitment) {
        RecruitmentResponse response = new RecruitmentResponse();
        response.setId(recruitment.getId());
        response.setTitle(recruitment.getTitle());
        response.setSlug(recruitment.getSlug());
        response.setOrganization(recruitment.getOrganization());
        response.setState(recruitment.getState());
        response.setNotificationDate(recruitment.getNotificationDate());
        response.setApplicationStartDate(recruitment.getApplicationStartDate());
        response.setApplicationEndDate(recruitment.getApplicationEndDate());
        response.setVacancies(recruitment.getVacancies());
        response.setQualificationDetails(recruitment.getQualificationDetails());
        response.setAgeLimit(recruitment.getAgeLimit());
        response.setSalary(recruitment.getSalary());
        response.setApplicationFee(recruitment.getApplicationFee());
        response.setSelectionProcess(recruitment.getSelectionProcess());
        response.setDescription(recruitment.getDescription());
        response.setOfficialNotificationUrl(recruitment.getOfficialNotificationUrl());
        response.setOfficialWebsiteUrl(recruitment.getOfficialWebsiteUrl());
        response.setApplyUrl(recruitment.getApplyUrl());
        response.setStatus(recruitment.getStatus());
        response.setPublished(recruitment.isPublished());
        response.setFeatured(recruitment.isFeatured());
        response.setCategories(recruitment.getCategories());
        response.setQualifications(recruitment.getQualifications());
        response.setCreatedAt(recruitment.getCreatedAt());
        response.setUpdatedAt(recruitment.getUpdatedAt());
        response.setPublishedAt(recruitment.getPublishedAt());
        return response;
    }
}
