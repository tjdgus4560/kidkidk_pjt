package com.ssafy.kdkd.domain.dto.fund;

import com.ssafy.kdkd.domain.entity.fund.FundStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundStatusDto {

    private int amount;
    private boolean submit;
    private boolean answer;
    private Long childId;

    public static FundStatusDto mappingFundStatus(FundStatus fundStatus) {
        return FundStatusDto.builder()
            .amount(fundStatus.getAmount())
            .submit(fundStatus.isSubmit())
            .answer(fundStatus.isAnswer())
            .childId(fundStatus.getId())
            .build();
    }

}
