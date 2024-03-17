package com.ssafy.kdkd.service.saving;

import com.ssafy.kdkd.domain.entity.saving.SavingHistory;
import com.ssafy.kdkd.repository.saving.SavingHistoryRepository;
import com.ssafy.kdkd.repository.saving.SavingRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SavingHistoryService {

    private final SavingRepository savingRepository;
    private final SavingHistoryRepository savingHistoryRepository;

    /**
     * 적금내역 목록 조회
     *
     * @param childId 자식 아이디
     * @return List<SavingHistory> 적금 내역 목록 조회
     */
    public List<SavingHistory> findSavingHistoriesByChildId(Long childId) {

        if (!savingRepository.existsById(childId)) {
            return null;
        }

        return savingHistoryRepository.findSavingHistoriesByChildId(childId);
    }

}
