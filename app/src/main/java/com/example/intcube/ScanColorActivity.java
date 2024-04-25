package com.example.intcube;


import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;

import android.widget.Toast;


import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.OpenCVLoader;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ScanColorActivity extends CameraActivity implements CvCameraViewListener2 {
    private static final String TAG = "OCVSample::Activity";

    private CameraBridgeViewBase mOpenCvCameraView;

    private ImageView leftupcorner;
    private ImageView upedge;
    private ImageView rightupcorner;
    private ImageView rightedge;
    private ImageView center;
    private ImageView leftedge;
    private ImageView leftdowncorner;
    private ImageView rightdowncorner;

    private ImageView downedge;

    private ImageView[] preview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);

        if (OpenCVLoader.initLocal()) {
            Log.i(TAG, "OpenCV loaded successfully");
        } else {
            Log.e(TAG, "OpenCV initialization failed!");
            (Toast.makeText(this, "OpenCV initialization failed!", Toast.LENGTH_LONG)).show();
            return;
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_scan_color);

         SeekBar firstbar = findViewById(R.id.firstbar);
         SeekBar secondbar = findViewById(R.id.secondbar);


        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.tutorial1_activity_java_surface_view);

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

        mOpenCvCameraView.setCvCameraViewListener(this);

        preview = new ImageView[]{
                leftupcorner = findViewById(R.id.leftupcorner),
                upedge = findViewById(R.id.upedge),
                rightupcorner = findViewById(R.id.rightupcorner),
                rightedge = findViewById(R.id.rightedge),
                center = findViewById(R.id.center),
                leftedge = findViewById(R.id.leftedge),
                leftdowncorner = findViewById(R.id.leftdowncorner),
                downedge = findViewById(R.id.downedge),
                rightdowncorner = findViewById(R.id.rightdowncorner)
        };
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.enableView();
    }

    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(mOpenCvCameraView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
    }

    @Override
    public void onCameraViewStopped() {
    }

    @Override
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        Mat mRgba = inputFrame.rgba();
        Scalar[] scalars = new Scalar[9];

        scalars[0] = new Scalar(255, 255, 255);
        scalars[1] = new Scalar(0, 255, 255);
        scalars[2] = new Scalar(255, 0, 255);
        scalars[3] = new Scalar(255, 255, 0);
        scalars[4] = new Scalar(0, 0, 255);
        scalars[5] = new Scalar(255, 0, 0);
        scalars[6] = new Scalar(0, 255, 0);
        scalars[7] = new Scalar(125, 125, 255);
        scalars[8] = new Scalar(255, 125, 125);

        Rect[] rois = DisplayGrid(mRgba);

        for (int i = 0; i < 9; i++) {
            Mat ROI = new Mat(mRgba, rois[i]);
            Mat gray = new Mat();
            Imgproc.cvtColor(ROI, gray, Imgproc.COLOR_BGR2GRAY);

            Mat dst = new Mat();
            Mat lines = new Mat();
            Mat cdst = new Mat();
            Imgproc.Canny(gray, dst, 10, 10, 3, false);
            Imgproc.HoughLines(dst, lines, 1, Math.PI / 180, 120);
            Imgproc.cvtColor(dst, cdst, Imgproc.COLOR_GRAY2BGR);

            ProcessingPreview(i, ROI, lines);


            // Отрисовка линий
            for(int x = 0; x < lines.rows();x++){
                double rho = lines.get(x, 0)[0],
                        theta = lines.get(x, 0)[1];
                double a = Math.cos(theta), b = Math.sin(theta);
                double x0 = a * rho, y0 = b * rho;
                Point pt1 = new Point(rois[i].x + Math.round(x0 + 1000 * (-b)), rois[i].y + Math.round(y0 + 1000 * (a)));
                Point pt2 = new Point(rois[i].x + Math.round(x0 - 1000 * (-b)), rois[i].y + Math.round(y0 - 1000 * (a)));
                Imgproc.line(mRgba, pt1, pt2, scalars[i], 2, Imgproc.LINE_AA, 0);
                break;
            }
        }
        return mRgba;
    }


    public Rect[] DisplayGrid(Mat view){
        Rect[] rois = new Rect[9];
        int w = view.width();
        int h = view.height();
        double min = (double) Math.min(w, h);
        double step = min * 0.15;
        Point stepCenter = new Point(w * 0.5 - step * 0.5 - step, h * 0.5 - step * 0.5);
        int index = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = 1; j >= -1; j--) {
                Point pt1 = new Point(stepCenter.x + step * i, stepCenter.y + step * j);
                Point pt2 = new Point(stepCenter.x + step + step * i, stepCenter.y + step + step * j);
                Imgproc.rectangle(view,
                        pt1,
                        pt2,
                        Scalar.all(255.0));
                //Imgproc.putText(mRgba, String.valueOf(index), pt1, 4, 2, scalars[index], 4);
                pt1.x += 1; pt1.y += 1;
                pt2.x -= 1; pt2.y -= 1;
                rois[index] = new Rect(pt1, pt2);
                index++;
            }
        }
        return rois;
    }


    public void ProcessingPreview(int i, Mat ROI, Mat lines){
        /*
        * Функция вывода превью сканирования
        *
         */
        if(i % 2 == 0){
            if(i == 4){
                runOnUiThread(() -> preview[i].setImageResource(
                        GetCenter(Get2Color(ROI, lines).split("")))); //todo
                return;
            }
            if(lines.empty())
                runOnUiThread(() ->
                {
                    preview[i].setImageResource(R.drawable.one_color_corner);
                    DrawableCompat.setTint(preview[i].getDrawable(),
                            ContextCompat.getColor(getApplicationContext(), getColor(ROI)));
                });
            else runOnUiThread(() -> preview[i].setImageResource(
                    Get2ColorsCorner(Get2Color(ROI, lines).split(""))));
        }
        else{
            if(lines.empty())
                runOnUiThread(() ->
                {
                    preview[i].setImageResource(R.drawable.one_color_edge_lda);
                    DrawableCompat.setTint(preview[i].getDrawable(),
                            ContextCompat.getColor(getApplicationContext(), getColor(ROI)));
                });
            else runOnUiThread(() ->{
                preview[i].setImageResource(Get2ColorsEdge(Get2Color(ROI, lines).split("")));
            });
        }
    }


    public String Get2Color(Mat roi, Mat lines){
        return "WR";
    }

    public int GetCenter(String[] colors){
        /*
        @params colors: ["W", "O"] or ["W"]
         */
        List<String> listColors = Arrays.asList(colors);
        if(listColors.contains("W") && listColors.contains("R")){
            return R.drawable.wr_center;
        }
        else if(listColors.contains("W") && listColors.contains("G")){
            return R.drawable.wg_center;
        }
        else if(listColors.contains("R") && listColors.contains("G")){
            return R.drawable.rg_center;
        }
        else if(listColors.contains("B") && listColors.contains("O")){
            return R.drawable.bo_center;
        }
        else if(listColors.contains("B") && listColors.contains("Y")){
            return R.drawable.by_center;
        }
        else if(listColors.contains("Y") && listColors.contains("O")){
            return R.drawable.yo_center;
        }
        else return R.drawable.center;
    }


    public int Get2ColorsEdge(String[] colors){
        /*
        @params colors: ["W", "O"] or ["W"]
         */
        List<String> listColors = Arrays.asList(colors);
        if(listColors.contains("W") && listColors.contains("O")){
            return R.drawable.wo_edge_lda;
        }
        else if(listColors.contains("W") && listColors.contains("B")){
            return R.drawable.wb_edge_lda;
        }
        else if(listColors.contains("B") && listColors.contains("R")){
            return R.drawable.br_edge_lda;
        }
        else if(listColors.contains("Y") && listColors.contains("R")){
            return R.drawable.yr_edge_lda;
        }
        else if(listColors.contains("Y") && listColors.contains("G")){
            return R.drawable.yg_edge_lda;
        }
        else if(listColors.contains("G") && listColors.contains("O")){
            return R.drawable.go_edge_lda;
        }
        else return R.drawable.two_color_edge_lda;
    }


    public int Get2ColorsCorner(String[] colors){
        List<String> listColors = Arrays.asList(colors);
        if(listColors.contains("R") && listColors.contains("W")){
            return R.drawable.rw_corner;
        }
        else if(listColors.contains("B") && listColors.contains("O")){
            return R.drawable.bo_corner;
        }
        else if(listColors.contains("G") && listColors.contains("R")){
            return R.drawable.gr_corner;
        }
        else if(listColors.contains("Y") && listColors.contains("O")){
            return R.drawable.yo_corner;
        }
        else if(listColors.contains("G") && listColors.contains("W")){
            return R.drawable.gw_corner;
        }
        else if(listColors.contains("Y") && listColors.contains("B")){
            return R.drawable.yb_corner;
        }
        else return R.drawable.three_color_corner;
    }

    public int getColor(Mat roi) {
        Scalar color = Core.mean(roi);
        if (color.val[0] >= 100 && color.val[1] < 100 && color.val[2] < 100) {
            return R.color.red;
        } else if (color.val[0] < 100 && color.val[1] >= 100 && color.val[2] < 100) {
            return R.color.green;
        } else if (color.val[0] < 100 && color.val[1] < 100 && color.val[2] >= 50) {
            return R.color.blue;
        } else if (color.val[0] >= 150 && color.val[1] >= 170 && color.val[2] < 100) {
            return R.color.yellow;
        } else if (color.val[0] >= 150 && color.val[1] >= 80 && color.val[1]<=200  && color.val[2] < 100) {
            return R.color.orange;
        } else if (color.val[0] > 150 && color.val[1] > 150 && color.val[2] > 150) {
            return R.color.white;
        }
        return R.color.white;
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