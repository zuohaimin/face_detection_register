package cn.edu.swpu.face_detection_register.cons;

/**
 * @Author: 季才
 * @Date: 2020/1/8
 * @Description:
 **/
public interface FaceDetectionRegisterConstrant {

    String API_KEY = "teiv2vs5oFWLjcgZU4G3VwwK";
    String SECRET_KEY = "KGEA7V8RDx4yrbthIakGOqiF19jSrVsX";
    String BAIDU_ADD_FACE = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add?access_token={1}";
    String BAIDU_GET_ACCESSTOKEN = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id={1}&client_secret={2}";
    String BAIDU_UPDATE_FACE = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/update?access_token={1}";
    String BAIDU_DELETE_FACE = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/face/delete?access_token={1}";
    String BAIDU_MATCH_FACE = "https://aip.baidubce.com/rest/2.0/face/v3/match?access_token={1}";
    String BAIDU_DETECT_FACE = "https://aip.baidubce.com/rest/2.0/face/v3/detect?access_token={1}";
    String BAIDU_SEARCH_FACE = "https://aip.baidubce.com/rest/2.0/face/v3/search?access_token={1}";
}
