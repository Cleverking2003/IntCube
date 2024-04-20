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
    public void moveU(View view) {
        CubeGLView cube_view = findViewById(R.id.cubeGLView2);
        cube_view.executeMove(0);
    }
    public void moveD(View view) {
        CubeGLView cube_view = findViewById(R.id.cubeGLView2);
        cube_view.executeMove(1);
    }
    public void moveL(View view) {
        CubeGLView cube_view = findViewById(R.id.cubeGLView2);
        cube_view.executeMove(2);
    }
    public void moveR(View view) {
        CubeGLView cube_view = findViewById(R.id.cubeGLView2);
        cube_view.executeMove(3);
    }
    public void moveF(View view) {
        CubeGLView cube_view = findViewById(R.id.cubeGLView2);
        cube_view.executeMove(4);
    }
    public void moveB(View view) {
        CubeGLView cube_view = findViewById(R.id.cubeGLView2);
        cube_view.executeMove(5);
    }
    public void moveM(View view) {
        CubeGLView cube_view = findViewById(R.id.cubeGLView2);
        cube_view.executeMove(6);
    }
    public void moveE(View view) {
        CubeGLView cube_view = findViewById(R.id.cubeGLView2);
        cube_view.executeMove(7);
    }
    public void moveS(View view) {
        CubeGLView cube_view = findViewById(R.id.cubeGLView2);
        cube_view.executeMove(8);
    }
    public void moveReset(View view) {
        CubeGLView cube_view = findViewById(R.id.cubeGLView2);
        cube_view.executeMove(9);
    }
}