package com.koipsool_new.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MedicalJobModel implements Serializable{

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<JobData> data;

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

    public List<JobData> getData() {
        return data;
    }

    public void setData(List<JobData> data) {
        this.data = data;
    }


    public  class  JobData implements Serializable {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("company_id")
        @Expose
        private String companyId;
        @SerializedName("skill_required_id")
        @Expose
        private String skillRequiredId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("work_type")
        @Expose
        private String workType;
        @SerializedName("job_type")
        @Expose
        private String jobType;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("start_from")
        @Expose
        private String startFrom;
        @SerializedName("salary")
        @Expose
        private String salary;
        @SerializedName("skills")
        @Expose
        private List<Skill> skills;
        @SerializedName("perks")
        @Expose
        private String perks;
        @SerializedName("price_from")
        @Expose
        private String priceFrom;
        @SerializedName("price_to")
        @Expose
        private String priceTo;
        @SerializedName("about")
        @Expose
        private String about;
        @SerializedName("day_to_day_work")
        @Expose
        private String dayToDayWork;
        @SerializedName("questions")
        @Expose
        private String questions;
        @SerializedName("number_of_opening")
        @Expose
        private String numberOfOpening;
        @SerializedName("job_responsibilities")
        @Expose
        private String jobResponsibilities;
        @SerializedName("ctc_breakup")
        @Expose
        private String ctcBreakup;
        @SerializedName("fixed_pay")
        @Expose
        private String fixedPay;
        @SerializedName("variable_pay")
        @Expose
        private String variablePay;
        @SerializedName("other_incentives")
        @Expose
        private String otherIncentives;
        @SerializedName("provision")
        @Expose
        private String provision;
        @SerializedName("provision_mounth")
        @Expose
        private String provisionMounth;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("website")
        @Expose
        private String website;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("company_name")
        @Expose
        private String companyName;
        @SerializedName("company_address")
        @Expose
        private String companyAddress;

        @SerializedName("questions_1")
        @Expose
        private String questions_1;

        @SerializedName("questions_2")
        @Expose
        private String questions_2;

        @SerializedName("questions_3")
        @Expose
        private String questions_3;

        @SerializedName("questions_4")
        @Expose
        private String questions_4;

        @SerializedName("questions_5")
        @Expose
        private String questions_5;

        @SerializedName("count")
        @Expose
        private String count;
        @SerializedName("i_saved")
        @Expose
        private String iSaved;
        @SerializedName("i_applied")
        @Expose
        private String iApplied;

        @SerializedName("company_image")
        @Expose
        private String company_image;

        public String getCompany_image() {
            return company_image;
        }

        public void setCompany_image(String company_image) {
            this.company_image = company_image;
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

        public String getQuestions_1() {
            return questions_1;
        }

        public void setQuestions_1(String questions_1) {
            this.questions_1 = questions_1;
        }

        public String getQuestions_2() {
            return questions_2;
        }

        public void setQuestions_2(String questions_2) {
            this.questions_2 = questions_2;
        }

        public String getQuestions_3() {
            return questions_3;
        }

        public void setQuestions_3(String questions_3) {
            this.questions_3 = questions_3;
        }

        public String getQuestions_4() {
            return questions_4;
        }

        public void setQuestions_4(String questions_4) {
            this.questions_4 = questions_4;
        }

        public String getQuestions_5() {
            return questions_5;
        }

        public void setQuestions_5(String questions_5) {
            this.questions_5 = questions_5;
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

        public String getSkillRequiredId() {
            return skillRequiredId;
        }

        public void setSkillRequiredId(String skillRequiredId) {
            this.skillRequiredId = skillRequiredId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWorkType() {
            return workType;
        }

        public void setWorkType(String workType) {
            this.workType = workType;
        }

        public String getJobType() {
            return jobType;
        }

        public void setJobType(String jobType) {
            this.jobType = jobType;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getStartFrom() {
            return startFrom;
        }

        public void setStartFrom(String startFrom) {
            this.startFrom = startFrom;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public List<Skill> getSkills() {
            return skills;
        }

        public void setSkills(List<Skill> skills) {
            this.skills = skills;
        }

        public String getPerks() {
            return perks;
        }

        public void setPerks(String perks) {
            this.perks = perks;
        }

        public String getPriceFrom() {
            return priceFrom;
        }

        public void setPriceFrom(String priceFrom) {
            this.priceFrom = priceFrom;
        }

        public String getPriceTo() {
            return priceTo;
        }

        public void setPriceTo(String priceTo) {
            this.priceTo = priceTo;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getDayToDayWork() {
            return dayToDayWork;
        }

        public void setDayToDayWork(String dayToDayWork) {
            this.dayToDayWork = dayToDayWork;
        }

        public String getQuestions() {
            return questions;
        }

        public void setQuestions(String questions) {
            this.questions = questions;
        }

        public String getNumberOfOpening() {
            return numberOfOpening;
        }

        public void setNumberOfOpening(String numberOfOpening) {
            this.numberOfOpening = numberOfOpening;
        }

        public String getJobResponsibilities() {
            return jobResponsibilities;
        }

        public void setJobResponsibilities(String jobResponsibilities) {
            this.jobResponsibilities = jobResponsibilities;
        }

        public String getCtcBreakup() {
            return ctcBreakup;
        }

        public void setCtcBreakup(String ctcBreakup) {
            this.ctcBreakup = ctcBreakup;
        }

        public String getFixedPay() {
            return fixedPay;
        }

        public void setFixedPay(String fixedPay) {
            this.fixedPay = fixedPay;
        }

        public String getVariablePay() {
            return variablePay;
        }

        public void setVariablePay(String variablePay) {
            this.variablePay = variablePay;
        }

        public String getOtherIncentives() {
            return otherIncentives;
        }

        public void setOtherIncentives(String otherIncentives) {
            this.otherIncentives = otherIncentives;
        }

        public String getProvision() {
            return provision;
        }

        public void setProvision(String provision) {
            this.provision = provision;
        }

        public String getProvisionMounth() {
            return provisionMounth;
        }

        public void setProvisionMounth(String provisionMounth) {
            this.provisionMounth = provisionMounth;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyAddress() {
            return companyAddress;
        }

        public void setCompanyAddress(String companyAddress) {
            this.companyAddress = companyAddress;
        }

        public class Skill implements Serializable{

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("skill_name")
            @Expose
            private String skillName;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("created_at")
            @Expose
            private String createdAt;

            public String getId() {
                return id;
            }
            public void setId(String id) {
                this.id = id;
            }

            public String getSkillName() {
                return skillName;
            }

            public void setSkillName(String skillName) {
                this.skillName = skillName;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }
        }
    }


}
