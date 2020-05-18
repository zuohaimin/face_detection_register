package cn.edu.swpu.face_detection_register.util;/**
 * Created by Administrator on 2019/5/3.
 *
 * @author Administrator
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName ContextPropertiesUtil
 * @Description 配置参数
 * @Autor Administrator
 * @Date 2019/5/3 21:43
 **/
@ConfigurationProperties(prefix = "properties")
public class ContextPropertiesUtil {
    private String imagePath;
    private String facesPath;
    private double matching;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getFacesPath() {
        return facesPath;
    }

    public void setFacesPath(String facesPath) {
        this.facesPath = facesPath;
    }

    public double getMatching() {
        return matching;
    }

    public void setMatching(double matching) {
        this.matching = matching;
    }
}
