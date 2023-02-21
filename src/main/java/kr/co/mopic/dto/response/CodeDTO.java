package kr.co.mopic.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CodeDTO {

    @ApiModelProperty(value = "코드아이디")
    private String codeId;

    @ApiModelProperty(value = "코드명")
    private String codeName;

    @ApiModelProperty(value = "코드 타입")
    private String codeType;
}
