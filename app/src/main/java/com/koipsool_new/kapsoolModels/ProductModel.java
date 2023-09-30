package com.koipsool_new.kapsoolModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<ProductData> data;

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

    public List<ProductData> getData() {
        return data;
    }

    public void setData(List<ProductData> data) {
        this.data = data;
    }


    public  class  ProductData{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("company_id")
        @Expose
        private String companyId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("desciption")
        @Expose
        private String desciption;
        @SerializedName("batch_no")
        @Expose
        private String batchNo;
        @SerializedName("expiry")
        @Expose
        private String expiry;
        @SerializedName("mrp")
        @Expose
        private String mrp;
        @SerializedName("tax_class")
        @Expose
        private String taxClass;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("hsn_code")
        @Expose
        private String hsn_code;
        @SerializedName("cate_name")
        @Expose
        private String cate_name;

        public String getHsn_code() {
            return hsn_code;
        }

        public void setHsn_code(String hsn_code) {
            this.hsn_code = hsn_code;
        }

        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesciption() {
            return desciption;
        }

        public void setDesciption(String desciption) {
            this.desciption = desciption;
        }

        public String getBatchNo() {
            return batchNo;
        }

        public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
        }

        public String getExpiry() {
            return expiry;
        }

        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public String getTaxClass() {
            return taxClass;
        }

        public void setTaxClass(String taxClass) {
            this.taxClass = taxClass;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }


    }

}
