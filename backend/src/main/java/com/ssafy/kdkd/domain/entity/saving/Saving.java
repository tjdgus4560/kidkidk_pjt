package com.ssafy.kdkd.domain.entity.saving;

import com.ssafy.kdkd.domain.dto.saving.SavingDto;
import com.ssafy.kdkd.domain.entity.user.Child;

import static jakarta.persistence.FetchType.LAZY;

import java.time.LocalDateTime;

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
@Table(name = "saving")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Saving {

    @Id
    private Long id;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "count")
    private int count;

    @Column(name = "payment")
    private int payment;

    @Column(name = "rate")
    private int rate;

    @MapsId
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "saving_id")
    private Child child;

    /**
     * 연관관계 메서드
     */
    public void setChild(Child child) {
        this.child = child;
    }

    /**
     * 적금 생성
     */
    public static Saving createSaving(SavingDto savingDto) {
        Saving saving = new Saving();
        saving.startDate = savingDto.getStartDate();
        saving.count = savingDto.getCount();
        saving.payment = savingDto.getPayment();
        saving.rate = savingDto.getRate();
        return saving;
    }

    /**
     * 적금 업데이트
     */
    public void updateSaving(int count) {
        this.count = count;
    }

}
