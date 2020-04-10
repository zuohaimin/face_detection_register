package cn.edu.swpu.face_detection_register.dao;

import cn.edu.swpu.face_detection_register.FaceDetectionRegisterApplicationTests;
import cn.edu.swpu.face_detection_register.model.bo.UserInfo;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.model.vo.SearchFaceVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.json.JSONObject;
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

    @Test
    public void jsonParseTest() {
        String param = "{\"error_code\":0,\"error_msg\":\"SUCCESS\",\"log_id\":1017999201796,\"timestamp\":1586314473,\"cached\":0,\"result\":{\"face_token\":\"1c2f42c20cf6bfe34f142d69f9ba3b05\",\"user_list\":[{\"group_id\":\"201912311715\",\"user_id\":\"3ed76b55058a4b17a7894630754e750d\",\"user_info\":\"zuohaimin\",\"score\":98.126693725586}]}}";
        ResponseVo<SearchFaceVo> searchFaceVoResponseVo = JSON.parseObject(param, new TypeReference<ResponseVo<SearchFaceVo>>() {});
        System.out.println(JSON.toJSON(searchFaceVoResponseVo));
    }
}