package kr.co.mopic.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    @JsonIgnore
    @ApiModelProperty(value = "유저ID")
    private Long userId;

    @ApiModelProperty(value = "닉네임")
    private String nickname;

    @ApiModelProperty(value = "소셜 제공처", example = "KAKAO")
    private String provider;

    @ApiModelProperty(value = "소셜 제공처 ID")
    private String providerId;
}