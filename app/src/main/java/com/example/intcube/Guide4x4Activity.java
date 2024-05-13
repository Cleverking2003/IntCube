package com.example.intcube;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Guide4x4Activity extends AppCompatActivity {

    WebView webGuide4x4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_4x4);

        webGuide4x4 = findViewById(R.id.webGuide4x4);
        webGuide4x4.loadUrl("file:///android_asset/html/4x4/4x4.html");
        Toolbar toolbar = findViewById(R.id.guideToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Обучающий курс 4x4");
        }
    }

    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }
}
