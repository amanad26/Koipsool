package com.koipsool_new.Session;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    public static String pharma = "pharma";
    public static String medical = "medical";
    public static String chemist = "chemist";
    private static String fileName = "user_data";
    public  static String user_id = "user_id";
    private static String user_name = "user_name";
    private static String email = "email";
    private static String mobile = "mobile";
    private static String image = "image";
    private static String type = "type";
    private static String bank_details = "bank";
    private static String otp = "otp";
    private static String state = "state";
    private static String city = "city";
    private static String myCoverLetter = "myCoverLetter";




    Context context;
    public SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public Session(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }


    public void setUserName(String name) {
        editor.putString(user_name, name);
        editor.commit();
    }

      public void setMyCoverLetter(String name) {
        editor.putString(myCoverLetter, name);
        editor.commit();
        editor.apply();
    }


    public void setState(String name) {
        editor.putString(state, name);
        editor.commit();
    }

    public void setCity(String name) {
        editor.putString(city, name);
        editor.commit();
    }



    public void setOtp(String otp2) {
        editor.putString(otp, otp2);
        editor.commit();
    }

    public String getUserName() {
        return sharedPreferences.getString(user_name, "");
    }

    public String getMyCoverLetter() {
        return sharedPreferences.getString(myCoverLetter, "");
    }


    public String getState() {
        return sharedPreferences.getString(state, "");
    }

    public String getCity() {
        return sharedPreferences.getString(city, "");
    }

    public String getOtp() {
        return sharedPreferences.getString(otp, "");
    }

    public void setUser_id(String id) {
        editor.putString(user_id, id);
        editor.commit();
    }

    public String getUserId() {
        return sharedPreferences.getString(user_id, "");
    }

    public void setEmail(String em) {
        editor.putString(email, em);
        editor.commit();
    }

    public String getEmail() {
        return sharedPreferences.getString(email, "");
    }

    public void setMobile(String mob) {
        editor.putString(mobile, mob);
        editor.commit();
    }

    public String getMobile() {
        return sharedPreferences.getString(mobile, "");
    }


    public void setImage(String img) {
        editor.putString(image, img);
        editor.commit();
    }

    public String getImage() {
        return sharedPreferences.getString(image, "");
    }


    public void setType(String tp) {
        editor.putString(type, tp);
        editor.commit();
    }

    public String getType() {
        return sharedPreferences.getString(type, "");
    }


    public void logOut() {
        editor.clear();
        editor.commit();
        editor.apply();
    }


    public void setBank_details(String bank) {
        editor.putString(bank_details, bank);
        editor.commit();
        editor.apply();
    }

    public String getBank() {
        return sharedPreferences.getString(bank_details, "");
    }


}
