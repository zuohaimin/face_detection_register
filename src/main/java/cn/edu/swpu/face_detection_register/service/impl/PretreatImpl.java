package cn.edu.swpu.face_detection_register.service.impl;/**
 * Created by Administrator on 2019/5/3.
 *
 * @author Administrator
 */

import cn.edu.swpu.face_detection_register.model.dto.ExcutionResultUtil;
import cn.edu.swpu.face_detection_register.service.Pretreat;
import cn.edu.swpu.face_detection_register.util.ContextPropertiesUtil;
import cn.edu.swpu.face_detection_register.util.PathUtil;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_imgproc.equalizeHist;
import static org.bytedeco.opencv.global.opencv_imgproc.resize;

/**
 * @ClassName PretreatImpl
 * @Description 预处理实现类
 * @Autor Administrator
 * @Date 2019/5/3 17:04
 **/
@Service
@EnableConfigurationProperties({ContextPropertiesUtil.class})
public class PretreatImpl implements Pretreat {

    @Autowired
    private ContextPropertiesUtil contextProperties;

    /**
     * 用于存放检测出人脸的区域
     */
    RectVector faces = null;
    /**
     * 设置日期格式
     */
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 通过图片路径识别人脸
     *
     * @param path
     * @return
     */
    private ExcutionResultUtil detectFaceByPath(String path) {
        //每次调用检测人脸方法都要重新初始化人脸区域
        faces = new RectVector();

        //以8位单通道灰度的形式读入指定图片
        Mat image = imread(path, 0);
        //对图片做均衡化处理
        equalizeHist(image, image);

        //创建分类器  haarcascade_frontalface_alt.xml haarcascade_frontalface_alt2.xml haarcascade_frontalface_alt_tree
        // .xml haarcascade_frontalface_default.xml lbpcascade_frontalface.xml
        CascadeClassifier faceClassifier = new CascadeClassifier(System.getProperty("user.dir") +
                "\\src\\main\\resources\\classifiler\\lbpcascade_frontalface.xml");
        CascadeClassifier eyeClassifier = new CascadeClassifier(System.getProperty("user.dir") +
                "\\src\\main\\resources\\classifiler\\haarcascade_eye_tree_eyeglasses.xml");
        //从照片中检测人脸区域
        faceClassifier.detectMultiScale(image, faces, 1.1, 1, IMREAD_GRAYSCALE, new Size(30, 30), new Size(0,
                0));


        if (faces.size() <= 0) {
            return new ExcutionResultUtil(false, "没有检测到人脸，请重新拍摄");
        } else if (faces.size() > 1) {
            //创建临时人脸存储容器
            RectVector facestemp = new RectVector();
            //当检测出多个人脸时，遍历检测出来的人脸
            for (int i = 0; i < faces.size(); i++) {
                //截取当前人脸
                Mat face = image.apply(faces.get(i));
                RectVector eye = new RectVector();
                //进行人眼检测
                eyeClassifier.detectMultiScale(face, eye, 1.1, 1, IMREAD_GRAYSCALE, new Size(3, 3), new Size(0, 0));
                if (eye.size() == 2) {
                    facestemp.put(faces.get(i));
                }
            }
            if (facestemp.size() == 1) {
                faces = facestemp;
                return new ExcutionResultUtil(true, "检测人脸成功");
            } else {
                return new ExcutionResultUtil(false, "检测到多个人脸，请重新拍摄");
            }
        }
        return new ExcutionResultUtil(true, "检测人脸成功");

    }

    /**
     * 裁剪人脸
     *
     * @param imagePath
     * @param username
     * @return
     */
    private ExcutionResultUtil cutFace(String imagePath, String username) {

        //用户人脸信息文件夹
        String dirPath = contextProperties.getFacesPath() + username;
        //生成指定文件夹
        PathUtil.makeDirPath(dirPath);
        //根据当前时间生成人脸存储路径
        String facePath = dirPath + "\\" + df.format(new Date()) + ".jpg";

        try {
            //获取源图片
            Mat image = imread(imagePath, 0);
            //获取当前人脸位置信息
            Rect rect = faces.get(0);
            //截取当前人脸
            Mat face = image.apply(rect);
            //接收大小规格化后的人脸
            Mat mat = new Mat();
            //指定规格化大小
            Size size = new Size(100, 100);
            //规格化
            resize(face, mat, size);
            //保存到指定路径
            boolean imwrite = imwrite(facePath, mat);
            if (imwrite) {
                return new ExcutionResultUtil(true, facePath);
            } else {
                return new ExcutionResultUtil(false, "保存截取图片失败");
            }
        } catch (Exception e) {
            return new ExcutionResultUtil(false, "截取人脸失败:" + e.getMessage());
        } finally {
            //当裁剪人脸后，删除Image图片
            PathUtil.deleteDirPath(imagePath);
        }


    }

    /**
     * 存储图片
     *
     * @param img
     * @return 保存成功则返回存储路径，保存失败则返回失败信息
     */
    private ExcutionResultUtil saveImg(String img) {
        //用base64替代Base64Decoder
//        Base64.Decoder decoder = Base64.getDecoder();
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = new byte[0];
        try {
            bytes = decoder.decodeBuffer(img);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ExcutionResultUtil(false, "保存图片失败");
        }
        //生成指定文件夹
        PathUtil.makeDirPath(contextProperties.getImagePath());
        FileOutputStream fileOutputStream = null;
        //图片存储路径
        String imagePath = contextProperties.getImagePath() + "image.jpg";
        try {
            //当为新用户则保存当前图片
            fileOutputStream = new FileOutputStream(imagePath);
            fileOutputStream.write(bytes);
            return new ExcutionResultUtil(true, imagePath);

        } catch (IOException e) {
            return new ExcutionResultUtil(false, "保存图片失败");
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ExcutionResultUtil pretreatImg(String img, String username) {
        username = String.valueOf(username.hashCode());
        //保存图片
        ExcutionResultUtil saveImg = saveImg(img);
        if (saveImg.isSuccess()) {

            //保存成功则进行预处理

            //获取图片保存路径
            String imagePath = saveImg.getMsg();

            //检测出人脸区域并保存至RectVector中
            ExcutionResultUtil detectFaceByPath = detectFaceByPath(imagePath);
            if (!detectFaceByPath.isSuccess()) {
                //如果检测人脸失败需要删除文件
                ExcutionResultUtil delectDirPath = PathUtil.deleteDirPath(imagePath);
                if (delectDirPath.isSuccess()) {
                    //删除成功则返回裁剪失败原因
                    return new ExcutionResultUtil(false, detectFaceByPath.getMsg());
                } else {
                    //删除失败则返回删除失败原因
                    return new ExcutionResultUtil(false, detectFaceByPath.getMsg() + delectDirPath.getMsg());
                }
            }

            //裁剪人脸
            ExcutionResultUtil cutFace = cutFace(imagePath, username);
            return cutFace;

        } else {
            //保存失败
            return new ExcutionResultUtil(false, saveImg.getMsg());
        }


    }

}
