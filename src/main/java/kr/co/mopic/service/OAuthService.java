package kr.co.mopic.service;

import kr.co.mopic.feign.KakaoAuthClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthService {

    private final KakaoAuthClient kakaoAuthClient;

    public Map<String, String> getKakaoTokens(String clientId, String redirectUrl, String code) {

        Map<String, String> tokenMap = kakaoAuthClient.getKakaoAccessToken("authorization_code", clientId, redirectUrl, code);

        return tokenMap;
    }
}
