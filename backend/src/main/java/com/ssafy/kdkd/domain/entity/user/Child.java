package com.ssafy.kdkd.domain.entity.user;

import com.ssafy.kdkd.domain.entity.account.Profile;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "child")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Child {

    @Id
    private Long id;

    @Column(name = "coin")
    private int coin;

    @Column(name = "fund_money")
    private int fundMoney;

    @MapsId
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "child_id")
    private Profile profile;


    public Child(Profile profile) {
        this.profile = profile;
    }

    /**
     * 자식 업데이트
     */
    public void updateChild(int coin) {
        this.coin = coin;
    }

    public void updateFundMoney(int fundMoney) {
        this.fundMoney = fundMoney;
    }

    public void transferChild(int fundMoney) {
        this.fundMoney = fundMoney;
    }

}
