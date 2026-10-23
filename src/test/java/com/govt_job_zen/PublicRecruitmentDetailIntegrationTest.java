package com.govt_job_zen;

import com.govt_job_zen.controller.PublicRecruitmentController;
import com.govt_job_zen.dto.RecruitmentResponse;
import com.govt_job_zen.domain.RecruitmentStatus;
import com.govt_job_zen.service.RecruitmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PublicRecruitmentDetailIntegrationTest {

    private MockMvc mockMvc;
    private RecruitmentService recruitmentService;

    @BeforeEach
    void setUp() {
        recruitmentService = Mockito.mock(RecruitmentService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new PublicRecruitmentController(recruitmentService)).build();
    }

    @Test
    void publicCanFetchPublishedRecruitmentBySlug() throws Exception {
        RecruitmentResponse response = new RecruitmentResponse();
        response.setId(7L);
        response.setTitle("SSC CGL 2026");
        response.setSlug("ssc-cgl-2026");
        response.setStatus(RecruitmentStatus.UPCOMING);
        response.setPublished(true);

        when(recruitmentService.getPublicRecruitmentBySlug("ssc-cgl-2026")).thenReturn(response);

        mockMvc.perform(get("/api/public/recruitments/{slug}", "ssc-cgl-2026"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.slug").value("ssc-cgl-2026"))
                .andExpect(jsonPath("$.title").value("SSC CGL 2026"));
    }

    @Test
    void publicGetByUnknownSlugReturnsNotFound() throws Exception {
        when(recruitmentService.getPublicRecruitmentBySlug("missing-slug"))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Recruitment not found"));

        mockMvc.perform(get("/api/public/recruitments/{slug}", "missing-slug"))
                .andExpect(status().isNotFound());
    }
}
