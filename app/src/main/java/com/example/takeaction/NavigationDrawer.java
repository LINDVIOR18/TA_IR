package com.example.takeaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.takeaction.homemap.HomeMapActivity;
import com.example.takeaction.incident.ReportIncidentActivity;
import com.example.takeaction.profile.ProfileActivity;
import com.google.android.material.navigation.NavigationView;

public abstract class NavigationDrawer extends AppCompatActivity {

    protected DrawerLayout drawer_layout;
    private ActionBarDrawerToggle action_toggle;
    private NavigationView navigation_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        drawer_layout = findViewById(R.id.activity_navigation_drawer);
        action_toggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close);

        drawer_layout.addDrawerListener(action_toggle);
        action_toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigation_view = findViewById(R.id.navigation_view);
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.home:
                        Intent home = new Intent(getApplicationContext(), HomeMapActivity.class);
                        startActivity(home);
                        break;
                    case R.id.account:
                        Intent account = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(account);
                        break;
                    case R.id.incident:
                        Intent incident = new Intent(getApplicationContext(), ReportIncidentActivity.class);
                        startActivity(incident);
                        break;
                    case R.id.logout:
//                        Intent logout = new Intent(getApplicationContext(), LgoutActivity.class);
//                        startActivity(logout);
                        break;
                    default:
                        return true;
                }


                return true;

            }
        });

        setNavigation();
    }

    protected abstract int getLayoutRes();

    private void setNavigation() {
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(getLayoutRes(), null, false);
        drawer_layout.addView(contentView, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (action_toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}