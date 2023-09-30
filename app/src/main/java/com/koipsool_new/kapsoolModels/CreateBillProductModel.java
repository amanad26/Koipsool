package com.koipsool_new.kapsoolModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CreateBillProductModel implements Serializable  {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<OrderItemData> data;

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

    public List<OrderItemData> getData() {
        return data;
    }

    public void setData(List<OrderItemData> data) {
        this.data = data;
    }

    public  class  OrderItemData implements  Serializable{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("client_id")
        @Expose
        private String clientId;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("product_mrp")
        @Expose
        private String productMrp;
        @SerializedName("product_selling_price")
        @Expose
        private String productSellingPrice;
        @SerializedName("product_quantity")
        @Expose
        private String productQuantity;
        @SerializedName("product_free")
        @Expose
        private String productFree;
        @SerializedName("product_free_name")
        @Expose
        private String productFreeName;
        @SerializedName("product_free_id")
        @Expose
        private String productFreeId;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("updated_date")
        @Expose
        private String updatedDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductMrp() {
            return productMrp;
        }

        public void setProductMrp(String productMrp) {
            this.productMrp = productMrp;
        }

        public String getProductSellingPrice() {
            return productSellingPrice;
        }

        public void setProductSellingPrice(String productSellingPrice) {
            this.productSellingPrice = productSellingPrice;
        }

        public String getProductQuantity() {
            return productQuantity;
        }

        public void setProductQuantity(String productQuantity) {
            this.productQuantity = productQuantity;
        }

        public String getProductFree() {
            return productFree;
        }

        public void setProductFree(String productFree) {
            this.productFree = productFree;
        }

        public String getProductFreeName() {
            return productFreeName;
        }

        public void setProductFreeName(String productFreeName) {
            this.productFreeName = productFreeName;
        }

        public String getProductFreeId() {
            return productFreeId;
        }

        public void setProductFreeId(String productFreeId) {
            this.productFreeId = productFreeId;
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
    }


}
