package com.example.intcube;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
                .setMessage(R.string.cubeNote);
        return builder.create();
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