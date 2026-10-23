package com.govt_job_zen.repository;

import com.govt_job_zen.domain.RecruitmentResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitmentResultRepository extends JpaRepository<RecruitmentResult, Long> {
    List<RecruitmentResult> findByRecruitment_SlugAndPublishedTrueOrderByPublishedAtDesc(String recruitmentSlug);
    List<RecruitmentResult> findByRecruitment_IdAndPublishedTrueOrderByPublishedAtDesc(Long recruitmentId);
}
