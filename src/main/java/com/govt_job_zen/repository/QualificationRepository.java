package com.govt_job_zen.repository;

import com.govt_job_zen.domain.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QualificationRepository extends JpaRepository<Qualification, Long> {
    Optional<Qualification> findBySlug(String slug);
    Optional<Qualification> findByNameIgnoreCase(String name);
}
