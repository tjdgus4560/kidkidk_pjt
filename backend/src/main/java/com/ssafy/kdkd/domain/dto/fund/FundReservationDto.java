package com.ssafy.kdkd.domain.dto.fund;

import com.ssafy.kdkd.domain.entity.fund.FundReservation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundReservationDto {

    private String name;
    private String content;
    private int yield;
    private boolean state;
    private Long childId;

    public static FundReservationDto mappingFundReservationDto(FundReservation fundReservation) {
        return FundReservationDto.builder()
            .name(fundReservation.getName())
            .content(fundReservation.getContent())
            .yield(fundReservation.getYield())
            .state(fundReservation.isState())
            .childId(fundReservation.getId())
            .build();
    }

}
