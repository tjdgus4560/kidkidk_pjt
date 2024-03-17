package com.ssafy.kdkd.repository.account;

import com.ssafy.kdkd.domain.dto.account.GetChildListDto;
import com.ssafy.kdkd.domain.dto.account.ProfileLoginDto;
import com.ssafy.kdkd.domain.dto.account.ProfileSelectAllDto;
import com.ssafy.kdkd.domain.dto.account.ProfileUpdateDto;
import com.ssafy.kdkd.domain.entity.account.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	@Query("SELECT new com.ssafy.kdkd.domain.dto.account.ProfileSelectAllDto(p.id,p.nickname,p.profileImage,p.type) FROM Profile p WHERE p.user.id = :user_id")
	List<ProfileSelectAllDto> profileSelectAllDto(@Param("user_id") Long user_id);

	@Query("SELECT new com.ssafy.kdkd.domain.dto.account.ProfileLoginDto(p.id,p.nickname,p.profileImage,p.type) FROM Profile p WHERE  p.id = :profileId AND p.pin = :pin")
	ProfileLoginDto profileLoginDto(@Param("profileId") Long id, @Param("pin") int pin);

	@Modifying
	@Query("UPDATE Profile p SET p.nickname = :nickname, p.pin = :pin, p.profileImage = :profileImage WHERE p.id = :profile_id")
	void profileUpdate(@Param("profile_id") Long profile_id, @Param("nickname") String nickname, @Param("pin") int pin, @Param("profileImage") String profileImage);

	@Query("SELECT new com.ssafy.kdkd.domain.dto.account.GetChildListDto(p.id,p.nickname) FROM Profile p WHERE p.user.id = :user_id AND p.type = false")
	List<GetChildListDto> getChildList(@Param("user_id") Long userId);

//	@Modifying
//	@Query("DELETE FROM Profile p WHERE p.id = :profile_id")
//	void profileDelete(@Param("profile_id") Long profileId);
}
