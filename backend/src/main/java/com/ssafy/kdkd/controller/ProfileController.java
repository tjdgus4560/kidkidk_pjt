package com.ssafy.kdkd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.kdkd.domain.dto.account.ChildDto;
import com.ssafy.kdkd.domain.dto.account.GetChildListDto;
import com.ssafy.kdkd.domain.dto.account.ProfileDeleteDto;
import com.ssafy.kdkd.domain.dto.account.ProfileDto;
import com.ssafy.kdkd.domain.dto.account.ProfileLoginDto;
import com.ssafy.kdkd.domain.dto.account.ProfileSelectAllDto;
import com.ssafy.kdkd.domain.dto.account.ProfileUpdateDto;
import com.ssafy.kdkd.domain.dto.account.TransferDto;
import com.ssafy.kdkd.service.account.ProfileService;
import com.ssafy.kdkd.service.account.UserService;
import com.ssafy.kdkd.service.user.ChildService;

import io.swagger.v3.oas.annotations.Operation;

//feat/BE-profile로 브랜치명을 변경하였습니다.
@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class ProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ChildService childService;

    // @PostMapping("/signup")
    // @Operation(summary = "회원가입")
    // public ResponseEntity<?> signUp(UserDto userDto){
    // 	if(userService.signUp(userDto)==1) {
    // 		return new ResponseEntity<Void>(HttpStatus.CREATED);
    // 	}else{
    // 		return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
    // 	}
    // }
    //
    // @PostMapping("/login")
    // @Operation(summary = "계정 로그인")
    // public ResponseEntity<?> userLogin(UserLoginDto userLoginDto){
    // 	if(userService.userLogin(userLoginDto).getUserId()!=null) {
    // 		return new ResponseEntity<UserLoginDto>(HttpStatus.OK);
    // 	}else{
    // 		return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
    // 	}
    // }

    @GetMapping("/profile/selectAll/{userId}")
    @Operation(summary = "프로필 모두 가져오기")
    public ResponseEntity<?> profileSelectAll(@PathVariable Long userId) {
        System.out.println(userId);
        ProfileSelectAllDto profileSelectAllDto = new ProfileSelectAllDto();
        profileSelectAllDto.setUserId(userId);
        List<ProfileSelectAllDto> returnDto = profileService.profileSelectAll(profileSelectAllDto);
        return new ResponseEntity<>(returnDto, HttpStatus.OK);
    }

    @PostMapping("/profile/login")
    @Operation(summary = "프로필 로그인")
    public ResponseEntity<?> profileLogin(@RequestBody ProfileLoginDto profileLoginDto) {
        ProfileLoginDto returnDto = profileService.profileLogin(profileLoginDto);
        if (returnDto != null) {
            System.out.println(returnDto);
            return new ResponseEntity<ProfileLoginDto>(returnDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/profile/create")
    @Operation(summary = "프로필 생성")
    public ResponseEntity<?> profileCreate(@RequestBody ProfileDto profileDto) {
        int create = profileService.profileCreate(profileDto);
        if (create == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/profile/update")
    @Operation(summary = "프로필 수정")
    public ResponseEntity<?> profileUpdate(@RequestBody ProfileUpdateDto profileUpdateDto) {
        profileService.profileUpdate(profileUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/profile/delete/{profileId}")
    @Operation(summary = "프로필 삭제")
    public ResponseEntity<?> profileDelete(@PathVariable Long profileId) {
        ProfileDeleteDto profileDeleteDto = new ProfileDeleteDto();
        profileDeleteDto.setProfileId(profileId);
        profileService.profileDelete(profileDeleteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/profile/childlist/{userId}")
    @Operation(summary = "내 자식 목록가져오기")
    public ResponseEntity<?> getChildList(@PathVariable Long userId) {
        GetChildListDto getChildListDto = new GetChildListDto();
        getChildListDto.setUserId(userId);
        List<GetChildListDto> returnDto = profileService.getChildList(getChildListDto);
        return new ResponseEntity<>(returnDto, HttpStatus.OK);
    }

    @GetMapping("/profile/getchild/{childId}")
    @Operation(summary = "내 자식 접근")
    public ResponseEntity<?> getChild(@PathVariable Long childId) {
        ChildDto returnDto = profileService.getChild(childId);
        return new ResponseEntity<>(returnDto, HttpStatus.OK);
    }

    @PutMapping("/profile/child/update")
    @Operation(summary = "아이 테이블 coin,fund_money 수정")
    public ResponseEntity<?> childUpdate(@RequestBody ChildDto childDto) {
        childService.childUpdate(childDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/profile/transfer")
    @Operation(summary = "아이 주머니에서 투자계좌로 송금")
    public ResponseEntity<?> transfer(@RequestBody TransferDto transferDto) {
        boolean result = profileService.transferToFundMoney(transferDto);
        if (result) {
            return new ResponseEntity<>(transferDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Fail", HttpStatus.BAD_REQUEST);

        }
    }

}
