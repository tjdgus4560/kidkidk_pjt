package com.ssafy.kdkd.service.fund;

import com.ssafy.kdkd.domain.dto.fund.FundNewsDto;
import com.ssafy.kdkd.domain.entity.fund.FundNews;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.fund.FundNewsRepository;
import com.ssafy.kdkd.repository.user.ChildRepository;

import static com.ssafy.kdkd.domain.entity.fund.FundNews.createFundNews;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FundNewsService {

    private final ChildRepository childRepository;
    private final FundNewsRepository fundNewsRepository;

    @Transactional
    public FundNewsDto insertFundNews(FundNewsDto fundNewsDto) {
        Long childId = fundNewsDto.getChildId();
        Optional<Child> findChild = childRepository.findById(childId);

        if (findChild.isEmpty()) {
            return null;
        }

        Child child = findChild.get();
        fundNewsDto.setDataLog(LocalDateTime.now());
        FundNews fundNews = createFundNews(fundNewsDto);
        fundNews.setChild(child);
        fundNewsRepository.save(fundNews);
        return fundNewsDto;
    }

}
