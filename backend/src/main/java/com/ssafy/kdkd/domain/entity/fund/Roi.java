package com.ssafy.kdkd.domain.entity.fund;

import com.ssafy.kdkd.domain.dto.fund.RoiDto;
import com.ssafy.kdkd.domain.entity.user.Child;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
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
@Table(name = "roi")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Roi {

    @Id
    private Long id;

    @Column(name = "success")
    private int success;

    @Column(name = "count")
    private int count;

    @MapsId
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "roi_id")
    private Child child;

    /**
     * 연관관계 메서드
     */
    public void setChild(Child child) {
        this.child = child;
    }

    /**
     * roi 생성
     */
    public static Roi createRoi(RoiDto roiDto) {
        Roi roi = new Roi();
        roi.success = roiDto.getSuccess();
        roi.count = roiDto.getCount();
        return roi;
    }

    public void updateRoi(RoiDto roiDto) {
        this.success = roiDto.getSuccess();
        this.count = roiDto.getCount();
    }

}
