package com.ssafy.kdkd.repository.saving;

import com.ssafy.kdkd.domain.entity.saving.SavingHistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface SavingHistoryRepository extends JpaRepository<SavingHistory, Long> {

    List<SavingHistory> findSavingHistoriesByChildId(Long childId);

    @Modifying
    @Transactional
    void deleteSavingHistoriesByChildId(Long childId);

}
