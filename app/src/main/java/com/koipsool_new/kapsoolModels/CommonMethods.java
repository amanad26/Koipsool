package com.koipsool_new.kapsoolModels;

public class CommonMethods {


    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches();
    }

}
