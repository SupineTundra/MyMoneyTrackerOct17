package com.lofstschool.mymoneytrackeroct17;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    ViewPager pager;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.pages);
        tabs = findViewById(R.id.tabs);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);


        pager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), getResources()));
        tabs.setupWithViewPager(pager);

       startActivity(new Intent(this, AuthActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!((App) getApplication()).isLoggedIn()) {
            startActivity(new Intent(this, AuthActivity.class));

        } else {
            pager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), getResources()));
            tabs.setupWithViewPager(pager);
        }
    }

}
