package com.ssafy.kdkd.domain.dto.alarm;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeRequestDto {

    private int amount;
    private Long childId;

}
