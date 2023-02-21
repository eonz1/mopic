package kr.co.mopic.mapper;

import kr.co.mopic.dto.request.FileAddDTO;
import kr.co.mopic.dto.response.FileDTO;

import java.util.List;

public interface FileMapper {

    void insertFile(FileAddDTO dto);

    List<FileDTO> getFiles(Integer seq);

    void deleteFile(String fileId);
}
