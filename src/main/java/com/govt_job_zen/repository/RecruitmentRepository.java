package com.govt_job_zen.repository;

import com.govt_job_zen.domain.Recruitment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    Optional<Recruitment> findBySlug(String slug);
    Optional<Recruitment> findBySlugAndPublishedTrue(String slug);
    List<Recruitment> findByPublishedTrueOrderByPublishedAtDesc(Pageable pageable);
    List<Recruitment> findByPublishedTrueAndState_SlugOrderByPublishedAtDesc(String stateSlug, Pageable pageable);
    List<Recruitment> findByPublishedTrueAndCategories_SlugOrderByPublishedAtDesc(String categorySlug, Pageable pageable);
    List<Recruitment> findByPublishedTrueAndState_SlugAndCategories_SlugOrderByPublishedAtDesc(String stateSlug, String categorySlug, Pageable pageable);
}
