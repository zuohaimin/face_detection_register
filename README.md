面部识别的登录系统
目前支持扫脸注册，扫脸登录

实现逻辑： 注册： 获取人脸face_token(每张人脸唯一)，存库

登陆： 获取登陆人头像的Base64码，调用对比接口
匹配成功： 颁发token，进入个人页面 匹配失败： 登陆失败

支持简单集成，如SpringSecurity
支持嵌入式段的集成
支持第三方接口的集成
后台： 面部信息分析统计
![avatar](https://github.com/zuohaimin/face_detection_register/blob/master/src/main/resources/images/%E5%B9%B4%E9%BE%84%E5%88%86%E5%B8%83%E5%9B%BE.png)
