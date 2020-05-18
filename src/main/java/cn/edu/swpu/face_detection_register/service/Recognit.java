package cn.edu.swpu.face_detection_register.service;/**
 * Created by Administrator on 2019/5/5.
 *
 * @author Administrator
 */


import cn.edu.swpu.face_detection_register.model.dto.ExcutionResultUtil;

/**
 *
 *@ClassName Recognit
 *@Description 识别接口
 *@Autor Administrator
 *@Date 2019/5/5 21:59
 **/
public interface Recognit {
    /**
     * 对登录需要检验的图片做预处理
     * @param imagePath
     * @return
     */
    ExcutionResultUtil compare(String tempPath, String imagePath);

}
