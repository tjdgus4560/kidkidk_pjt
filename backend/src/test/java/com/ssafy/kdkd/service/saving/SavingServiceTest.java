package com.ssafy.kdkd.service.saving;

import com.ssafy.kdkd.domain.dto.saving.SavingDto;
import com.ssafy.kdkd.domain.entity.saving.Saving;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.deposit.DepositRepository;
import com.ssafy.kdkd.repository.saving.SavingHistoryRepository;
import com.ssafy.kdkd.repository.saving.SavingRepository;
import com.ssafy.kdkd.service.user.ChildService;

import static com.ssafy.kdkd.domain.entity.saving.Saving.createSaving;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(readOnly = true)
public class SavingServiceTest {

    @Autowired SavingService savingService;
    @Autowired SavingRepository savingRepository;
    @Autowired SavingHistoryRepository savingHistoryRepository;
    @Autowired DepositRepository depositRepository;
    @Autowired ChildService childService;
    @Autowired EntityManager em;

    @Test
    @DisplayName("적금 생성")
    @Transactional
    public void 적금_생성() throws Exception {
        //given
        Long childId = 2L;
        Child child = childService.findChild(childId).get();

        //when
        SavingDto savingDto =
            new SavingDto(LocalDateTime.now(), 5, 50000, 5, childId);
        Saving saving = createSaving(savingDto);
        saving.setChild(child);
        savingRepository.save(saving);
        em.flush();
        em.clear();

        //then
        Saving findSaving = savingRepository.findById(childId).get();

        System.out.println("===== 적금 정보 출력 =====");
        System.out.println("saving_id: " + findSaving.getId() +
            " start_date: " + findSaving.getStartDate() +
            " count: " + findSaving.getCount() +
            " payment: " + findSaving.getPayment() +
            " rate: " + findSaving.getRate());
        System.out.println("===== 적금 정보 출력 =====");

        assertEquals("자식 아이디로 적금이 생성됩니다.", childId, findSaving.getId());
    }

    @Test
    @DisplayName("적금 생성 서비스")
    @Transactional
    @Rollback(value = false)
    public void 적금_생성_서비스() throws Exception {
        //given
        Long childId = 2L;
        SavingDto savingDto =
            new SavingDto(LocalDateTime.now(), 0, 50000, 0, childId);

        //when
        SavingDto saving = savingService.createSaving(savingDto);

        //then
        boolean result = savingRepository.existsById(childId);

        assertEquals("적금이 생성됩니다.", true, result);
    }

    @Test(expected = Exception.class)
    @DisplayName("적금 중복 생성")
    @Transactional
    public void 적금_중복_생성() throws Exception {
        //given
        Long childId = 2L;
        Child child = childService.findChild(childId).get();

        //when
        SavingDto savingDto =
            new SavingDto(LocalDateTime.now(), 5, 50000, 5, childId);
        Saving saving = createSaving(savingDto);
        saving.setChild(child);

        //then
        boolean caught = false;

        try {
            savingRepository.save(saving);
            em.flush();
            em.clear();
        } catch (ConstraintViolationException | UnexpectedRollbackException e) {
            caught = true;
        }

        if (!caught) {
            fail("적금은 하나만 생성이 가능합니다.");
        }
    }

    @Test
    @DisplayName("적금 중복 생성 서비스")
    @Transactional
    public void 적금_중복_생성_서비스() throws Exception {
        //given
        Long childId = 2L;
        Child child = childService.findChild(childId).get();

        //when
        SavingDto savingDto =
            new SavingDto(LocalDateTime.now(), 5, 50000, 5, childId);
        SavingDto saving = savingService.createSaving(savingDto);

        //then
        assertNull("적금은 중복생성이 불가합니다.", saving);
    }

    @Test
    @DisplayName("적금 조회")
    public void 적금_조회() throws Exception {
        //given
        Long childId = 2L;

        //when
        Saving findSaving = savingRepository.findById(childId).get();

        //then
        System.out.println("===== 적금 정보 출력 =====");
        System.out.println("find_saving_id: " + findSaving.getId() +
            " start_date: " + findSaving.getStartDate() +
            " count: " + findSaving.getCount() +
            " payment: " + findSaving.getPayment() +
            " rate: " + findSaving.getRate());
        System.out.println("===== 적금 정보 출력 =====");

        assertEquals("조회된 적금의 자식 아이디가 동일해야 합니다.", childId, findSaving.getId());
    }

    @Test
    @DisplayName("적금 전체 조회")
    public void 적금_전체조회() throws Exception {
        //when
        List<Saving> list = savingRepository.findAll();
        int size = list.size();

        //then
        System.out.println("===== 적금 정보 출력 =====");
        for (Saving saving : list) {
            System.out.println("saving_id: " + saving.getId() +
                " start_date: " + saving.getStartDate() +
                " count: " + saving.getCount() +
                " payment: " + saving.getPayment() +
                " rate: " + saving.getRate());
        }
        System.out.println("===== 적금 정보 출력 =====");

        assertTrue("전체 적금이 조회되어야 합니다.", (size > 0));
    }

    @Test
    @DisplayName("적금 업데이트")
    @Transactional
    public void 적금_업데이트() throws Exception {
        //given
        Long childId = 2L;
        Saving existingSaving = savingRepository.findById(childId).get();
        int count = existingSaving.getCount();

        //when
        existingSaving.updateSaving(count - 1);
        em.flush();
        em.clear();

        //then
        Saving findSaving = savingRepository.findById(childId).get();
        int findCount = findSaving.getCount();

        System.out.println("===== 적금 정보 출력 =====");
        System.out.println("findSaving_id: " + findSaving.getId() +
            " start_date: " + findSaving.getStartDate() +
            " count: " + findSaving.getCount() +
            " payment: " + findSaving.getPayment() +
            " rate: " + findSaving.getRate());
        System.out.println("===== 적금 정보 출력 =====");

        assertEquals("적금 납입할 횟수가 감소해야 합니다.", count - 1, findCount);
    }

    @Test(expected = NoSuchElementException.class)
    @DisplayName("적금 삭제")
    @Transactional
    public void 적금_삭제() throws Exception {
        //given
        Long childId = 2L;
        Saving existingSaving = savingRepository.findById(childId).get();

        //when
        savingRepository.delete(existingSaving);
        em.flush();
        em.clear();

        //then
        existingSaving = savingRepository.findById(childId).get();

        fail("적금이 없어야 합니다.");
    }

    @Test
    @DisplayName("적금 스케줄러")
    @Transactional
    public void 적금_스케줄러() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        Long[] childId = new Long[5];
        SavingDto[] savingDto = new SavingDto[5];
        childId[0] = 2L;
        childId[1] = 3L;
        childId[2] = 5L;
        childId[3] = 6L;
        childId[4] = 7L;
        savingDto[0] = new SavingDto(now.minusDays(22), 2, 100, 0, childId[0]);
        savingDto[1] = new SavingDto(now.minusDays(22), 3, 100, 0, childId[1]);
        savingDto[2] = new SavingDto(now.minusDays(8), 4, 100, 0, childId[2]);
        savingDto[3] = new SavingDto(now.minusDays(1), 4, 100000, 0, childId[3]);
        savingDto[4] = new SavingDto(now.minusDays(10), 3, 100, 0, childId[4]);
        for (int i = 0; i < 5; i++) {
            savingService.createSaving(savingDto[i]);
        }

        savingRepository.findById(childId[0]).get().updateSaving(2);
        savingRepository.findById(childId[1]).get().updateSaving(3);
        savingRepository.findById(childId[2]).get().updateSaving(4);
        savingRepository.findById(childId[3]).get().updateSaving(4);
        savingRepository.findById(childId[4]).get().updateSaving(3);

        //when
        System.out.println("==== updateSaving ====");
        savingService.updateSaving();
        System.out.println("==== updateSaving ====");

        // then
        boolean[] check = new boolean[15];
        check[0] = savingRepository.findById(childId[0]).isEmpty();
        check[1] = savingRepository.findById(childId[1]).isEmpty();
        check[2] = savingRepository.existsById(childId[2]);
        check[3] = savingRepository.existsById(childId[3]);
        check[4] = savingRepository.existsById(childId[4]);
        check[5] = !savingHistoryRepository.existsById(childId[0]);
        check[6] = !savingHistoryRepository.existsById(childId[1]);
        check[7] = !savingHistoryRepository.findSavingHistoriesByChildId(childId[2]).isEmpty();
        check[8] = !savingHistoryRepository.findSavingHistoriesByChildId(childId[3]).isEmpty();
        check[9] = !savingHistoryRepository.existsById(childId[4]);
        check[10] = depositRepository.findDepositsByChildId(childId[0]).size() == 2;
        check[11] = depositRepository.findDepositsByChildId(childId[1]).size() == 1;
        check[12] = depositRepository.findDepositsByChildId(childId[2]).size() == 1;
        check[13] = depositRepository.findDepositsByChildId(childId[3]).isEmpty();
        check[14] = depositRepository.findDepositsByChildId(childId[4]).isEmpty();

        boolean pass = true;

        for (int i = 0; i < 15; i++) {
            System.out.println(i + " " + check[i]);
            if (!check[i]) {
                pass = false;
            }
        }

        if (pass) {
            System.out.println("적금 스케줄러 성공");
        } else {
            fail("적금 스케줄러 실패");
        }
    }

}