package com.example.takeaction.incidents;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.takeaction.R;

public class IncidentDetails extends AppCompatActivity {
    private TextView tvTitle;
    private TextView tvAddress;
    private ImageView ivImage;
    private TextView tvText;


    public static final String INCIDENT_KEY = "INCIDENT_KEY";

    private IncidentList incident;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_details);
        tvTitle = findViewById(R.id.tv_incident);
        tvAddress = findViewById(R.id.tv_address);
        ivImage = findViewById(R.id.iv_incident);
        tvText = findViewById(R.id.tv_description);

        incident = (IncidentList)getIntent().getSerializableExtra(INCIDENT_KEY);
        assert incident != null;
        tvTitle.setText(incident.getTitle());
        tvAddress.setText(incident.getAddress());
        ivImage.setImageResource(incident.getImg());
        tvText.setText(incident.getDescription());
    }
}
