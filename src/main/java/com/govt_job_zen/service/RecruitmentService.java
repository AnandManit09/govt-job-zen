package com.govt_job_zen.service;

import com.govt_job_zen.domain.*;
import com.govt_job_zen.dto.CategoryResponse;
import com.govt_job_zen.dto.RecruitmentRequest;
import com.govt_job_zen.dto.RecruitmentResponse;
import com.govt_job_zen.dto.StateResponse;
import com.govt_job_zen.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    public RecruitmentResponse getRecruitmentById(Long id) {
        Recruitment recruitment = recruitmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recruitment not found with id: " + id));
        return toResponse(recruitment);
    }

    @Transactional(readOnly = true)
    public List<RecruitmentResponse> getRecruitments(int page, int size) {
        return recruitmentRepository.findAll(PageRequest.of(page, size)).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RecruitmentResponse getPublicRecruitmentBySlug(String slug) {
        Recruitment recruitment = recruitmentRepository.findBySlugAndPublishedTrue(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recruitment not found for slug: " + slug));
        return toResponse(recruitment);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getPublicCategories() {
        return categoryRepository.findAll(Sort.by("name").ascending()).stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StateResponse> getPublicStates() {
        return stateRepository.findAll(Sort.by("name").ascending()).stream()
                .map(this::toStateResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecruitmentResponse> getPublicRecruitments(String state, String category, int limit) {
        int normalizedLimit = Math.max(limit, 1);
        String normalizedState = state == null ? null : state.trim();
        String normalizedCategory = category == null ? null : category.trim();

        if ((normalizedState == null || normalizedState.isBlank()) && (normalizedCategory == null || normalizedCategory.isBlank())) {
            return getLatestRecruitments(normalizedLimit);
        }

        if (normalizedState != null && !normalizedState.isBlank() && normalizedCategory != null && !normalizedCategory.isBlank()) {
            return recruitmentRepository.findByPublishedTrueAndState_SlugAndCategories_SlugOrderByPublishedAtDesc(
                    normalizedState,
                    normalizedCategory,
                    PageRequest.of(0, normalizedLimit)
            ).stream().map(this::toResponse).collect(Collectors.toList());
        }

        if (normalizedCategory != null && !normalizedCategory.isBlank()) {
            return recruitmentRepository.findByPublishedTrueAndCategories_SlugOrderByPublishedAtDesc(
                    normalizedCategory,
                    PageRequest.of(0, normalizedLimit)
            ).stream().map(this::toResponse).collect(Collectors.toList());
        }

        return recruitmentRepository.findByPublishedTrueAndState_SlugOrderByPublishedAtDesc(
                normalizedState,
                PageRequest.of(0, normalizedLimit)
        ).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecruitmentResponse> getLatestRecruitments(int limit) {
        int normalizedLimit = Math.max(limit, 1);
        return recruitmentRepository.findByPublishedTrueOrderByPublishedAtDesc(PageRequest.of(0, normalizedLimit)).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public RecruitmentResponse updateRecruitment(Long id, RecruitmentRequest request) {
        Recruitment existing = recruitmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recruitment not found with id: " + id));

        String normalizedSlug = slugify(request.getSlug() != null && !request.getSlug().isBlank()
                ? request.getSlug()
                : existing.getSlug());

        recruitmentRepository.findBySlug(normalizedSlug)
                .filter(candidate -> !candidate.getId().equals(id))
                .ifPresent(candidate -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Recruitment slug already exists: " + normalizedSlug);
                });

        Organization organization = organizationRepository.findByNameIgnoreCase(request.getOrganizationName())
                .orElseGet(() -> organizationRepository.save(new Organization(request.getOrganizationName(), slugify(request.getOrganizationName()))));

        State state = null;
        if (request.getStateName() != null && !request.getStateName().isBlank()) {
            state = stateRepository.findByNameIgnoreCase(request.getStateName())
                    .orElseGet(() -> stateRepository.save(new State(request.getStateName(), slugify(request.getStateName()))));
        }

        existing.setTitle(request.getTitle());
        existing.setSlug(normalizedSlug);
        existing.setOrganization(organization);
        existing.setState(state);
        existing.setDescription(request.getDescription());
        existing.setNotificationDate(request.getNotificationDate());
        existing.setApplicationStartDate(request.getApplicationStartDate());
        existing.setApplicationEndDate(request.getApplicationEndDate());
        existing.setVacancies(request.getVacancies());
        existing.setQualificationDetails(request.getQualificationDetails());
        existing.setAgeLimit(request.getAgeLimit());
        existing.setSalary(request.getSalary());
        existing.setApplicationFee(request.getApplicationFee());
        existing.setSelectionProcess(request.getSelectionProcess());
        existing.setOfficialNotificationUrl(request.getOfficialNotificationUrl());
        existing.setOfficialWebsiteUrl(request.getOfficialWebsiteUrl());
        existing.setApplyUrl(request.getApplyUrl());
        existing.setStatus(request.getStatus() == null ? RecruitmentStatus.UPCOMING : request.getStatus());
        existing.setPublished(request.isPublished());
        existing.setFeatured(request.isFeatured());
        existing.setCategories(resolveCategories(request.getCategoryNames()));
        existing.setQualifications(resolveQualifications(request.getQualificationNames()));

        if (request.isPublished() && existing.getPublishedAt() == null) {
            existing.setPublishedAt(LocalDateTime.now());
        }
        if (!request.isPublished()) {
            existing.setPublishedAt(null);
        }

        Recruitment saved = recruitmentRepository.save(existing);
        return toResponse(saved);
    }

    @Transactional
    public void deleteRecruitment(Long id) {
        Recruitment recruitment = recruitmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recruitment not found with id: " + id));
        recruitmentRepository.delete(recruitment);
    }

    private Set<Category> resolveCategories(Set<String> categoryNames) {
        if (categoryNames == null || categoryNames.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Category> categories = new HashSet<>();
        for (String categoryName : categoryNames) {
            Category category = resolveCategory(categoryName);
            if (category != null) {
                categories.add(category);
            }
        }
        return categories;
    }

    private Set<Qualification> resolveQualifications(Set<String> qualificationNames) {
        if (qualificationNames == null || qualificationNames.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Qualification> qualifications = new HashSet<>();
        for (String qualificationName : qualificationNames) {
            Qualification qualification = resolveQualification(qualificationName);
            if (qualification != null) {
                qualifications.add(qualification);
            }
        }
        return qualifications;
    }

    private Category resolveCategory(String categoryName) {
        if (categoryName == null || categoryName.isBlank()) {
            return null;
        }

        String cleaned = categoryName.trim();
        String slug = slugify(cleaned);
        String displayName = displayNameFromInput(cleaned);

        return categoryRepository.findBySlug(slug)
                .or(() -> categoryRepository.findByNameIgnoreCase(cleaned))
                .or(() -> categoryRepository.findByNameIgnoreCase(displayName))
                .orElseGet(() -> categoryRepository.save(new Category(displayName, slug)));
    }

    private Qualification resolveQualification(String qualificationName) {
        if (qualificationName == null || qualificationName.isBlank()) {
            return null;
        }

        String cleaned = qualificationName.trim();
        String slug = slugify(cleaned);
        String displayName = displayNameFromInput(cleaned);

        return qualificationRepository.findBySlug(slug)
                .or(() -> qualificationRepository.findByNameIgnoreCase(cleaned))
                .or(() -> qualificationRepository.findByNameIgnoreCase(displayName))
                .orElseGet(() -> qualificationRepository.save(new Qualification(displayName, slug)));
    }

    private String displayNameFromInput(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }

        String normalized = value.trim();
        if (normalized.contains("-")) {
            return Arrays.stream(normalized.split("-"))
                    .filter(part -> !part.isBlank())
                    .map(part -> part.substring(0, 1).toUpperCase(Locale.ROOT) + part.substring(1).toLowerCase(Locale.ROOT))
                    .collect(Collectors.joining(" "));
        }

        return normalized.substring(0, 1).toUpperCase(Locale.ROOT) + normalized.substring(1);
    }

    private String slugify(String value) {
        if (value == null || value.isBlank()) {
            return UUID.randomUUID().toString();
        }
        return value.trim().toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }

    private CategoryResponse toCategoryResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setSlug(category.getSlug());
        return response;
    }

    private StateResponse toStateResponse(State state) {
        StateResponse response = new StateResponse();
        response.setId(state.getId());
        response.setName(state.getName());
        response.setSlug(state.getSlug());
        return response;
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
