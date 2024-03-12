package com.example.intcube;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    Button buttonToScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (OpenCVLoader.initLocal()) {
            Log.i(TAG, "OpenCV loaded successfully");
        } else {
            Log.e(TAG, "OpenCV initialization failed!");
            return;
        }
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