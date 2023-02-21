package kr.co.mopic.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.mopic.aop.LoginCheck;
import kr.co.mopic.dto.request.UserAddDTO;
import kr.co.mopic.dto.request.UserLoginDTO;
import kr.co.mopic.dto.request.UserModifyDTO;
import kr.co.mopic.dto.response.UserDTO;
import kr.co.mopic.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "사용자 등록", notes = "소셜 로그인 후, DB에 없으면 사용자 등록을 한다.")
    @PostMapping("")
    public ResponseEntity<?> insertUser(@RequestBody @Valid UserAddDTO userAddDTO) {
        // 있는 지 확인하고
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .providerId(userAddDTO.getProviderId())
                .nickname(userAddDTO.getNickname())
                .provider(userAddDTO.getProvider())
                .build();
        UserDTO userDTO = userService.getUserInfo(userLoginDTO);

        // 있으면 에러뱉기
        if(userDTO != null) {
            return ResponseEntity.status(409).body("이미 등록된 아이디입니다.");
        }

        // 없으면 추가하기
        userService.insertUser(userAddDTO);

        return ResponseEntity.ok().body(userService.getUserInfo(UserLoginDTO.builder().userId(userAddDTO.getUserId()).build()));
    }

    @LoginCheck
    @ApiOperation(value = "사용자 정보 조회")
    @GetMapping ("")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);

        return ResponseEntity.ok().header("sid", httpSession.getId()).body(request.getSession().getAttribute("USER"));
    }

    @LoginCheck
    @ApiOperation(value = "사용자 닉네임 변경")
    @PatchMapping ("/nickname/{nickname}")
    public ResponseEntity<UserDTO> changeUserNickname(@PathVariable String nickname, HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);

        UserModifyDTO dto = UserModifyDTO.builder()
                                        .userId(((UserDTO) request.getSession().getAttribute("USER")).getUserId())
                                        .nickname(nickname)
                                        .build();

        userService.changeUserNickname(dto);

        return ResponseEntity.ok().header("sid", httpSession.getId()).body(userService.getUserInfo(UserLoginDTO.builder()
                                                                            .userId(((UserDTO) request.getSession().getAttribute("USER")).getUserId()).build()));
    }

    @LoginCheck
    @ApiOperation(value = "사용자 작업자유형 변경")
    @PutMapping ("/types")
    public ResponseEntity<UserDTO> changeUserTypes(@RequestBody UserModifyDTO userModifyDTO, HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);

        UserModifyDTO dto = UserModifyDTO.builder()
                                    .userId(((UserDTO) request.getSession().getAttribute("USER")).getUserId())
                                    .types(userModifyDTO.getTypes())
                                    .build();

        userService.changeUserTypes(dto);

        return ResponseEntity.ok().header("sid", httpSession.getId()).body(userService.getUserInfo(UserLoginDTO.builder().userId(dto.getUserId()).build()));
    }
}
