package com.ssafy.kdkd.domain.dto.fund;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferDto {

    private int fundMoney;
    private Long childId;

}
