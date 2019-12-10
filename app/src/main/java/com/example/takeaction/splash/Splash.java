package com.example.takeaction.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.example.takeaction.MainActivity;
import com.example.takeaction.R;
import com.example.takeaction.intro.Slider;

public class Splash extends AppCompatActivity {
    LauncherManager launcherManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        launcherManager = new LauncherManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (launcherManager.isFirstTime()) {
                    launcherManager.setFirstLunch();
                    startActivity(new Intent(getApplicationContext(), Slider.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }
            }
        }, 2000);
    }
}