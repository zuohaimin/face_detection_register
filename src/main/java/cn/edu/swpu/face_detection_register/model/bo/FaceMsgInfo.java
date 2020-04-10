package cn.edu.swpu.face_detection_register.model.bo;

import java.util.Date;

public class FaceMsgInfo {
    private Long keyId;

    private String userId;

    private Short age;

    private String faceShape;

    private String gender;

    private String glasses;

    private String emotion;

    private Byte beauty;

    private String expression;

    private Date addTime;

    private Date modifyTime;

    private Byte isDelete;

    public FaceMsgInfo(Long keyId, String userId, Short age, String faceShape, String gender, String glasses, String emotion, Byte beauty, String expression, Date addTime, Date modifyTime, Byte isDelete) {
        this.keyId = keyId;
        this.userId = userId;
        this.age = age;
        this.faceShape = faceShape;
        this.gender = gender;
        this.glasses = glasses;
        this.emotion = emotion;
        this.beauty = beauty;
        this.expression = expression;
        this.addTime = addTime;
        this.modifyTime = modifyTime;
        this.isDelete = isDelete;
    }

    public Long getKeyId() {
        return keyId;
    }

    public String getUserId() {
        return userId;
    }

    public Short getAge() {
        return age;
    }

    public String getFaceShape() {
        return faceShape;
    }

    public String getGender() {
        return gender;
    }

    public String getGlasses() {
        return glasses;
    }

    public String getEmotion() {
        return emotion;
    }

    public Byte getBeauty() {
        return beauty;
    }

    public String getExpression() {
        return expression;
    }

    public Date getAddTime() {
        return addTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public Byte getIsDelete() {
        return isDelete;
    }
}