package com.example.intcube;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CubeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cube);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        CubeGLView cube = findViewById(R.id.cubeGLView2);
        cube.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        CubeGLView cube = findViewById(R.id.cubeGLView2);
        cube.onResume();
    }

    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
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
    public void moveReset(View view) {
        CubeGLView cube_view = findViewById(R.id.cubeGLView2);
        cube_view.executeMove(12, false);
    }
}