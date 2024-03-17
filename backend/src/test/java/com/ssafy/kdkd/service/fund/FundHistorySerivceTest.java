package com.ssafy.kdkd.service.fund;

import com.ssafy.kdkd.domain.dto.fund.FundHistoryDto;
import com.ssafy.kdkd.domain.entity.fund.FundHistory;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.fund.FundHistoryRepository;
import com.ssafy.kdkd.service.user.ChildService;

import static com.ssafy.kdkd.domain.entity.fund.FundHistory.createFundHistory;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(readOnly = true)
public class FundHistorySerivceTest {

    @Autowired FundHistoryService fundHistorySerivce;
    @Autowired FundHistoryRepository fundHistoryRepository;
    @Autowired ChildService childService;
    @Autowired EntityManager em;

    @Test
    @DisplayName("내역 생성")
    @Transactional
    @Rollback(value = false)
    public void 내역_생성() throws Exception {
        //given
        Long childId = 2L;

        //when
        int size = 100;
        createFundHistoryForTest(childId, size);
        em.flush();
        em.clear();

        //then
        List<FundHistory> findFundHistories = fundHistoryRepository.findFundHistoriesByChildId(childId);
        int result = findFundHistories.size();

        System.out.println("===== 투자 내역 출력 =====");
        for (FundHistory fh : findFundHistories) {
            System.out.println("fundHistory_id: "+ fh.getId() +
                " dataLog: " + fh.getDataLog() +
                " seedMoney: " + fh.getSeedMoney() +
                " yield: " + fh.getYield() +
                " pnl: " + fh.getPnl() +
                " child_id: " + fh.getChild().getId());
        }
        System.out.println("===== 투자 내역 출력 =====");
        assertEquals("조회된 내역 사이즈는 100입니다.", size, result);
    }

    @Test
    @DisplayName("내역 조회")
    @Transactional
    public void 내역_조회() throws Exception {
        //given
        Long childId = 2L;

        //when
        int size = 100;
        List<FundHistory> fundHistories = fundHistoryRepository.findFundHistoriesByChildId(childId);

        //then
        int result = fundHistories.size();
        System.out.println("===== 투자 내역 출력 =====");
        for (FundHistory fundHistory : fundHistories) {
            System.out.println("fundHistory_id: "+ fundHistory.getId() +
                " dataLog: " + fundHistory.getDataLog() +
                " seedMoney: " + fundHistory.getSeedMoney() +
                " yield: " + fundHistory.getYield() +
                " pnl: " + fundHistory.getPnl() +
                " child_id: " + fundHistory.getChild().getId());
        }
        System.out.println("===== 투자 내역 출력 =====");
        assertEquals("조회된 투자 내역 사이즈는 100입니다.", size, result);
    }

    @Test
    @DisplayName("투자 내역 전체 삭제")
    @Transactional
    @Rollback(value = false)
    public void 내역_전체_삭제() throws Exception {
        //given
        Long childId = 2L;
        int size = 0;

        //when
        fundHistoryRepository.deleteAllByChildId(childId);

        //then
        List<FundHistory> fundHistories = fundHistoryRepository.findFundHistoriesByChildId(childId);
        int result = fundHistories.size();
        assertEquals("조회된 투자 내역 사이즈는 0입니다.", size, result);
    }

    private void createFundHistoryForTest(Long childId, int size) {
        Child child = childService.findChild(childId).get();
        for (int i = 0; i < size; i++) {
            FundHistoryDto fundHistoryDto = new FundHistoryDto(LocalDateTime.now(), 0, 0, 0, childId);
            FundHistory fundHistory = createFundHistory(fundHistoryDto);
            fundHistory.setChild(child);
            fundHistoryRepository.save(fundHistory);
        }
    }

}