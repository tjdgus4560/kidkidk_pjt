package com.ssafy.kdkd.service.saving;

import com.ssafy.kdkd.domain.dto.saving.SavingHistoryDto;
import com.ssafy.kdkd.domain.entity.saving.SavingHistory;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.saving.SavingHistoryRepository;
import com.ssafy.kdkd.service.user.ChildService;

import static com.ssafy.kdkd.domain.entity.saving.SavingHistory.createSavingHistory;
import static org.junit.Assert.*;

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
public class SavingHistoryServiceTest {

    @Autowired SavingHistoryService savingHistoryService;
    @Autowired SavingHistoryRepository savingHistoryRepository;
    @Autowired ChildService childService;
    @Autowired EntityManager em;

    @Test
    @DisplayName("적금내역 생성")
    @Transactional
    @Rollback(value = false)
    public void 적금내역_생성() throws Exception {
        //given
        Long childId = 2L;
        Child child = childService.findChild(childId).get();

        //when
        SavingHistoryDto savingHistoryDto =
            new SavingHistoryDto(LocalDateTime.now(), "적금 납입", true, 1000, childId);
        SavingHistory savingHistory = createSavingHistory(savingHistoryDto);
        savingHistory.setChild(child);
        savingHistoryRepository.save(savingHistory);
        em.flush();

        //then
        List<SavingHistory> findSavingHistories = savingHistoryRepository.findSavingHistoriesByChildId(childId);
        int size = findSavingHistories.size();

        System.out.println("===== 내역 출력 =====");
        for (SavingHistory sh : findSavingHistories) {
            System.out.println("savingHistory_id: "+ sh.getId() +
                " dataLog: " + sh.getDataLog() +
                " detail: " + sh.getDetail() +
                " type: " + sh.isType() +
                " amount: " + sh.getAmount() +
                " child_id: " + sh.getChild().getId());
        }
        System.out.println("===== 내역 출력 =====");
        assertEquals("생성된 내역 갯수는 1개 입니다.", 1, size);
    }

    @Test
    @DisplayName("적금 내역 조회")
    @Transactional
    public void 적금내역_조회() throws Exception {
        //given
        int size = 100;
        Long childId = 2L;
        Child child = childService.findChild(childId).get();

        for (int i = 0; i < size; i++) {
            creatSavingHistoryList(child, childId);
        }
        em.flush();

        //when
        List<SavingHistory> savingHistories = savingHistoryService.findSavingHistoriesByChildId(childId);
        int findCount = savingHistories.size();

        //then
        System.out.println("===== 적금 내역 출력 =====");
        for (SavingHistory savingHistory : savingHistories) {
            System.out.println("saving_history_id: "+ savingHistory.getId() +
                " dataLog: " + savingHistory.getDataLog() +
                " detail: " + savingHistory.getDetail() +
                " type: " + savingHistory.isType() +
                " amount: " + savingHistory.getAmount() +
                " child_id: " + savingHistory.getChild().getId());
        }
        System.out.println("===== 적금 내역 출력 =====");
        assertEquals("조회된 적금 내역 갯수는 100개 입니다.", size, findCount);
    }

    @Test
    @DisplayName("적금 내역 삭제")
    @Transactional
    public void 적금내역_삭제() throws Exception {
        //given
        int size = 0;
        Long childId = 2L;

        //when
        savingHistoryRepository.deleteSavingHistoriesByChildId(childId);

        //then
        List<SavingHistory> savingHistories = savingHistoryService.findSavingHistoriesByChildId(childId);
        int findCount = savingHistories.size();

        System.out.println("===== 적금 내역 출력 =====");
        for (SavingHistory savingHistory : savingHistories) {
            System.out.println("saving_history_id: "+ savingHistory.getId() +
                " dataLog: " + savingHistory.getDataLog() +
                " detail: " + savingHistory.getDetail() +
                " type: " + savingHistory.isType() +
                " amount: " + savingHistory.getAmount() +
                " child_id: " + savingHistory.getChild().getId());
        }
        System.out.println("===== 적금 내역 출력 =====");
        assertEquals("적금 내역 전체 삭제 이후, 조회된 갯수가 0개 입니다.", size, findCount);
    }

    private void creatSavingHistoryList(Child child, Long childId) {
        //내역 생성
        SavingHistoryDto savingHistoryDto =
            new SavingHistoryDto(LocalDateTime.now(), "적금 납입", true, 1000, childId);
        SavingHistory savingHistory = createSavingHistory(savingHistoryDto);
        savingHistory.setChild(child);
        em.persist(savingHistory);
    }

}