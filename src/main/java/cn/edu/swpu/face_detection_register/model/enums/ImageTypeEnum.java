package cn.edu.swpu.face_detection_register.model.enums;

import lombok.Getter;

@Getter
public enum ImageTypeEnum {
    BASE64("BASE64"),
    URL("URL"),
    FACE_TOKEN("FACE_TOKEN");

    private String imageType;

    ImageTypeEnum(String imageType) {
        this.imageType = imageType;
    }
}
