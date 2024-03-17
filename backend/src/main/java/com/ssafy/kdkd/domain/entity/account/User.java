package com.ssafy.kdkd.domain.entity.account;

import com.ssafy.kdkd.domain.dto.account.UserDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "access_token", length = 2000)
    private String accessToken;

    @Column(name = "email", length = 2000)
    private String email;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     *  생성자
     */
    public static User createUser(UserDto userDto){
        User user = new User();
        user.accessToken = userDto.getAccessToken();
        user.email = userDto.getEmail();
        return user;
    }
}
