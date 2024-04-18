package com.example.intcube;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class CubeActivity extends AppCompatActivity {
    private CubeGLView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new CubeGLView(this);
        setContentView(view);
    }
}