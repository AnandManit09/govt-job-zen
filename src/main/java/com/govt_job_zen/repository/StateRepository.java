package com.govt_job_zen.repository;

import com.govt_job_zen.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findBySlug(String slug);
    Optional<State> findByNameIgnoreCase(String name);
}
