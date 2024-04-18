package com.example.intcube;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CubeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cube);
    }

    public void create2x2(View view) {
        CubeGLView cube_view = findViewById(R.id.cubeGLView2);
        cube_view.changeCube(2);
    }
    public void create3x3(View view) {
        CubeGLView cube_view = findViewById(R.id.cubeGLView2);
        cube_view.changeCube(3);
    }
    public void createAxis(View view) {
        CubeGLView cube_view = findViewById(R.id.cubeGLView2);
        cube_view.changeCube(0);
    }
}