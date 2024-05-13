package com.example.intcube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GuideActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        Toolbar toolbar = findViewById(R.id.guideToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Обучающие курсы");
        }
    }

    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }

    public void startActivity2x2G(View v){
        Intent intent = new Intent(this, Guide2x2Activity.class);
        startActivity(intent);
    }

    public void startActivity3x3G(View v){
        Intent intent = new Intent(this, Guide3x3Activity.class);
        startActivity(intent);
    }

    public void startActivity4x4G(View v){
        Intent intent = new Intent(this, Guide4x4Activity.class);
        startActivity(intent);
    }

    public void startActivityAxisG(View v){
        Intent intent = new Intent(this, GuideAxisActivity.class);
        startActivity(intent);
    }
}