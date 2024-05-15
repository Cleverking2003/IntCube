package com.example.intcube;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class GuideAxisActivity extends AppCompatActivity {

    WebView webGuideAxis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_axis);

        webGuideAxis = findViewById(R.id.webGuideAxis);
        webGuideAxis.loadUrl("file:///android_asset/html/Axis/Axis.html");
        Toolbar toolbar = findViewById(R.id.guideToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Обучающий курс Axis Cube");
        }
    }

    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }
}
