package cn.edu.swpu.face_detection_register.service.impl;/**
 * Created by Administrator on 2019/5/5.
 *
 * @author Administrator
 */

import cn.edu.swpu.face_detection_register.model.dto.ExcutionResultUtil;
import cn.edu.swpu.face_detection_register.service.Pretreat;
import cn.edu.swpu.face_detection_register.service.Recognit;
import cn.edu.swpu.face_detection_register.util.ContextPropertiesUtil;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.opencv.opencv_core.CvHistogram;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import static org.bytedeco.opencv.global.opencv_core.CV_HIST_ARRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.*;
import static org.bytedeco.opencv.helper.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.opencv.helper.opencv_imgproc.cvCalcHist;

/**
 * @ClassName RecoginteImpl
 * @Description 识别实现类
 * @Autor Administrator
 * @Date 2019/5/5 22:10
 **/
@Service
@EnableConfigurationProperties({ContextPropertiesUtil.class})
@Slf4j
public class RecoginteImpl implements Recognit {
    @Autowired
    private Pretreat pretreat;
    @Autowired
    private ContextPropertiesUtil contextPropertiesUtil;

    @Override
    public ExcutionResultUtil compare(String tempPath, String imagePath) {

        try {
            //CV_LOAD_IMAGE_GRAYSCALE = 0 ,将指定图像处理为灰度图
            IplImage image1 = cvLoadImage(imagePath, 0);
            IplImage image2 = cvLoadImage(tempPath, 0);
            if (null == image1 || null == image2) {
                return new ExcutionResultUtil(false, "比较对象为空");
            }

            int l_bins = 256;
            int hist_size[] = {l_bins};
            float v_ranges[] = {0, 255};
            float ranges[][] = {v_ranges};

            IplImage imageArr1[] = {image1};
            IplImage imageArr2[] = {image2};
            //创建直方图 https://blog.csdn.net/t1234xy4/article/details/51713895
            CvHistogram Histogram1 = CvHistogram.create(1, hist_size, CV_HIST_ARRAY, ranges, 1);
            CvHistogram Histogram2 = CvHistogram.create(1, hist_size, CV_HIST_ARRAY, ranges, 1);
            //计算图片的直方图
            cvCalcHist(imageArr1, Histogram1, 0, null);
            cvCalcHist(imageArr2, Histogram2, 0, null);
            //通过缩放来归一化直方块，使得所有块的和等于 factor.
            cvNormalizeHist(Histogram1, 100.0);
            cvNormalizeHist(Histogram2, 100.0);
            // 参考：http://blog.csdn.net/nicebooks/article/details/8175002
            double c1 = cvCompareHist(Histogram1, Histogram2, CV_COMP_CORREL) * 100;
            double c2 = cvCompareHist(Histogram1, Histogram2, CV_COMP_INTERSECT);
            double matchingRate = (c1 + c2) / 2;
            log.info("匹配度 ："+matchingRate);
            if (matchingRate > contextPropertiesUtil.getMatching()) {
                return new ExcutionResultUtil(true, "匹配度高");
            } else {
                return new ExcutionResultUtil(false, "匹配度低,请重新登录，匹配度为：" + matchingRate);
            }
        } catch (Exception e) {
            return new ExcutionResultUtil(false, "比较失败" + e.getMessage());
        }
    }

}
