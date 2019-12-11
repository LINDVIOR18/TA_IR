package com.example.takeaction.incidents;
import  androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
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
                IncidentList incident = getMyList().get(position);

                Intent appInfo = new Intent(ReportIncidentListActivity.this, IncidentDetails.class);
                appInfo.putExtra(IncidentDetails.INCIDENT_KEY, incident);
                startActivity(appInfo);
            }
        });

        mRecyclerView.setAdapter(myAdapter);
    }

    private ArrayList<IncidentList> getMyList() {
        ArrayList<IncidentList> models = new ArrayList<>();
        IncidentList m = new IncidentList();
        m.setTitle("Forest To Study Fire And Smoke");
        m.setAddress("Stefan cel Mare si Sfant Boulevard 202/2, Chisinau");
        m.setDescription("In the next few weeks, the U.S. Forest Service plans to conduct a massive controlled burn on a remote mountain in Utah, part of the agency’s efforts to better understand the behavior of giant fires that are becoming more common in the West.\n" +
                "The plan is to light hundreds of forested acres on fire when weather conditions are just right. Dozens of scientists will then monitor the blaze with planes, drones, and laser technology. It’s all part of the Forest Service-led Fire and Smoke Model Evaluation Experiment, or FASMEE, on the Fishlake National Forest in Utah.\n" +
                "\n" +
                "“The goal is so that they can collect data on the fuels and fuel consumption, the heat release,” said Mike Battaglia, a research forester based in Colorado. “They’re looking at the plume dynamics of the smoke going into the air, they’re sampling the smoke for microorganisms.”\n" +
                "\n" +
                "Battaglia says the more we understand how fire works, the better we’ll be at fighting wildfires, as well as implementing prescribed burns to benefit ecosystems. Battaglia himself will be studying the impact of the burn on aspens and other trees in the area.");
        m.setImg(R.drawable.fire_tree);
        models.add(m);

        m = new IncidentList();
        m.setTitle("Incident2");
        m.setAddress("Bulevardul Ștefan cel Mare și Sfînt 171/1, Chișinău 2004");
        m.setDescription("Some bla bla bla for incident2");
        m.setImg(R.drawable.ambulance);
        models.add(m);


        m = new IncidentList();
        m.setTitle("Incident3");
        m.setAddress("Strada Ion Creangă 1/3, Chișinău, Moldova");
        m.setDescription("Some bla bla bla for incident3");
        m.setImg(R.drawable.flash);
        models.add(m);

        return models;

    }
}