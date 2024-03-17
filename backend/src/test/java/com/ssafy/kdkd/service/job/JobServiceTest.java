package com.ssafy.kdkd.service.job;

import com.ssafy.kdkd.domain.dto.job.JobReservationDto;
import com.ssafy.kdkd.domain.entity.job.Job;
import com.ssafy.kdkd.domain.entity.job.JobInfo;
import com.ssafy.kdkd.domain.entity.job.JobReservation;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.job.JobRepository;
import com.ssafy.kdkd.repository.job.JobReservationRepository;
import com.ssafy.kdkd.repository.user.ChildRepository;
import com.ssafy.kdkd.service.user.ChildService;

import static org.junit.Assert.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(readOnly = true)
public class JobServiceTest {

    @Autowired JobService jobService;
    @Autowired JobRepository jobRepository;
    @Autowired JobReservationService jobReservationService;
    @Autowired JobReservationRepository jobReservationRepository;
    @Autowired ChildService childService;
    @Autowired ChildRepository childRepository;
    @Autowired EntityManager em;

    @Test
    @DisplayName("직업 생성")
    @Transactional
    @Rollback(value = false)
    public void 직업_생성() throws Exception {
        //given
        Long childId = 2L;
        Child child = childService.findChild(childId).get();

        //when
        jobService.insertJob(jobReservationRepository.findById(childId).get());

        //then
        boolean result = jobRepository.existsById(childId);

        assertTrue("직업이 생성되어야 합니다.", result);
    }

    @Test
    @DisplayName("직업 조회")
    public void 직업_조회() throws Exception {
        //given
        Long childId = 2L;

        //when
        Job findJob = jobRepository.findById(childId).get();
        JobInfo jobInfo = findJob.getJobInfo();
        //then
        System.out.println("===== 직업 정보 출력 =====");
        System.out.println("job_id: " + findJob.getId() +
            " name: " + jobInfo.getName() +
            " wage: " + jobInfo.getWage() +
            " task: " + jobInfo.getTask() +
            " taskAmount: " + jobInfo.getTaskAmount() +
            " done_count: " + findJob.getDoneCount());
        System.out.println("===== 직업 정보 출력 =====");

        assertEquals("조회된 직업이 자식아이디로 등록되어 있어야 합니다.", childId, findJob.getId());
    }

    @Test
    @DisplayName("직업 업데이트 예약(수정)")
    @Transactional
    public void 직업_업데이트_from_예약_수정() throws Exception {
        //given
        Long childId = 2L;

        //when
        jobService.updateJob(jobReservationRepository.findById(childId).get());
        em.flush();
        em.clear();

        //then
        Job findJob = jobRepository.findById(childId).get();
        boolean findJobReservation = jobReservationRepository.findById(childId).isEmpty();

        JobInfo jobInfo = findJob.getJobInfo();
        boolean result = jobInfo.getTaskAmount() == 2 && jobInfo.getWage() == 2000 && findJobReservation;
        System.out.println("===== 직업 정보 출력 =====");
        System.out.println("job_id: " + findJob.getId() +
            " name: " + jobInfo.getName() +
            " wage: " + jobInfo.getWage() +
            " task: " + jobInfo.getTask() +
            " taskAmount: " + jobInfo.getTaskAmount() +
            " done_count: " + findJob.getDoneCount());
        System.out.println("===== 직업 정보 출력 =====");

        assertTrue("직업의 봉급과 업무량이 업데이트 되고, 직업예약이 삭제되어야 합니다.", result);
    }

    @Test(expected = NoSuchElementException.class)
    @DisplayName("직업 업데이트 예약(삭제)")
    @Transactional
    public void 직업_업데이트_from_예약_삭제() throws Exception {
        //given
        Long childId = 2L;

        //when
        jobService.updateJob(jobReservationRepository.findById(childId).get());
        em.flush();
        em.clear();

        //then
        Job findJob = jobRepository.findById(childId).get();

        fail("직업이 삭제되어야 합니다.");
    }

    @Test
    @DisplayName("직업 업데이트 끝낸 일 횟수")
    @Transactional
    public void 직업_업데이트_끝낸일횟수() throws Exception {
        //given
        Long childId = 2L;
        Child child = childService.findChild(childId).get();
        Job existingJob = jobRepository.findById(childId).get();

        //when
        existingJob.updateJob(existingJob.getDoneCount() + 1);
        existingJob.setChild(child);
        jobRepository.save(existingJob);
        em.flush();
        em.clear();

        //then
        Job job = jobRepository.findById(childId).get();
        JobInfo jobInfo = job.getJobInfo();
        System.out.println("===== 직업 정보 출력 =====");
        System.out.println("job_id: " + job.getId() +
            " name: " + jobInfo.getName() +
            " wage: " + jobInfo.getWage() +
            " task: " + jobInfo.getTask() +
            " taskAmount: " + jobInfo.getTaskAmount() +
            " done_count: " + job.getDoneCount());
        System.out.println("===== 직업 정보 출력 =====");

        assertEquals("끝낸 일 횟수가 1이어야 합니다.", 1, job.getDoneCount());
    }

    @Test
    @DisplayName("직업 스케줄러")
    @Transactional
    public void 직업_스케줄러() throws Exception {
        //given
        int size = 5;
        Long[] childId = new Long[size];
        childId[0] = 2L;
        childId[1] = 3L;
        childId[2] = 5L;
        childId[3] = 6L;
        childId[4] = 7L;
        JobReservationDto[] jobReservationDto = new JobReservationDto[size];
        jobReservationDto[0] = new JobReservationDto("강아지 미용사", 1000, "강아지 미용", 5, true, childId[0]);
        jobReservationDto[1] = new JobReservationDto("강아지 미용사", 2000, "강아지 미용", 5, true, childId[1]);
        jobReservationDto[2] = new JobReservationDto("강아지 미용사", 10000, "강아지 미용", 2, true, childId[2]);
        jobReservationDto[3] = new JobReservationDto("강아지 미용사", 1000, "강아지 미용", 2, true, childId[3]);
        jobReservationDto[4] = new JobReservationDto("강아지 미용사", 10000, "강아지 미용", 6, true, childId[4]);

        for (int i = 0; i < size; i++) {
            createJobReservationForTest(jobReservationDto[i], false);
        }
        em.flush();
        em.clear();

        for (int i = 0; i < size; i++) {
            Job job = Job.createJob(jobReservationRepository.findById(childId[i]).get());
            job.setChild(childRepository.findById(childId[i]).get());
            jobRepository.save(job);
        }
        em.flush();
        em.clear();

        jobReservationDto[0].setState(false);
        jobReservationDto[2] = new JobReservationDto("강아지 미용사", 100, "강아지 미용", 5, true, childId[2]);
        jobReservationDto[3] = new JobReservationDto("강아지 미용사", 10000, "강아지 미용", 6, false, childId[3]);
        for (int i = 0; i < size; i++) {
            if (i == 1 || i == 4) {
                jobReservationRepository.deleteById(jobReservationDto[i].getChildId());
            } else {
                jobReservationService.modifyJobReservation(jobReservationDto[i]);
            }
        }
        em.flush();
        em.clear();

        System.out.println("===== 직업 정보 출력 =====");
        List<Job> jobList = jobRepository.findAll();
        for (Job job : jobList) {
            JobInfo jobInfo = job.getJobInfo();
            System.out.println("job_id: " + job.getId() +
                " name: " + jobInfo.getName() +
                " wage: " + jobInfo.getWage() +
                " task: " + jobInfo.getTask() +
                " taskAmount: " + jobInfo.getTaskAmount() +
                " done_count: " + job.getDoneCount());
        }
        System.out.println("===== 직업 정보 출력 =====");

        System.out.println("===== 직업예약 정보 출력 =====");
        List<JobReservation> findJobReservationList = jobReservationRepository.findAll();
        for (JobReservation jobReservation : findJobReservationList) {
            JobInfo jobInfo = jobReservation.getJobInfo();
            System.out.println("job_id: " + jobReservation.getId() +
                " name: " + jobInfo.getName() +
                " wage: " + jobInfo.getWage() +
                " task: " + jobInfo.getTask() +
                " taskAmount: " + jobInfo.getTaskAmount() +
                " state: " + jobReservation.isState());
        }
        System.out.println("===== 직업예약 정보 출력 =====");

        //when
        System.out.println("==== updateJob ====");
        jobService.updateJob();
        System.out.println("==== updateJob ====");

        //then
        List<JobReservation> jobReservationList = jobReservationRepository.findAll();
        int testSize = 10;
        boolean[] check = new boolean[testSize];
        check[0] = jobReservationList.isEmpty();
        check[1] = jobRepository.existsById(childId[1]);
        check[2] = jobRepository.existsById(childId[2]);
        check[3] = jobRepository.existsById(childId[4]);
        check[4] = jobRepository.findById(childId[0]).isEmpty();
        check[5] = jobRepository.findById(childId[3]).isEmpty();
        check[6] = jobRepository.findById(childId[1]).get().getDoneCount() == 0;
        check[7] = jobRepository.findById(childId[1]).get().getJobInfo().getWage() == 2000;
        check[8] = jobRepository.findById(childId[2]).get().getJobInfo().getWage() == 100;
        check[9] = jobRepository.findById(childId[3]).isEmpty();

        for (int i = 0; i < testSize; i++) {
            System.out.println(check[i]);
            if (!check[i]) {
                fail("직업 스케줄러 실패");
            }
        }
    }

    @Transactional
    public void createJobReservationForTest(JobReservationDto jobReservationDto, boolean type) {
        jobReservationService.createJobReservation(jobReservationDto, type);
    }

}