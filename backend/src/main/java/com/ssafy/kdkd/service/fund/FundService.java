package com.ssafy.kdkd.service.fund;

import com.ssafy.kdkd.domain.dto.deposit.DepositDto;
import com.ssafy.kdkd.domain.dto.fund.TransferDto;
import com.ssafy.kdkd.domain.entity.deposit.Deposit;
import com.ssafy.kdkd.domain.entity.fund.Fund;
import com.ssafy.kdkd.domain.entity.fund.FundReservation;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.deposit.DepositRepository;
import com.ssafy.kdkd.repository.fund.FundRepository;
import com.ssafy.kdkd.service.user.ChildService;

import static com.ssafy.kdkd.domain.entity.deposit.Deposit.createDeposit;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FundService {

    private final FundRepository fundRepository;
    private final ChildService childService;
    private final DepositRepository depositRepository;

    /**
     * 투자예약 -> 투자 생성
     *
     * @param fundReservation 투자예약
     */
    @Transactional
    public void updateFund(FundReservation fundReservation) {
        Long childId = fundReservation.getId();

        Child child = childService.findChild(childId).get();
        Optional<Fund> findFund = fundRepository.findById(childId);
        if (findFund.isEmpty()) {
            Fund fund = Fund.createFund(fundReservation);
            fund.setChild(child);
            fundRepository.save(fund);
        } else {
            Fund fund = findFund.get();
            fund.updateFund(fundReservation);
        }
    }

    /**
     * 투자계좌 -> 예금계좌 이체
     *
     * @param transferDto 이체 금액, 자식 아이디
     * @return boolean 이체 성공 여부
     */
    @Transactional
    public boolean transferToCoin(TransferDto transferDto) {
        int fundMoney = transferDto.getFundMoney();
        Long childId = transferDto.getChildId();
        Optional<Child> findChild = childService.findChild(childId);

        if (findChild.isEmpty()) {
            return false;
        }

        Child child = findChild.get();
        int updateFundMoney = child.getFundMoney() - fundMoney;
        int updateCoin = child.getCoin() + fundMoney;
        if (updateFundMoney < 0) {
            return false;
        }

        child.updateFundMoney(updateFundMoney);
        child.updateChild(updateCoin);

        String detail = "투자 입금";
        boolean type = true;

        DepositDto depositDto = new DepositDto(LocalDateTime.now(), detail, type, fundMoney, updateCoin, childId);
        Deposit deposit = createDeposit(depositDto);
        deposit.setChild(child);
        depositRepository.save(deposit);
        return true;
    }

}
