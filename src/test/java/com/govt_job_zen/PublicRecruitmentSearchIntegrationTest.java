package com.govt_job_zen;

import com.govt_job_zen.controller.PublicRecruitmentController;
import com.govt_job_zen.dto.CategoryResponse;
import com.govt_job_zen.dto.RecruitmentResponse;
import com.govt_job_zen.dto.StateResponse;
import com.govt_job_zen.domain.RecruitmentStatus;
import com.govt_job_zen.service.RecruitmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PublicRecruitmentSearchIntegrationTest {

    private MockMvc mockMvc;
    private RecruitmentService recruitmentService;

    @BeforeEach
    void setUp() {
        recruitmentService = Mockito.mock(RecruitmentService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new PublicRecruitmentController(recruitmentService)).build();
    }

    @Test
    void publicCanListCategories() throws Exception {
        CategoryResponse response = new CategoryResponse();
        response.setId(1L);
        response.setName("Banking");
        response.setSlug("banking");

        when(recruitmentService.getPublicCategories()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/public/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Banking"))
                .andExpect(jsonPath("$[0].slug").value("banking"));
    }

    @Test
    void publicCanListStates() throws Exception {
        StateResponse response = new StateResponse();
        response.setId(21L);
        response.setName("Kerala");
        response.setSlug("kerala");

        when(recruitmentService.getPublicStates()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/public/states"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Kerala"))
                .andExpect(jsonPath("$[0].slug").value("kerala"));
    }

    @Test
    void publicCanFilterRecruitmentsByStateAndCategory() throws Exception {
        RecruitmentResponse response = new RecruitmentResponse();
        response.setId(101L);
        response.setTitle("SSC CGL 2026");
        response.setSlug("ssc-cgl-2026");
        response.setStatus(RecruitmentStatus.UPCOMING);
        response.setPublished(true);

        when(recruitmentService.getPublicRecruitments("kerala", "banking", 10)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/public/recruitments")
                        .param("state", "kerala")
                        .param("category", "banking")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(101))
                .andExpect(jsonPath("$[0].slug").value("ssc-cgl-2026"));
    }

    @Test
    void emptyFilterResultReturnsEmptyList() throws Exception {
        when(recruitmentService.getPublicRecruitments("unknown-state", "unknown-category", 10)).thenReturn(List.of());

        mockMvc.perform(get("/api/public/recruitments")
                        .param("state", "unknown-state")
                        .param("category", "unknown-category")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").doesNotExist());
    }
}
