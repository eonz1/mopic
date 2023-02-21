package kr.co.mopic.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.mopic.aop.LoginCheck;
import kr.co.mopic.dto.request.*;
import kr.co.mopic.dto.response.ModelBoardDTO;
import kr.co.mopic.dto.response.UserDTO;
import kr.co.mopic.service.FileService;
import kr.co.mopic.service.ModelBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/model")
public class ModelBoardController {

    private final ModelBoardService modelBoardService;

    private final FileService fileService;

    @LoginCheck
    @ApiOperation(value = "모델 구인글 등록하기")
    @PostMapping(value="", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<ModelBoardDTO>> insertModelBoard(@RequestPart("board") ModelBoardAddDTO modelBoardAddDTO
                                                                , @RequestPart("imageFileList") List<MultipartFile> imageFileList
                                                                , HttpServletRequest request) {

        HttpSession httpSession = request.getSession(false);
        Long userId = ((UserDTO) request.getSession().getAttribute("USER")).getUserId();
        modelBoardAddDTO.setUserId(userId);

        // 게시글 등록
        int insertBoard = modelBoardService.insertModelBoard(modelBoardAddDTO);

        if(insertBoard == 0)
            throw new HttpStatusCodeException(HttpStatus.INTERNAL_SERVER_ERROR, "게시글 등록이 실패하였습니다.") {};

        // 파일 업로드
        fileService.uploadImage(imageFileList, modelBoardAddDTO.getSeq(), modelBoardAddDTO.getUserId());

        // 폼 등록
        modelBoardService.insertBoardForm(modelBoardAddDTO.getUrlList(), modelBoardAddDTO.getSeq());

        return ResponseEntity.ok().header("sid", httpSession.getId()).body(modelBoardService.getModelBoardPost(modelBoardAddDTO.getSeq(), userId));
    }

    @ApiOperation(value = "모델 구인글 조회", notes = "특정글을 클릭했을 시에 seq 번호 넘겨주기<br>디폴트로 파라미터 없이 목록조회")
    @GetMapping ("")
    public ResponseEntity<List<ModelBoardDTO>> getModelBoardPost(@RequestParam(required = false) Integer seq, HttpServletRequest request) {

        Long userId = 0L;

        if(request.getSession(false) != null)
            userId = ((UserDTO) request.getSession(false).getAttribute("USER")).getUserId();

        return ResponseEntity.ok().body(modelBoardService.getModelBoardPost(seq, userId));
    }

    @LoginCheck
    @ApiOperation(value = "모델 구인글 변경")
    @PatchMapping (value="/{seq}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<ModelBoardDTO>> modifyModelBoardPost(@PathVariable("seq") int seq
                                                                    , @RequestPart("board") ModelBoardAddDTO modelBoardAddDTO
                                                                    , @RequestPart(value = "imageFileList", required = false) List<MultipartFile> imageFileList
                                                                    , HttpServletRequest request) {

        HttpSession httpSession = request.getSession(false);
        Long userId = ((UserDTO) request.getSession().getAttribute("USER")).getUserId();
        modelBoardAddDTO.setUserId(userId);

        // 본인글만 변경가능

        // 글내용 바꾸기
        modelBoardAddDTO.setSeq(seq);
        modelBoardService.modifyModelBoardPost(modelBoardAddDTO);

        List<FormAddDTO> formAddList = new ArrayList<>();
        for(int i = 0; i < modelBoardAddDTO.getUrlList().size(); i++) {
            FormAddDTO formAddDTO = FormAddDTO.builder()
                                                .boardSeq(modelBoardAddDTO.getSeq())
                                                .boardType("MODEL")
                                                .type(modelBoardAddDTO.getUrlList().get(i).getType())
                                                .url(modelBoardAddDTO.getUrlList().get(i).getUrl())
                                                .build();

            formAddList.add(formAddDTO);
        }

        // 지원방법 url 바꾸기
        modelBoardService.insertBoardForm(formAddList, seq);

        // 새 첨부파일 등록하기
        if(imageFileList != null)
            fileService.uploadImage(imageFileList, seq, modelBoardAddDTO.getUserId());

        return ResponseEntity.ok().header("sid", httpSession.getId()).body(modelBoardService.getModelBoardPost(seq, userId));
    }

    @LoginCheck
    @ApiOperation(value = "모델 구인글 삭제")
    @DeleteMapping ("/{seq}")
    public ResponseEntity<String> deleteModelBoardPost(@PathVariable("seq") int seq, HttpServletRequest request) {

        HttpSession httpSession = request.getSession(false);

        // 본인글만 삭제가능

        modelBoardService.deleteModelBoardPost(seq);
        
        FormAddDTO formAddDTO = FormAddDTO.builder()
                                        .boardSeq(seq)
                                        .boardType("MODEL")
                                        .build();
        modelBoardService.deleteForm(formAddDTO);

        return ResponseEntity.ok().header("sid", httpSession.getId()).body("삭제가 완료되었습니다.");
    }
}
