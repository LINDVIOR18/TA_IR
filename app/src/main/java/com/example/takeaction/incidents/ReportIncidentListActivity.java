package com.example.takeaction.incidents;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takeaction.NavigationDrawer;
import com.example.takeaction.R;

import java.util.ArrayList;

public class ReportIncidentListActivity extends NavigationDrawer {

    RecyclerView mRecyclerView;
    IncidentAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new IncidentAdapter(getMyList(), new IncidentAdapter.Callback() {
            @Override
            public void onItemClick(int position) {
                IncidentList incident = getMyList().get(position);

                Intent appInfo = new Intent(ReportIncidentListActivity.this, IncidentDetails.class);
                appInfo.putExtra(IncidentDetails.INCIDENT_KEY, incident);
                startActivity(appInfo);
            }
        });

        mRecyclerView.setAdapter(myAdapter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_list_report_incident;
    }

    private ArrayList<IncidentList> getMyList() {
        ArrayList<IncidentList> models = new ArrayList<>();
        IncidentList m = new IncidentList();
        m.setTitle("Incident1");
        m.setDescription("Adresa");
        m.setImg(R.drawable.fire);
        models.add(m);

        m = new IncidentList();
        m.setTitle("Incident2");
        m.setDescription("Adresa");
        m.setImg(R.drawable.ambulance);
        models.add(m);


        m = new IncidentList();
        m.setTitle("Incident3");
        m.setDescription("Adresa");
        m.setImg(R.drawable.flash);
        models.add(m);

        return models;

    }
}