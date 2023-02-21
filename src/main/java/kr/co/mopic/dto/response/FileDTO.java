package kr.co.mopic.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {

    @ApiModelProperty(value = "파일명")
    private String fileName;

    @ApiModelProperty(value = "url")
    private String url;

    @JsonIgnore
    @ApiModelProperty(value = "게시판 타입")
    private String boardType;

    @JsonIgnore
    @ApiModelProperty(value = "게시판 번호")
    private int boardSeq;

}
