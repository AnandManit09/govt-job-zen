package com.govt_job_zen.repository;

import com.govt_job_zen.domain.RecruitmentAdmitCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitmentAdmitCardRepository extends JpaRepository<RecruitmentAdmitCard, Long> {
    List<RecruitmentAdmitCard> findByRecruitment_SlugAndPublishedTrueOrderByPublishedAtDesc(String recruitmentSlug);
    List<RecruitmentAdmitCard> findByRecruitment_IdAndPublishedTrueOrderByPublishedAtDesc(Long recruitmentId);
}
