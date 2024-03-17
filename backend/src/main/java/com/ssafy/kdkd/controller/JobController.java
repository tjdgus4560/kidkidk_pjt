package com.ssafy.kdkd.controller;

import com.ssafy.kdkd.domain.dto.job.JobDto;
import com.ssafy.kdkd.domain.dto.job.JobReservationDto;
import com.ssafy.kdkd.domain.entity.job.Job;
import com.ssafy.kdkd.domain.entity.job.JobReservation;
import com.ssafy.kdkd.repository.job.JobRepository;
import com.ssafy.kdkd.repository.job.JobReservationRepository;
import com.ssafy.kdkd.service.job.JobReservationService;
import com.ssafy.kdkd.service.job.JobService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/job")
public class JobController {

    private final JobService jobService;
    private final JobRepository jobRepository;
    private final JobReservationService jobReservationService;
    private final JobReservationRepository jobReservationRepository;

    @GetMapping("/retrieve/{childId}")
    @Operation(summary = "직업 조회")
    public ResponseEntity<?> retrieveJob(@PathVariable("childId") Long childId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: retrieveJob() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                Optional<Job> result = jobRepository.findById(childId);
                if (result.isEmpty()) {
                    status = HttpStatus.NO_CONTENT;
                } else {
                    resultMap.put("Job", JobDto.mappingJobDto(result.get()));
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/retrieve/reservation/{childId}")
    @Operation(summary = "직업 예약 조회")
    public ResponseEntity<?> retrieveJobReservation(@PathVariable("childId") Long childId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: retrieveJobReservation() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                Optional<JobReservation> result = jobReservationRepository.findById(childId);
                if (result.isEmpty()) {
                    status = HttpStatus.NO_CONTENT;
                } else {
                    resultMap.put("JobReservation", JobReservationDto.mappingJobReservationDto(result.get()));
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/create")
    @Operation(summary = "직업 생성")
    public ResponseEntity<?> createJob(@RequestBody JobReservationDto jobReservationDto, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;
        try {
            log.info("fund controller: createJob() Enter");
            Long childId = jobReservationDto.getChildId();
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                boolean type = true;
                JobReservationDto result =
                    jobReservationService.createJobReservation(jobReservationDto, type);

                if (result == null) {
                    status = HttpStatus.CONFLICT;
                } else {
                    resultMap.put("JobReservation", result);
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/reservation/create")
    @Operation(summary = "직업 예약 생성")
    public ResponseEntity<?> createJobReservation(@RequestBody JobReservationDto jobReservationDto, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;
        try {
            log.info("fund controller: createJobReservation() Enter");
            Long childId = jobReservationDto.getChildId();
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                boolean type = false;
                JobReservationDto result =
                    jobReservationService.createJobReservation(jobReservationDto, type);

                if (result == null) {
                    status = HttpStatus.CONFLICT;
                } else {
                    resultMap.put("JobReservation", result);
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PutMapping("/reservation/modify")
    @Operation(summary = "직업 예약 수정")
    public ResponseEntity<?> modifyJobReservation(@RequestBody JobReservationDto jobReservationDto, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: modifyJobReservation() Enter");
            Long childId = jobReservationDto.getChildId();
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                JobReservationDto result =
                    jobReservationService.modifyJobReservation(jobReservationDto);

                if (result == null) {
                    status = HttpStatus.NO_CONTENT;
                } else {
                    resultMap.put("JobReservation", result);
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @DeleteMapping("/delete/{childId}")
    @Operation(summary = "직업 삭제")
    public ResponseEntity<?> deleteJob(@PathVariable("childId") Long childId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: deleteJob() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                JobReservationDto result = jobReservationService.deleteJob(childId);

                if (result == null) {
                    status = HttpStatus.NO_CONTENT;
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @DeleteMapping("/reservation/delete/{childId}")
    @Operation(summary = "직업 예약 삭제")
    public ResponseEntity<?> deleteJobReservation(@PathVariable("childId") Long childId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: deleteJobReservation() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                boolean isEmpty = jobReservationService.delete(childId);

                if (isEmpty) {
                    status = HttpStatus.NO_CONTENT;
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}
