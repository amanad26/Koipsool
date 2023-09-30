package com.koipsool_new.kapsoolModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResumeJobModel implements Serializable {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<ResumeJobData> data;

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

    public List<ResumeJobData> getData() {
        return data;
    }

    public void setData(List<ResumeJobData> data) {
        this.data = data;
    }


    public class ResumeJobData implements Serializable {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("medical_id")
        @Expose
        private String medicalId;
        @SerializedName("job_profile")
        @Expose
        private String jobProfile;
        @SerializedName("organization")
        @Expose
        private String organization;
        @SerializedName("work_from_home")
        @Expose
        private String workFromHome;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("start_date")
        @Expose
        private String startDate;
        @SerializedName("end_date")
        @Expose
        private String endDate;
        @SerializedName("currently_working")
        @Expose
        private String currentlyWorking;
        @SerializedName("work_discription")
        @Expose
        private String workDiscription;
        @SerializedName("type")
        @Expose
        private String type;
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

        public String getJobProfile() {
            return jobProfile;
        }

        public void setJobProfile(String jobProfile) {
            this.jobProfile = jobProfile;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public String getWorkFromHome() {
            return workFromHome;
        }

        public void setWorkFromHome(String workFromHome) {
            this.workFromHome = workFromHome;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
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

        public String getCurrentlyWorking() {
            return currentlyWorking;
        }

        public void setCurrentlyWorking(String currentlyWorking) {
            this.currentlyWorking = currentlyWorking;
        }

        public String getWorkDiscription() {
            return workDiscription;
        }

        public void setWorkDiscription(String workDiscription) {
            this.workDiscription = workDiscription;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
