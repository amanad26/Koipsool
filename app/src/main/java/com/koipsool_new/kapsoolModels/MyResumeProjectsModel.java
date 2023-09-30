package com.koipsool_new.kapsoolModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MyResumeProjectsModel implements Serializable{


    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<ResumeProjectData> data;

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

    public List<ResumeProjectData> getData() {
        return data;
    }

    public void setData(List<ResumeProjectData> data) {
        this.data = data;
    }

    public class  ResumeProjectData implements Serializable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("medical_id")
        @Expose
        private String medicalId;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("start_date")
        @Expose
        private String startDate;
        @SerializedName("end_date")
        @Expose
        private String endDate;
        @SerializedName("currently_going")
        @Expose
        private String currentlyGoing;
        @SerializedName("discription")
        @Expose
        private String discription;
        @SerializedName("project_link")
        @Expose
        private String projectLink;
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

        public String getMedicalId() {
            return medicalId;
        }

        public void setMedicalId(String medicalId) {
            this.medicalId = medicalId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getCurrentlyGoing() {
            return currentlyGoing;
        }

        public void setCurrentlyGoing(String currentlyGoing) {
            this.currentlyGoing = currentlyGoing;
        }

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
        }

        public String getProjectLink() {
            return projectLink;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
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
