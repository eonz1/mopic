package kr.co.mopic.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.mopic.service.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/common")
public class CommonController {

    private final CommonService cmnService;

    @ApiOperation(value = "기준정보 코드 조회", notes = "CODE_TYPE으로 코드 조회하기")
    @GetMapping("/codes")
    public ResponseEntity<?> getCodes(@RequestParam(required = false) String codeType) {

        return ResponseEntity.ok().body(cmnService.getCodes(codeType));
    }
}
