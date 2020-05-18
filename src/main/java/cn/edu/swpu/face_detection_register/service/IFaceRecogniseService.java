package cn.edu.swpu.face_detection_register.service;

import cn.edu.swpu.face_detection_register.model.dto.ExcutionResultUtil;

public interface IFaceRecogniseService {
    ExcutionResultUtil userRecognit(String img, int userid);
}
