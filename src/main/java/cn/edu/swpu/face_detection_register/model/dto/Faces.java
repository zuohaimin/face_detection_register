package cn.edu.swpu.face_detection_register.model.dto;/**
 * Created by Administrator on 2019/5/11.
 *
 * @author Administrator
 */

/**
 * @ClassName Faces
 * @Description TODO
 * @Autor Administrator
 * @Date 2019/5/11 21:43
 **/
public class Faces {
    private int id;
    private String path;
    private int userid;

    public Faces(String path, int userid) {
        this.path = path;
        this.userid = userid;
    }

    public Faces(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Faces{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", userid=" + userid +
                '}';
    }
}
