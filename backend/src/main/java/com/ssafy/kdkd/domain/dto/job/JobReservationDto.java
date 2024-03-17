package com.ssafy.kdkd.domain.dto.job;

import com.ssafy.kdkd.domain.entity.job.JobInfo;
import com.ssafy.kdkd.domain.entity.job.JobReservation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobReservationDto {

    private String name;
    private int wage;
    private String task;
    private int taskAmount;
    private boolean state;
    private Long childId;

    public static JobReservationDto mappingJobReservationDto(JobReservation jobReservation) {
        JobInfo jobInfo = jobReservation.getJobInfo();
        return JobReservationDto.builder()
            .name(jobInfo.getName())
            .wage(jobInfo.getWage())
            .task(jobInfo.getTask())
            .taskAmount(jobInfo.getTaskAmount())
            .state(jobReservation.isState())
            .childId(jobReservation.getId())
            .build();
    }

}
