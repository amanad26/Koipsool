package com.koipsool_new.kapsoolModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PharmaCompanyModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<Datum> data;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("company_name")
        @Expose
        private String companyName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("otp")
        @Expose
        private String otp;
        @SerializedName("verify_otp")
        @Expose
        private String verifyOtp;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("state_id")
        @Expose
        private String stateId;
        @SerializedName("city_id")
        @Expose
        private String cityId;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("updated_date")
        @Expose
        private String updatedDate;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("pin_code")
        @Expose
        private String pinCode;
        @SerializedName("buyerc_ontact_number")
        @Expose
        private String buyercOntactNumber;
        @SerializedName("drugs_licence_image")
        @Expose
        private String drugsLicenceImage;
        @SerializedName("gst_no")
        @Expose
        private String gstNo;
        @SerializedName("fcm_id")
        @Expose
        private String fcmId;
        @SerializedName("ceiling_in")
        @Expose
        private String ceilingIn;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getVerifyOtp() {
            return verifyOtp;
        }

        public void setVerifyOtp(String verifyOtp) {
            this.verifyOtp = verifyOtp;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStateId() {
            return stateId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPinCode() {
            return pinCode;
        }

        public void setPinCode(String pinCode) {
            this.pinCode = pinCode;
        }

        public String getBuyercOntactNumber() {
            return buyercOntactNumber;
        }

        public void setBuyercOntactNumber(String buyercOntactNumber) {
            this.buyercOntactNumber = buyercOntactNumber;
        }

        public String getDrugsLicenceImage() {
            return drugsLicenceImage;
        }

        public void setDrugsLicenceImage(String drugsLicenceImage) {
            this.drugsLicenceImage = drugsLicenceImage;
        }

        public String getGstNo() {
            return gstNo;
        }

        public void setGstNo(String gstNo) {
            this.gstNo = gstNo;
        }

        public String getFcmId() {
            return fcmId;
        }

        public void setFcmId(String fcmId) {
            this.fcmId = fcmId;
        }

        public String getCeilingIn() {
            return ceilingIn;
        }

        public void setCeilingIn(String ceilingIn) {
            this.ceilingIn = ceilingIn;
        }

    }
}
