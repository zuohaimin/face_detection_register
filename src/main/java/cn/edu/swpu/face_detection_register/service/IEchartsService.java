package cn.edu.swpu.face_detection_register.service;

import cn.edu.swpu.face_detection_register.model.vo.EchartsDataVo;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import com.github.abel533.echarts.Option;

import java.util.List;
import java.util.Map;

public interface IEchartsService {
    ResponseVo<List<EchartsDataVo>> ageDistributePie();

    ResponseVo<List<EchartsDataVo>> faceShapePie();

    ResponseVo<List<List<Integer>>> faceValueLine();


    ResponseVo<List<EchartsDataVo>> sexRadioChart();

}
