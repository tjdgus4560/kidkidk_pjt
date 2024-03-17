package com.ssafy.kdkd.domain.dto.account;

import com.ssafy.kdkd.domain.entity.account.User;

import lombok.Data;

@Data
public class UserDto {

    private Long userId;

    private String accessToken;

    private String email;

    public UserDto() {
    }

    public UserDto(String accessToken, String email) {
        this.accessToken = accessToken;
        this.email = email;
    }

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setAccessToken(user.getAccessToken());

        return userDto;
    }
}
