package com.example.takeaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.takeaction.validation.AuthValidation;

public class LoginActivity extends AppCompatActivity {

   private EditText password;
   private EditText e_mail;
   private Button login;
   private TextView signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        password = findViewById(R.id.et_password);
        e_mail = findViewById(R.id.et_email);
        login = findViewById(R.id.btn_login);
        signup = findViewById(R.id.tv_signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataEntered();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(j);
            }
        });
    }

    void checkDataEntered() {
        if ((AuthValidation.isEmpty(e_mail)) & (AuthValidation.isEmpty(password))) {
            Toast mail = Toast.makeText(this, "You must complete the spaces!", Toast.LENGTH_SHORT);
            mail.show();
        } else if (AuthValidation.isEmpty(e_mail)) {
            Toast mail = Toast.makeText(this, "You must enter email to register!", Toast.LENGTH_SHORT);
            mail.show();
        } else if (AuthValidation.isEmpty(password)) {
            Toast pas = Toast.makeText(this, "You must enter password to register!", Toast.LENGTH_SHORT);
            pas.show();
        }

        if (!AuthValidation.isEmail(e_mail)) {
            e_mail.setError("Enter valid email!");
        }

        if (!AuthValidation.isValidPassword(password.getText().toString())) {
            password.setError("Password must contain mix of upper and lower case letters as well as digits and one special charecter(6-20)");
        }
        if ((AuthValidation.isEmail(e_mail)) & (AuthValidation.isValidPassword(password.getText().toString()))) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

    }
}
