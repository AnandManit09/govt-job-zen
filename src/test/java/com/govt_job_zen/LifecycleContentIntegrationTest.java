package com.govt_job_zen;

import com.govt_job_zen.controller.LifecycleContentController;
import com.govt_job_zen.dto.RecruitmentAdmitCardResponse;
import com.govt_job_zen.dto.RecruitmentAnswerKeyResponse;
import com.govt_job_zen.dto.RecruitmentResultResponse;
import com.govt_job_zen.service.LifecycleContentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LifecycleContentIntegrationTest {

    private MockMvc mockMvc;
    private LifecycleContentService lifecycleContentService;

    @BeforeEach
    void setUp() {
        lifecycleContentService = Mockito.mock(LifecycleContentService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new LifecycleContentController(lifecycleContentService)).build();
    }

    @Test
    void publicCanFetchPublishedResultsForRecruitment() throws Exception {
        RecruitmentResultResponse result = new RecruitmentResultResponse();
        result.setId(11L);
        result.setTitle("SSC CGL 2026 Result");
        result.setSlug("ssc-cgl-2026-result");
        result.setPublished(true);

        when(lifecycleContentService.getPublishedResultsForRecruitmentSlug("ssc-cgl-2026")).thenReturn(List.of(result));

        mockMvc.perform(get("/api/public/recruitments/{slug}/results", "ssc-cgl-2026"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("SSC CGL 2026 Result"))
                .andExpect(jsonPath("$[0].slug").value("ssc-cgl-2026-result"));
    }

    @Test
    void publicCannotFetchUnpublishedLifecycleRecords() throws Exception {
        when(lifecycleContentService.getPublishedAnswerKeysForRecruitmentSlug("ssc-cgl-2026"))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "No published answer keys found for slug: ssc-cgl-2026"));

        mockMvc.perform(get("/api/public/recruitments/{slug}/answer-keys", "ssc-cgl-2026"))
                .andExpect(status().isNotFound());
    }

    @Test
    void publicCanFetchPublishedAdmitCards() throws Exception {
        RecruitmentAdmitCardResponse admitCard = new RecruitmentAdmitCardResponse();
        admitCard.setId(21L);
        admitCard.setTitle("SSC CGL 2026 Admit Card");
        admitCard.setSlug("ssc-cgl-2026-admit-card");
        admitCard.setPublished(true);

        when(lifecycleContentService.getPublishedAdmitCardsForRecruitmentSlug("ssc-cgl-2026")).thenReturn(List.of(admitCard));

        mockMvc.perform(get("/api/public/recruitments/{slug}/admit-cards", "ssc-cgl-2026"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("SSC CGL 2026 Admit Card"));
    }
}
