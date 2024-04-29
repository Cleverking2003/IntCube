package com.example.intcube;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class Guide4x4Activity extends AppCompatActivity {

    WebView webGuide4x4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_4x4);

        webGuide4x4 = findViewById(R.id.webGuide4x4);
        webGuide4x4.loadUrl("file:///android_asset/html/4x4/4x4.html");
    }
}
