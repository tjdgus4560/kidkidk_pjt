package com.ssafy.kdkd.domain.entity.fund;

import com.ssafy.kdkd.domain.dto.fund.FundReservationDto;
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
@Table(name = "fund_reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundReservation {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "yield")
    private int yield;

    @Column(name = "state")
    private boolean state;

    @MapsId
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "fund_reservation_id")
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
    public static FundReservation createFundReservation(FundReservationDto fundReservationDto) {
        FundReservation fundReservation = new FundReservation();
        fundReservation.name = fundReservationDto.getName();
        fundReservation.content = fundReservationDto.getContent();
        fundReservation.yield = fundReservationDto.getYield();
        fundReservation.state = fundReservationDto.isState();
        return fundReservation;
    }

    /**
     * 투자예약 수정
     */
    public void updateFundReservation(FundReservationDto fundReservationDto) {
        this.name = fundReservationDto.getName();
        this.content = fundReservationDto.getContent();
        this.state = fundReservationDto.isState();
    }
    
}
