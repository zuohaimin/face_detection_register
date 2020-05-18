package cn.edu.swpu.face_detection_register.model.dto;/**
 * Created by Administrator on 2019/5/3.
 *
 * @author Administrator
 */

/**
 *
 *@ClassName ExcutionResultUtil
 *@Description 返回结果包装类
 *@Autor Administrator
 *@Date 2019/5/3 20:39
 **/
public class ExcutionResultUtil {
    private boolean isSuccess;
    private String Msg;

    public ExcutionResultUtil(boolean isSuccess, String msg) {
        this.isSuccess = isSuccess;
        Msg = msg;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMsg() {
        return Msg;
    }
}
