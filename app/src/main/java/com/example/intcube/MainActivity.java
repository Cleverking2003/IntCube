package com.example.intcube;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (OpenCVLoader.initLocal()){
            Log.d("LOADED", "success");
        }
        else{
            Log.d("LOADED", "err");
        }

        System.loadLibrary("libcube");
    }

    public void startActivityScan(View v){
        Intent intent = new Intent(this, ScanTypeActivity.class);
        startActivity(intent);
    }

    public void startActivityScanSqr(View v){
        Intent intent = new Intent(this, ScanColorSqrActivity.class);
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

    public void startActivityCube(View v){
        Intent intent = new Intent(this, CubeActivity.class);
        startActivity(intent);
    }
}