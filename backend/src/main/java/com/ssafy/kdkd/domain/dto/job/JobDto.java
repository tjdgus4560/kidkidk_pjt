package com.ssafy.kdkd.domain.dto.job;

import com.ssafy.kdkd.domain.entity.job.Job;
import com.ssafy.kdkd.domain.entity.job.JobInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobDto {

    private String name;
    private int wage;
    private String task;
    private int taskAmount;
    private int doneCount;
    private Long childId;

    public static JobDto mappingJobDto(Job job) {
        JobInfo jobInfo = job.getJobInfo();
        return JobDto.builder()
            .name(jobInfo.getName())
            .wage(jobInfo.getWage())
            .task(jobInfo.getTask())
            .taskAmount(jobInfo.getTaskAmount())
            .doneCount(job.getDoneCount())
            .childId(job.getId())
            .build();
    }

}
