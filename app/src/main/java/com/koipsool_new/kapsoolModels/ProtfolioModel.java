package com.koipsool_new.kapsoolModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProtfolioModel {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<ProtfolioData> data;

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

    public List<ProtfolioData> getData() {
        return data;
    }

    public void setData(List<ProtfolioData> data) {
        this.data = data;
    }


    public class  ProtfolioData{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("medical_id")
        @Expose
        private String medicalId;
        @SerializedName("blog_link")
        @Expose
        private String blogLink;
        @SerializedName("github_profile")
        @Expose
        private String githubProfile;
        @SerializedName("play_store_link")
        @Expose
        private String playStoreLink;
        @SerializedName("portfolio_link")
        @Expose
        private String portfolioLink;
        @SerializedName("other_work")
        @Expose
        private String otherWork;
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

        public String getBlogLink() {
            return blogLink;
        }

        public void setBlogLink(String blogLink) {
            this.blogLink = blogLink;
        }

        public String getGithubProfile() {
            return githubProfile;
        }

        public void setGithubProfile(String githubProfile) {
            this.githubProfile = githubProfile;
        }

        public String getPlayStoreLink() {
            return playStoreLink;
        }

        public void setPlayStoreLink(String playStoreLink) {
            this.playStoreLink = playStoreLink;
        }

        public String getPortfolioLink() {
            return portfolioLink;
        }

        public void setPortfolioLink(String portfolioLink) {
            this.portfolioLink = portfolioLink;
        }

        public String getOtherWork() {
            return otherWork;
        }

        public void setOtherWork(String otherWork) {
            this.otherWork = otherWork;
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
