package com.example.takeaction.intro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.takeaction.LoginActivity;
import com.example.takeaction.R;

public class Slider extends AppCompatActivity {
    private ViewPager viewPager;
    private Button btnNext;
    private int[] layouts;
    private Button btnSkip;
    private LinearLayout layoutDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isFirstTimeStartApp()) {
            startMainActivity();
            finish();
        }

        setStatusBarTransparent();

        setContentView(R.layout.activity_slider);

        viewPager = findViewById(R.id.vp_pager);
        layoutDot = findViewById(R.id.ll_line);
        btnNext = findViewById(R.id.btn_next);
        btnSkip = findViewById(R.id.btn_skip);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startMainActivity();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = viewPager.getCurrentItem() + 1;
                if (currentPage < layouts.length) {
                    viewPager.setCurrentItem(currentPage);
                } else {
                    startMainActivity();
                }
            }
        });

        layouts = new int[]{
                R.layout.slider1,
                R.layout.slider2,
                R.layout.slider3
        };

        Adapter pagerAdapter = new Adapter(this, layouts);
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int position) {
                if (position == layouts.length - 1) {
                    btnNext.setText("START");
                    btnSkip.setVisibility(View.GONE);
                } else {
                    btnNext.setText("NEXT");
                    btnSkip.setVisibility(View.VISIBLE);
                }
                setDotStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setDotStatus(0);
    }

    private boolean isFirstTimeStartApp() {
        SharedPreferences ref = getApplicationContext().getSharedPreferences("IntroSliderApp", Context.MODE_PRIVATE);
        return ref.getBoolean("FirstTimeStartFlag", true);
    }

    private void setFirstTimeStartStatus() {
        SharedPreferences ref = getApplicationContext().getSharedPreferences("IntroSliderApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putBoolean("FirstTimeStartFlag", false);
        editor.apply();
    }

    private void setDotStatus(int page) {
        layoutDot.removeAllViews();
        TextView[] dotstv = new TextView[layouts.length];
        for (int i = 0; i < dotstv.length; i++) {
            dotstv[i] = new TextView(this);
            dotstv[i].setText(HtmlCompat.fromHtml("&#8226;", HtmlCompat.FROM_HTML_MODE_LEGACY));
            dotstv[i].setTextSize(30);
            dotstv[i].setTextColor(Color.parseColor("#ffffff"));
            layoutDot.addView(dotstv[i]);
        }

        if (dotstv.length > 0) {
            dotstv[page].setTextColor(Color.parseColor("#153c9a"));
        }
    }

    private void startMainActivity() {
        setFirstTimeStartStatus();
        startActivity(new Intent(Slider.this, LoginActivity.class));
        finish();
    }

    private void setStatusBarTransparent() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }
}