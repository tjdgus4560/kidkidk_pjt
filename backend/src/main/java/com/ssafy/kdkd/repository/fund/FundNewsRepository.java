package com.ssafy.kdkd.repository.fund;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.kdkd.domain.entity.fund.FundNews;

public interface FundNewsRepository extends JpaRepository<FundNews, Long> {

    List<FundNews> findFundNewsByChildId(Long childId);

}
