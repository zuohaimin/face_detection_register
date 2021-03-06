package cn.edu.swpu.face_detection_register.dao;

import cn.edu.swpu.face_detection_register.model.bo.UserInfo;
import cn.edu.swpu.face_detection_register.model.dto.VerifyUserNameParam;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.model.vo.UserSelectVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoMapper {
    int deleteByPrimaryKey(String userId);

    int insert(UserInfo record);

    UserInfo selectByPrimaryKey(String userId);

    List<UserInfo> selectAll();

    UserInfo selectByFaceToken(String faceToken);

    List<UserInfo> selectByUserName(String userName);

    int updateByPrimaryKey(UserInfo record);

    List<UserInfo> selectUserInfoByCondition(UserInfo userInfo);

    Integer batchUpdateIsFaceMsg(UserInfo userInfo);

    List<UserSelectVo> selectUserList(VerifyUserNameParam verifyUserNameParam);

    UserSelectVo selectByUserId(String userId);

}