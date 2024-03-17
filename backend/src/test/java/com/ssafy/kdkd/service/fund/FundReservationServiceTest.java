package com.ssafy.kdkd.service.fund;

import com.ssafy.kdkd.domain.dto.fund.FundReservationDto;
import com.ssafy.kdkd.domain.entity.fund.FundReservation;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.fund.FundReservationRepository;
import com.ssafy.kdkd.service.user.ChildService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import jakarta.persistence.EntityManager;

import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(readOnly = true)
public class FundReservationServiceTest {

    @Autowired FundReservationService fundReservationService;
    @Autowired FundReservationRepository fundReservationRepository;
    @Autowired ChildService childService;
    @Autowired EntityManager em;

    @Test
    @DisplayName("투자생성 예약")
    @Transactional
    public void 투자생성_예약() throws Exception {
        //given
        Long childId = 2L;
        Child child = childService.findChild(childId).get();
        boolean type = true;

        //when
        FundReservationDto fundReservationDto = new FundReservationDto("test fund reservation", "test content", 1, false, childId);
        fundReservationService.createFundReservation(fundReservationDto, type);

        //then
        FundReservation findFundReservation = fundReservationRepository.findById(childId).get();

        System.out.println("===== 투자예약 정보 출력 =====");
        System.out.println("fund_id: " + findFundReservation.getId() +
            " name: " + findFundReservation.getName() +
            " content: " + findFundReservation.getContent() +
            " yield: " + findFundReservation.getYield() +
            " state: " + findFundReservation.isState());
        System.out.println("===== 투자예약 정보 출력 =====");

        assertEquals("생성된 투자예약의 자식 아이디가 동일해야 합니다.", childId, findFundReservation.getId());
    }

    @Test
    @DisplayName("투자예약 생성")
    @Transactional
    public void 투자예약_생성() throws Exception {
        //given
        Long childId = 2L;
        Child child = childService.findChild(childId).get();
        boolean type = false;

        //when
        FundReservationDto fundReservationDto = new FundReservationDto("test fund reservation", "test content", 1, false, childId);
        fundReservationService.createFundReservation(fundReservationDto, type);

        //then
        FundReservation findFundReservation = fundReservationRepository.findById(childId).get();

        System.out.println("===== 투자예약 정보 출력 =====");
        System.out.println("fund_id: " + findFundReservation.getId() +
            " name: " + findFundReservation.getName() +
            " content: " + findFundReservation.getContent() +
            " yield: " + findFundReservation.getYield() +
            " state: " + findFundReservation.isState());
        System.out.println("===== 투자예약 정보 출력 =====");

        assertEquals("생성된 투자예약의 자식 아이디가 동일해야 합니다.", childId, findFundReservation.getId());
    }

    @Test
    @DisplayName("투자 예약 조회")
    public void 투자예약_조회() throws Exception {
        //given
        Long childId = 2L;

        //when
        FundReservation findFundReservation = fundReservationRepository.findById(childId).get();

        //then
        System.out.println("===== 투자예약 정보 출력 =====");
        System.out.println("fund_id: " + findFundReservation.getId() +
            " name: " + findFundReservation.getName() +
            " content: " + findFundReservation.getContent() +
            " yield: " + findFundReservation.getYield() +
            " state: " + findFundReservation.isState());
        System.out.println("===== 투자예약 정보 출력 =====");

        assertEquals("조회된 투자예약의 자식 아이디가 동일해야 합니다.", childId, findFundReservation.getId());
    }

    @Test
    @DisplayName("투자예약 수정")
    @Transactional
    public void 투자예약_수정() throws Exception {
        //given
        Long childId = 2L;
        FundReservation existingFundReservation = fundReservationRepository.findById(childId).get();

        //when
        FundReservationDto fundReservationDto = new FundReservationDto(
            existingFundReservation.getName(), "새로운 투자예약", existingFundReservation.getYield(), false,
            existingFundReservation.getId()
        );

        fundReservationService.modifyFundReservation(fundReservationDto);
        em.flush();
        em.clear();

        //then
        FundReservation findModifiedFundReservation = fundReservationRepository.findById(childId).get();
        System.out.println("===== 투자예약 정보 출력 =====");
        System.out.println("fund_id: " + findModifiedFundReservation.getId() +
            " name: " + findModifiedFundReservation.getName() +
            " content: " + findModifiedFundReservation.getContent() +
            " yield: " + findModifiedFundReservation.getYield() +
            " state: " + findModifiedFundReservation.isState() +
            " child_id: " + findModifiedFundReservation.getChild().getId());
        System.out.println("===== 투자예약 정보 출력 =====");

        assertEquals("수정된 투자예약의 자식 아이디가 동일해야 합니다.", childId, findModifiedFundReservation.getChild().getId());
    }

    @Test
    @DisplayName("투자삭제 예약(성공)")
    @Transactional
    public void 투자삭제_예약_성공() throws Exception {
        //given
        Long childId = 2L;

        //when
        FundReservationDto result = fundReservationService.deleteFundReservation(childId);

        //then
        FundReservation fundReservation = fundReservationRepository.findById(childId).get();
        System.out.println("===== 투자예약 정보 출력 =====");
        System.out.println("fund_id: " + fundReservation.getId() +
            " name: " + fundReservation.getName() +
            " content: " + fundReservation.getContent() +
            " yield: " + fundReservation.getYield() +
            " state: " + fundReservation.isState() +
            " child_id: " + fundReservation.getChild().getId());
        System.out.println("===== 투자예약 정보 출력 =====");

        assertFalse("투자삭제 예약이 등록되어야 합니다.", fundReservation.isState());
    }

    @Test
    @DisplayName("투자삭제 예약(실패)")
    @Transactional
    public void 투자삭제_예약_실패() throws Exception {
        //given
        Long childId = 2L;

        //when
        FundReservationDto result = fundReservationService.deleteFundReservation(childId);
        em.flush();
        em.clear();

        //then
        if (result != null) {
            fail("투자삭제 예약이 실패하여야 합니다.");
        }
    }

    @Test(expected = NoSuchElementException.class)
    @DisplayName("투자예약 삭제")
    @Transactional
    public void 투자예약_삭제() throws Exception {
        //given
        Long childId = 2L;

        //when
        fundReservationRepository.deleteById(childId);
        em.flush();
        em.clear();

        //then
        FundReservation findFundReservation = fundReservationRepository.findById(childId).get();

        fail("투자예약이 없어야 합니다.");
    }

}