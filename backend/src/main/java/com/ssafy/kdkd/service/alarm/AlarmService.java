package com.ssafy.kdkd.service.alarm;

import com.ssafy.kdkd.domain.dto.alarm.ExchangeRequestDto;
import com.ssafy.kdkd.domain.dto.alarm.JobRequestDto;
import com.ssafy.kdkd.domain.entity.job.Job;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.job.JobRepository;
import com.ssafy.kdkd.repository.user.ChildRepository;
import com.ssafy.kdkd.service.user.ChildService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlarmService {

    private final JobRepository jobRepository;
    private final ChildService childService;
    private final ChildRepository childRepository;

    @Transactional
    public void confirmTask(JobRequestDto jobRequestDto) {
        Long childId = jobRequestDto.getChildId();
        Job job = jobRepository.findById(childId).get();
        int updateDoneCount = job.getDoneCount() + 1;
        job.updateJob(updateDoneCount);
        jobRepository.save(job);
    }

    @Transactional
    public boolean confirmExchange(ExchangeRequestDto exchangeRequestDto) {
        int amount = exchangeRequestDto.getAmount();
        Long childId = exchangeRequestDto.getChildId();
        Child child = childService.findChild(childId).get();
        int coin = child.getCoin();
        int updateCoin = coin - amount;

        if (updateCoin < 0) {
            return false;
        }
        child.updateChild(updateCoin);
        childRepository.save(child);
        return true;
    }

}
