package com.ssafy.kdkd.domain.dto.fund;

import com.ssafy.kdkd.domain.entity.fund.Roi;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoiDto {

    private int success;
    private int count;
    private Long childId;

    public static RoiDto mappingRoiDto(Roi roi) {
        return RoiDto.builder()
            .success(roi.getSuccess())
            .count(roi.getCount())
            .childId(roi.getId())
            .build();
    }

}
