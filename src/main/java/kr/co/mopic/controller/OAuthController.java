package kr.co.mopic.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.mopic.dto.response.UserDTO;
import kr.co.mopic.service.OAuthService;
import kr.co.mopic.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2")
public class OAuthController {

    private final UserService userService;

    private final OAuthService oAuthService;

    /**
     * 카카오 callback
     */
    @ApiOperation(value = "카카오 소셜 로그인", notes = "exist값에 false가 오면 사용자 등록 api를 통해 등록 해야 함")
    @GetMapping("/kakao")
    public ResponseEntity<UserDTO> kakaoCallback(@RequestParam @ApiParam(value = "인가코드") String code) {

        // 엑세스 토큰 및 리프레시 토큰 가져오기
        Map<String, String> tokenMap = oAuthService.getKakaoTokens("9b54f45ba686db5d6eb8cb8939bc703b", "http://localhost:8080/oauth2/kakao", code);

        // 액세스 토큰으로 유저정보 조회하기
        Map<String, String> userInfo = userService.getKakaoAccount(tokenMap.get("access_token"));

        // 유저 db에서 해당 유저 있는 지 확인
        UserDTO userDto = userService.findUserByProviderAndPrvId(userInfo.get("id"), userInfo.get("provider"));

        if(userDto == null) {
            // 없으면 exist를 false로 보내, 등록 필요 고지
            return ResponseEntity.ok().body(UserDTO.builder()
                    .nickname(userInfo.get("nickname"))
                    .providerId(userInfo.get("id"))
                    .provider(userInfo.get("provider"))
                    .exist(false)
                    .build());
        }

        return ResponseEntity.ok().body(UserDTO.builder()
                .nickname(userDto.getNickname())
                .providerId(userDto.getProviderId())
                .provider(userDto.getProvider())
                .exist(true)
                .build());
    }

    /**
     * 네이버 callback
     */
    @ApiOperation(value = "네이버 소셜 로그인", notes = "exist값에 false가 오면 사용자 등록 api를 통해 등록 해야 함")
    @GetMapping("/naver")
    public ResponseEntity<UserDTO> naverCallback(@RequestParam @ApiParam(value = "인가코드") String code) {

        log.info(code);

        /*
        // 엑세스 토큰 및 리프레시 토큰 가져오기
        Map<String, String> tokenMap = oAuthService.getKakaoTokens("9b54f45ba686db5d6eb8cb8939bc703b", "http://localhost:8080/oauth2/kakao", code);

        // 액세스 토큰으로 유저정보 조회하기
        Map<String, String> userInfo = userService.getKakaoAccount(tokenMap.get("access_token"));

        // 유저 db에서 해당 유저 있는 지 확인
        UserDTO userDto = userService.findUserByProviderAndPrvId(userInfo.get("id"), userInfo.get("provider"));

        if(userDto == null) {
            // 없으면 exist를 false로 보내, 등록 필요 고지
            return ResponseEntity.ok().body(UserDTO.builder()
                    .nickname(userInfo.get("nickname"))
                    .providerId(userInfo.get("id"))
                    .provider(userInfo.get("provider"))
                    .exist(false)
                    .build());
        }
        */

        return ResponseEntity.ok().body(UserDTO.builder()
                //.nickname(userDto.getNickname())
                //.providerId(userDto.getProviderId())
                //.provider(userDto.getProvider())
                .exist(true)
                .build());
    }
}