package com.example.intcube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.VectorDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectTypeActivity extends AppCompatActivity {

    @Override
    @SuppressLint("MissingInflatedId")
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