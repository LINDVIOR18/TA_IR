package com.example.takeaction.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.takeaction.MainActivity;
import com.example.takeaction.R;
import com.example.takeaction.firebase.AuthDataCallback;
import com.example.takeaction.firebase.AuthRepository;
import com.example.takeaction.validation.AuthValidation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText userPassword;
    private EditText confirmPassword;
    private EditText userEmail;

    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authRepository = new AuthRepository(FirebaseAuth.getInstance());

        userPassword = findViewById(R.id.input_password);
        confirmPassword = findViewById(R.id.input_confirm_password);
        userEmail = findViewById(R.id.input_mail);
        final Button buttonRegistration = findViewById(R.id.buttonRegister);

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    private void createUser() {

        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if ((AuthValidation.isEmpty(userEmail)) & (AuthValidation.isEmpty(userPassword))) {
            Toast em_pass = Toast.makeText(this, "You must complete the spaces!", Toast.LENGTH_SHORT);
            em_pass.show();
        } else if (AuthValidation.isEmpty(userPassword)) {
            Toast.makeText(RegisterActivity.this, "You must enter password to register!", Toast.LENGTH_SHORT).show();
        } else if (AuthValidation.isEmpty(confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "Enter your confirmation password!", Toast.LENGTH_SHORT).show();
        }
        if (!AuthValidation.isEmail(userEmail)) {
            userEmail.setError("Enter valid email");
        }
        if (!AuthValidation.isValidPassword(userPassword.getText().toString())) {
            userPassword.setError("Password must contain mix of upper and lower case letters as well as digits and one special character(6-20)");
        }
        if (!AuthValidation.isValidConfirmPassword(userPassword.getText().toString(), confirmPassword.getText().toString())) {
            confirmPassword.setError("Password do not match");
            return;
        }
        if (!email.isEmpty() && !password.isEmpty()) {
            authRepository.signUp(this, email, password, new AuthDataCallback<Task<AuthResult>>() {
                @Override
                public void onSuccess(Task<AuthResult> response) {
                    Toast.makeText(RegisterActivity.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                    authRepository.onAuthSuccess(Objects.requireNonNull(Objects.requireNonNull(response.getResult()).getUser()));
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onError() {
                    Toast.makeText(RegisterActivity.this, "Sign Up Failed, Try Again!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

