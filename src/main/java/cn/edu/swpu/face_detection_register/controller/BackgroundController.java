package cn.edu.swpu.face_detection_register.controller;

import cn.edu.swpu.face_detection_register.model.vo.EchartsDataVo;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.service.IEchartsService;
import cn.edu.swpu.face_detection_register.service.impl.EchartsServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(value = "网站后台接口集")
@RequestMapping(value = "/background")
@CrossOrigin
public class BackgroundController {
    @Autowired
    private IEchartsService echartsService;
    //    - 性别比例
    @GetMapping(value = "/sexRadioChart")
    ResponseVo<List<EchartsDataVo>> sexRadioChart(){
        return echartsService.sexRadioChart();
    }
    //    - 颜值曲线
    @GetMapping(value = "/faceValueLine")
    ResponseVo<List<List<Integer>>> faceValueCurve(){
        return echartsService.faceValueLine();
    }
    //    - 脸型比例
    @GetMapping(value = "/faceShapePie")
    ResponseVo<List<EchartsDataVo>> faceShapePie(){
        return echartsService.faceShapePie();
    }
    //    - 年龄
    @GetMapping(value = "/ageDistributePie")
    ResponseVo<List<EchartsDataVo>> ageDistributePie(){
        return echartsService.ageDistributePie();
    }

}
