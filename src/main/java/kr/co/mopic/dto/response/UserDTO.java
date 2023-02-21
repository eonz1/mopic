package kr.co.mopic.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @ApiModelProperty(value = "닉네임")
    private String nickname;

    @ApiModelProperty(value = "작업자 유형")
    private List<String> types;

    @ApiModelProperty(value = "아이디 존재여부")
    private boolean exist = true;

    @ApiModelProperty(value = "소셜 제공처", example = "KAKAO")
    private String provider;

    @ApiModelProperty(value = "소셜 제공처 ID")
    private String providerId;

    @JsonIgnore
    @ApiModelProperty(value = "유저ID")
    private Long userId;

    @JsonIgnore
    private String profileImageUrl;

    @JsonIgnore
    private String strTypes;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private Integer phoneNumber;
}