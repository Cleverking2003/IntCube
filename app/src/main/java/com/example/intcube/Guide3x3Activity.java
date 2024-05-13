package com.example.intcube;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Guide3x3Activity extends AppCompatActivity {

    WebView webGuide3x3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_3x3);

        webGuide3x3 = findViewById(R.id.webGuide3x3);
        webGuide3x3.loadUrl("file:///android_asset/html/3x3/3x3.html");
        Toolbar toolbar = findViewById(R.id.guideToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Обучающий курс 3x3");
        }
    }

    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }
}
