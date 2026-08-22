package com.govt_job_zen.repository;

import com.govt_job_zen.domain.Recruitment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    Optional<Recruitment> findBySlug(String slug);
    List<Recruitment> findByPublishedTrueOrderByPublishedAtDesc(Pageable pageable);
}
