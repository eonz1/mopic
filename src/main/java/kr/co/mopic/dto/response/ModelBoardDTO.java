package kr.co.mopic.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModelBoardDTO {

    @ApiModelProperty(value = "작성자유무")
    private boolean boolWriter;

    @ApiModelProperty(value = "글번호")
    private int seq;

    @ApiModelProperty(value = "닉네임")
    private String nickname;

    @ApiModelProperty(value = "제목")
    private String title;

    @ApiModelProperty(value = "내용")
    private String description;

    @ApiModelProperty(value = "가격")
    private int price;

    @ApiModelProperty(value = "서비스지역")
    private String location;

    @ApiModelProperty(value = "작성일")
    private String createDate;

    @ApiModelProperty(value = "지원양식")
    private String applyForm;

    @ApiModelProperty(value = "지원방법")
    private List<FormDTO> applyUrls;

    @ApiModelProperty(value = "파일 URL")
    private List<FileDTO> fileUrls;

    @JsonIgnore
    @ApiModelProperty(value = "작성자아이디")
    private Long userId;
    
    @JsonIgnore
    @ApiModelProperty(value = "타입(패션뷰티, 촬영 등)")
    private String type;

    @JsonIgnore
    @ApiModelProperty(value = "지원방법(KAKAO, INSTAGRAM, HOMEPAGE)", example = "KAKAO")
    private String applyType;

    @JsonIgnore
    @ApiModelProperty(value = "지원방법 url")
    private String applyUrl;

    public void setWriter(Boolean b) {
        this.boolWriter = b;
    }
}