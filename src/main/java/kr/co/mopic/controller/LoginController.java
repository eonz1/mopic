package kr.co.mopic.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.mopic.dto.request.UserLoginDTO;
import kr.co.mopic.dto.response.UserDTO;
import kr.co.mopic.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class LoginController {

    private final UserService userService;

    @ApiOperation(value = "로그인", notes = "oauth2 api 호출 후, 결과 값으로 로그인 하기")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userloginDTO, HttpServletRequest request) {

        UserDTO userDTO = userService.getUserInfo(userloginDTO);

        if(userDTO == null) {
            return ResponseEntity.status(401).body(userloginDTO);
        }

        HttpSession httpSession = request.getSession(true);

        UserDTO rtnDTO = UserDTO.builder()
                                .nickname(userDTO.getNickname())
                                .types(userDTO.getTypes())
                                .exist(true)
                                .userId(userDTO.getUserId())
                                .provider(userDTO.getProvider())
                                .providerId(userDTO.getProviderId())
                                .build();

        httpSession.setAttribute("USER", rtnDTO);

        return ResponseEntity.ok().header("sid", httpSession.getId()).body(rtnDTO);
    }

    @ApiOperation(value = "로그아웃", notes = "로그아웃 하기")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        request.getSession().invalidate();

        return ResponseEntity.ok().body("Log Out Success");
    }
}
