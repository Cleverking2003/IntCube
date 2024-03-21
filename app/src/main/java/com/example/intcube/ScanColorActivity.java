package com.example.intcube;

import static org.opencv.android.NativeCameraView.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

public class ScanColorActivity extends AppCompatActivity{

    private static final int CAMERA_PERMISSION_CODE = 100;

    private CameraBridgeViewBase cameraBridgeViewBase;

    private Mat mat1, mat2, mat3;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_color);

        //! [ocv_loader_init]
        if (OpenCVLoader.initLocal()) {
            Log.i(TAG, "OpenCV loaded successfully");
        } else {
            Log.e(TAG, "OpenCV initialization failed!");
            (Toast.makeText(this, "OpenCV initialization failed!", Toast.LENGTH_LONG)).show();
        }

        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(ScanColorActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(ScanColorActivity.this, new String[] { permission }, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ScanColorActivity.this, "Разрешение на использование камеры получено", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ScanColorActivity.this, "Для сканирования необходимо разрешение для использования камеры", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BackActivityScanType(View v){
        Intent intent = new Intent(this, ScanTypeActivity.class);
        startActivity(intent);
    }

    public void StartActivitySolution(View v){
        Intent intent = new Intent(this, SolutionActivity.class);
        startActivity(intent);
    }
}