package kr.co.mopic.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import kr.co.mopic.dto.response.FormDTO;
import lombok.*;

import java.util.List;

@Data
public class ModelBoardAddDTO {

    @ApiModelProperty(value = "제목")
    private String title;

    @ApiModelProperty(value = "내용")
    private String description;

    @ApiModelProperty(value = "가격")
    private int price;

    @ApiModelProperty(value = "지원양식")
    private String form;

    @ApiModelProperty(value = "지원방법")
    private List<FormAddDTO> urlList;

    @ApiModelProperty(value = "서비스 지역")
    private String location;

    @JsonIgnore
    @ApiModelProperty(value = "작성자 아이디")
    private Long userId;

    @JsonIgnore
    @ApiModelProperty(value = "글번호")
    private Integer seq;

    @JsonIgnore
    @ApiModelProperty(value = "타입(패션뷰티, 촬영 등)")
    private String type;
}