package com.example.intcube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class Guide2x2Activity extends AppCompatActivity {

    WebView webGuide2x2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_2x2);

        webGuide2x2 = findViewById(R.id.webGuide2x2);
        webGuide2x2.loadUrl("file:///android_asset/html/2x2/2x2.html");
    }
}
