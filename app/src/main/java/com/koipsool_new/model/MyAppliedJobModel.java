package com.koipsool_new.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MyAppliedJobModel implements Serializable{

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<MyAppliedData> data;

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

    public List<MyAppliedData> getData() {
        return data;
    }

    public void setData(List<MyAppliedData> data) {
        this.data = data;
    }


    public  class  MyAppliedData implements Serializable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("medical_id")
        @Expose
        private String medicalId;
        @SerializedName("company_id")
        @Expose
        private String companyId;
        @SerializedName("job_internship_id")
        @Expose
        private String jobInternshipId;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("apply_status")
        @Expose
        private String applyStatus;
        @SerializedName("apply_date")
        @Expose
        private String applyDate;
        @SerializedName("answer_1")
        @Expose
        private String answer1;
        @SerializedName("answer_2")
        @Expose
        private String answer2;
        @SerializedName("answer_3")
        @Expose
        private String answer3;
        @SerializedName("answer_4")
        @Expose
        private String answer4;
        @SerializedName("answer_5")
        @Expose
        private String answer5;
        @SerializedName("company_name")
        @Expose
        private String companyName;
        @SerializedName("company_type")
        @Expose
        private String companyType;
        @SerializedName("post_job_name")
        @Expose
        private String postJobName;

        @SerializedName("medical_name")
        @Expose
        private String medicalName;
        @SerializedName("medical_type")
        @Expose
        private String medicalType;

        @SerializedName("questions_1")
        @Expose
        private String questions1;
        @SerializedName("questions_2")
        @Expose
        private String questions2;
        @SerializedName("questions_3")
        @Expose
        private String questions3;
        @SerializedName("questions_4")
        @Expose
        private String questions4;
        @SerializedName("questions_5")
        @Expose
        private String questions5;
        @SerializedName("count")
        @Expose
        private String count;
        @SerializedName("i_saved")
        @Expose
        private String iSaved;
        @SerializedName("i_applied")
        @Expose
        private String iApplied;
        @SerializedName("post_internship_name")
        @Expose
        private String postInternshipName;

        public String getMedicalName() {
            return medicalName;
        }

        public void setMedicalName(String medicalName) {
            this.medicalName = medicalName;
        }

        public String getMedicalType() {
            return medicalType;
        }

        public void setMedicalType(String medicalType) {
            this.medicalType = medicalType;
        }

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

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getJobInternshipId() {
            return jobInternshipId;
        }

        public void setJobInternshipId(String jobInternshipId) {
            this.jobInternshipId = jobInternshipId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(String applyStatus) {
            this.applyStatus = applyStatus;
        }

        public String getApplyDate() {
            return applyDate;
        }

        public void setApplyDate(String applyDate) {
            this.applyDate = applyDate;
        }

        public String getAnswer1() {
            return answer1;
        }

        public void setAnswer1(String answer1) {
            this.answer1 = answer1;
        }

        public String getAnswer2() {
            return answer2;
        }

        public void setAnswer2(String answer2) {
            this.answer2 = answer2;
        }

        public String getAnswer3() {
            return answer3;
        }

        public void setAnswer3(String answer3) {
            this.answer3 = answer3;
        }

        public String getAnswer4() {
            return answer4;
        }

        public void setAnswer4(String answer4) {
            this.answer4 = answer4;
        }

        public String getAnswer5() {
            return answer5;
        }

        public void setAnswer5(String answer5) {
            this.answer5 = answer5;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyType() {
            return companyType;
        }

        public void setCompanyType(String companyType) {
            this.companyType = companyType;
        }

        public String getPostJobName() {
            return postJobName;
        }

        public void setPostJobName(String postJobName) {
            this.postJobName = postJobName;
        }

        public String getQuestions1() {
            return questions1;
        }

        public void setQuestions1(String questions1) {
            this.questions1 = questions1;
        }

        public String getQuestions2() {
            return questions2;
        }

        public void setQuestions2(String questions2) {
            this.questions2 = questions2;
        }

        public String getQuestions3() {
            return questions3;
        }

        public void setQuestions3(String questions3) {
            this.questions3 = questions3;
        }

        public String getQuestions4() {
            return questions4;
        }

        public void setQuestions4(String questions4) {
            this.questions4 = questions4;
        }

        public String getQuestions5() {
            return questions5;
        }

        public void setQuestions5(String questions5) {
            this.questions5 = questions5;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getiSaved() {
            return iSaved;
        }

        public void setiSaved(String iSaved) {
            this.iSaved = iSaved;
        }

        public String getiApplied() {
            return iApplied;
        }

        public void setiApplied(String iApplied) {
            this.iApplied = iApplied;
        }

        public String getPostInternshipName() {
            return postInternshipName;
        }

        public void setPostInternshipName(String postInternshipName) {
            this.postInternshipName = postInternshipName;
        }

    }

}
