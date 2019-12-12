package com.example.takeaction.firebase;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.takeaction.model.IncidentModel;
import com.example.takeaction.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    public String getUid() {
        return Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    }

    public String getAuthor(){
        return Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName());
    }

    private void writeNewPost(final IncidentModel incidentModel, final AuthDataCallback<IncidentModel> callback) {

        databaseReference = FirebaseDatabase.getInstance().getReference();

        String key = databaseReference.child("incidents").push().getKey();
        Map<String, Object> postValues = incidentModel.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/incidents/" + key, postValues);
        childUpdates.put("/user-incidents/" + getUid() + "/" + key, postValues);

        Task<Void> task = databaseReference.updateChildren(childUpdates);
        task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.onSuccess(incidentModel);
            }
        });
    }

    public void createIncident(final IncidentModel incidentModel, final AuthDataCallback<IncidentModel> callback) {

        databaseReference.child("users").child(getUid())
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                UserModel user = dataSnapshot.getValue(UserModel.class);

                                if (user == null) {
                                    callback.onError();
                                } else {
                                    writeNewPost(incidentModel, callback);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.w("Activity", "getUser:onCancelled", databaseError.toException());
                            }
                        });
    }
}