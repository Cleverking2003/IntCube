package com.example.intcube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        Toolbar toolbar = findViewById(R.id.guideToolbar);

        setSupportActionBar(toolbar);
        //toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Обучающие курсы");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.window_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_info:
                AlertDialog dialogInfo = (AlertDialog) createDialog();
                dialogInfo.show();
                return true;
        }
        return true;
    }

    public Dialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Справка")
                .setMessage(R.string.guideNote);
        return builder.create();
    }

    public void startActivity2x2G(View v){
        Intent intent = new Intent(this, Guide2x2Activity.class);
        startActivity(intent);
    }

    public void startActivity3x3G(View v){
        Intent intent = new Intent(this, Guide3x3Activity.class);
        startActivity(intent);
    }

    public void startActivity4x4G(View v){
        Intent intent = new Intent(this, Guide4x4Activity.class);
        startActivity(intent);
    }

    public void startActivityAxisG(View v){
        Intent intent = new Intent(this, GuideAxisActivity.class);
        startActivity(intent);
    }
}