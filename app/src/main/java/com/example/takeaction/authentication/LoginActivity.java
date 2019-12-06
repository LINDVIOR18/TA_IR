package com.example.takeaction.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {

    private EditText userPassword;
    private EditText userEmail;

    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authRepository = new AuthRepository(FirebaseAuth.getInstance());

        userPassword = findViewById(R.id.password);
        userEmail = findViewById(R.id.e_mail);
        Button login = findViewById(R.id.login);
        TextView signup = findViewById(R.id.signup);

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

        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if ((AuthValidation.isEmpty(userEmail)) & (AuthValidation.isEmpty(userPassword))) {
            Toast mail = Toast.makeText(this, "You must complete the spaces!", Toast.LENGTH_SHORT);
            mail.show();
        } else if (AuthValidation.isEmpty(userEmail)) {
            Toast mail = Toast.makeText(this, "You must enter email to register!", Toast.LENGTH_SHORT);
            mail.show();
        } else if (AuthValidation.isEmpty(userPassword)) {
            Toast pas = Toast.makeText(this, "You must enter password to register!", Toast.LENGTH_SHORT);
            pas.show();
        }

        if (!AuthValidation.isEmail(userEmail)) {
            userEmail.setError("Enter valid email!");
        }

        if (!AuthValidation.isValidPassword(userPassword.getText().toString())) {
            userPassword.setError("Password must contain mix of upper and lower case letters as well as digits and one special charecter(4-20)");
        }

        if (!password.isEmpty() && !email.isEmpty()) {

            authRepository.signIn(this, email, password, new AuthDataCallback<Task<AuthResult>>() {
                @Override
                public void onSuccess(Task<AuthResult> response) {
                    Toast.makeText(LoginActivity.this, "Sign In Successfully!", Toast.LENGTH_SHORT).show();
                    authRepository.onAuthSuccess(Objects.requireNonNull(Objects.requireNonNull(response.getResult()).getUser()));
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onError() {
                    Toast.makeText(LoginActivity.this, "Sign In Failed! Try Again!", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }
}

