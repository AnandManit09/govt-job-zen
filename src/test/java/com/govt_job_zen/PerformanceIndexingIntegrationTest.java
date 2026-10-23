package com.govt_job_zen;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PerformanceIndexingIntegrationTest {

    @Test
    void migrationIncludesIndexesForCriticalQueryPatterns() throws IOException {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("db/migration/V3__add_performance_indexes.sql")) {
            assertNotNull(stream, "Migration V3 should exist");
            String sql = new String(stream.readAllBytes(), StandardCharsets.UTF_8);

            assertTrue(sql.contains("idx_recruitments_published_published_at"));
            assertTrue(sql.contains("idx_recruitments_published_state_published_at"));
            assertTrue(sql.contains("idx_recruitment_categories_category_id"));
            assertTrue(sql.contains("idx_recruitment_qualifications_qualification_id"));
            assertTrue(sql.contains("idx_recruitment_results_published_published_at"));
        }
    }
}
