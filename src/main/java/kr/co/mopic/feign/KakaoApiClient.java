package kr.co.mopic.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name="kakaoApi", url="https://kapi.kakao.com")
public interface KakaoApiClient {

    @PostMapping(value="/v2/user/me", consumes="application/x-www-form-urlencoded", produces="application/json;charset=UTF-8")
    Map<String, Object> getKakaoAccount(@RequestHeader("Authorization") String bearer);
}