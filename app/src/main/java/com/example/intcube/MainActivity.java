package com.example.intcube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Button buttonToScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startActivityScan(View v){
        Intent intent = new Intent(this, ScanTypeActivity.class);
        startActivity(intent);
    }

    public void startActivityGuide(View v){
        Intent intent = new Intent(this, GuideActivity.class);
        startActivity(intent);
    }

    public void startActivityHelp(View v){
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
}