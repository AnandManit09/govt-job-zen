package com.govt_job_zen;

import com.govt_job_zen.domain.RecruitmentStatus;
import com.govt_job_zen.dto.RecruitmentRequest;
import com.govt_job_zen.dto.RecruitmentResponse;
import com.govt_job_zen.repository.RecruitmentRepository;
import com.govt_job_zen.service.RecruitmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RecruitmentApiIntegrationTest {

    @Autowired
    private RecruitmentService recruitmentService;

    @Autowired
    private RecruitmentRepository recruitmentRepository;

    @Test
    void adminCanCreateRecruitment() {
        RecruitmentRequest request = new RecruitmentRequest();
        request.setTitle("SSC CGL 2026");
        request.setSlug("ssc-cgl-2026");
        request.setDescription("Staff Selection Commission Combined Graduate Level recruitment.");
        request.setOrganizationName("SSC");
        request.setStateName("All India");
        request.setStatus(RecruitmentStatus.UPCOMING);
        request.setNotificationDate(LocalDate.of(2026, 1, 15));
        request.setApplicationStartDate(LocalDate.of(2026, 2, 1));
        request.setApplicationEndDate(LocalDate.of(2026, 2, 28));
        request.setVacancies(1200);
        request.setQualificationDetails("Graduate");
        request.setAgeLimit("18-32 years");
        request.setSalary("Level-7 pay matrix");
        request.setApplicationFee("Rs. 100");
        request.setSelectionProcess("Tier I, Tier II, Document Verification");
        request.setOfficialNotificationUrl("https://example.com/ssc-cgl-notification");
        request.setOfficialWebsiteUrl("https://ssc.nic.in");
        request.setApplyUrl("https://example.com/apply");
        request.setPublished(true);
        request.setFeatured(true);
        request.setCategoryNames(Set.of("banking", "central-government"));
        request.setQualificationNames(Set.of("graduate"));

        RecruitmentResponse response = recruitmentService.createRecruitment(request);

        assertNotNull(response);
        assertEquals("SSC CGL 2026", response.getTitle());
        assertEquals("ssc-cgl-2026", response.getSlug());
        assertNotNull(response.getOrganization());
        assertEquals("SSC", response.getOrganization().getName());
        assertFalse(response.getCategories().isEmpty());
        assertFalse(response.getQualifications().isEmpty());
        assertTrue(recruitmentRepository.findBySlug("ssc-cgl-2026").isPresent());
    }

    @Test
    void publicLatestJobsAreAvailable() {
        RecruitmentRequest request = new RecruitmentRequest();
        request.setTitle("UPSC Civil Services 2026");
        request.setSlug("upsc-civil-services-2026");
        request.setDescription("Civil services recruitment");
        request.setOrganizationName("UPSC");
        request.setStatus(RecruitmentStatus.ACTIVE);
        request.setPublished(true);
        request.setCategoryNames(Set.of("central-government"));
        request.setQualificationNames(Set.of("graduate"));

        recruitmentService.createRecruitment(request);

        assertFalse(recruitmentService.getLatestRecruitments(10).isEmpty());
    }
}
