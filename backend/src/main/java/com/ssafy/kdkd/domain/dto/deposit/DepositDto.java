package com.ssafy.kdkd.domain.dto.deposit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.kdkd.domain.entity.deposit.Deposit;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepositDto {

    private LocalDateTime dataLog;
    private String detail;
    private boolean type;
    private int amount;
    private int money;
    private Long childId;

    public static DepositDto mappingDepositDto(Deposit deposit) {
        return DepositDto.builder()
            .dataLog(deposit.getDataLog())
            .detail(deposit.getDetail())
            .type(deposit.isType())
            .amount(deposit.getAmount())
            .money(deposit.getMoney())
            .childId(deposit.getId())
            .build();
    }

    public static List<DepositDto> mappingDepositDto(List<Deposit> deposits) {
        List<DepositDto> depositDtoList = new ArrayList<>();
        for (Deposit deposit : deposits) {
            depositDtoList.add(mappingDepositDto(deposit));
        }
        return depositDtoList;
    }

}
