package com.example.intcube;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.opencv.android.OpenCVLoader;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageButton settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            TextView logo = findViewById(R.id.textTitle);
            ImageButton helpButton = findViewById(R.id.buttonToHelp);
            ImageButton settingsButton = findViewById(R.id.buttonToSettings);

            int whiteColor = getResources().getColor(R.color.white,  null);
            logo.setTextColor(whiteColor);
            helpButton.setColorFilter(Color.argb(255, 255, 255, 255));
            settingsButton.setColorFilter(Color.argb(255, 255, 255, 255));
        }

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
}