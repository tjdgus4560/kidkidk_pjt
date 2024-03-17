package com.ssafy.kdkd.domain.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

	private String nickname;

	private int pin;

	private String profileImage;

	private boolean type;

	private Long userId;

}
