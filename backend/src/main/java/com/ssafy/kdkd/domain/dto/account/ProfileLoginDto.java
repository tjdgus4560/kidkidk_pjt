package com.ssafy.kdkd.domain.dto.account;

import lombok.Data;

@Data
public class ProfileLoginDto {
	private Long profileId;
	private String nickname;
	private int pin;
	private String profileImage;
	private boolean type;
	private Long userId;

	public ProfileLoginDto() {
	}

	public ProfileLoginDto(Long profileId, String nickname, String profile_image, boolean type) {
		this.profileId = profileId;
		this.nickname = nickname;
		this.profileImage = profile_image;
		this.type = type;
	}
}
