package com.ssafy.kdkd.domain.dto.account;

import lombok.Data;

@Data
public class UserLoginDto {
	private String accessToken;
	private Long userId;

	public UserLoginDto() {
	}

	public UserLoginDto(String accessToken) {
		this.accessToken = accessToken;
	}
	public UserLoginDto(Long userId) {
		this.userId = userId;
	}
}
