package com.ssafy.kdkd.repository.user;

import com.ssafy.kdkd.domain.dto.account.ChildDto;
import com.ssafy.kdkd.domain.dto.account.ProfileLoginDto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.kdkd.domain.entity.user.Child;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChildRepository extends JpaRepository<Child, Long> {
	@Query("SELECT new com.ssafy.kdkd.domain.dto.account.ChildDto(c.id,c.coin,c.fundMoney) FROM Child c WHERE  c.id = :childId")
	ChildDto childDto(@Param("childId") Long id);

	@Modifying
	@Query("UPDATE Child c SET c.coin = :coin, c.fundMoney = :fund_money WHERE c.id = :child_id")
	void childUpdate(@Param("child_id") Long childId,@Param("coin") int coin, @Param("fund_money") int fundMoney);

}
