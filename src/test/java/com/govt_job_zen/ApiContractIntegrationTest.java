package com.govt_job_zen;

import com.govt_job_zen.api.ApiExceptionHandler;
import com.govt_job_zen.controller.PublicRecruitmentController;
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

class ApiContractIntegrationTest {

    private MockMvc mockMvc;
    private RecruitmentService recruitmentService;

    @BeforeEach
    void setUp() {
        recruitmentService = Mockito.mock(RecruitmentService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new PublicRecruitmentController(recruitmentService))
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @Test
    void publicApiReturnsStandardErrorContractForNotFound() throws Exception {
        when(recruitmentService.getPublicRecruitmentBySlug("missing-slug"))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Recruitment not found for slug: missing-slug"));

        mockMvc.perform(get("/api/public/recruitments/{slug}", "missing-slug"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Recruitment not found for slug: missing-slug"))
                .andExpect(jsonPath("$.path").value("/api/public/recruitments/missing-slug"));
    }
}
