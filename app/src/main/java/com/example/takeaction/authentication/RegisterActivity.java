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
import com.example.takeaction.model.User;
import com.example.takeaction.validation.AuthValidation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText userPassword;
    private EditText confirmPassword;
    private EditText userEmail;
    private TextView signin;

    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authRepository = new AuthRepository(FirebaseAuth.getInstance());

        userPassword = findViewById(R.id.et_password);
        confirmPassword = findViewById(R.id.et_confirm_password);
        userEmail = findViewById(R.id.et_email);
        final Button buttonRegistration = findViewById(R.id.btn_register);
        signin = findViewById(R.id.tv_signin);

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent m = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(m);
            }
        });
    }

    private void createUser() {

        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if ((AuthValidation.isEmpty(userEmail)) & (AuthValidation.isEmpty(userPassword))) {
            Toast em_pass = Toast.makeText(this, "You must complete the spaces!", Toast.LENGTH_SHORT);
            em_pass.show();
        } else if (AuthValidation.isEmpty(userEmail)) {
            Toast mail = Toast.makeText(this, "You must enter email to register!", Toast.LENGTH_SHORT);
            mail.show();
        } else if (AuthValidation.isEmpty(userPassword)) {
            Toast pas = Toast.makeText(this, "You must enter password to register!", Toast.LENGTH_SHORT);
            pas.show();
        } else if (AuthValidation.isEmpty(confirmPassword)) {
            Toast cpass = Toast.makeText(this, "You must confirm your password!", Toast.LENGTH_SHORT);
            cpass.show();
        }

        if (AuthValidation.isEmail(userEmail)) {
            userEmail.setError("Enter valid email!");
        }
        if (!AuthValidation.isValidPassword(userPassword.getText().toString())) {
            userPassword.setError("Password must contain mix of upper and lower case letters as well as digits and one special charecter(6-20)");
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

    public void onAuthSuccess(FirebaseUser user) {

        String username = usernameFromEmail(Objects.requireNonNull(user.getEmail()));

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());
        startActivity(new Intent(com.example.takeaction.authentication.RegisterActivity.this, MainActivity.class));
        finish();

    }


    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseDatabase.child("users").child(userId).setValue(user);
    }
}


