package com.example.intcube;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class Guide3x3Activity extends AppCompatActivity {

    WebView webGuide3x3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_3x3);

        webGuide3x3 = findViewById(R.id.webGuide3x3);
        webGuide3x3.loadUrl("file:///android_asset/html/3x3/3x3.html");
    }
}
