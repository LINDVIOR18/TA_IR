package com.example.takeaction.firebase;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.example.takeaction.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AuthRepository {

    private FirebaseAuth firebaseAuth;

    public AuthRepository(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public void onAuthSuccess(FirebaseUser user) {

        String username = usernameFromEmail(Objects.requireNonNull(user.getEmail()));

        writeNewUser(user.getUid(), username, user.getEmail());
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void writeNewUser(String userId, String name, String email) {
        UserModel userModel = new UserModel(name, email);
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseDatabase.child("users").child(userId).setValue(userModel);
    }

    public void signUp(Activity activity, String email, String password, final AuthDataCallback<Task<AuthResult>> callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            callback.onSuccess(task);
                        } else
                            callback.onError();
                    }
                });
    }

    public void signIn(Activity activity, String email, String password, final AuthDataCallback<Task<AuthResult>> callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            callback.onSuccess(task);
                        } else
                            callback.onError();
                    }
                });
    }
}
