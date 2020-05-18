package cn.edu.swpu.face_detection_register.service;

import cn.edu.swpu.face_detection_register.model.dto.ExcutionResultUtil;
import cn.edu.swpu.face_detection_register.util.FileUtil;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.misc.BASE64Encoder;

import java.io.File;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PretreatTest {
    @Autowired
    private Pretreat pretreat;

    @Test
    public void pretreatImg() {
        String base64Url = FileUtil.ImageToBase64("D:\\faceRecogniseSource\\TEST\\WIN_20200511_23_56_30_Pro.jpg");
        ExcutionResultUtil response = pretreat.pretreatImg(base64Url, "zuohaimin");
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void getUserDir(){
        System.out.println(System.getProperty("user.dir"));
    }
}