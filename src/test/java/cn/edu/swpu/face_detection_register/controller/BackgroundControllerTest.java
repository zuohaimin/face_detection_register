package cn.edu.swpu.face_detection_register.controller;

import cn.edu.swpu.face_detection_register.FaceDetectionRegisterApplicationTests;
import cn.edu.swpu.face_detection_register.model.dto.Base64ImageRequestParam;
import cn.edu.swpu.face_detection_register.model.vo.EchartsDataVo;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BackgroundControllerTest extends FaceDetectionRegisterApplicationTests {

    @Autowired
    private BackgroundController backgroundController;
    @Test
    public void ageDistributePie() {
        ResponseVo<List<EchartsDataVo>> mapResponseVo = backgroundController.ageDistributePie();
        System.out.println(JSON.toJSONString(mapResponseVo));
    }
}