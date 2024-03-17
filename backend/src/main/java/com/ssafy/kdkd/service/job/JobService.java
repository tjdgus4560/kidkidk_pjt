package com.ssafy.kdkd.service.job;

import com.ssafy.kdkd.domain.dto.deposit.DepositDto;
import com.ssafy.kdkd.domain.entity.deposit.Deposit;
import com.ssafy.kdkd.domain.entity.job.Job;
import com.ssafy.kdkd.domain.entity.job.JobInfo;
import com.ssafy.kdkd.domain.entity.job.JobReservation;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.deposit.DepositRepository;
import com.ssafy.kdkd.repository.job.JobRepository;
import com.ssafy.kdkd.repository.job.JobReservationRepository;
import com.ssafy.kdkd.service.user.ChildService;

import static com.ssafy.kdkd.domain.entity.deposit.Deposit.createDeposit;
import static com.ssafy.kdkd.domain.entity.job.Job.createJob;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class JobService {

    private final JobRepository jobRepository;
    private final JobReservationRepository jobReservationRepository;
    private final ChildService childService;
    private final DepositRepository depositRepository;

    /**
     * 직업예약 -> 직업 생성
     * 
     * @param jobReservation 직업예약
     */
    @Transactional
    public void insertJob(JobReservation jobReservation) {
        Long childId = jobReservation.getId();
        Optional<Child> findChild = childService.findChild(childId);
        boolean isExist = jobRepository.existsById(childId);

        if (findChild.isEmpty() || isExist) {
            log.info("직업 생성 실패");
            return;
        }

        Child child = findChild.get();
        Job job = createJob(jobReservation);
        job.setChild(child);
        jobRepository.save(job);
    }

    /**
     * 직업예약 -> 직업 업데이트(수정, 삭제)
     *
     * @param jobReservation 직업예약 정보
     * @return JobDto 생성된 직업
     */
    @Transactional
    public boolean updateJob(JobReservation jobReservation) {
        Long childId = jobReservation.getId();
        Optional<Child> findChild = childService.findChild(childId);

        if (findChild.isEmpty()) {
            log.info("직업 업데이트 실패");
            return false;
        }

        Child child = findChild.get();
        Optional<Job> findJob = jobRepository.findById(childId);

        if (findJob.isEmpty()) {
            log.info("직업 업데이트 실패");
            return false;
        }

        boolean isUpdate = jobReservation.isState();

        if (isUpdate) {
            Job job = findJob.get();
            job.updateJob(jobReservation);
            job.setChild(child);
            jobRepository.save(job);
        } else {
            jobRepository.deleteById(childId);
        }
        jobReservationRepository.deleteById(childId);

        return true;
    }

    /**
     * 직업 스케줄러
     *
     * 상세:
     * 1. 급여 지급
     * 2. 직업예약 정보 반영
     */
    @Transactional
    public void updateJob() {
        List<Job> jobList = jobRepository.findAll();

        for (Job job : jobList) {
            Long childId = job.getId();
            Child child = childService.findChild(childId).get();
            JobInfo jobInfo = job.getJobInfo();
            int doneCount = job.getDoneCount();
            int taskAmount = jobInfo.getTaskAmount();
            boolean isDone = doneCount >= taskAmount;
            int wage = jobInfo.getWage();
            int updateCoin = child.getCoin() + wage;
            
            if (isDone) {
                child.updateChild(updateCoin);
                
                String detail = "급여 입금";
                boolean type = true;
                DepositDto depositDto = new DepositDto(LocalDateTime.now(), detail, type, wage, updateCoin, childId);
                Deposit deposit = createDeposit(depositDto);
                deposit.setChild(child);
                depositRepository.save(deposit);
            }

            Optional<JobReservation> jobReservation = jobReservationRepository.findById(childId);
            if (jobReservation.isPresent()) {
                updateJob(jobReservation.get());
            } else {
                job.updateJob(0);
                jobRepository.save(job);
            }
        }
    }

}
