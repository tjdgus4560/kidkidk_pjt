package com.ssafy.kdkd.repository.deposit;

import com.ssafy.kdkd.domain.entity.deposit.Deposit;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

    List<Deposit> findDepositsByChildId(Long childId);

}
