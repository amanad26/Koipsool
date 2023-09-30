package com.koipsool_new.kapsoolModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MyJobsModel implements Serializable {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private MyJobInternshipData data;

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

    public MyJobInternshipData getData() {
        return data;
    }

    public void setData(MyJobInternshipData data) {
        this.data = data;
    }

    public  class  MyJobInternshipData implements Serializable{

        @SerializedName("post_internship")
        @Expose
        private List<PostInternship> postInternship;
        @SerializedName("post_job")
        @Expose
        private List<PostJob> postJob;

        public List<PostInternship> getPostInternship() {
            return postInternship;
        }

        public void setPostInternship(List<PostInternship> postInternship) {
            this.postInternship = postInternship;
        }

        public List<PostJob> getPostJob() {
            return postJob;
        }

        public void setPostJob(List<PostJob> postJob) {
            this.postJob = postJob;
        }


        public class PostInternship implements Serializable{

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("company_id")
            @Expose
            private String companyId;
            @SerializedName("internship_title")
            @Expose
            private String internshipTitle;
            @SerializedName("skills_required")
            @Expose
            private List<SkillsRequired> skillsRequired;
            @SerializedName("internship_types")
            @Expose
            private String internshipTypes;
            @SerializedName("city")
            @Expose
            private String city;
            @SerializedName("part_time_allowed")
            @Expose
            private String partTimeAllowed;
            @SerializedName("number_of_opening")
            @Expose
            private String numberOfOpening;
            @SerializedName("internship_start_date")
            @Expose
            private String internshipStartDate;
            @SerializedName("internship_duration")
            @Expose
            private String internshipDuration;
            @SerializedName("internship_responsibilities")
            @Expose
            private String internshipResponsibilities;
            @SerializedName("pop")
            @Expose
            private String pop;
            @SerializedName("pop_month")
            @Expose
            private String popMonth;
            @SerializedName("websites")
            @Expose
            private String websites;
            @SerializedName("minimum_assured")
            @Expose
            private String minimumAssured;
            @SerializedName("maximum_assured")
            @Expose
            private String maximumAssured;
            @SerializedName("scale")
            @Expose
            private String scale;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("work_type")
            @Expose
            private String workType;
            @SerializedName("location")
            @Expose
            private String location;
            @SerializedName("duration")
            @Expose
            private String duration;
            @SerializedName("start_from")
            @Expose
            private String startFrom;
            @SerializedName("stipend")
            @Expose
            private String stipend;
            @SerializedName("skills")
            @Expose
            private String skills;
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
            @SerializedName("allow_women")
            @Expose
            private String allowWomen;
            @SerializedName("created_date")
            @Expose
            private String createdDate;
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

            public String getInternshipTitle() {
                return internshipTitle;
            }

            public void setInternshipTitle(String internshipTitle) {
                this.internshipTitle = internshipTitle;
            }

            public List<SkillsRequired> getSkillsRequired() {
                return skillsRequired;
            }

            public void setSkillsRequired(List<SkillsRequired> skillsRequired) {
                this.skillsRequired = skillsRequired;
            }

            public String getInternshipTypes() {
                return internshipTypes;
            }

            public void setInternshipTypes(String internshipTypes) {
                this.internshipTypes = internshipTypes;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getPartTimeAllowed() {
                return partTimeAllowed;
            }

            public void setPartTimeAllowed(String partTimeAllowed) {
                this.partTimeAllowed = partTimeAllowed;
            }

            public String getNumberOfOpening() {
                return numberOfOpening;
            }

            public void setNumberOfOpening(String numberOfOpening) {
                this.numberOfOpening = numberOfOpening;
            }

            public String getInternshipStartDate() {
                return internshipStartDate;
            }

            public void setInternshipStartDate(String internshipStartDate) {
                this.internshipStartDate = internshipStartDate;
            }

            public String getInternshipDuration() {
                return internshipDuration;
            }

            public void setInternshipDuration(String internshipDuration) {
                this.internshipDuration = internshipDuration;
            }

            public String getInternshipResponsibilities() {
                return internshipResponsibilities;
            }

            public void setInternshipResponsibilities(String internshipResponsibilities) {
                this.internshipResponsibilities = internshipResponsibilities;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPopMonth() {
                return popMonth;
            }

            public void setPopMonth(String popMonth) {
                this.popMonth = popMonth;
            }

            public String getWebsites() {
                return websites;
            }

            public void setWebsites(String websites) {
                this.websites = websites;
            }

            public String getMinimumAssured() {
                return minimumAssured;
            }

            public void setMinimumAssured(String minimumAssured) {
                this.minimumAssured = minimumAssured;
            }

            public String getMaximumAssured() {
                return maximumAssured;
            }

            public void setMaximumAssured(String maximumAssured) {
                this.maximumAssured = maximumAssured;
            }

            public String getScale() {
                return scale;
            }

            public void setScale(String scale) {
                this.scale = scale;
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

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getStartFrom() {
                return startFrom;
            }

            public void setStartFrom(String startFrom) {
                this.startFrom = startFrom;
            }

            public String getStipend() {
                return stipend;
            }

            public void setStipend(String stipend) {
                this.stipend = stipend;
            }

            public String getSkills() {
                return skills;
            }

            public void setSkills(String skills) {
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

            public String getAllowWomen() {
                return allowWomen;
            }

            public void setAllowWomen(String allowWomen) {
                this.allowWomen = allowWomen;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
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

            public class SkillsRequired implements  Serializable {

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

        public class PostJob implements Serializable{

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

            @SerializedName("experience")
            @Expose
            private String experience;


            public String getExperience() {
                return experience;
            }

            public void setExperience(String experience) {
                this.experience = experience;
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

            public class Skill implements  Serializable {

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


}
