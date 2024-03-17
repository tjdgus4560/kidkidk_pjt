package com.ssafy.kdkd.domain.entity.account;

import com.ssafy.kdkd.domain.dto.account.ProfileDto;
import com.ssafy.kdkd.domain.dto.account.UserDto;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.domain.entity.user.Parent;

import static com.ssafy.kdkd.domain.entity.account.User.createUser;
import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.*;

@Entity
@Table(name = "profile")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Profile {

    @Id
    @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "pin")
    private int pin;

    @Lob
    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "type")
    private boolean type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Profile(ProfileDto profileDto, User user) {
        this.nickname = profileDto.getNickname();
        this.pin = profileDto.getPin();
        this.profileImage = profileDto.getProfileImage();
        this.type = profileDto.isType();
        this.user = user;
    }


    /**
     * 프로필 생성
     */
}
