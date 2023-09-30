package com.koipsool_new.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrderModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private List<MyOrderData> data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<MyOrderData> getData() {
        return data;
    }

    public void setData(List<MyOrderData> data) {
        this.data = data;
    }

    public  class  MyOrderData{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("client_id")
        @Expose
        private String clientId;
        @SerializedName("customer_name")
        @Expose
        private String customerName;
        @SerializedName("customer_address")
        @Expose
        private String customerAddress;
        @SerializedName("customer_state")
        @Expose
        private String customerState;
        @SerializedName("customer_city")
        @Expose
        private String customerCity;
        @SerializedName("customer_number")
        @Expose
        private String customerNumber;
        @SerializedName("total_amount")
        @Expose
        private String totalAmount;
        @SerializedName("gst")
        @Expose
        private String gst;
        @SerializedName("selling_in")
        @Expose
        private String sellingIn;
        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("hsn")
        @Expose
        private String hsn;
        @SerializedName("drug_licance")
        @Expose
        private String drugLicance;
        @SerializedName("order_date")
        @Expose
        private String orderDate;
        @SerializedName("updated_date")
        @Expose
        private String updatedDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerAddress() {
            return customerAddress;
        }

        public void setCustomerAddress(String customerAddress) {
            this.customerAddress = customerAddress;
        }

        public String getCustomerState() {
            return customerState;
        }

        public void setCustomerState(String customerState) {
            this.customerState = customerState;
        }

        public String getCustomerCity() {
            return customerCity;
        }

        public void setCustomerCity(String customerCity) {
            this.customerCity = customerCity;
        }

        public String getCustomerNumber() {
            return customerNumber;
        }

        public void setCustomerNumber(String customerNumber) {
            this.customerNumber = customerNumber;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getGst() {
            return gst;
        }

        public void setGst(String gst) {
            this.gst = gst;
        }

        public String getSellingIn() {
            return sellingIn;
        }

        public void setSellingIn(String sellingIn) {
            this.sellingIn = sellingIn;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getHsn() {
            return hsn;
        }

        public void setHsn(String hsn) {
            this.hsn = hsn;
        }

        public String getDrugLicance() {
            return drugLicance;
        }

        public void setDrugLicance(String drugLicance) {
            this.drugLicance = drugLicance;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

    }


}
