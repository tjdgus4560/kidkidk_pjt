package com.ssafy.kdkd.service.fund;

import com.ssafy.kdkd.domain.dto.fund.FundStatusDto;
import com.ssafy.kdkd.domain.entity.fund.Fund;
import com.ssafy.kdkd.domain.entity.fund.FundStatus;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.fund.FundRepository;
import com.ssafy.kdkd.repository.fund.FundStatusRepository;
import com.ssafy.kdkd.repository.user.ChildRepository;
import com.ssafy.kdkd.service.user.ChildService;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

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
public class FundStatusServiceTest {

    @Autowired FundStatusService fundStatusService;
    @Autowired FundStatusRepository fundStatusRepository;
    @Autowired FundService fundService;
    @Autowired FundRepository fundRepository;
    @Autowired ChildRepository childRepository;
    @Autowired ChildService childService;
    @Autowired EntityManager em;

    @Test
    @DisplayName("상태 조회")
    public void 상태_조회() throws Exception {
        //given
        Long fundId = 2L;

        //when
        FundStatus findFundStatus = fundStatusRepository.findById(fundId).get();

        //then
        System.out.println("===== 투자 상태 출력 =====");
        System.out.println("fund_status_id: "+ findFundStatus.getFund().getId() +
            " submit: " + findFundStatus.isSubmit() +
            " answer: " + findFundStatus.isAnswer());
        System.out.println("===== 투자 상태 출력 =====");
        assertEquals("찾은 FundStatus의 아이디는 fundId와 같아야 합니다.", fundId, findFundStatus.getFund().getId());
    }

    @Test
    @DisplayName("상태 전체 조회")
    public void 상태_전체_조회() throws Exception {
        //given
        int size = 3;

        //when
        List<FundStatus> findFundStatuses = fundStatusRepository.findAll();
        int result = findFundStatuses.size();
        em.flush();
        em.clear();

        //then
        for (FundStatus findFundStatus : findFundStatuses) {
            System.out.println("===== 투자 상태 출력 =====");
            System.out.println("fund_status_id: "+ findFundStatus.getFund().getId() +
                " submit: " + findFundStatus.isSubmit() +
                " answer: " + findFundStatus.isAnswer());
            System.out.println("===== 투자 상태 출력 =====");
        }

        assertEquals("찾은 투자상태의 갯수는 3개 입니다.", size, result);
    }

    @Test
    @DisplayName("상태 생성(자식)")
    @Transactional
    public void 상태_생성_자식() throws Exception {
        //given
        Long childId = 7L;
        Fund findFund = fundRepository.findById(childId).get();
        Long fundId = findFund.getId();

        //when
        FundStatusDto fundStatusDto = new FundStatusDto(1001, false, true, childId);
        boolean b = fundStatusService.setFundStatus(fundStatusDto, true);
        em.flush();
        em.clear();

        //then
        Optional<FundStatus> findFundStatus = fundStatusRepository.findById(fundId);
        boolean result = b && findFundStatus.isEmpty();

        assertTrue("투자 상태가 생성되어야 합니다.", result);
    }

    @Test
    @DisplayName("상태 생성(부모)")
    @Transactional
    public void 상태_생성_부모() throws Exception {
        //given
        Long childId = 7L;
        Fund findFund = fundRepository.findById(childId).get();
        Long fundId = findFund.getId();

        //when
        FundStatusDto fundStatusDto = new FundStatusDto(1000, false, true, childId);
        fundStatusService.setFundStatus(fundStatusDto, false);
        em.flush();
        em.clear();

        //then
        FundStatus findFundStatus = fundStatusRepository.findById(fundId).get();
        boolean result = findFundStatus.isAnswer() && findFundStatus.getId() == childId;

        System.out.println("===== 투자 상태 출력 =====");
        System.out.println("fund_status_id: "+ findFundStatus.getFund().getId() +
            " submit: " + findFundStatus.isSubmit() +
            " answer: " + findFundStatus.isAnswer() +
            " amount: " + findFundStatus.getAmount());
        System.out.println("===== 투자 상태 출력 =====");

        assertTrue("투자 상태가 생성되어야 합니다.", result);
    }

    @Test
    @DisplayName("상태 업데이트(부모)")
    @Transactional
    public void 상태_업데이트_부모() throws Exception {
        //given
        Long childId = 2L;
        Child child = childRepository.findById(childId).get();
        Fund findFund = fundRepository.findById(childId).get();
        FundStatus fundStatus = fundStatusRepository.findById(childId).get();
        Long fundId = findFund.getId();

        //when
        FundStatusDto fundStatusDto = new FundStatusDto(1000, false, false, childId);
        fundStatusService.updateStatus(fundStatusDto, fundStatus, child.getFundMoney(), false);
        em.flush();
        em.clear();

        //then
        FundStatus findFundStatus = fundStatusRepository.findById(fundId).get();
        boolean result = !findFundStatus.isAnswer() && findFundStatus.isSubmit() &&
            findFundStatus.getAmount() == 1 && findFundStatus.getId() == childId;

        System.out.println("===== 투자 상태 출력 =====");
        System.out.println("fund_status_id: "+ findFundStatus.getFund().getId() +
            " submit: " + findFundStatus.isSubmit() +
            " answer: " + findFundStatus.isAnswer() +
            " amount: " + findFundStatus.getAmount());
        System.out.println("===== 투자 상태 출력 =====");

        assertTrue("투자상태가 업데이트 되어야 합니다.", result);
    }

    @Test
    @DisplayName("상태 업데이트(자식)")
    @Transactional
    public void 상태_업데이트_자식() throws Exception {
        //given
        Long childId = 2L;
        Child child = childRepository.findById(childId).get();
        Fund findFund = fundRepository.findById(childId).get();
        FundStatus fundStatus = fundStatusRepository.findById(childId).get();
        Long fundId = findFund.getId();

        //when
        FundStatusDto fundStatusDto = new FundStatusDto(1000, false, false, childId);
        fundStatusService.updateStatus(fundStatusDto, fundStatus, child.getFundMoney(), true);
        em.flush();
        em.clear();

        //then
        FundStatus findFundStatus = fundStatusRepository.findById(fundId).get();
        boolean result = findFundStatus.isAnswer() && !findFundStatus.isSubmit() &&
            findFundStatus.getAmount() == 1000 && findFundStatus.getId() == childId;

        System.out.println("===== 투자 상태 출력 =====");
        System.out.println("fund_status_id: "+ findFundStatus.getFund().getId() +
            " submit: " + findFundStatus.isSubmit() +
            " answer: " + findFundStatus.isAnswer() +
            " amount: " + findFundStatus.getAmount());
        System.out.println("===== 투자 상태 출력 =====");

        assertTrue("투자상태가 업데이트 되어야 합니다.", result);
    }

    @Test
    @DisplayName("상태 업데이트(자식 한도초과)")
    @Transactional
    public void 상태_업데이트_자식_한도초과() throws Exception {
        //given
        Long childId = 2L;
        Child child = childRepository.findById(childId).get();
        Fund findFund = fundRepository.findById(childId).get();
        FundStatus fundStatus = fundStatusRepository.findById(childId).get();
        Long fundId = findFund.getId();

        //when
        FundStatusDto fundStatusDto = new FundStatusDto(10001, false, false, childId);
        boolean result = fundStatusService.updateStatus(fundStatusDto, fundStatus, child.getFundMoney(), true);
        em.flush();
        em.clear();

        //then

        assertTrue("투자상태 업데이트에 실패하여야 합니다.", result);
    }

    @Test
    @DisplayName("상태 전체 삭제")
    @Transactional
    public void 상태전체_삭제() throws Exception {
        //when
        fundStatusRepository.deleteAll();

        //then
        List<FundStatus> findFundStatuses = fundStatusRepository.findAll();
        int size = findFundStatuses.size();
        em.flush();
        em.clear();

        assertEquals("찾은 투자상태의 갯수는 0개 입니다.", 0, size);
    }

}