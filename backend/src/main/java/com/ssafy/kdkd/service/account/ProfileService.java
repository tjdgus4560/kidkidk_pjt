package com.ssafy.kdkd.service.account;

import static com.ssafy.kdkd.domain.entity.deposit.Deposit.createDeposit;

import com.ssafy.kdkd.domain.dto.account.*;
import com.ssafy.kdkd.domain.dto.account.TransferDto;
import com.ssafy.kdkd.domain.dto.deposit.DepositDto;
import com.ssafy.kdkd.domain.entity.account.Profile;
import com.ssafy.kdkd.domain.entity.account.User;
import com.ssafy.kdkd.domain.entity.deposit.Deposit;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.domain.entity.user.Parent;
import com.ssafy.kdkd.repository.account.ProfileRepository;
import com.ssafy.kdkd.repository.account.UserRepository;
import com.ssafy.kdkd.repository.deposit.DepositRepository;
import com.ssafy.kdkd.repository.user.ChildRepository;
import com.ssafy.kdkd.repository.user.ParentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	ParentRepository parentRepository;

	@Autowired
	ChildRepository childRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	DepositRepository depositRepository;

	public int profileCreate(ProfileDto profileDto){
		System.out.println(profileDto.toString());
		Optional<User> userOptional = userRepository.findById(profileDto.getUserId());
		if (userOptional.isPresent()) { // Optional에서 User 객체를 꺼내기 전에 존재 여부를 확인하는 것이 좋습니다.
			User user = userOptional.get(); // Optional에서 User 객체를 꺼냅니다.
			Profile profile = new Profile(profileDto, user);
			profileRepository.save(profile);
			if(profileDto.isType()){
				parentRepository.save(new Parent(profile));
			}else{
				childRepository.save(new Child(profile));
			}
			return 1; //프로필이 생성되면 1을 반환
		} else {
			// 프로필이 생성되지않으면 0을 반환
			return 0;
		}
	}

	public List<ProfileSelectAllDto> profileSelectAll(ProfileSelectAllDto profileSelectAllDto){
		Long user_id = profileSelectAllDto.getUserId();
		List<ProfileSelectAllDto> returnDto = profileRepository.profileSelectAllDto(user_id);
		return returnDto;
	}

	public ProfileLoginDto profileLogin(ProfileLoginDto profileLoginDto){
		Long profile_id = profileLoginDto.getProfileId();
		int pin  = profileLoginDto.getPin();
		ProfileLoginDto returnDto = profileRepository.profileLoginDto(profile_id, pin);
		return returnDto;
	}

	@Transactional
	public void profileUpdate(ProfileUpdateDto profileUpdateDto){
		Long profileId = profileUpdateDto.getProfileId();
		String nickname = null;
		int pin = 0;
		String profileImage = null;

		Optional<Profile> updateProfile = profileRepository.findById(profileId);
		if (updateProfile.isPresent()) {
			Profile profile = updateProfile.get();
			nickname = profile.getNickname();
			pin = profile.getPin();
			profileImage = profile.getProfileImage();
		}else{

		}

		String updateNickname = profileUpdateDto.getNickname();
		int updatePin = profileUpdateDto.getPin();
		String updateProfileImage = profileUpdateDto.getProfileImage();

		if(updateNickname != null){
			nickname = updateNickname;
		}
		if(updatePin != 0){
			pin = updatePin;
		}
		if(updateProfileImage != null){
			profileImage = updateProfileImage;
		}

		profileRepository.profileUpdate(profileId,nickname,pin,profileImage);

	}

	@Transactional
	public void profileDelete(ProfileDeleteDto profileDeleteDto){
		Long profileId = profileDeleteDto.getProfileId();
		if(parentRepository.findById(profileId).isPresent()) {
			parentRepository.deleteById(profileId);
		}else{
			childRepository.deleteById(profileId);
		}
//		profileRepository.profileDelete(profileId); //이전에 사용하던 delete메소드(외래키 문제로 폐지되었다,)
		profileRepository.deleteById(profileId);
	}

	public List<GetChildListDto> getChildList(GetChildListDto getChildListDto){
		Long userId = getChildListDto.getUserId();
		List<GetChildListDto> returnDto = profileRepository.getChildList(userId);
		return returnDto;
	}

	public ChildDto getChild(Long childId){
		return childRepository.childDto(childId);
	}

	@Transactional
	public boolean transferToFundMoney(TransferDto transferDto) {
		int coin = transferDto.getCoin();
		Long childId = transferDto.getChildId();
		Optional<Child> findChild = childRepository.findById(childId);

		if (findChild.isEmpty()) {
			return false;
		}

		Child child = findChild.get();
		int updateCoin = child.getCoin() - coin;
		int updateFundMoney = child.getFundMoney() + coin;

		if (updateCoin < 0) {
			return false;
		}

		child.updateFundMoney(updateFundMoney);
		child.updateChild(updateCoin);

		String detail = "투자 출금";
		boolean type = false;

		DepositDto depositDto = new DepositDto(LocalDateTime.now(), detail, type, coin, updateCoin, childId);
		Deposit deposit = createDeposit(depositDto);
		deposit.setChild(child);
		depositRepository.save(deposit);
		return true;
	}
}
