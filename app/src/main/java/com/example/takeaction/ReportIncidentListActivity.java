package com.example.takeaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ReportIncidentListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_report_incident);


        mRecyclerView = findViewById(R.id.Incident);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(this, getMyList());
        mRecyclerView.setAdapter(myAdapter);



    }

    private ArrayList<Incident> getMyList(){

        ArrayList<Incident> models = new ArrayList<>();

        Incident m = new Incident();
        m.setTitle("Atentie");
        m.setDescription("This is newsfeed description ..");
        m.setImg(R.drawable.ic_launcher_background);
        models.add(m);


        m = new Incident();
        m.setTitle("Img");
        m.setDescription("This is newsfeed description ..");
        m.setImg(R.drawable.atention);
        models.add(m);


        m = new Incident();
        m.setTitle("Risc");
        m.setDescription("This is newsfeed description ..");
        m.setImg(R.drawable.risc);
        models.add(m);

        return models;

    }
}
