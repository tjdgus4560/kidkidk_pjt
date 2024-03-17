package com.ssafy.kdkd.domain.dto.account;

import lombok.Data;

@Data
public class GetChildListDto {
	private Long userId;

	private Long childId;

	private String nickname;

	private String profileImage;

	public GetChildListDto(){

	}

	public GetChildListDto(Long childId, String nickname) {
		this.childId = childId;
		this.nickname = nickname;
	}

	public GetChildListDto(Long userId, Long childId, String nickname, String profileImage) {
		this.userId = userId;
		this.childId = childId;
		this.nickname = nickname;
		this.profileImage = profileImage;
	}
}
