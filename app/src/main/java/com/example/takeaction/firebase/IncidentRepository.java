package com.example.takeaction.firebase;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.takeaction.model.CategoryModel;
import com.example.takeaction.model.IncidentModel;
import com.example.takeaction.model.UserModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IncidentRepository {

    private DatabaseReference databaseReference;

    public IncidentRepository(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    private String getUid() {
        return Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    }


    private void writeNewPost(String userId, String username, String title, String description,
                              CategoryModel categoryModel, String address, String date) {

        databaseReference = FirebaseDatabase.getInstance().getReference();

        String key = databaseReference.child("incidents").push().getKey();
        IncidentModel incidentModel = new IncidentModel(userId, username, title, description, categoryModel, address, date);
        Map<String, Object> postValues = incidentModel.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/incidents/" + key, postValues);
        childUpdates.put("/user-incidents/" + userId + "/" + key, postValues);

        databaseReference.updateChildren(childUpdates);
    }

    public void createIncident(Activity activity, final String title, final String description, final CategoryModel categoryModel,
                               final String address, final String date, final AuthDataCallback<DataSnapshot> callback) {

        databaseReference.child("users").child(getUid())
                .addListenerForSingleValueEvent(activity,
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserModel user = dataSnapshot.getValue(UserModel.class);

                        if (user == null) {
                            callback.onError();
                        } else {
                            callback.onSuccess(writeNewPost(getUid(), , title, description, categoryModel, address, date));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("Activity", "getUser:onCancelled", databaseError.toException());
                    }
                });
    }
}
