package com.govt_job_zen.dto;

import com.govt_job_zen.domain.RecruitmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public class RecruitmentRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String slug;

    @NotBlank
    private String description;

    @NotBlank
    private String organizationName;

    private String stateName;

    private LocalDate notificationDate;
    private LocalDate applicationStartDate;
    private LocalDate applicationEndDate;
    private Integer vacancies;
    private String qualificationDetails;
    private String ageLimit;
    private String salary;
    private String applicationFee;
    private String selectionProcess;
    private String officialNotificationUrl;
    private String officialWebsiteUrl;
    private String applyUrl;

    @NotNull
    private RecruitmentStatus status = RecruitmentStatus.UPCOMING;

    private boolean published = false;
    private boolean featured = false;
    private Set<String> categoryNames = Set.of();
    private Set<String> qualificationNames = Set.of();

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getOrganizationName() { return organizationName; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }

    public String getStateName() { return stateName; }
    public void setStateName(String stateName) { this.stateName = stateName; }

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

    public Set<String> getCategoryNames() { return categoryNames; }
    public void setCategoryNames(Set<String> categoryNames) { this.categoryNames = categoryNames; }

    public Set<String> getQualificationNames() { return qualificationNames; }
    public void setQualificationNames(Set<String> qualificationNames) { this.qualificationNames = qualificationNames; }
}
