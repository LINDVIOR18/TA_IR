package com.example.takeaction.validation;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationValidation {

    public static boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

   public static boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public static boolean isValidPassword(String password) {
        Matcher matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{6,20})").matcher(password);
        return matcher.matches();
    }


    public static boolean validateConfirmPassword(String password , String confirmPassword) {
        boolean temp=true;

        if(!password.equals(confirmPassword)){
            temp=false;
        }
        return temp;
    }
}
