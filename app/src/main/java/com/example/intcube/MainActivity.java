package com.example.intcube;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;

import java.io.IOException;

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

        ImageView image = findViewById(R.id.imageView);
        ImageDecoder.Source src = ImageDecoder.createSource(getResources(), R.drawable.cube);
        try {
            @SuppressLint("WrongThread") Drawable draw = ImageDecoder.decodeDrawable(src);
            image.setImageDrawable(draw);
            if (draw instanceof AnimatedImageDrawable) {
                ((AnimatedImageDrawable) draw).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


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

    public void startActivitySettings(View v){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void startActivitySelectType(View v) {
        Intent intent = new Intent(this, SelectTypeActivity.class);
        startActivity(intent);
    }

    public void startActivityManualInput(View v){
        Intent intent = new Intent(this, CubeManualInput.class);
        startActivity(intent);
    }
}