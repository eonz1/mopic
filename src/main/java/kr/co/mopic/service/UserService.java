package kr.co.mopic.service;

import kr.co.mopic.dto.request.UserAddDTO;
import kr.co.mopic.dto.request.UserLoginDTO;
import kr.co.mopic.dto.request.UserModifyDTO;
import kr.co.mopic.dto.response.UserDTO;
import kr.co.mopic.feign.KakaoApiClient;
import kr.co.mopic.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final KakaoApiClient kakaoApiClient;

    private final UserMapper userMapper;

    /**
     * 카카오 토큰으로 카카오 계정정보 가져오기
     * @param token
     * @return kakao_account
     */
    public Map<String, String> getKakaoAccount(String token) {

        // 유저 정보 가져오기
        Map<String, Object> map = kakaoApiClient.getKakaoAccount("Bearer " + token);

        Map<String, Object> accountMap = (Map<String, Object>) map.get("kakao_account");

        Map<String, String> profile = (Map<String, String>) accountMap.get("profile");
        profile.put("id", map.get("id").toString());
        profile.put("provider", "KAKAO");
        profile.put("profileImageUrl", profile.get("profile_image_url"));
        if(accountMap.get("has_email").equals("true"))
            profile.put("email", accountMap.get("email").toString());

        return profile;
    }

    /**
     * 아이디랑 제공처로 회원정보 가져오기
     * @param providerId
     * @param provider
     * @return UserDTO
     */
    public UserDTO findUserByProviderAndPrvId(String providerId, String provider) {

        return userMapper.getUserInfo(UserLoginDTO.builder().providerId(providerId).provider(provider).build());
    }

    /**
     * 회원정보 가져오기
     * @param userloginDTO
     * @return userDTO
     */
    public UserDTO getUserInfo(UserLoginDTO userloginDTO) {

        UserDTO dto = userMapper.getUserInfo(userloginDTO);

        if(dto == null)
            return null;

        return UserDTO.builder()
                .userId(dto.getUserId())
                .nickname(dto.getNickname())
                .types(List.of(dto.getStrTypes().split(",")))
                .provider(dto.getProvider())
                .exist(true)
                .providerId(dto.getProviderId())
                .build();
    }

    /**
     * 회원 등록하기
     * @param userAddDTO
     */
    public void insertUser(UserAddDTO userAddDTO) {

        // 유저 등록하고
        userMapper.insertUser(userAddDTO);

        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for(int i = 0; i < userAddDTO.getTypes().size(); i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", String.valueOf(userAddDTO.getUserId()));
            map.put("type", userAddDTO.getTypes().get(i));

            listMap.add(map);
        }

        // 작업자 유형 등록하기
        userMapper.insertUserType(listMap);
    }

    /**
     * 회원 닉네임 변경하기
     * @param userModifyDTO
     */
    public void changeUserNickname(UserModifyDTO userModifyDTO) {

        userMapper.changeUserNickname(userModifyDTO);
    }

    /**
     * 회원 타입 변경하기
     * @param userModifyDTO
     */
    public void changeUserTypes(UserModifyDTO userModifyDTO) {

        // 삭제하고
        userMapper.deleteUserTypes(userModifyDTO.getUserId());

        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for(int i = 0; i < userModifyDTO.getTypes().size(); i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", String.valueOf(userModifyDTO.getUserId()));
            map.put("type", userModifyDTO.getTypes().get(i));

            listMap.add(map);
        }

        // 새로넣기
        userMapper.insertUserType(listMap);
    }
}
