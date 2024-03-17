package com.ssafy.kdkd.service.account;

import static com.ssafy.kdkd.domain.entity.account.User.createUser;

import org.springframework.stereotype.Service;

import com.ssafy.kdkd.domain.dto.account.UserDto;
import com.ssafy.kdkd.domain.entity.account.User;
import com.ssafy.kdkd.repository.account.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(UserDto userDto) {
        User userEntity = createUser(userDto);
        userRepository.save(userEntity);
    }

    public void updateUser(UserDto userDto) {
        User userEntity = userRepository.findByEmail(userDto.getEmail());

        userEntity.setAccessToken(userDto.getAccessToken());
        userRepository.save(userEntity);
    }

    public UserDto getUser(String email) {
        User findUserEntity = userRepository.findByEmail(email);
        return UserDto.from(findUserEntity);
    }

    public boolean isUserExists(String email) {
        return userRepository.existsByEmail(email);
    }

}
