package com.koipsool_new.kapsoolModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CouponsModel {


    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<CouponsData> data;

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

    public List<CouponsData> getData() {
        return data;
    }

    public void setData(List<CouponsData> data) {
        this.data = data;
    }


    public  class  CouponsData{
        @SerializedName("company_name")
        @Expose
        private String companyName;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("company_id")
        @Expose
        private String companyId;
        @SerializedName("product")
        @Expose
        private String product;
        @SerializedName("mrp")
        @Expose
        private String mrp;
        @SerializedName("corporation")
        @Expose
        private String corporation;
        @SerializedName("offer")
        @Expose
        private String offer;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("percentage")
        @Expose
        private String percentage;
        @SerializedName("discount_price")
        @Expose
        private String discountPrice;
        @SerializedName("coupon_validity")
        @Expose
        private String couponValidity;
        @SerializedName("available")
        @Expose
        private String available;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("update_date")
        @Expose
        private String updateDate;

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
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

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public String getCorporation() {
            return corporation;
        }

        public void setCorporation(String corporation) {
            this.corporation = corporation;
        }

        public String getOffer() {
            return offer;
        }

        public void setOffer(String offer) {
            this.offer = offer;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getCouponValidity() {
            return couponValidity;
        }

        public void setCouponValidity(String couponValidity) {
            this.couponValidity = couponValidity;
        }

        public String getAvailable() {
            return available;
        }

        public void setAvailable(String available) {
            this.available = available;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }
    }

}
