package kr.co.mopic.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileAddDTO {

    @ApiModelProperty(value = "파일명")
    private String fileName;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "게시판 타입", example = "MODEL")
    private String boardType;

    @ApiModelProperty(value = "게시판 번호")
    private int boardSeq;

    @ApiModelProperty(value = "유저아이디")
    private Long userId;

}
