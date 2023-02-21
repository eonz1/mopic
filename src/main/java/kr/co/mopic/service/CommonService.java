package kr.co.mopic.service;

import kr.co.mopic.dto.response.CodeDTO;
import kr.co.mopic.mapper.CommonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommonService {

    private final CommonMapper cmnMapper;

    public List<CodeDTO> getCodes(String codeType) {
        return cmnMapper.getCodes(codeType);
    }

}
