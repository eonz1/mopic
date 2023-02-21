package kr.co.mopic.dto.request;

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
public class FormAddDTO {

    @ApiModelProperty(value = "글번호")
    private Integer boardSeq;

    @ApiModelProperty(value = "글타입", example = "MODEL")
    private String boardType;

    @ApiModelProperty(value = "지원방법(KAKAO, INSTAGRAM, HOMEPAGE)", example = "KAKAO")
    private String type;

    @ApiModelProperty(value = "지원방법 url")
    private String url;
}