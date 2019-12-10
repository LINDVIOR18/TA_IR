package com.example.takeaction.incidents;
import  androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import com.example.takeaction.R;
import com.example.takeaction.model.IncidentModel;

import java.util.ArrayList;
import java.util.List;

public class ReportIncidentListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    IncidentAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_report_incident);


        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new IncidentAdapter(getIncidents(), new IncidentAdapter.Callback() {
            @Override
            public void onItemClick(int position) {
                assert getIncidents() != null;
                IncidentModel incident = getIncidents().get(position);

                Intent appInfo = new Intent(ReportIncidentListActivity.this, IncidentDetails.class);
                appInfo.putExtra(IncidentDetails.INCIDENT_KEY, incident);
                startActivity(appInfo);
            }
        });

        mRecyclerView.setAdapter(myAdapter);
    }

    private List<IncidentModel> getIncidents() {

        return null;
    }
}

//    private ArrayList<IncidentModel> getMyList() {
//        ArrayList<IncidentModel> models = new ArrayList<>();
//
////        IncidentModel model = new IncidentModel();
//
////        model.setTitle("Incident1");
////        m.setDescription("Adresa");
////        m.setImg(R.drawable.fire);
////        models.add(m);
//
//        return models;
//
//    }
//}