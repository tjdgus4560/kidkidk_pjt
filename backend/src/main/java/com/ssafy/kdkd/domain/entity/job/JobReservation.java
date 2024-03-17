package com.ssafy.kdkd.domain.entity.job;

import com.ssafy.kdkd.domain.dto.job.JobReservationDto;
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
@Table(name = "job_reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobReservation {

    @Id
    private Long id;

    @Embedded
    private JobInfo jobInfo;

    @Column(name = "state")
    private boolean state;

    @MapsId
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "job_reservation_id")
    private Child child;

    /**
     * 연관관계 메서드
     */
    public void setChild(Child child) {
        this.child = child;
    }

    /**
     * 투자예약 생성
     */
    public static JobReservation createJobReservation(JobReservationDto jobReservationDto) {
        JobReservation jobReservation = new JobReservation();
        jobReservation.jobInfo = new JobInfo(jobReservationDto.getName(),
            jobReservationDto.getWage(),
            jobReservationDto.getTask(),
            jobReservationDto.getTaskAmount());
        jobReservation.state = jobReservationDto.isState();
        return jobReservation;
    }

    /**
     * 투자예약 업데이트
     */
    public void updateJobReservation(JobReservationDto jobReservationDto) {
        this.jobInfo = new JobInfo(jobReservationDto.getName(),
            jobReservationDto.getWage(),
            jobReservationDto.getTask(),
            jobReservationDto.getTaskAmount());
        this.state = jobReservationDto.isState();
    }

}
