package com.ssafy.kdkd.service.deposit;

import com.ssafy.kdkd.domain.dto.deposit.DepositDto;
import com.ssafy.kdkd.domain.entity.deposit.Deposit;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.deposit.DepositRepository;
import com.ssafy.kdkd.service.user.ChildService;

import static com.ssafy.kdkd.domain.entity.deposit.Deposit.createDeposit;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(readOnly = true)
public class DepositServiceTest {

    @Autowired DepositService depositService;
    @Autowired DepositRepository depositRepository;
    @Autowired ChildService childService;
    @Autowired EntityManager em;

    @Test
    @DisplayName("내역 생성")
    @Transactional
    public void 내역_생성() throws Exception {
        //given
        Long childId = 2L;
        Child child = childService.findChild(childId).get();

        //when
        DepositDto depositDto
            = new DepositDto(LocalDateTime.now(), "test detail", true, 1000, 1000, childId);
        Deposit deposit = createDeposit(depositDto);
        deposit.setChild(child);
        depositRepository.save(deposit);
        em.flush();

        //then
        List<Deposit> findDeposits = depositService.findDepositsByChildId(childId);
        int size = findDeposits.size();

        System.out.println("===== 내역 출력 =====");
        for (Deposit d : findDeposits) {
            System.out.println("deposit_id: "+ d.getId() +
                " dataLog: " + d.getDataLog() +
                " detail: " + d.getDetail() +
                " type: " + d.isType() +
                " amount: " + d.getAmount() +
                " money: " + d.getMoney() +
                " child_id: " + d.getChild().getId());
        }
        System.out.println("===== 내역 출력 =====");
        assertEquals("생성된 내역 갯수는 1개 입니다.", 1, size);
    }

    @Test
    @DisplayName("내역 조회")
    @Transactional
    public void 내역_조회() throws Exception {
        //given
        Long childId = 2L;
        Child child = childService.findChild(childId).get();
        int size = 100;

        for (int i = 0; i < size; i++) {
            createTestDeposit(i, childId, child);
        }
        em.flush();

        //when
        List<Deposit> depositList = depositService.findDepositsByChildId(childId);

        //then
        int findCount = depositList.size();
        System.out.println("===== 내역 출력 =====");
        for (Deposit deposit : depositList) {
            System.out.println("deposit_id: "+ deposit.getId() +
                " dataLog: " + deposit.getDataLog() +
                " detail: " + deposit.getDetail() +
                " type: " + deposit.isType() +
                " amount: " + deposit.getAmount() +
                " money: " + deposit.getMoney() +
                " child_id: " + deposit.getChild().getId());
        }
        System.out.println("===== 내역 출력 =====");
        assertEquals("조회된 내역 갯수는 100개 입니다.", 100, findCount);
    }

    private void createTestDeposit(int i, Long childId, Child child) {
        //내역 생성
        DepositDto depositDto =
            new DepositDto(LocalDateTime.now(), i + " test detail", true, 1000, 1000, childId);
        Deposit deposit = createDeposit(depositDto);
        deposit.setChild(child);
        em.persist(deposit);
    }
    
}