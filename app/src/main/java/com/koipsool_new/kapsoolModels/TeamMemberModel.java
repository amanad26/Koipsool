package com.koipsool_new.kapsoolModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamMemberModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<MemberData> data = null;

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

    public List<MemberData> getData() {
        return data;
    }

    public void setData(List<MemberData> data) {
        this.data = data;
    }

    public  class  MemberData{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("company_id")
        @Expose
        private String companyId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("department")
        @Expose
        private String department;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("work")
        @Expose
        private String work;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("image")
        @Expose
        private String image = null;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getWork() {
            return work;
        }

        public void setWork(String work) {
            this.work = work;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }


    }
}
