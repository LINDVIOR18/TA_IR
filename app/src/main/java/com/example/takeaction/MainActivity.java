package com.example.takeaction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button BtnToMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BtnToMaps = findViewById(R.id.buttonToMap);

        BtnToMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveToMaps();
            }
        });
    }

    private void moveToMaps (){
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);

        startActivity(intent);
    }
}
