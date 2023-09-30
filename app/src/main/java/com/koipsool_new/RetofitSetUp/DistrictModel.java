package com.koipsool_new.RetofitSetUp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DistrictModel {

        @SerializedName("result")
        @Expose
        private String result;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("data")
        @Expose
        private List<DistrictData> data = null;

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

        public List<DistrictData> getData() {
            return data;
        }

        public void setData(List<DistrictData> data) {
            this.data = data;
        }


    public class DistrictData {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("district")
        @Expose
        private String district;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

    }

    }