package kr.co.mopic.mapper;

import kr.co.mopic.dto.response.CodeDTO;

import java.util.List;

public interface CommonMapper {

    List<CodeDTO> getCodes(String codeType);
}
