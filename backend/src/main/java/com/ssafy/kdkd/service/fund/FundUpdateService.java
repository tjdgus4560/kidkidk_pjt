package com.ssafy.kdkd.service.fund;

import com.ssafy.kdkd.domain.dto.fund.FundHistoryDto;
import com.ssafy.kdkd.domain.entity.fund.Fund;
import com.ssafy.kdkd.domain.entity.fund.FundHistory;
import com.ssafy.kdkd.domain.entity.fund.FundReservation;
import com.ssafy.kdkd.domain.entity.fund.FundStatus;
import com.ssafy.kdkd.domain.entity.fund.Roi;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.fund.FundHistoryRepository;
import com.ssafy.kdkd.repository.fund.FundRepository;
import com.ssafy.kdkd.repository.fund.FundReservationRepository;
import com.ssafy.kdkd.repository.fund.FundStatusRepository;
import com.ssafy.kdkd.repository.fund.RoiRepository;
import com.ssafy.kdkd.service.user.ChildService;

import static com.ssafy.kdkd.domain.entity.fund.FundHistory.createFundHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FundUpdateService {

    private final FundService fundService;
    private final FundRepository fundRepository;
    private final FundStatusRepository fundStatusRepository;
    private final FundReservationRepository fundReservationRepository;
    private final FundHistoryRepository fundHistoryRepository;
    private final RoiService roiService;
    private final RoiRepository roiRepository;
    private final ChildService childService;

    /**
     * 투자 스케줄러
     * 상세:
     * 1. 투자의 성공, 실패여부 반영
     * 2. 투자예약 정보 반영
     */
    @Transactional
    public void updateFund() {
        List<FundStatus> list = fundStatusRepository.findAll();

        for (FundStatus fundStatus : list) {
            Fund fund = fundStatus.getFund();
            Optional<Child> findChild = childService.findChild(fund.getId());

            if (findChild.isEmpty()) {
                log.info("투자 스케줄러 실패");
                return;
            }

            Child child = findChild.get();
            Long childId = child.getId();
            int amount = fundStatus.getAmount();

            if (amount <= 0) {
                return;
            }

            boolean answer = fundStatus.isAnswer();
            boolean submit = fundStatus.isSubmit();
            boolean isSuccess = answer == submit;
            int yield = fund.getYield();
            int sign = isSuccess ? 1 : -1;
            int add = isSuccess ? 1 : 0;
            double rate = (100D + sign * yield) / 100;
            int fundMoney = child.getFundMoney() - amount;
            int pnl = (int)Math.floor(amount * rate);
            int updateFundMoney = fundMoney + pnl;
            Optional<Roi> roi = roiRepository.findById(childId);

            // 투자계좌 결과 반영
            child.updateFundMoney(updateFundMoney);
            // fund_history 업데이트
            FundHistory fundHistory =
                createFundHistory(new FundHistoryDto(
                    LocalDateTime.now(),
                    amount,
                    yield,
                    pnl,
                    childId
                ));
            fundHistory.setChild(child);
            fundHistoryRepository.save(fundHistory);
            // roi 업데이트
            roiService.updateRoi(roi, add, child);
        }
        // fund_status 전체 삭제
        fundStatusRepository.deleteAll();

        // fund에 fund_reservation 반영
        List<FundReservation> reservationList = fundReservationRepository.findAll();
        for (FundReservation fundReservation : reservationList) {
            boolean isUpdate = fundReservation.isState();

            if (isUpdate) {
                fundService.updateFund(fundReservation);
            } else {
                fundRepository.deleteById(fundReservation.getId());
            }
        }
        // fund_reservation 전체 삭제
        fundReservationRepository.deleteAll();
    }

}
