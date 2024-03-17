package com.ssafy.kdkd.domain.dto.fund;

import com.ssafy.kdkd.domain.entity.fund.Fund;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundDto {

    private String name;
    private String content;
    private int yield;
    private Long childId;

    public static FundDto mappingFundDto(Fund fund) {
        return FundDto.builder()
            .name(fund.getName())
            .content(fund.getContent())
            .yield(fund.getYield())
            .childId(fund.getId())
            .build();
    }

}
