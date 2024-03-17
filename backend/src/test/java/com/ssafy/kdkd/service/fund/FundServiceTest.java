package com.ssafy.kdkd.service.fund;

import com.ssafy.kdkd.domain.dto.fund.FundReservationDto;
import com.ssafy.kdkd.domain.dto.fund.TransferDto;
import com.ssafy.kdkd.domain.entity.fund.Fund;
import com.ssafy.kdkd.domain.entity.fund.FundReservation;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.fund.FundRepository;
import com.ssafy.kdkd.repository.fund.FundReservationRepository;
import com.ssafy.kdkd.service.user.ChildService;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

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
public class FundServiceTest {

    @Autowired FundService fundService;
    @Autowired FundRepository fundRepository;
    @Autowired FundReservationRepository fundReservationRepository;
    @Autowired ChildService childService;
    @Autowired EntityManager em;

    @Test
    @DisplayName("투자금 이체(성공)")
    @Transactional
    public void 투자금_이체_성공() throws Exception {
        //given
        Long childId = 2L;
        Child child = childService.findChild(childId).get();
        int childFundMoney = child.getFundMoney();
        int childCoin = child.getCoin();
        int fundMoney = 9000;
        int updateFundMoney = childFundMoney - fundMoney;
        int updateCoin = childCoin + fundMoney;
        TransferDto transferDto = new TransferDto(fundMoney, childId);

        //when
        boolean b = fundService.transferToCoin(transferDto);
        Child afterChild = childService.findChild(childId).get();
        boolean result = b && afterChild.getFundMoney() == updateFundMoney && afterChild.getCoin() == updateCoin;

        //then
        assertTrue("이체가 성공하여야 합니다.", result);
    }

    @Test
    @DisplayName("투자금 이체(실패)")
    @Transactional
    public void 투자금_이체_실패() throws Exception {
        //given
        Long childId = 2L;
        int fundMoney = 90000;

        TransferDto transferDto = new TransferDto(fundMoney, childId);

        //when
        boolean result = fundService.transferToCoin(transferDto);

        //then
        assertFalse("이체가 실패하여야 합니다.", result);
    }

    @Test
    @DisplayName("투자 생성")
    @Transactional
    public void 투자_생성() throws Exception {
        //given
        Long childId = 2L;

        //when
        FundReservation fundReservation = fundReservationRepository.findById(childId).get();
        fundService.updateFund(fundReservation);
        em.flush();
        em.clear();

        //then
        Fund findFund = fundRepository.findById(childId).get();

        System.out.println("===== 투자 정보 출력 =====");
        System.out.println("fund_id: " + findFund.getId() +
                        " name: " + findFund.getName() +
                        " content: " + findFund.getContent() +
                        " yield: " +findFund.getYield());
        System.out.println("===== 투자 정보 출력 =====");

        assertEquals("조회된 투자의 자식 아이디가 동일해야 합니다.", childId, findFund.getId());
    }

    @Test
    public void 투자_조회() throws Exception {
        //given
        Long childId = 3L;

        //when
        Fund findFund = fundRepository.findById(childId).get();

        //then
        System.out.println("===== 투자 정보 출력 =====");
        System.out.println("fund_id: " + findFund.getId() +
            " name: " + findFund.getName() +
            " content: " + findFund.getContent() +
            " yield: " +findFund.getYield());
        System.out.println("===== 투자 정보 출력 =====");
        Long findChildId = findFund.getId();

        assertEquals("조회된 투자의 자식 아이디가 동일해야 합니다.", childId, findChildId);
    }

    @Test
    @DisplayName("투자 수정")
    @Transactional
    public void 투자_수정() throws Exception {
        //given
        Long childId = 3L;
        Fund existingFund = fundRepository.findById(childId).get();

        //when
        FundReservation fundReservation = fundReservationRepository.findById(childId).get();
        existingFund.updateFund(fundReservation);
        em.flush();
        em.clear();

        //then
        Fund findModifiedFund = fundRepository.findById(childId).get();
        System.out.println("===== 투자 정보 출력 =====");
        System.out.println("fund_id: " + findModifiedFund.getId() +
            " name: " + findModifiedFund.getName() +
            " content: " + findModifiedFund.getContent() +
            " yield: " + findModifiedFund.getYield() +
            " child_id: " + findModifiedFund.getChild().getId());
        System.out.println("===== 투자 정보 출력 =====");

        assertEquals("조회된 투자의 자식 아이디가 동일해야 합니다.", childId, findModifiedFund.getChild().getId());
    }

    @Test(expected = NoSuchElementException.class)
    @DisplayName("투자 삭제")
    @Transactional
    public void 투자_삭제() throws Exception {
        //given
        Long childId = 3L;
        Fund findFund = fundRepository.findById(childId).get();

        //when
        fundRepository.delete(findFund);

        //then
        findFund = fundRepository.findById(childId).get();
        System.out.println("===== 투자 정보 출력 =====");
        System.out.println("fund_id: " + findFund.getId() +
            " name: " + findFund.getName() +
            " content: " + findFund.getContent() +
            " yield: " + findFund.getYield() +
            " child_id: " + findFund.getChild().getId());
        System.out.println("===== 투자 정보 출력 =====");

        fail("투자가 없어야 합니다.");
    }

}