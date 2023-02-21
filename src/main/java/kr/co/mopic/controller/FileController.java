package kr.co.mopic.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.mopic.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @ApiOperation(value = "파일 삭제", notes = "게시글 조회시 fileUrls에 있는 fileName을 넣어 해당 사진 삭제")
    @DeleteMapping("/{fileName}")
    public ResponseEntity<?> deleteFile(@PathVariable String fileName){

        fileService.deleteImage(fileName);

        return ResponseEntity.ok().body("파일 삭제가 완료되었습니다.");
    }
}
