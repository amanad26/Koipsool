package com.koipsool_new.kapsoolModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EducationModel implements Serializable{
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<EducationData> data;

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

    public List<EducationData> getData() {
        return data;
    }

    public void setData(List<EducationData> data) {
        this.data = data;
    }

    public  class  EducationData implements Serializable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("medical_id")
        @Expose
        private String medicalId;
        @SerializedName("education")
        @Expose
        private String education;
        @SerializedName("school_collage")
        @Expose
        private String schoolCollage;
        @SerializedName("start_date")
        @Expose
        private String startDate;
        @SerializedName("end_date")
        @Expose
        private String endDate;
        @SerializedName("degree")
        @Expose
        private String degree;
        @SerializedName("stream")
        @Expose
        private String stream;
        @SerializedName("performance_scale")
        @Expose
        private String performanceScale;
        @SerializedName("performance")
        @Expose
        private String performance;
        @SerializedName("board")
        @Expose
        private String board;
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

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getSchoolCollage() {
            return schoolCollage;
        }

        public void setSchoolCollage(String schoolCollage) {
            this.schoolCollage = schoolCollage;
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

        public String getDegree() {
            return degree;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }

        public String getStream() {
            return stream;
        }

        public void setStream(String stream) {
            this.stream = stream;
        }

        public String getPerformanceScale() {
            return performanceScale;
        }

        public void setPerformanceScale(String performanceScale) {
            this.performanceScale = performanceScale;
        }

        public String getPerformance() {
            return performance;
        }

        public void setPerformance(String performance) {
            this.performance = performance;
        }

        public String getBoard() {
            return board;
        }

        public void setBoard(String board) {
            this.board = board;
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
