package com.ssafy.kdkd.service.deposit;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.kdkd.domain.entity.deposit.Deposit;
import com.ssafy.kdkd.repository.deposit.DepositRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DepositService {

    private final DepositRepository depositRepository;

    public List<Deposit> findDepositsByChildId(Long childId) {
        return depositRepository.findDepositsByChildId(childId);
    }

}
