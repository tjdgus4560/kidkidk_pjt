package com.ssafy.kdkd.repository.job;

import com.ssafy.kdkd.domain.entity.job.Job;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
