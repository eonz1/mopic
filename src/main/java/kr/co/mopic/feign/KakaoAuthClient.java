package kr.co.mopic.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name="kakaoAuth", url="https://kauth.kakao.com")
public interface KakaoAuthClient {

    @PostMapping(value="/oauth/token", consumes="application/x-www-form-urlencoded", produces="application/json;charset=UTF-8")
    Map<String, String> getKakaoAccessToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_url") String redirectUrl,
            @RequestParam("code") String code);
}