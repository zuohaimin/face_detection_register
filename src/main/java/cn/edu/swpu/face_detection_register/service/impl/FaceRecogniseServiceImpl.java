package cn.edu.swpu.face_detection_register.service.impl;

import cn.edu.swpu.face_detection_register.model.dto.ExcutionResultUtil;
import cn.edu.swpu.face_detection_register.model.dto.Faces;
import cn.edu.swpu.face_detection_register.service.IFaceRecogniseService;
import cn.edu.swpu.face_detection_register.service.Pretreat;
import cn.edu.swpu.face_detection_register.service.Recognit;
import cn.edu.swpu.face_detection_register.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FaceRecogniseServiceImpl implements IFaceRecogniseService {
    @Autowired
    private Pretreat pretreat;

    @Autowired
    private Recognit recognit;

    @Override
    public ExcutionResultUtil userRecognit(String img, int userid) {
        //通过用户id获取用户人脸库
        //TODO
//        List<Faces> facesByUserId = facesDao.getFacesByUserId(userid);
        List<Faces> facesByUserId = getFacesByUserId(userid);
        //对图片进行预处理
        ExcutionResultUtil pretreatImg = pretreat.pretreatImg(img, "temp");

        if (!pretreatImg.isSuccess()) {
            //预处理失败则返回
            return new ExcutionResultUtil(false, pretreatImg.getMsg());
        }

        //需要进行比对的人脸路径
        String tempPath = pretreatImg.getMsg();

        try {
            for (Faces face : facesByUserId) {
                //将人脸库图片进行比对
                ExcutionResultUtil compare = recognit.compare(tempPath, face.getPath());
                if (compare.isSuccess()) {
                    return compare;
                }
            }
            return new ExcutionResultUtil(false, "匹配失败");
        } finally {
            if (pretreatImg.isSuccess()) {
                //将比对图片删除
                PathUtil.deleteDirPath(tempPath);
            }
        }

    }

    private List<Faces> getFacesByUserId(int userid) {
        List<Faces> facesList = new ArrayList<>();
        Faces faces = new Faces();
        facesList.add(faces);
        faces.setId(111111);
        faces.setUserid(userid);
        faces.setPath("D:\\faceRecogniseSource\\FACES\\-916823210\\20200515232012.jpg");
        return facesList;
    }
}
