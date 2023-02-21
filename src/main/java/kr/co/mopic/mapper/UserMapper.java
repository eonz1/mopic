package kr.co.mopic.mapper;

import kr.co.mopic.dto.request.UserAddDTO;
import kr.co.mopic.dto.request.UserLoginDTO;
import kr.co.mopic.dto.request.UserModifyDTO;
import kr.co.mopic.dto.response.UserDTO;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    void insertUser(UserAddDTO userAddDTO);

    UserDTO getUserInfo(UserLoginDTO userloginDTO);

    void changeUserNickname(UserModifyDTO userModifyDTO);

    void deleteUserTypes(Long userId);

    void insertUserType(List<Map<String, String>> listMap);

}
