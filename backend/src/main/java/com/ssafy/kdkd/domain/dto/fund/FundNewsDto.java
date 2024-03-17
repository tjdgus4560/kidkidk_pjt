package com.ssafy.kdkd.domain.dto.fund;

import com.ssafy.kdkd.domain.entity.fund.FundNews;

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
public class FundNewsDto {

    private LocalDateTime dataLog;
    private String content;
    private Long childId;

    private static FundNewsDto mappingFundNewsDto(FundNews fundNews) {
        return FundNewsDto.builder()
            .dataLog(fundNews.getDataLog())
            .content(fundNews.getContent())
            .childId(fundNews.getChild().getId())
            .build();
    }

    public static List<FundNewsDto> mappingFundNewsDto(List<FundNews> fundNewsList) {
        List<FundNewsDto> list = new ArrayList<>();
        for (FundNews fundNews : fundNewsList) {
            list.add(mappingFundNewsDto(fundNews));
        }
        return list;
    }
}
