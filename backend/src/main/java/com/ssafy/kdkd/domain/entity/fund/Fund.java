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
@Table(name = "fund")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fund {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "yield")
    private int yield;

    @MapsId
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "fund_id")
    private Child child;

    /**
     * 연관관계 메서드
     */
    public void setChild(Child child) {
        this.child = child;
    }

    /**
     * 투자 생성
     */
    public static Fund createFund(FundReservation fundReservation) {
        Fund fund = new Fund();
        fund.name = fundReservation.getName();
        fund.content = fundReservation.getContent();
        fund.yield = fundReservation.getYield();
        return fund;
    }

    /**
     * 투자 수정
     */
    public void updateFund(FundReservation fundReservation) {
        this.name = fundReservation.getName();
        this.content = fundReservation.getContent();
        this.yield = fundReservation.getYield();
    }

}
