package com.ssafy.kdkd.domain.dto.account;

import lombok.Data;

@Data
public class ProfileUpdateDto {
	private Long profileId;
	private String nickname;
	private int pin;
	private String profileImage;

	public ProfileUpdateDto() {
	}

	public ProfileUpdateDto(String nickname, int pin, String profileImage) {
		this.nickname = nickname;
		this.pin = pin;
		this.profileImage = profileImage;
	}

	public ProfileUpdateDto(Long profileId, String nickname, int pin, String profileImage) {
		this.profileId = profileId;
		this.nickname = nickname;
		this.pin = pin;
		this.profileImage = profileImage;
	}
}
