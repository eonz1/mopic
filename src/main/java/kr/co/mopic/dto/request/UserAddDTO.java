package kr.co.mopic.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Builder
public class UserAddDTO {

    @ApiModelProperty(value = "닉네임")
    @NotBlank
    private String nickname;

    @ApiModelProperty(value = "소셜 제공처", example = "KAKAO")
    @NotBlank
    private String provider;

    @ApiModelProperty(value = "소셜 제공처 ID")
    @NotBlank
    private String providerId;

    @ApiModelProperty(value = "작업자 유형")
    private List<String> types;

    @JsonIgnore
    private Long userId;
}