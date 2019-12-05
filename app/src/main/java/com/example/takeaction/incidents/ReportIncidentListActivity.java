package com.example.takeaction.incidents;
import  androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.takeaction.R;

import java.util.ArrayList;

public class ReportIncidentListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    IncidentAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_report_incident);


        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new IncidentAdapter(this, getMyList(), new IncidentAdapter.Callback() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(ReportIncidentListActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(myAdapter);
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