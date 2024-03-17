package com.ssafy.kdkd.repository.job;

import com.ssafy.kdkd.domain.entity.job.JobReservation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobReservationRepository extends JpaRepository<JobReservation, Long> {

}
