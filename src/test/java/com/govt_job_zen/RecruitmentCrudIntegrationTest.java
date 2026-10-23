package com.govt_job_zen;

import com.govt_job_zen.controller.AdminRecruitmentController;
import com.govt_job_zen.dto.RecruitmentRequest;
import com.govt_job_zen.dto.RecruitmentResponse;
import com.govt_job_zen.domain.RecruitmentStatus;
import com.govt_job_zen.service.RecruitmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecruitmentCrudIntegrationTest {

    private MockMvc mockMvc;
    private RecruitmentService recruitmentService;

    @BeforeEach
    void setUp() {
        recruitmentService = Mockito.mock(RecruitmentService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new AdminRecruitmentController(recruitmentService)).build();
    }

    @Test
    void adminCanCreateRecruitment() throws Exception {
        RecruitmentRequest request = new RecruitmentRequest();
        request.setTitle("SSC CGL 2026");
        request.setSlug("ssc-cgl-2026");
        request.setDescription("Large government recruitment exam");
        request.setOrganizationName("SSC");
        request.setStateName("All India");
        request.setNotificationDate(LocalDate.of(2026, 1, 15));
        request.setApplicationStartDate(LocalDate.of(2026, 2, 1));
        request.setApplicationEndDate(LocalDate.of(2026, 2, 20));
        request.setVacancies(5000);
        request.setQualificationDetails("Bachelor's degree");
        request.setAgeLimit("18-32 years");
        request.setSalary("₹ 52,000 - ₹ 1,10,000");
        request.setApplicationFee("₹ 100");
        request.setSelectionProcess("Tier 1, Tier 2, Document Verification");
        request.setOfficialNotificationUrl("https://ssc.nic.in/notification");
        request.setOfficialWebsiteUrl("https://ssc.nic.in");
        request.setApplyUrl("https://ssc.nic.in/apply");
        request.setStatus(RecruitmentStatus.UPCOMING);
        request.setPublished(true);
        request.setFeatured(true);
        request.setCategoryNames(Set.of("Central Government"));
        request.setQualificationNames(Set.of("Graduate"));

        RecruitmentResponse response = new RecruitmentResponse();
        response.setId(101L);
        response.setTitle("SSC CGL 2026");
        response.setSlug("ssc-cgl-2026");
        response.setDescription("Large government recruitment exam");
        response.setStatus(RecruitmentStatus.UPCOMING);
        response.setPublished(true);

        when(recruitmentService.createRecruitment(any(RecruitmentRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/admin/recruitments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"SSC CGL 2026\",\"slug\":\"ssc-cgl-2026\",\"description\":\"Large government recruitment exam\",\"organizationName\":\"SSC\",\"stateName\":\"All India\",\"notificationDate\":\"2026-01-15\",\"applicationStartDate\":\"2026-02-01\",\"applicationEndDate\":\"2026-02-20\",\"vacancies\":5000,\"qualificationDetails\":\"Bachelor's degree\",\"ageLimit\":\"18-32 years\",\"salary\":\"₹ 52,000 - ₹ 1,10,000\",\"applicationFee\":\"₹ 100\",\"selectionProcess\":\"Tier 1, Tier 2, Document Verification\",\"officialNotificationUrl\":\"https://ssc.nic.in/notification\",\"officialWebsiteUrl\":\"https://ssc.nic.in\",\"applyUrl\":\"https://ssc.nic.in/apply\",\"status\":\"UPCOMING\",\"published\":true,\"featured\":true,\"categoryNames\":[\"Central Government\"],\"qualificationNames\":[\"Graduate\"]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.slug").value("ssc-cgl-2026"));
    }

    @Test
    void adminCanGetSingleRecruitment() throws Exception {
        RecruitmentResponse response = new RecruitmentResponse();
        response.setId(202L);
        response.setTitle("UPSC NDA 2027");
        response.setSlug("upsc-nda-2027");
        response.setStatus(RecruitmentStatus.ACTIVE);

        when(recruitmentService.getRecruitmentById(202L)).thenReturn(response);

        mockMvc.perform(get("/api/admin/recruitments/{id}", 202L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(202))
                .andExpect(jsonPath("$.title").value("UPSC NDA 2027"));
    }

    @Test
    void adminCanListRecruitments() throws Exception {
        RecruitmentResponse response = new RecruitmentResponse();
        response.setId(303L);
        response.setTitle("RRB NTPC 2027");
        response.setSlug("rrb-ntpc-2027");

        when(recruitmentService.getRecruitments(0, 20)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/admin/recruitments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(303))
                .andExpect(jsonPath("$[0].slug").value("rrb-ntpc-2027"));
    }

    @Test
    void adminCanUpdateRecruitment() throws Exception {
        RecruitmentRequest request = new RecruitmentRequest();
        request.setTitle("SSC CGL 2027");
        request.setSlug("ssc-cgl-2027");
        request.setDescription("Updated description");
        request.setOrganizationName("SSC");
        request.setStatus(RecruitmentStatus.UPCOMING);

        RecruitmentResponse response = new RecruitmentResponse();
        response.setId(404L);
        response.setTitle("SSC CGL 2027");
        response.setSlug("ssc-cgl-2027");

        when(recruitmentService.updateRecruitment(eq(404L), any(RecruitmentRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/admin/recruitments/{id}", 404L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"SSC CGL 2027\",\"slug\":\"ssc-cgl-2027\",\"description\":\"Updated description\",\"organizationName\":\"SSC\",\"status\":\"UPCOMING\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("SSC CGL 2027"))
                .andExpect(jsonPath("$.slug").value("ssc-cgl-2027"));
    }

    @Test
    void adminCanDeleteRecruitment() throws Exception {
        Mockito.doNothing().when(recruitmentService).deleteRecruitment(505L);

        mockMvc.perform(delete("/api/admin/recruitments/{id}", 505L))
                .andExpect(status().isNoContent());
    }
}
