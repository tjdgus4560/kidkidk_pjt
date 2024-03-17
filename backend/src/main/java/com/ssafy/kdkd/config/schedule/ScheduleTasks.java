package com.ssafy.kdkd.config.schedule;

import com.ssafy.kdkd.service.fund.FundUpdateService;
import com.ssafy.kdkd.service.job.JobService;
import com.ssafy.kdkd.service.saving.SavingService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleTasks {

    private final SavingService savingService;
    private final FundUpdateService fundUpdateService;
    private final JobService jobService;

    /**
     * 직업 스케줄러
     * 상세: 매주 월요일 오전 09시
     */
    @Scheduled(cron = "0 0 9 * * MON")
    public void scheduleJob() {
        log.info("schedule: scheduleJob() Enter");
        try {
            jobService.updateJob();
        } catch (Exception e) {
            log.info(e.toString());
        }
    }

    /**
     * 투자 스케줄러
     * 상세: 매일 오전 09시
     */
    @Scheduled(cron = "0 0 9 * * *")
    public void scheduleFund() {
        log.info("schedule: scheduleFund() Enter");
        try {
            fundUpdateService.updateFund();
        } catch (Exception e) {
            log.info(e.toString());
        }
    }

    /**
     * 적금 스케줄러
     * 상세: 매일 오전 09시
     */
    @Scheduled(cron = "0 0 9 * * *")
    public void scheduleSaving() {
        log.info("schedule: scheduleSaving() Enter");
        try {
            savingService.updateSaving();
        } catch (Exception e) {
            log.info(e.toString());
        }
    }

}

