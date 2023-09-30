package com.koipsool_new.RetofitSetUp;

import static com.koipsool_new.RetofitSetUp.BaseUrls.GET_ABOUT_US;
import static com.koipsool_new.RetofitSetUp.BaseUrls.add_company_billing_address;
import static com.koipsool_new.RetofitSetUp.BaseUrls.add_company_members;
import static com.koipsool_new.RetofitSetUp.BaseUrls.add_company_products;
import static com.koipsool_new.RetofitSetUp.BaseUrls.add_education;
import static com.koipsool_new.RetofitSetUp.BaseUrls.add_help_support;
import static com.koipsool_new.RetofitSetUp.BaseUrls.add_job_internship;
import static com.koipsool_new.RetofitSetUp.BaseUrls.add_order;
import static com.koipsool_new.RetofitSetUp.BaseUrls.add_order_item;
import static com.koipsool_new.RetofitSetUp.BaseUrls.add_portfolio;
import static com.koipsool_new.RetofitSetUp.BaseUrls.add_post_internship;
import static com.koipsool_new.RetofitSetUp.BaseUrls.add_post_job;
import static com.koipsool_new.RetofitSetUp.BaseUrls.add_projects_details;
import static com.koipsool_new.RetofitSetUp.BaseUrls.add_skills_company;
import static com.koipsool_new.RetofitSetUp.BaseUrls.applicants_apply_status;
import static com.koipsool_new.RetofitSetUp.BaseUrls.apply_job_internship;
import static com.koipsool_new.RetofitSetUp.BaseUrls.chemist_company_signup;
import static com.koipsool_new.RetofitSetUp.BaseUrls.chemist_get_profile;
import static com.koipsool_new.RetofitSetUp.BaseUrls.chemist_update_profile;
import static com.koipsool_new.RetofitSetUp.BaseUrls.company_login;
import static com.koipsool_new.RetofitSetUp.BaseUrls.company_logout;
import static com.koipsool_new.RetofitSetUp.BaseUrls.company_verify_otp;
import static com.koipsool_new.RetofitSetUp.BaseUrls.create_coupons;
import static com.koipsool_new.RetofitSetUp.BaseUrls.delete_company_members;
import static com.koipsool_new.RetofitSetUp.BaseUrls.delete_company_products;
import static com.koipsool_new.RetofitSetUp.BaseUrls.delete_company_profile;
import static com.koipsool_new.RetofitSetUp.BaseUrls.delete_education;
import static com.koipsool_new.RetofitSetUp.BaseUrls.delete_job_internship;
import static com.koipsool_new.RetofitSetUp.BaseUrls.delete_order;
import static com.koipsool_new.RetofitSetUp.BaseUrls.delete_order_item;
import static com.koipsool_new.RetofitSetUp.BaseUrls.delete_post_internship;
import static com.koipsool_new.RetofitSetUp.BaseUrls.delete_post_job;
import static com.koipsool_new.RetofitSetUp.BaseUrls.delete_projects_details;
import static com.koipsool_new.RetofitSetUp.BaseUrls.delete_skills_company;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_all_internship;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_all_jobs;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_applicants_list;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_category_kapsul_list;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_cities;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_company_billing_address;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_company_members;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_company_products;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_company_profile;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_company_user_list;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_company_user_profile_medical;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_coupons;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_education;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_items;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_items_order;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_job_internship;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_my_apply_job_internship_company;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_my_apply_job_internship_medical;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_my_skills;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_order;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_pharma_company;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_portfolio;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_post_internship;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_privacy_policy;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_projects_details;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_saved_job_internship_list;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_skills_company;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_states;
import static com.koipsool_new.RetofitSetUp.BaseUrls.get_terms_condition;
import static com.koipsool_new.RetofitSetUp.BaseUrls.google_login_company;
import static com.koipsool_new.RetofitSetUp.BaseUrls.medical_company_signup;
import static com.koipsool_new.RetofitSetUp.BaseUrls.pharma_company_signup;
import static com.koipsool_new.RetofitSetUp.BaseUrls.resend_otp_company_user;
import static com.koipsool_new.RetofitSetUp.BaseUrls.save_job_internship;
import static com.koipsool_new.RetofitSetUp.BaseUrls.update_company_billing_address;
import static com.koipsool_new.RetofitSetUp.BaseUrls.update_company_members;
import static com.koipsool_new.RetofitSetUp.BaseUrls.update_company_products;
import static com.koipsool_new.RetofitSetUp.BaseUrls.update_company_profile;
import static com.koipsool_new.RetofitSetUp.BaseUrls.update_coupons;
import static com.koipsool_new.RetofitSetUp.BaseUrls.update_education;
import static com.koipsool_new.RetofitSetUp.BaseUrls.update_job_internship;
import static com.koipsool_new.RetofitSetUp.BaseUrls.update_medical_profile;
import static com.koipsool_new.RetofitSetUp.BaseUrls.update_order;
import static com.koipsool_new.RetofitSetUp.BaseUrls.update_projects_details;
import static com.koipsool_new.RetofitSetUp.BaseUrls.update_skills_company;

import com.koipsool_new.kapsoolModels.CategoriesModel;
import com.koipsool_new.kapsoolModels.ChemistProfileModel;
import com.koipsool_new.kapsoolModels.CityModel;
import com.koipsool_new.kapsoolModels.CompanyProfileModel;
import com.koipsool_new.kapsoolModels.CouponsModel;
import com.koipsool_new.kapsoolModels.CreateBillProductModel;
import com.koipsool_new.kapsoolModels.EducationModel;
import com.koipsool_new.kapsoolModels.GetBillingAddressModel;
import com.koipsool_new.kapsoolModels.LoginModel;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.MyJobsModel;
import com.koipsool_new.kapsoolModels.MyResumeProjectsModel;
import com.koipsool_new.kapsoolModels.MySkillsModel;
import com.koipsool_new.kapsoolModels.OtpVerifyModel;
import com.koipsool_new.kapsoolModels.PharmaCompanyModel;
import com.koipsool_new.kapsoolModels.ProductModel;
import com.koipsool_new.kapsoolModels.ProtfolioModel;
import com.koipsool_new.kapsoolModels.ResumeJobModel;
import com.koipsool_new.kapsoolModels.SavedJobModel;
import com.koipsool_new.kapsoolModels.SignupModel;
import com.koipsool_new.kapsoolModels.SkillsModel;
import com.koipsool_new.kapsoolModels.StateModel;
import com.koipsool_new.kapsoolModels.TeamMemberModel;
import com.koipsool_new.model.AboutModel;
import com.koipsool_new.model.AddOrderModel;
import com.koipsool_new.model.ChemistSignupModel;
import com.koipsool_new.model.MedicalInternshipModel;
import com.koipsool_new.model.MedicalJobModel;
import com.koipsool_new.model.MedicalProfileModel;
import com.koipsool_new.model.MyAppliedJobModel;
import com.koipsool_new.model.MyOrderModel;
import com.koipsool_new.model.OrderDetailsModel;
import com.koipsool_new.model.PrivacyModel;
import com.koipsool_new.model.TermConditionModel;
import com.koipsool_new.model.UsersListModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterfaceKapsool {


    @GET(GET_ABOUT_US)
    Call<AboutModel> getAbout();

    @GET(get_privacy_policy)
    Call<PrivacyModel> get_privacy_policy();


    @GET(get_terms_condition)
    Call<TermConditionModel> term_conditiondata();


    @GET(get_states)
    Call<StateModel> getStates();


    @GET(get_pharma_company)
    Call<PharmaCompanyModel> getPharmaCompany();


    @GET(get_category_kapsul_list)
    Call<CategoriesModel> getCategories();


    @FormUrlEncoded
    @POST(google_login_company)
    Call<LoginModel> googleLogin(
            @Field("email") String email,
            @Field("name") String name,
            @Field("fcm_id") String fcm_id,
            @Field("google_id") String google_id,
            @Field("type") String type);


    @FormUrlEncoded
    @POST(get_cities)
    Call<CityModel> getCities(
            @Field("state_id") String state_id
    );

    @FormUrlEncoded
    @POST(add_help_support)
    Call<LogoutModel> helpAndSupport(
            @Field("company_user_id") String company_user_id,
            @Field("title") String title,
            @Field("query") String query,
            @Field("user_type") String user_type
    );


    ////////////////////////////////////////////////////
    ////////////////  Pharma Company Apis  /////////////

    @FormUrlEncoded
    @POST(pharma_company_signup)
    Call<SignupModel> pharmaSignUp(
            @Field("name") String name,
            @Field("company_name") String company_name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("mobile") String mobile,
            @Field("fcm_id") String fcm_id
    );

    @FormUrlEncoded
    @POST(company_verify_otp)
    Call<OtpVerifyModel> pharmaVerifyOtp(
            @Field("company_id") String company_id,
            @Field("otp") String otp
    );


    @FormUrlEncoded
    @POST(resend_otp_company_user)
    Call<OtpVerifyModel> resendOtp(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST(company_login)
    Call<LoginModel> pharmaLogin(
            @Field("mobile") String mobile,
            @Field("fcm_id") String fcm_id
    );

    @FormUrlEncoded
    @POST(company_logout)
    Call<LogoutModel> pharmaLogout(
            @Field("company_id") String company_id
    );

    @FormUrlEncoded
    @POST(delete_company_profile)
    Call<LogoutModel> deletePharmaAccount(
            @Field("company_id") String company_id
    );


    @FormUrlEncoded
    @POST(get_company_profile)
    Call<CompanyProfileModel> getCompanyProfile(
            @Field("company_id") String company_id
    );


    @FormUrlEncoded
    @POST(get_company_billing_address)
    Call<GetBillingAddressModel> getPharmacyBillingAddress(
            @Field("company_id") String company_id
    );

    @FormUrlEncoded
    @POST(add_company_billing_address)
    Call<LogoutModel> addPharmacyBillingAddress(
            @Field("company_id") String company_id,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("address") String address,
            @Field("state_id") String state_id,
            @Field("city_id") String city_id,
            @Field("gst_no") String gst_no,
            @Field("drug_license_no") String drug_license_no
    );


    @FormUrlEncoded
    @POST(update_company_profile)
    Call<LogoutModel> updatePharmaCompanyProfile(
            @Field("company_id") String company_id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("company_name") String company_name,
            @Field("mobile") String mobile,
            @Field("address") String address,
            @Field("pin_code") String pin_code,
            @Field("buyerc_ontact_number") String buyerc_ontact_number,
            @Field("gst_no") String gst_no,
            @Field("ceiling_in") String ceiling_in,
            @Field("state_id") String state_id,
            @Field("city_id") String city_id

    );


    @FormUrlEncoded
    @POST(update_company_billing_address)
    Call<LogoutModel> updatePharmaBillingAddress(
            @Field("bill_id") String bill_id,
            @Field("mobile") String mobile,
            @Field("address") String address,
            @Field("gst_no") String gst_no,
            @Field("state_id") String state_id,
            @Field("city_id") String city_id,
            @Field("drug_license_no") String drug_license_no
    );

    @FormUrlEncoded
    @POST(add_company_members)
    Call<LogoutModel> addPharmacyMembers(
            @Field("company_id") String company_id,
            @Field("name") String name,
            @Field("department") String department,
            @Field("location") String location,
            @Field("work") String work
    );


    @FormUrlEncoded
    @POST(get_company_members)
    Call<TeamMemberModel> getMyTeamMember(
            @Field("company_id") String company_id
    );


    @FormUrlEncoded
    @POST(update_company_members)
    Call<LogoutModel> updatePharmacyMembers(
            @Field("member_id") String company_id,
            @Field("name") String name,
            @Field("department") String department,
            @Field("location") String location,
            @Field("work") String work
    );

    @FormUrlEncoded
    @POST(create_coupons)
    Call<LogoutModel> createCoupons(
            @Field("company_id") String company_id,
            @Field("product") String product,
            @Field("corporation") String corporation,
            @Field("offer") String offer,
            @Field("discount") String discount,
            @Field("percentage") String percentage,
            @Field("discount_price") String discount_price,
            @Field("coupon_validity") String coupon_validity,
            @Field("mrp") String mrp,
            @Field("available") String available
    );

    @FormUrlEncoded
    @POST(delete_company_members)
    Call<LogoutModel> deletePharmacyMembers(
            @Field("member_id") String company_id
    );

    @FormUrlEncoded
    @POST(delete_post_job)
    Call<LogoutModel> deletePostJob(
            @Field("post_id") String post_id
    );

    @FormUrlEncoded
    @POST(delete_post_internship)
    Call<LogoutModel> deletePostInternship(
            @Field("post_id") String post_id
    );

    @FormUrlEncoded
    @POST(add_company_products)
    Call<LogoutModel> addCompanyProduct(
            @Field("company_id") String company_id,
            @Field("name") String name,
            @Field("desciption") String desciption,
            @Field("batch_no") String batch_no,
            @Field("expiry") String expiry,
            @Field("mrp") String mrp,
            @Field("tax_class") String tax_class,
            @Field("hsn_code") String hsn_code,
            @Field("category_kapsul_id") String category_kapsul_id
    );

    @FormUrlEncoded
    @POST(update_company_products)
    Call<LogoutModel> updateProduct(
            @Field("product_id") String product_id,
            @Field("name") String name,
            @Field("desciption") String desciption,
            @Field("batch_no") String batch_no,
            @Field("expiry") String expiry,
            @Field("mrp") String mrp,
            @Field("tax_class") String tax_class
    );


    @FormUrlEncoded
    @POST(get_company_products)
    Call<ProductModel> getMyProduct(
            @Field("company_id") String company_id
    );


    @FormUrlEncoded
    @POST(delete_company_products)
    Call<LogoutModel> deleteProduct(
            @Field("product_id") String product_id
    );


    @FormUrlEncoded
    @POST(update_coupons)
    Call<LogoutModel> updateCouponDetails(
            @Field("coupon_id") String coupon_id,
            @Field("product") String product,
            @Field("corporation") String corporation,
            @Field("offer") String offer,
            @Field("discount") String discount,
            @Field("percentage") String percentage,
            @Field("discount_price") String discount_price,
            @Field("coupon_validity") String coupon_validity,
            @Field("available") String available
    );

    @FormUrlEncoded
    @POST(add_post_job)
    Call<LogoutModel> addPharmacyPostJob(
            @Field("company_id") String company_id,
            @Field("job_type") String job_type,
            @Field("number_of_opening") String number_of_opening,
            @Field("job_responsibilities") String job_responsibilities,
            @Field("skills") String skills,
            @Field("ctc_breakup") String ctc_breakup,
            @Field("fixed_pay") String fixed_pay,
            @Field("variable_pay") String variable_pay,
            @Field("other_incentives") String other_incentives,
            @Field("provision") String provision,
            @Field("provision_mounth") String provision_mounth,
            @Field("name") String name,
            @Field("perks") String perks,
            @Field("about") String about,
            @Field("questions") String questions,
            @Field("salary") String salary,
            @Field("price_from") String price_from,
            @Field("price_to") String price_to,
            @Field("website") String website,
            @Field("questions_1") String questions_1,
            @Field("questions_2") String questions_2,
            @Field("questions_3") String questions_3,
            @Field("questions_4") String questions_4,
            @Field("questions_5") String questions_5,
            @Field("experience") String experience,
            @Field("expiry_date") String expiry_date

    );

    @FormUrlEncoded
    @POST(get_post_internship)
    Call<MyJobsModel> getMyJobPostInternships(
            @Field("company_id") String company_id
    );

    @FormUrlEncoded
    @POST(get_coupons)
    Call<CouponsModel> getMyCoupons(
            @Field("company_id") String company_id
    );

    @FormUrlEncoded
    @POST(add_post_internship)
    Call<LogoutModel> addInternshipPost(
            @Field("company_id") String company_id,
            @Field("internship_title") String internship_title,
            @Field("skills_required") String skills_required,
            @Field("internship_types") String internship_types,
            @Field("part_time_allowed") String part_time_allowed,
            @Field("city") String city,
            @Field("number_of_opening") String number_of_opening,
            @Field("internship_start_date") String internship_start_date,
            @Field("internship_duration") String internship_duration,
            @Field("internship_responsibilities") String internship_responsibilities,
            @Field("pop") String pop,
            @Field("websites") String websites,
            @Field("minimum_assured") String minimum_assured,
            @Field("maximum_assured") String maximum_assured,
            @Field("scale") String scale,
            @Field("stipend") String stipend,
            @Field("perks") String perks,
            @Field("about") String about,
            @Field("questions") String questions,
            @Field("price_from") String price_from,
            @Field("price_to") String price_to,
            @Field("allow_women") String allow_women,
            @Field("pop_month") String pop_month,
            @Field("questions_1") String questions_1,
            @Field("questions_2") String questions_2,
            @Field("questions_3") String questions_3,
            @Field("questions_4") String questions_4,
            @Field("questions_5") String questions_5,
            @Field("experience") String experience
    );


    @GET(get_my_skills)
    Call<SkillsModel> getSkills();

    @FormUrlEncoded
    @POST(get_my_apply_job_internship_company)
    Call<MyAppliedJobModel> getMyApplicationsCompany(
            @Field("company_id") String company_id
    );

    @FormUrlEncoded
    @POST(get_applicants_list)
    Call<MyAppliedJobModel> getAllApplicants(
            @Field("company_id") String company_id,
            @Field("job_internship_id") String job_internship_id
    );


    @FormUrlEncoded
    @POST(applicants_apply_status)
    Call<LogoutModel> acceptRejectCandidate(
            @Field("apply_id") String apply_id,
            @Field("company_id") String company_id,
            @Field("apply_status") String apply_status
    );

    @FormUrlEncoded
    @POST(add_order)
    Call<AddOrderModel> addOrder(
            @Field("user_id") String user_id,
            @Field("client_id") String client_id,
            @Field("customer_name") String customer_name,
            @Field("customer_state") String customer_state,
            @Field("customer_city") String customer_city,
            @Field("customer_number") String customer_number,
            @Field("total_amount") String total_amount,
            @Field("gst") String gst,
            @Field("selling_in") String selling_in,
            @Field("customer_id") String customer_id,
            @Field("hsn") String hsn,
            @Field("drug_licance") String drug_licance
    );

    @FormUrlEncoded
    @POST(get_order)
    Call<MyOrderModel> getMyOrder(
            @Field("user_id") String user_id,
            @Field("client_id") String client_id
    );

    @FormUrlEncoded
    @POST(delete_order)
    Call<LoginModel> deleteMyOrder(
            @Field("order_id") String order_id
    );


    @FormUrlEncoded
    @POST(add_order_item)
    Call<AddOrderModel> addOrderItem(
            @Field("order_id") String order_id,
            @Field("user_id") String user_id,
            @Field("client_id") String client_id,
            @Field("product_name") String product_name,
            @Field("product_mrp") String product_mrp,
            @Field("product_selling_price") String product_selling_price,
            @Field("product_quantity") String product_quantity,
            @Field("product_free") String product_free,
            @Field("product_free_name") String product_free_name,
            @Field("product_free_id") String product_free_id
    );


    @FormUrlEncoded
    @POST(get_items)
    Call<CreateBillProductModel> getOrderItems(
            @Field("order_id") String order_id
    );


    @FormUrlEncoded
    @POST(delete_order_item)
    Call<CreateBillProductModel> deleteOrderItems(
            @Field("item_id") String item_id
    );


    @FormUrlEncoded
    @POST(get_items_order)
    Call<OrderDetailsModel> getOrderDetails(@Field("order_id") String order_id);


    @FormUrlEncoded
    @POST(update_order)
    Call<LogoutModel> updateOrderDetails(
            @Field("order_id") String order_id,
            @Field("customer_name") String customer_name,
            @Field("customer_state") String customer_state,
            @Field("customer_city") String customer_city,
            @Field("customer_number") String customer_number,
            @Field("total_amount") String total_amount,
            @Field("gst") String gst,
            @Field("selling_in") String selling_in,
            @Field("customer_id") String customer_id,
            @Field("hsn") String hsn,
            @Field("drug_licance") String drug_licance
    );


    ////////////////////////////////////////////////////
    ////////////////  Medical Section  Apis  /////////////


    @FormUrlEncoded
    @POST(medical_company_signup)
    Call<SignupModel> medicalSignUp(
            @Field("mobile") String mobile,
            @Field("fcm_id") String fcm_id
    );


    @FormUrlEncoded
    @POST(add_education)
    Call<LogoutModel> addEducation(
            @Field("medical_id") String medical_id,
            @Field("education") String education,
            @Field("school_collage") String school_collage,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("degree") String degree,
            @Field("stream") String stream,
            @Field("performance_scale") String performance_scale,
            @Field("performance") String performance,
            @Field("board") String board
    );


    @FormUrlEncoded
    @POST(update_education)
    Call<LogoutModel> UpdateEducation(
            @Field("education_id") String education_id,
            @Field("education") String education,
            @Field("school_collage") String school_collage,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("degree") String degree,
            @Field("stream") String stream,
            @Field("performance_scale") String performance_scale,
            @Field("performance") String performance,
            @Field("board") String board
    );


    @FormUrlEncoded
    @POST(delete_education)
    Call<LogoutModel> deleteEducation(@Field("education_id") String education_id);

    @FormUrlEncoded
    @POST(get_education)
    Call<EducationModel> getMyEducation(
            @Field("medical_id") String medical_id
    );

    @FormUrlEncoded
    @POST(add_job_internship)
    Call<LogoutModel> addJobInternship(
            @Field("medical_id") String medical_id,
            @Field("job_profile") String job_profile,
            @Field("organization") String organization,
            @Field("work_from_home") String work_from_home,
            @Field("location") String location,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("currently_working") String currently_working,
            @Field("work_discription") String work_discription,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST(update_job_internship)
    Call<LogoutModel> updateJobInternship(
            @Field("job_internship_id") String job_internship_id,
            @Field("job_profile") String job_profile,
            @Field("organization") String organization,
            @Field("work_from_home") String work_from_home,
            @Field("location") String location,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("currently_working") String currently_working,
            @Field("work_discription") String work_discription,
            @Field("type") String type
    );


    @FormUrlEncoded
    @POST(get_job_internship)
    Call<ResumeJobModel> getResumeJobInternship(
            @Field("medical_id") String medical_id
    );


    @FormUrlEncoded
    @POST(delete_job_internship)
    Call<LogoutModel> deleteResumeJob(
            @Field("job_internship_id") String job_internship_id
    );

    @FormUrlEncoded
    @POST(add_skills_company)
    Call<LogoutModel> addMySkill(
            @Field("medical_id") String medical_id,
            @Field("skill_id") String skill_id,
            @Field("skill_name") String skill_name,
            @Field("skill_rate") String skill_rate
    );


    @FormUrlEncoded
    @POST(update_skills_company)
    Call<LogoutModel> updateMySkill(
            @Field("skills_company_id") String skills_company_id,
            @Field("medical_id") String medical_id,
            @Field("skill_id") String skill_id,
            @Field("skill_name") String skill_name,
            @Field("skill_rate") String skill_rate
    );


    @FormUrlEncoded
    @POST(get_skills_company)
    Call<MySkillsModel> getMySkill(
            @Field("medical_id") String medical_id
    );


    @FormUrlEncoded
    @POST(delete_skills_company)
    Call<LogoutModel> deleteSkill(
            @Field("skills_company_id") String skills_company_id
    );

    @FormUrlEncoded
    @POST(add_portfolio)
    Call<LogoutModel> addProtfolio(
            @Field("medical_id") String medical_id,
            @Field("blog_link") String blog_link,
            @Field("github_profile") String github_profile,
            @Field("play_store_link") String play_store_link,
            @Field("portfolio_link") String portfolio_link,
            @Field("other_work") String other_work
    );


    @FormUrlEncoded
    @POST(get_portfolio)
    Call<ProtfolioModel> getMyProtfolio(@Field("medical_id") String medical_id);

    @FormUrlEncoded
    @POST(add_projects_details)
    Call<LogoutModel> addMyProjects(
            @Field("medical_id") String medical_id,
            @Field("title") String title,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("currently_going") String currently_going,
            @Field("discription") String discription,
            @Field("project_link") String project_link
    );

    @FormUrlEncoded
    @POST(get_projects_details)
    Call<MyResumeProjectsModel> getMyResumeProject(
            @Field("medical_id") String medical_id
    );


    @FormUrlEncoded
    @POST(delete_projects_details)
    Call<LogoutModel> deleteMyResumeProject(
            @Field("projects_details_id") String projects_details_id
    );


    @FormUrlEncoded
    @POST(update_projects_details)
    Call<LogoutModel> updateMyProjects(
            @Field("projects_details_id") String projects_details_id,
            @Field("title") String title,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("currently_going") String currently_going,
            @Field("discription") String discription,
            @Field("project_link") String project_link
    );


    @FormUrlEncoded
    @POST(get_all_jobs)
    Call<MedicalJobModel> getJobMedical(
            @Field("medical_id") String medical_id
    );


    @FormUrlEncoded
    @POST(get_all_internship)
    Call<MedicalInternshipModel> getInternshipMedical(
            @Field("medical_id") String medical_id
    );


    @FormUrlEncoded
    @POST(apply_job_internship)
    Call<LogoutModel> applyJobInternship(
            @Field("medical_id") String medical_id,
            @Field("job_internship_id") String job_internship_id,
            @Field("company_id") String company_id,
            @Field("type") String type,
            @Field("answer_1") String answer_1,
            @Field("answer_2") String answer_2,
            @Field("answer_3") String answer_3,
            @Field("answer_4") String answer_4,
            @Field("answer_5") String answer_5
    );

    @FormUrlEncoded
    @POST(get_company_user_profile_medical)
    Call<MedicalProfileModel> getMedicalProfile(
            @Field("medical_id") String medical_id
    );


    @FormUrlEncoded
    @POST(update_medical_profile)
    Call<LogoutModel> updateMedicalProfile(
            @Field("medical_id") String medical_id,
            @Field("name") String name,
            @Field("state") String state,
            @Field("state_id") String state_id,
            @Field("city") String city,
            @Field("city_id") String city_id,
            @Field("email") String email,
            @Field("address") String address
    );


    @FormUrlEncoded
    @POST(get_my_apply_job_internship_medical)
    Call<MyAppliedJobModel> getMyAppliedJobInternship(
            @Field("medical_id") String medical_id
    );


    @FormUrlEncoded
    @POST(save_job_internship)
    Call<LogoutModel> saveUnSaveJob(
            @Field("medical_id") String medical_id,
            @Field("job_internship_id") String job_internship_id,
            @Field("company_id") String company_id,
            @Field("save") String save,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST(get_saved_job_internship_list)
    Call<SavedJobModel> getMySavedJob(
            @Field("medical_id") String medical_id
    );


    @FormUrlEncoded
    @POST(get_company_user_list)
    Call<UsersListModel> getUsersList(
            @Field("type") String type
    );

    /////Chemist Apis

    @FormUrlEncoded
    @POST(chemist_company_signup)
    Call<ChemistSignupModel> chemistSignUp(
            @Field("mobile") String mobile,
            @Field("fcm_id") String fcm_id
    );


    @FormUrlEncoded
    @POST(chemist_get_profile)
    Call<ChemistProfileModel> getChemistProfile(
            @Field("chemist_id") String chemist_id
    );


    @FormUrlEncoded
    @POST(chemist_update_profile)
    Call<LogoutModel> updateChemistProfile(
            @Field("chemist_id") String chemist_id,
            @Field("name") String name,
            @Field("state") String state,
            @Field("state_id") String state_id,
            @Field("city") String city,
            @Field("city_id") String city_id,
            @Field("email") String email,
            @Field("address") String address
    );


}
