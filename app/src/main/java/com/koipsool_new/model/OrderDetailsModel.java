package com.koipsool_new.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("order")
    @Expose
    private Order order;
    @SerializedName("item_data")
    @Expose
    private List<ItemDatum> itemData;

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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<ItemDatum> getItemData() {
        return itemData;
    }

    public void setItemData(List<ItemDatum> itemData) {
        this.itemData = itemData;
    }


    public class ItemDatum {

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


    public class Order {

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
        @SerializedName("status")
        @Expose
        private String status;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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
