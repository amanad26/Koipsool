package com.koipsool_new.kapsoolModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SavedJobModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("data")
    @Expose
    private List<SavedData> data;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<SavedData> getData() {
        return data;
    }

    public void setData(List<SavedData> data) {
        this.data = data;
    }

    public  class  SavedData{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("medical_id")
        @Expose
        private String medicalId;
        @SerializedName("job_internship_id")
        @Expose
        private String jobInternshipId;
        @SerializedName("company_id")
        @Expose
        private String companyId;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("save")
        @Expose
        private String save;
        @SerializedName("save_date")
        @Expose
        private String saveDate;
        @SerializedName("company_name")
        @Expose
        private String companyName;
        @SerializedName("company_type")
        @Expose
        private String companyType;
        @SerializedName("company_image")
        @Expose
        private String companyImage;
        @SerializedName("post_job_name")
        @Expose
        private String postJobName;
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
        @SerializedName("post_internship_name")
        @Expose
        private String post_internship_name;
        @SerializedName("i_applied")
        @Expose
        private String iApplied;
        @SerializedName("fixed_pay")
        @Expose
        private String fixed_pay;
        @SerializedName("price_from")
        @Expose
        private String price_from;
        @SerializedName("internship_duration")
        @Expose
        private String internship_duration;

        public String getFixed_pay() {
            return fixed_pay;
        }

        public void setFixed_pay(String fixed_pay) {
            this.fixed_pay = fixed_pay;
        }

        public String getPrice_from() {
            return price_from;
        }

        public void setPrice_from(String price_from) {
            this.price_from = price_from;
        }

        public String getInternship_duration() {
            return internship_duration;
        }

        public void setInternship_duration(String internship_duration) {
            this.internship_duration = internship_duration;
        }

        public String getPost_internship_name() {
            return post_internship_name;
        }

        public void setPost_internship_name(String post_internship_name) {
            this.post_internship_name = post_internship_name;
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

        public String getJobInternshipId() {
            return jobInternshipId;
        }

        public void setJobInternshipId(String jobInternshipId) {
            this.jobInternshipId = jobInternshipId;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSave() {
            return save;
        }

        public void setSave(String save) {
            this.save = save;
        }

        public String getSaveDate() {
            return saveDate;
        }

        public void setSaveDate(String saveDate) {
            this.saveDate = saveDate;
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

        public String getCompanyImage() {
            return companyImage;
        }

        public void setCompanyImage(String companyImage) {
            this.companyImage = companyImage;
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

    }

}
