package com.example.takeaction.incidents;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.takeaction.R;
import com.example.takeaction.firebase.IncidentCallback;
import com.example.takeaction.firebase.IncidentRepository;
import com.example.takeaction.model.IncidentModel;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ReportIncidentListActivity extends AppCompatActivity {

    private IncidentRepository incidentRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_report_incident);

        incidentRepository = new IncidentRepository(FirebaseDatabase.getInstance().getReference().child("incidents"));

        getIncidents();
//        setList();
    }

    private void setList(List<IncidentModel> models) {
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        IncidentAdapter myAdapter = new IncidentAdapter(models, new IncidentAdapter.Callback() {
            @Override
            public void onItemClick(int position) {
                IncidentModel incident = getIncidents();

                Intent appInfo = new Intent(ReportIncidentListActivity.this, IncidentDetails.class);
                appInfo.putExtra(IncidentDetails.INCIDENT_KEY, incident);
                startActivity(appInfo);
            }
        });

        mRecyclerView.setAdapter(myAdapter);
    }

    private IncidentModel getIncidents() {

        incidentRepository.getIncidents(new IncidentCallback() {
            @Override
            public void onDataSuccess(List<IncidentModel> incidentModels) {
                Toast.makeText(ReportIncidentListActivity.this, "incidentRepository success", Toast.LENGTH_LONG).show();
                setList(incidentModels);
            }
        });
        return null;
    }
}