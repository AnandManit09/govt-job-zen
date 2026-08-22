package com.govt_job_zen.dto;

import com.govt_job_zen.domain.Category;
import com.govt_job_zen.domain.Organization;
import com.govt_job_zen.domain.Qualification;
import com.govt_job_zen.domain.RecruitmentStatus;
import com.govt_job_zen.domain.State;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class RecruitmentResponse {

    private Long id;
    private String title;
    private String slug;
    private Organization organization;
    private State state;
    private LocalDate notificationDate;
    private LocalDate applicationStartDate;
    private LocalDate applicationEndDate;
    private Integer vacancies;
    private String qualificationDetails;
    private String ageLimit;
    private String salary;
    private String applicationFee;
    private String selectionProcess;
    private String description;
    private String officialNotificationUrl;
    private String officialWebsiteUrl;
    private String applyUrl;
    private RecruitmentStatus status;
    private boolean published;
    private boolean featured;
    private Set<Category> categories;
    private Set<Qualification> qualifications;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;

    public RecruitmentResponse() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public Organization getOrganization() { return organization; }
    public void setOrganization(Organization organization) { this.organization = organization; }

    public State getState() { return state; }
    public void setState(State state) { this.state = state; }

    public LocalDate getNotificationDate() { return notificationDate; }
    public void setNotificationDate(LocalDate notificationDate) { this.notificationDate = notificationDate; }

    public LocalDate getApplicationStartDate() { return applicationStartDate; }
    public void setApplicationStartDate(LocalDate applicationStartDate) { this.applicationStartDate = applicationStartDate; }

    public LocalDate getApplicationEndDate() { return applicationEndDate; }
    public void setApplicationEndDate(LocalDate applicationEndDate) { this.applicationEndDate = applicationEndDate; }

    public Integer getVacancies() { return vacancies; }
    public void setVacancies(Integer vacancies) { this.vacancies = vacancies; }

    public String getQualificationDetails() { return qualificationDetails; }
    public void setQualificationDetails(String qualificationDetails) { this.qualificationDetails = qualificationDetails; }

    public String getAgeLimit() { return ageLimit; }
    public void setAgeLimit(String ageLimit) { this.ageLimit = ageLimit; }

    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }

    public String getApplicationFee() { return applicationFee; }
    public void setApplicationFee(String applicationFee) { this.applicationFee = applicationFee; }

    public String getSelectionProcess() { return selectionProcess; }
    public void setSelectionProcess(String selectionProcess) { this.selectionProcess = selectionProcess; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getOfficialNotificationUrl() { return officialNotificationUrl; }
    public void setOfficialNotificationUrl(String officialNotificationUrl) { this.officialNotificationUrl = officialNotificationUrl; }

    public String getOfficialWebsiteUrl() { return officialWebsiteUrl; }
    public void setOfficialWebsiteUrl(String officialWebsiteUrl) { this.officialWebsiteUrl = officialWebsiteUrl; }

    public String getApplyUrl() { return applyUrl; }
    public void setApplyUrl(String applyUrl) { this.applyUrl = applyUrl; }

    public RecruitmentStatus getStatus() { return status; }
    public void setStatus(RecruitmentStatus status) { this.status = status; }

    public boolean isPublished() { return published; }
    public void setPublished(boolean published) { this.published = published; }

    public boolean isFeatured() { return featured; }
    public void setFeatured(boolean featured) { this.featured = featured; }

    public Set<Category> getCategories() { return categories; }
    public void setCategories(Set<Category> categories) { this.categories = categories; }

    public Set<Qualification> getQualifications() { return qualifications; }
    public void setQualifications(Set<Qualification> qualifications) { this.qualifications = qualifications; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }
}
