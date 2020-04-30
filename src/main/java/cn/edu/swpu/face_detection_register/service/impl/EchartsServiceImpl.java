package cn.edu.swpu.face_detection_register.service.impl;

import cn.edu.swpu.face_detection_register.dao.FaceMsgInfoMapper;
import cn.edu.swpu.face_detection_register.exception.SystemException;
import cn.edu.swpu.face_detection_register.model.enums.ExceptionInfoEnum;
import cn.edu.swpu.face_detection_register.model.vo.EchartsDataVo;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.service.IEchartsService;
import cn.edu.swpu.face_detection_register.util.ResponseVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EchartsServiceImpl implements IEchartsService {

    @Autowired
    private FaceMsgInfoMapper faceMsgInfoMapper;
    @Override
    public ResponseVo<List<EchartsDataVo>> ageDistributePie() {
        List<EchartsDataVo> echartsDataVos = faceMsgInfoMapper.selectFaceMsgGroupByAge();
        if (echartsDataVos == null || echartsDataVos.size() < 1 ) {
            throw new SystemException(ExceptionInfoEnum.FACE_MSG_NULL,null);
        }
        Map<String,Integer> ageDistributeData = new HashMap<>();
        //少年 < 13
        ageDistributeData.put("少年",0);
        //青少年 13-17
        ageDistributeData.put("青少年",0);
        //青年 18-35
        ageDistributeData.put("青年",0);
        //中年 36-45
        ageDistributeData.put("中年",0);
        //中老年 46-60
        ageDistributeData.put("中老年",0);
        //老年 >60
        ageDistributeData.put("老年",0);
        echartsDataVos.forEach(o->{

            if (Integer.valueOf(o.getName()) < 0) {
                return;
            }else if (Integer.valueOf(o.getName()) < 13){
                ageDistributeData.put("少年",ageDistributeData.get("少年")+o.getValue());
            }else if (Integer.valueOf(o.getName()) < 18){
                ageDistributeData.put("青少年",ageDistributeData.get("青少年")+o.getValue());
            }else if (Integer.valueOf(o.getName()) < 36){
                ageDistributeData.put("青年",ageDistributeData.get("青年")+o.getValue());
            }else if (Integer.valueOf(o.getName()) < 46){
                ageDistributeData.put("中年",ageDistributeData.get("中年")+o.getValue());
            }else if (Integer.valueOf(o.getName()) < 61){
                ageDistributeData.put("中老年",ageDistributeData.get("中老年")+o.getValue());
            }else {
                ageDistributeData.put("老年",ageDistributeData.get("老年")+o.getValue());
            }
        });
        List<EchartsDataVo> result = new ArrayList<>();
        ageDistributeData.forEach((key,value)->{
            EchartsDataVo echartsDataVo = new EchartsDataVo();
            echartsDataVo.setName(key);
            echartsDataVo.setValue(value);
            result.add(echartsDataVo);
        });
        return ResponseVoUtil.success(result);
    }

    @Override
    public ResponseVo<List<EchartsDataVo>> faceShapePie() {
        List<EchartsDataVo> echartsDataVos = faceMsgInfoMapper.selectFaceMsgGroupByFaceShape();
        if (echartsDataVos == null || echartsDataVos.size() < 1) {
            throw new SystemException(ExceptionInfoEnum.FACE_MSG_NULL,null);
        }
        echartsDataVos.sort(Comparator.comparingInt(EchartsDataVo::getValue));
        return ResponseVoUtil.success(echartsDataVos);
    }

    @Override
    public ResponseVo<List<List<Integer>>> faceValueLine() {
        List<EchartsDataVo> echartsDataVos = faceMsgInfoMapper.selectFaceMsgGroupByBeauty();
        if (echartsDataVos == null || echartsDataVos.size() < 1) {
            throw new SystemException(ExceptionInfoEnum.FACE_MSG_NULL,null);
        }
        List<List<Integer>> result = new ArrayList<>();
        echartsDataVos.forEach(o->{
            List<Integer> point = new ArrayList<>(2);
            point.add(Integer.valueOf(o.getName()));
            point.add(o.getValue());
            result.add(point);
        });
        result.sort((a,b)->{
            return a.get(0)-b.get(0);
        });
        return ResponseVoUtil.success(result);
    }

    @Override
    public ResponseVo<List<EchartsDataVo>> sexRadioChart() {
        List<EchartsDataVo> echartsDataVos = faceMsgInfoMapper.selectFaceMsgGroupByGender();
        if (echartsDataVos == null || echartsDataVos.size() < 1) {
            throw new SystemException(ExceptionInfoEnum.FACE_MSG_NULL,null);
        }

        return ResponseVoUtil.success(echartsDataVos);
    }
}
