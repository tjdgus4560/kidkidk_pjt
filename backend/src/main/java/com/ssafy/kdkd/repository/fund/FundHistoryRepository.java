package com.ssafy.kdkd.repository.fund;

import com.ssafy.kdkd.domain.entity.fund.FundHistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface FundHistoryRepository extends JpaRepository<FundHistory, Long> {

    List<FundHistory> findFundHistoriesByChildId(Long childId);

    @Modifying
    @Transactional
    void deleteAllByChildId(Long childId);

}
