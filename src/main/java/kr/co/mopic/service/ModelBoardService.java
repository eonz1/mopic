package kr.co.mopic.service;

import kr.co.mopic.dto.request.*;
import kr.co.mopic.dto.response.FileDTO;
import kr.co.mopic.dto.response.FormDTO;
import kr.co.mopic.dto.response.ModelBoardDTO;
import kr.co.mopic.mapper.FileMapper;
import kr.co.mopic.mapper.ModelBoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class ModelBoardService {

    private final ModelBoardMapper modelBoardMapper;

    private final FileMapper fileMapper;

    /**
     * 모델 구인글 등록하기
     * @param modelBoardAddDTO
     */
    @Transactional
    public int insertModelBoard(ModelBoardAddDTO modelBoardAddDTO) {

        return modelBoardMapper.insertModelBoard(modelBoardAddDTO);
    }

    /**
     * 모델 구인글 조회하기
     * @param seq
     * @return
     */
    public List<ModelBoardDTO> getModelBoardPost(Integer seq, Long userId){

        List<ModelBoardDTO> boardList = modelBoardMapper.getModelBoardPost(seq);
        List<FileDTO> fileList = fileMapper.getFiles(seq);
        List<FormDTO> urlList = modelBoardMapper.getForm(FormAddDTO.builder().build());

        for(ModelBoardDTO boardDTO : boardList) {
            // 첨부파일 리스트 담기
            List<FileDTO> fileUrls = new ArrayList<>();

            for(FileDTO fileDTO : fileList) {
                if(boardDTO.getSeq() == fileDTO.getBoardSeq()) {
                    fileUrls.add(fileDTO);
                }
            }

            boardDTO.setFileUrls(fileUrls);

            // 지원양식 리스트담기
            List<FormDTO> applyUrls = new ArrayList<>();

            for(FormDTO formDTO : urlList) {
                if(boardDTO.getSeq() == formDTO.getBoardSeq()) {
                    applyUrls.add(formDTO);
                }
            }

            // 작성자 확인유무
            if(boardDTO.getUserId() == userId)
                boardDTO.setWriter(true);

            boardDTO.setApplyUrls(applyUrls);
        }

        return boardList;
    }

    /**
     * 모델 구인글 수정하기
     * @param boardAddDTO
     */
    @Transactional
    public void modifyModelBoardPost(ModelBoardAddDTO boardAddDTO){

        modelBoardMapper.modifyModelBoardPost(boardAddDTO);
    }

    /**
     * 모델 구인글 삭제하기
     * @param seq
     */
    @Transactional
    public void deleteModelBoardPost(int seq){

        modelBoardMapper.deleteModelBoardPost(seq);
    }

    /**
     * 지원방법 등록 및 수정하기
     * @param formAddList
     */
    @Transactional
    public void insertBoardForm(List<FormAddDTO> formAddList, int boardSeq){

        List<FormDTO> formList = modelBoardMapper.getForm(formAddList.get(0));

        // 있다면 삭제하고
        if(formList.size() > 0)
            modelBoardMapper.deleteForm(formAddList.get(0));

        // 새로 넣기
        List<FormAddDTO> list = new ArrayList<>();
        for(int i = 0; i < formAddList.size(); i++) {
            FormAddDTO formAddDTO = FormAddDTO.builder()
                                                .boardSeq(boardSeq)
                                                .boardType("MODEL")
                                                .type(formAddList.get(i).getType())
                                                .url(formAddList.get(i).getUrl())
                                                .build();

            list.add(formAddDTO);
        }

        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        map.put("list", list);

        modelBoardMapper.insertForm(map);
    }

    /**
     * 지원방법 삭제하기
     * @param formAddDTO
     */
    @Transactional
    public void deleteForm(FormAddDTO formAddDTO){

        modelBoardMapper.deleteForm(formAddDTO);
    }
}
