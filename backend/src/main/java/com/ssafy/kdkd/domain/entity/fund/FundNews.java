package com.ssafy.kdkd.domain.entity.fund;

import com.ssafy.kdkd.domain.dto.fund.FundNewsDto;
import com.ssafy.kdkd.domain.entity.user.Child;

import static jakarta.persistence.FetchType.LAZY;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fund_news")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fund_news_id")
    private Long id;

    @Column(name = "data_log")
    private LocalDateTime dataLog;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "child_id")
    private Child child;

    /**
     * 연관관계 메서드
     */
    public void setChild(Child child) {
        this.child = child;
    }

    /**
     * 투자내역 생성
     */
    public static FundNews createFundNews(FundNewsDto fundNewsDto) {
        FundNews fundNews = new FundNews();
        fundNews.dataLog = fundNewsDto.getDataLog();
        fundNews.content = fundNewsDto.getContent();
        return fundNews;
    }

}
