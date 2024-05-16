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
        startActivityForResult(intent,1);
    }

    public void choosing3x3(View v)
    {
        Intent intent = new Intent(this, SelectColorsActivity.class);
        intent.putExtra("sizeCube", "3");
        startActivityForResult(intent,1);
    }

    public void choosing4x4(View v)
    {
        Intent intent = new Intent(this, SelectColorsActivity.class);
        intent.putExtra("sizeCube", "4");
        startActivityForResult(intent,1);
    }

    public void choosingAxis(View v)
    {
        Intent intent = new Intent(this, SettingAxisActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            setResult(1);
            finish();
        }
    }

}