package com.example.takeaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.takeaction.model.User;
import com.example.takeaction.validation.AuthValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText userPassword;
    private EditText userEmail;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

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
        if ((AuthValidation.isEmail(userEmail)) & (AuthValidation.isValidPassword(userPassword.getText().toString()))) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }


        /*
         * Cumva De scoos tot asta de aici ....
         * logarea merge insa trebuie de fixat erorrile care le arunca...
         */

        if (!password.isEmpty() && !email.isEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                                onAuthSuccess(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()));
                            } else {
                                Toast.makeText(LoginActivity.this, "Sign In Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    public void onAuthSuccess(FirebaseUser user) {

        String username = usernameFromEmail(Objects.requireNonNull(user.getEmail()));

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        //This shit start activity
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
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

