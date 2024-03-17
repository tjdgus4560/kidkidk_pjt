package com.ssafy.kdkd.domain.dto.fund;

import com.ssafy.kdkd.domain.entity.fund.FundHistory;

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
public class FundHistoryDto {

    private LocalDateTime dataLog;
    private int seedMoney;
    private int yield;
    private int pnl;
    private Long childId;

    private static FundHistoryDto mappingFundHistoryDto(FundHistory fundHistory) {
        return FundHistoryDto.builder()
            .dataLog(fundHistory.getDataLog())
            .seedMoney(fundHistory.getSeedMoney())
            .yield(fundHistory.getYield())
            .pnl(fundHistory.getPnl())
            .childId(fundHistory.getChild().getId())
            .build();
    }

    public static List<FundHistoryDto> mappingFundHistoryDto(List<FundHistory> fundHistories) {
        List<FundHistoryDto> list = new ArrayList<>();
        for (FundHistory fundHistory : fundHistories) {
            list.add(mappingFundHistoryDto(fundHistory));
        }
        return list;
    }

}
