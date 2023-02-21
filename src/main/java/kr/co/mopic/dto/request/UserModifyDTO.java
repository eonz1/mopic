package kr.co.mopic.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
public class UserModifyDTO {

    @JsonIgnore
    @ApiModelProperty(value = "유저ID")
    private Long userId;

    @ApiModelProperty(value = "닉네임")
    private String nickname;

    @ApiModelProperty(value = "작업자 유형")
    private List<String> types;
}