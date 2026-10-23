package com.govt_job_zen.repository;

import com.govt_job_zen.domain.RecruitmentAnswerKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitmentAnswerKeyRepository extends JpaRepository<RecruitmentAnswerKey, Long> {
    List<RecruitmentAnswerKey> findByRecruitment_SlugAndPublishedTrueOrderByPublishedAtDesc(String recruitmentSlug);
    List<RecruitmentAnswerKey> findByRecruitment_IdAndPublishedTrueOrderByPublishedAtDesc(Long recruitmentId);
}
