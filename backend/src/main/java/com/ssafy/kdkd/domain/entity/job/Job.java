package com.ssafy.kdkd.domain.entity.job;

import com.ssafy.kdkd.domain.entity.user.Child;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Job {

    @Id
    private Long id;

    @Embedded
    private JobInfo jobInfo;

    @Column(name = "done_count")
    private int doneCount;

    @MapsId
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "job_id")
    private Child child;

    /**
     * 연관관계 메서드
     */
    public void setChild(Child child) {
        this.child = child;
    }

    /**
     * 직업 생성
     */
    public static Job createJob(JobReservation jobReservation) {
        Job job = new Job();
        job.jobInfo = jobReservation.getJobInfo();
        job.doneCount = 0;
        return job;
    }

    /**
     * 직업 업데이트
     */
    public void updateJob(JobReservation jobReservation) {
        JobInfo updateJobInfo = jobReservation.getJobInfo();
        this.jobInfo = new JobInfo(updateJobInfo.getName(),
            updateJobInfo.getWage(),
            updateJobInfo.getTask(),
            updateJobInfo.getTaskAmount());
        this.doneCount = 0;
    }

    public void updateJob(int doneCount) {
        this.doneCount = doneCount;
    }

}
