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
public class FormDTO {

    @JsonIgnore
    @ApiModelProperty(value = "글번호")
    private int boardSeq;

    @JsonIgnore
    @ApiModelProperty(value = "글타입")
    private String boardType;

    @ApiModelProperty(value = "지원방법(KAKAO, INSTAGRAM, HOMEPAGE)", example = "KAKAO")
    private String type;

    @ApiModelProperty(value = "지원방법 url")
    private String url;
}