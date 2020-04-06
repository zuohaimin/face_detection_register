package cn.edu.swpu.face_detection_register.dao;

import cn.edu.swpu.face_detection_register.FaceDetectionRegisterApplicationTests;
import cn.edu.swpu.face_detection_register.model.bo.UserInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.Assert.*;

public class UserInfoMapperTest extends FaceDetectionRegisterApplicationTests {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Test
    public void insert() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(UUID.randomUUID().toString().replace("-",""));
        userInfo.setFaceToken("ceshi");
        userInfo.setUserName("季才");
        userInfo.setIsLock(0);
        userInfo.setIsDelete(0);
        int i = userInfoMapper.insert(userInfo);
        System.out.println(i);
    }
}