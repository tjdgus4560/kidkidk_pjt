package com.ssafy.kdkd.domain.dto.account;

import lombok.Data;
@Data
public class ProfileSelectAllDto {
	private Long profileId;
	private String nickname;
	private String profileImage;
	private boolean type;
	private Long userId;

	public ProfileSelectAllDto() {
	}

	public ProfileSelectAllDto(Long userId) {
		this.userId = userId;
	}

	public ProfileSelectAllDto(Long profileId, String nickname, String profileImage, boolean type) {
		this.profileId = profileId;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.type = type;
	}
}
