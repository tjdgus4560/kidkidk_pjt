package com.ssafy.kdkd.service.job;

import com.ssafy.kdkd.domain.dto.job.JobReservationDto;
import com.ssafy.kdkd.domain.entity.job.Job;
import com.ssafy.kdkd.domain.entity.job.JobInfo;
import com.ssafy.kdkd.domain.entity.job.JobReservation;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.job.JobRepository;
import com.ssafy.kdkd.repository.job.JobReservationRepository;
import com.ssafy.kdkd.service.user.ChildService;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class JobReservationService {

    private final JobReservationRepository jobReservationRepository;
    private final JobService jobService;
    private final JobRepository jobRepository;
    private final ChildService childService;

    /**
     * 직업예약 생성
     *
     * @param jobReservationDto 직업예약 정보
     * @param type 직업생성/직업예약생성 확인
     * @return JobReservationDto 생성된 직업예약
     */
    @Transactional
    public JobReservationDto createJobReservation(JobReservationDto jobReservationDto, boolean type) {
        Long childId = jobReservationDto.getChildId();
        Optional<Child> findChild = childService.findChild(childId);

        if (findChild.isEmpty()) {
            log.info("직업 생성 등록 실패");
            return null;
        }

        if (type && jobRepository.existsById(childId) && jobReservationRepository.existsById(childId)) {
            log.info("직업 생성 등록 실패");
            return null;
        }

        Child child = findChild.get();
        jobReservationDto.setState(true);
        JobReservation reservation = JobReservation.createJobReservation(jobReservationDto);
        reservation.setChild(child);
        jobReservationRepository.save(reservation);
        if (type) {
            // 직업 처음 생성 -> 바로 생성
            jobService.insertJob(reservation);
        }
        return jobReservationDto;
    }

    /**
     * 직업예약 수정
     *
     * @param jobReservationDto
     * @return JobReservationDto 수정된 직업예약
     */
    @Transactional
    public JobReservationDto modifyJobReservation(JobReservationDto jobReservationDto) {
        Long childId = jobReservationDto.getChildId();
        Optional<JobReservation> existingJobReservation = jobReservationRepository.findById(childId);

        if (existingJobReservation.isEmpty()) {
            log.info("직업예약 수정 실패");
            return null;
        }

        JobReservation jobReservation = existingJobReservation.get();
        jobReservation.updateJobReservation(jobReservationDto);
        jobReservationRepository.save(jobReservation);
        return jobReservationDto;
    }

    /**
     * 직업 삭제(예약)
     *
     * @param childId 자식 아이디
     * @return JobReservationDto 직업예약(삭제 예약된 상태 = state가 false)
     */
    @Transactional
    public JobReservationDto deleteJob(Long childId) {
        Optional<Child> findChild = childService.findChild(childId);

        if (findChild.isEmpty()) {
            return null;
        }

        Optional<Job> job = jobRepository.findById(childId);

        if (job.isEmpty()) {
            return null;
        }

        Child child = findChild.get();
        Job existingJob = job.get();
        JobInfo jobInfo = existingJob.getJobInfo();
        JobReservationDto jobReservationDto =
            new JobReservationDto(
                jobInfo.getName(),
                jobInfo.getWage(),
                jobInfo.getTask(),
                jobInfo.getTaskAmount(),
                false,
                childId
            );
        Optional<JobReservation> findJobReservation = jobReservationRepository.findById(childId);

        if (findJobReservation.isEmpty()){
            JobReservation jobReservation = JobReservation.createJobReservation(jobReservationDto);
            jobReservation.setChild(child);
            jobReservationRepository.save(jobReservation);
        } else {
            JobReservation jobReservation = findJobReservation.get();
            jobReservation.updateJobReservation(jobReservationDto);
        }

        return jobReservationDto;
    }

    /**
     * 직업예약 삭제
     *
     * @param childId 자식 아이디
     * @return boolean 비어있는지 확인(true = 직업예약 X)
     */
    @Transactional
    public boolean delete(Long childId) {

        if (jobReservationRepository.findById(childId).isEmpty()) {
            return true;
        }

        jobReservationRepository.deleteById(childId);
        return false;
    }



}
