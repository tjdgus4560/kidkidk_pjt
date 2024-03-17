package com.ssafy.kdkd.domain.entity.saving;

import com.ssafy.kdkd.domain.dto.saving.SavingHistoryDto;
import com.ssafy.kdkd.domain.entity.user.Child;

import static jakarta.persistence.FetchType.LAZY;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "saving_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SavingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saving_history_id")
    private Long id;

    @Column(name = "data_log")
    private LocalDateTime dataLog;

    @Column(name = "detail")
    private String detail;

    @Column(name = "type")
    private boolean type;

    @Column(name = "amount")
    private int amount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "child_id")
    private Child child;

    /**
     * 연관관계 메서드
     */
    public void setChild(Child child) {
        this.child = child;
    }

    /**
     * 적금내역 생성
     */
    public static SavingHistory createSavingHistory(SavingHistoryDto savingHistoryDto) {
        SavingHistory savingHistory = new SavingHistory();
        savingHistory.dataLog = savingHistoryDto.getDataLog();
        savingHistory.detail = savingHistoryDto.getDetail();
        savingHistory.type = savingHistoryDto.isType();
        savingHistory.amount = savingHistoryDto.getAmount();
        return savingHistory;
    }

}
