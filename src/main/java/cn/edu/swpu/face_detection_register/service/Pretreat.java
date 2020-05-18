package cn.edu.swpu.face_detection_register.service;/**
 * Created by Administrator on 2019/5/3.
 *
 * @author Administrator
 */


import cn.edu.swpu.face_detection_register.model.dto.ExcutionResultUtil;

/**
 * @ClassName Pretreat
 * @Description 预处理接口
 * @Autor Administrator
 * @Date 2019/5/3 17:03
 **/
public interface Pretreat {


    /**
     * 对图片进行预处理
     *
     * @param img
     * @return
     */
    ExcutionResultUtil pretreatImg(String img, String username);
}
