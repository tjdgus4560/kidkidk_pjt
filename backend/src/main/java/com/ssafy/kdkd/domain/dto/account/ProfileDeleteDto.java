package com.ssafy.kdkd.domain.dto.account;

import lombok.Data;

@Data
public class ProfileDeleteDto {
	private Long profileId;

	public ProfileDeleteDto() {
	}

	public ProfileDeleteDto(Long profileId) {
		this.profileId = profileId;
	}
}
