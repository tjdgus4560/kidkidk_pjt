package com.ssafy.kdkd.service.saving;

import com.ssafy.kdkd.domain.dto.deposit.DepositDto;
import com.ssafy.kdkd.domain.dto.saving.SavingDto;
import com.ssafy.kdkd.domain.dto.saving.SavingHistoryDto;
import com.ssafy.kdkd.domain.entity.deposit.Deposit;
import com.ssafy.kdkd.domain.entity.saving.Saving;
import com.ssafy.kdkd.domain.entity.saving.SavingHistory;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.deposit.DepositRepository;
import com.ssafy.kdkd.repository.saving.SavingHistoryRepository;
import com.ssafy.kdkd.repository.saving.SavingRepository;
import com.ssafy.kdkd.service.user.ChildService;

import static com.ssafy.kdkd.domain.entity.deposit.Deposit.createDeposit;
import static com.ssafy.kdkd.domain.entity.saving.SavingHistory.createSavingHistory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
public class SavingService {

    private final SavingRepository savingRepository;
    private final SavingHistoryRepository savingHistoryRepository;
    private final DepositRepository depositRepository;
    private final ChildService childService;

    /**
     * 적금 생성
     * 
     * @param savingDto 적금생성을 위한 입력값
     * @return SavingDto 생성된 적금정보
     */
    @Transactional
    public SavingDto createSaving(SavingDto savingDto) {
        Long childId = savingDto.getChildId();
        Optional<Child> findChild = childService.findChild(childId);

        if (findChild.isEmpty() | savingRepository.existsById(childId)) {
            return null;
        }

        Child child = findChild.get();
        int count = 4;
        int rate = 5;

        savingDto.updateSavingDto(count, rate);
        Saving saving = Saving.createSaving(savingDto);
        saving.setChild(child);
        savingRepository.save(saving);
        return savingDto;
    }

    /**
     * 적금 스케줄러
     * 상세:
     * 1. 적금 납입
     * 2. 적금, 적금 내역 업데이트
     * 3. 자식 코인 업데이트
     * 4. deposit에 내역 업데이트
     */
    @Transactional
    public void updateSaving() {
        List<Saving> list = savingRepository.findAll();

        for (Saving saving : list) {
            LocalDateTime startDate = saving.getStartDate();
            LocalDateTime currentDate = LocalDateTime.now();
            long daysBetween = ChronoUnit.DAYS.between(startDate, currentDate) - 1;

            if (daysBetween % 7 == 0) {
                Long childId = saving.getId();
                Optional<Child> findChild = childService.findChild(childId);

                if (findChild.isEmpty()) {
                    log.info("적금 스케줄러 실패");
                    return;
                }

                Child child = findChild.get();
                int requiredCount = 4;
                int payment = saving.getPayment();
                int coin = child.getCoin();
                int leftChance = requiredCount - ((int)daysBetween / 7);
                int isPaymentCompleted = coin < payment ? 0 : 1;
                int count = saving.getCount();
                int updateCount = count - isPaymentCompleted;
                int paidCount = requiredCount - updateCount;
                int state = leftChance + paidCount;
                boolean isNotTerminatedForcibly = state > 2;
                int updateCoin = coin;
                int money = payment;
                String detail = "적금";
                boolean type = true;

                if (leftChance > 0 && isNotTerminatedForcibly) {
                    saving.updateSaving(updateCount);

                    updateCoin -= payment;
                    int updateAmount = payment;
                    if (isPaymentCompleted == 1) {
                        detail += " 납입";
                    } else {
                        detail += " 미납";
                        updateAmount = 0;
                        updateCoin = coin;
                    }
                    SavingHistoryDto savingHistoryDto = new SavingHistoryDto(LocalDateTime.now(), detail, type, updateAmount, childId);
                    SavingHistory savingHistory = createSavingHistory(savingHistoryDto);
                    savingHistory.setChild(child);
                    savingHistoryRepository.save(savingHistory);
                    type = false;
                } else {
                    if (isNotTerminatedForcibly) {
                        double rate = (100D + saving.getRate()) / 100;
                        int amount = paidCount * payment;
                        money = (int)Math.floor(amount * rate);

                        updateCoin -= payment;
                        DepositDto depositDto = new DepositDto(LocalDateTime.now(), detail + " 납입", false, payment, updateCoin, childId);
                        Deposit deposit = createDeposit(depositDto);
                        deposit.setChild(child);
                        depositRepository.save(deposit);
                        updateCoin += money;
                        detail += " 만기";
                    } else {
                        money = paidCount * payment;
                        updateCoin += money;
                        detail += " 해지";
                    }

                    savingRepository.delete(saving);
                    savingHistoryRepository.deleteSavingHistoriesByChildId(childId);
                }

                child.updateChild(updateCoin);

                if (updateCoin != coin) {
                    DepositDto depositDto = new DepositDto(LocalDateTime.now(), detail, type, money, updateCoin,
                        childId);
                    Deposit deposit = createDeposit(depositDto);
                    deposit.setChild(child);
                    depositRepository.save(deposit);
                }
            }
        }
    }

}
