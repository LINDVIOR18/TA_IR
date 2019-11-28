package com.example.takeaction;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

   private EditText password;
   private EditText confirmPassword;
   private EditText email;
   private Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        password = findViewById(R.id.inputPassword);
        confirmPassword = findViewById(R.id.inputConfirmPassword);
        email = findViewById(R.id.inputEmail);
        register = findViewById(R.id.buttonRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataEntered();
            }
        });
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public static boolean isValidPassword(String password) {
        Matcher matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{6,20})").matcher(password);
        return matcher.matches();
    }


    private static boolean validateConfirmPassword(String password , String confirmPassword) {
        boolean temp=true;
        String pass=password;
        String cpass=confirmPassword;

         if(!pass.equals(cpass)){
            temp=false;
        }
        return temp;
    }



    void checkDataEntered() {
        if (isEmpty(password)) {
            Toast t = Toast.makeText(this, "You must enter password to register!", Toast.LENGTH_SHORT);
            t.show();
        }
        if (isEmpty(confirmPassword)) {
            Toast t = Toast.makeText(this, "You must confirm password to register!", Toast.LENGTH_SHORT);
            t.show();
        }

        if (isEmail(email) == false) {
            email.setError("Enter valid email!");
        } else if (isValidPassword(password.getText().toString()) == false) {
            password.setError("Password must contain mix of upper and lower case letters as well as digits and one special charecter(6-20)");
        }else if (validateConfirmPassword(password.getText().toString(),confirmPassword.getText().toString()) == false) {
            confirmPassword.setError("Password do not match");
        }
        else{
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }


    }
}
