package com.koipsool_new.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChemistSignupModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("otp")
    @Expose
    private Integer otp;
    @SerializedName("data")
    @Expose
    private ChemistSignupData data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public ChemistSignupData getData() {
        return data;
    }

    public void setData(ChemistSignupData data) {
        this.data = data;
    }

    public  class  ChemistSignupData{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("fcm_id")
        @Expose
        private String fcmId;
        @SerializedName("verify_otp")
        @Expose
        private String verifyOtp;
        @SerializedName("otp")
        @Expose
        private String otp;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFcmId() {
            return fcmId;
        }

        public void setFcmId(String fcmId) {
            this.fcmId = fcmId;
        }

        public String getVerifyOtp() {
            return verifyOtp;
        }

        public void setVerifyOtp(String verifyOtp) {
            this.verifyOtp = verifyOtp;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }
    }

}
