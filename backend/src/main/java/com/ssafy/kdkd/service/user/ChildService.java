package com.ssafy.kdkd.service.user;

import com.ssafy.kdkd.domain.dto.account.ChildDto;
import com.ssafy.kdkd.domain.entity.account.Profile;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.user.ChildRepository;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ChildService {

    @Autowired
    private final ChildRepository childRepository;

    public Optional<Child> findChild(Long id) {
        return childRepository.findById(id);
    }

    @Transactional
    public void childUpdate(ChildDto childDto) {
        Long childId = childDto.getChildId();
        int coin = 0;
        int fundMoney = 0;

        Optional<Child> childUpdate = childRepository.findById(childId);
        if (childUpdate.isPresent()) {
            Child child = childUpdate.get();
            coin = child.getCoin();
            fundMoney = child.getFundMoney();
        }else{

        }
        int updateCoin = childDto.getCoin();
        int updateFundMoney = childDto.getFundMoney();

        if(updateCoin != 0){
            coin = updateCoin;
        }

        if(updateFundMoney != 0){
            fundMoney = updateFundMoney;
        }


        childRepository.childUpdate(childId, coin, fundMoney);
    }
}
