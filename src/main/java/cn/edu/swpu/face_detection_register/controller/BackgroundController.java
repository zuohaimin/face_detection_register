package cn.edu.swpu.face_detection_register.controller;

import cn.edu.swpu.face_detection_register.model.vo.EchartsDataVo;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.service.IEchartsService;
import cn.edu.swpu.face_detection_register.service.impl.EchartsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "性别比例图")
    @GetMapping(value = "/sexRadioChart")
    public ResponseVo<List<EchartsDataVo>> sexRadioChart(){
        return echartsService.sexRadioChart();
    }
    //    - 颜值曲线
    @ApiOperation(value = "颜值曲线图")
    @GetMapping(value = "/faceValueLine")
    public ResponseVo<List<List<Integer>>> faceValueCurve(){
        return echartsService.faceValueLine();
    }
    //    - 脸型比例
    @ApiOperation(value = "脸型比例图")
    @GetMapping(value = "/faceShapePie")
    public ResponseVo<List<EchartsDataVo>> faceShapePie(){
        return echartsService.faceShapePie();
    }
    //    - 年龄
    @GetMapping(value = "/ageDistributePie")
    @ApiOperation(value = "年龄饼图")
    public ResponseVo<List<EchartsDataVo>> ageDistributePie(){
        return echartsService.ageDistributePie();
    }
}
