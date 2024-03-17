package com.ssafy.kdkd.service.job;

import com.ssafy.kdkd.domain.dto.job.JobReservationDto;
import com.ssafy.kdkd.domain.entity.job.JobInfo;
import com.ssafy.kdkd.domain.entity.job.JobReservation;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.job.JobReservationRepository;
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
public class JobReservationServiceTest {

    @Autowired JobService jobService;
    @Autowired JobReservationService jobReservationService;
    @Autowired JobReservationRepository jobReservationRepository;
    @Autowired ChildService childService;
    @Autowired EntityManager em;

    @Test
    @DisplayName("직업 생성(직업예약)")
    @Transactional
    public void 직업_생성_최초() throws Exception {
        //given
        Long childId = 2L;
        Child child = childService.findChild(childId).get();

        //when
        JobReservationDto jobReservationDto =
            new JobReservationDto("강아지 미용사", 1000, "강아지 미용", 5, true, childId);
        jobReservationService.createJobReservation(jobReservationDto, true);
        em.flush();
        em.clear();

        //then
        boolean result = jobReservationRepository.existsById(childId);

        assertTrue("직업예약이 생성되어야 합니다.", result);
    }

    @Test
    @DisplayName("직업 생성(직업예약) 직업 존재")
    @Transactional
    public void 직업_생성_직업() throws Exception {
        //given
        Long childId = 2L;
        createJobReservationForTest(childId);
        jobService.insertJob(jobReservationRepository.findById(childId).get());
        em.flush();
        em.clear();

        //when
        JobReservationDto jobReservationDto =
            new JobReservationDto("강아지 미용사", 1000, "강아지 미용", 5, true, childId);
        jobReservationService.createJobReservation(jobReservationDto, true);
        JobReservationDto result = jobReservationService.createJobReservation(jobReservationDto, true);

        //then
        if (result != null) {
            fail("직업 생성은 직업이 존재하지 않을때 실행되어야 합니다.");
        }
    }

    @Test
    @DisplayName("직업 생성(직업예약) 직업예약 존재")
    @Transactional
    public void 직업_생성_직업예약() throws Exception {
        //given
        Long childId = 2L;
        createJobReservationForTest(childId);

        //when
        JobReservationDto jobReservationDto =
            new JobReservationDto("강아지 미용사", 1000, "강아지 미용", 5, true, childId);
        jobReservationService.createJobReservation(jobReservationDto, true);
        JobReservationDto result = jobReservationService.createJobReservation(jobReservationDto, true);

        //then
        if (result != null) {
            fail("직업 생성은 직업예약이 존재하지 않을때 실행되어야 합니다.");
        }
    }

    @Test
    @DisplayName("직업예약 조회 단일")
    @Transactional
    public void 직업예약_조회_단일() throws Exception {
        //given
        Long childId = 2L;
        createJobReservationForTest(childId);

        //when
        JobReservation findJobReservation = jobReservationRepository.findById(childId).get();

        //then
        JobInfo jobInfo = findJobReservation.getJobInfo();
        System.out.println("===== 직업예약 정보 출력 =====");
        System.out.println("job_id: " + findJobReservation.getId() +
            " name: " + jobInfo.getName() +
            " wage: " + jobInfo.getWage() +
            " task: " + jobInfo.getTask() +
            " taskAmount: " + jobInfo.getTaskAmount() +
            " state: " + findJobReservation.isState());
        System.out.println("===== 직업예약 정보 출력 =====");

        assertEquals("조회된 직업예약이 자식아이디로 등록되어 있어야 합니다.", childId, findJobReservation.getId());
    }

    @Test
    @DisplayName("직업예약 조회 전체")
    @Transactional
    public void 직업예약_조회_전체() throws Exception {
        //given
        int size = 3;
        createJobReservationForTest(2L);
        createJobReservationForTest(3L);
        createJobReservationForTest(5L);

        //when
        List<JobReservation> jobReservationList = jobReservationRepository.findAll();
        int result = jobReservationList.size();

        //then
        System.out.println("===== 직업예약 정보 출력 =====");
        for (JobReservation jobReservation : jobReservationList) {
            JobInfo jobInfo = jobReservation.getJobInfo();
            System.out.println("job_id: " + jobReservation.getId() +
                " name: " + jobInfo.getName() +
                " wage: " + jobInfo.getWage() +
                " task: " + jobInfo.getTask() +
                " taskAmount: " + jobInfo.getTaskAmount() +
                " state: " + jobReservation.isState());
        }
        System.out.println("===== 직업예약 정보 출력 =====");

        assertEquals("조회된 직업예약 목록의 갯수는 3개 입니다..", size, result);
    }

    @Test
    @DisplayName("직업예약 생성")
    @Transactional
    public void 직업예약_생성() throws Exception {
        //given
        Long childId = 2L;
        Child child = childService.findChild(childId).get();
        JobReservationDto jobReservationDto =
            new JobReservationDto("강아지 미용사", 1000, "강아지 미용", 5, true, childId);

        //when
        jobReservationService.createJobReservation(jobReservationDto, false);
        em.flush();
        em.clear();

        //then
        assertTrue("직업예약이 생성되어야 합니다.", jobReservationRepository.existsById(childId));
    }

    @Test(expected = NoSuchElementException.class)
    @DisplayName("직업예약 삭제")
    @Transactional
    @Rollback(value = false)
    public void 직업예약_삭제() throws Exception {
        //given
        Long childId = 2L;
        createJobReservationForTest(childId);

        //when
        jobReservationService.delete(childId);
        em.flush();
        em.clear();

        //then
        JobReservation jobReservation = jobReservationRepository.findById(childId).get();

        fail("직업예약이 없어야 합니다.");
    }

    @Test
    @DisplayName("직업예약 수정")
    @Transactional
    public void 직업예약_수정() throws Exception {
        //given
        Long childId = 2L;
        createJobReservationForTest(childId);
        em.flush();
        em.clear();

        //when
        JobReservationDto jobReservationDto =
            new JobReservationDto("강아지 미용사", 2000, "강아지 미용", 3, false, childId);
        JobReservationDto result = jobReservationService.modifyJobReservation(jobReservationDto);
        em.flush();
        em.clear();

        //then
        JobReservation findJobReservation = jobReservationRepository.findById(childId).get();
        JobInfo jobInfo = findJobReservation.getJobInfo();
        boolean check = !findJobReservation.isState() && jobInfo.getWage() == 2000
                        && jobInfo.getTaskAmount() == 3;

        System.out.println("===== 직업예약 정보 출력 =====");
        System.out.println("job_id: " + findJobReservation.getId() +
            " name: " + jobInfo.getName() +
            " wage: " + jobInfo.getWage() +
            " task: " + jobInfo.getTask() +
            " taskAmount: " + jobInfo.getTaskAmount() +
            " state: " + findJobReservation.isState());
        System.out.println("===== 직업예약 정보 출력 =====");

        assertTrue("직업예약이 수정되어야 합니다.", check);
    }

    @Test
    @DisplayName("직업예약 수정(직업예약 X)")
    @Transactional
    public void 직업예약_수정_실패() throws Exception {
        //given
        Long childId = 2L;

        //when
        JobReservationDto jobReservationDto =
            new JobReservationDto("강아지 미용사", 2000, "강아지 미용", 3, false, childId);
        JobReservationDto result = jobReservationService.modifyJobReservation(jobReservationDto);

        //then
        if (result != null) {
            fail("직업예약을 수정하려면, 직업예약이 있어야 합니다.");
        }
    }

    @Transactional
    public void createJobReservationForTest(Long childId) {
        Child child = childService.findChild(childId).get();
        JobReservationDto jobReservationDto =
            new JobReservationDto("강아지 미용사", 1000, "강아지 미용", 5, true, childId);
        jobReservationService.createJobReservation(jobReservationDto, true);
        em.flush();
        em.clear();
    }

}