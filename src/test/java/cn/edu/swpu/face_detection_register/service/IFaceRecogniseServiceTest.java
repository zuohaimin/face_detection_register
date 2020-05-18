package cn.edu.swpu.face_detection_register.service;

import cn.edu.swpu.face_detection_register.model.dto.ExcutionResultUtil;
import cn.edu.swpu.face_detection_register.util.FileUtil;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IFaceRecogniseServiceTest {
    @Autowired
    private IFaceRecogniseService faceRecogniseService;

    @Test
    public void userRecognit() {
        String base64Url = FileUtil.ImageToBase64("D:\\faceRecogniseSource\\TEST\\WIN_20200515_23_13_12_Pro.jpg");
        ExcutionResultUtil excutionResultUtil = faceRecogniseService.userRecognit(base64Url, 111111);
        System.out.println(JSON.toJSONString(excutionResultUtil));
    }
}