package com.ssafy.kdkd.service.fund;

import com.ssafy.kdkd.domain.dto.fund.RoiDto;
import com.ssafy.kdkd.domain.entity.fund.Roi;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.fund.RoiRepository;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RoiService {

    private final RoiRepository roiRepository;

    /**
     * roi 업데이트
     * 
     * @param roi 찾은 roi
     * @param success 성공 여부(성공 = 1, 실패 = 0)
     * @param child 자식 entity
     */
    @Transactional
    public void updateRoi(Optional<Roi> roi, int success, Child child) {
        Long childId = child.getId();

        if (roi.isEmpty()) {
            Roi createRoi = Roi.createRoi(new RoiDto(success, 1, childId));
            createRoi.setChild(child);
            roiRepository.save(createRoi);
        } else {
            Roi findRoi = roi.get();
            findRoi.updateRoi(new RoiDto(findRoi.getSuccess() + success, findRoi.getCount() + 1, childId));
            roiRepository.save(findRoi);
        }
    }

}
