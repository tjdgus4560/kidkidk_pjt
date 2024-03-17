package com.ssafy.kdkd.domain.dto.saving;

import com.ssafy.kdkd.domain.entity.saving.SavingHistory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SavingHistoryDto {

    private LocalDateTime dataLog;
    private String detail;
    private boolean type;
    private int amount;
    private Long childId;

    public static SavingHistoryDto mappingSavingHistoryDto(SavingHistory savingHistory) {
        return SavingHistoryDto.builder()
            .dataLog(savingHistory.getDataLog())
            .detail(savingHistory.getDetail())
            .type(savingHistory.isType())
            .amount(savingHistory.getAmount())
            .childId(savingHistory.getId())
            .build();
    }

    public static List<SavingHistoryDto> mappingSavingHistoryDto(List<SavingHistory> list) {
        List<SavingHistoryDto> savingHistoryDtoList = new ArrayList<>();
        for (SavingHistory savingHistory : list) {
            savingHistoryDtoList.add(mappingSavingHistoryDto(savingHistory));
        }
        return savingHistoryDtoList;
    }

}
