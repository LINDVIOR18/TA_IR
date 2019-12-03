package com.example.takeaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.takeaction.validation.RegistrationValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText userPassword;
    private EditText confirmPassword;
    private EditText userEmail;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

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

        if (!RegistrationValidation.isEmail(userEmail)) {
            Toast temail = Toast.makeText(this, "You must enter email!", Toast.LENGTH_SHORT);
            temail.show();

        } else if (RegistrationValidation.isEmpty(userPassword)) {
            Toast tpass = Toast.makeText(this, "You must enter password to register!", Toast.LENGTH_SHORT);
            tpass.show();
        } else if (RegistrationValidation.isEmpty(confirmPassword)) {
            Toast tconfpass = Toast.makeText(this, "You must confirm password to register!", Toast.LENGTH_SHORT);
            tconfpass.show();
        }

        if (!RegistrationValidation.isEmail(userEmail)) {
            userEmail.setError("Enter valid email!");
        } else if (!RegistrationValidation.isValidPassword(userPassword.getText().toString())) {
            userPassword.setError("Password must contain mix of upper and lower case letters as well as digits and one special charecter(6-20)");
        } else if (!RegistrationValidation.validateConfirmPassword(userPassword.getText().toString(), confirmPassword.getText().toString())) {
            confirmPassword.setError("Password do not match");
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(RegisterActivity.this, "Registered Failed Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
}
