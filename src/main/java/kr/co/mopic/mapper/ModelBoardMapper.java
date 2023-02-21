package kr.co.mopic.mapper;

import kr.co.mopic.dto.request.FormAddDTO;
import kr.co.mopic.dto.request.ModelBoardAddDTO;
import kr.co.mopic.dto.response.FormDTO;
import kr.co.mopic.dto.response.ModelBoardDTO;

import java.util.List;
import java.util.Map;

public interface ModelBoardMapper {

    int insertModelBoard(ModelBoardAddDTO modelBoardAddDTO);

    List<ModelBoardDTO> getModelBoardPost(Integer seq);

    List<FormDTO> getForm(FormAddDTO formAddDTO);

    void modifyModelBoardPost(ModelBoardAddDTO boardAddDTO);

    void deleteModelBoardPost(int seq);

    void insertForm(Map<String, Object> map);

    void deleteForm(FormAddDTO formAddDTO);
}
