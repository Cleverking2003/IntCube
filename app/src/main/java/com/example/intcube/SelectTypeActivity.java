package com.example.intcube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);
    }

    public void choosing2x2(View v)
    {
        Intent intent = new Intent(this, SelectColorsActivity.class);
        intent.putExtra("sizeCube", "2");
        startActivity(intent);
    }

    public void choosing3x3(View v)
    {
        Intent intent = new Intent(this, SelectColorsActivity.class);
        intent.putExtra("sizeCube", "3");
        startActivity(intent);
    }

    public void choosing4x4(View v)
    {
        Intent intent = new Intent(this, SelectColorsActivity.class);
        intent.putExtra("sizeCube", "4");
        startActivity(intent);
    }

    public void choosingAxis(View v)
    {
        Intent intent = new Intent(this, SettingAxisActivity.class);
        startActivity(intent);
    }

}