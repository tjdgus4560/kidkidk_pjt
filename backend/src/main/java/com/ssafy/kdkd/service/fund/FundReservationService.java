package com.ssafy.kdkd.service.fund;

import com.ssafy.kdkd.domain.dto.fund.FundReservationDto;
import com.ssafy.kdkd.domain.entity.fund.Fund;
import com.ssafy.kdkd.domain.entity.fund.FundReservation;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.fund.FundRepository;
import com.ssafy.kdkd.repository.fund.FundReservationRepository;
import com.ssafy.kdkd.service.user.ChildService;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FundReservationService {

    private final FundService fundService;
    private final FundRepository fundRepository;
    private final FundReservationRepository fundReservationRepository;
    private final ChildService childService;

    /**
     * 투자예약 생성
     *
     * @param fundReservationDto 투자예약 생성
     * @return fundReservationDto 생성된 투자예약
     */
    @Transactional
    public FundReservationDto createFundReservation(FundReservationDto fundReservationDto, boolean type) {
        Long childId = fundReservationDto.getChildId();

        if (fundReservationRepository.existsById(childId)) {
            return null;
        }

        if (type && fundRepository.existsById(childId)) {
            return null;
        }

        Optional<Child> findChild = childService.findChild(childId);

        if (findChild.isEmpty()) {
            return null;
        }

        Child child = findChild.get();
        int rate = (int) Math.floor(Math.random() * 10);
        fundReservationDto.setYield(rate == 0 ? 1 : rate);
        fundReservationDto.setState(true);
        FundReservation fundReservation = FundReservation.createFundReservation(fundReservationDto);
        fundReservation.setChild(child);
        fundReservationRepository.save(fundReservation);
        if (type) {
            // 투자 처음 생성 -> 바로 생성
            fundService.updateFund(fundReservation);
        }
        return fundReservationDto;
    }

    /**
     * 투자예약 수정
     *
     * @param fundReservationDto 새로운 투자예약
     */
    @Transactional
    public FundReservationDto modifyFundReservation(FundReservationDto fundReservationDto) {
        Long childId = fundReservationDto.getChildId();
        Optional<FundReservation> existingFundReservation = fundReservationRepository.findById(childId);

        if (existingFundReservation.isEmpty()) {
            return null;
        }

        if (childService.findChild(childId).isEmpty()) {
            return null;
        }

        FundReservation reservation = existingFundReservation.get();
        reservation.updateFundReservation(fundReservationDto);
        fundReservationRepository.save(reservation);
        return fundReservationDto;
    }

    /**
     * 투자 삭제 예약
     *
     * @param childId 자식 아이디
     * @return FundReservationDto 투자예약(삭제 예약 상태 = state가 false)
     */
    @Transactional
    public FundReservationDto deleteFundReservation(Long childId) {
        Optional<Child> findChild = childService.findChild(childId);

        if (findChild.isEmpty()) {
            return null;
        }

        Optional<Fund> fund = fundRepository.findById(childId);

        if (fund.isEmpty()) {
            return null;
        }

        Child child = findChild.get();
        Fund existingFund = fund.get();
        FundReservationDto fundReservationDto =
            new FundReservationDto(existingFund.getName(), existingFund.getContent(), existingFund.getYield(), false, childId);

        Optional<FundReservation> findFundReservation = fundReservationRepository.findById(childId);

        if (findFundReservation.isEmpty()) {
            FundReservation fundReservation = FundReservation.createFundReservation(fundReservationDto);
            fundReservation.setChild(child);
            fundReservationRepository.save(fundReservation);
        } else {
            FundReservation fundReservation = findFundReservation.get();
            fundReservation.updateFundReservation(fundReservationDto);
        }

        return fundReservationDto;
    }

}
