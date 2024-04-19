package com.example.intcube;


import android.content.Intent;

import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import android.os.Bundle;

import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;

import android.widget.Toast;


import androidx.core.content.res.ResourcesCompat;

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
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ScanColorActivity extends CameraActivity implements CvCameraViewListener2 {
    private static final String TAG = "OCVSample::Activity";

    private CameraBridgeViewBase mOpenCvCameraView;

    private SeekBar threshold1, threshold2;

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

        threshold1 = findViewById(R.id.firstbar);
        threshold2 = findViewById(R.id.secondbar);
        ImageView leftupcorner = findViewById(R.id.leftupcorner);
        ImageView upedge = findViewById(R.id.upedge);
        ImageView rightupcorner = findViewById(R.id.rightupcorner);
        ImageView leftedge = findViewById(R.id.leftedge);
        ImageView center = findViewById(R.id.center);
        ImageView rightedge = findViewById(R.id.rightedge);
        ImageView leftdowncorner = findViewById(R.id.leftdowncorner);
        ImageView downedge = findViewById(R.id.downedge);
        ImageView rightdownedge = findViewById(R.id.rightdowncorner);

        ImageView[][] preview = {
            {leftupcorner, upedge, rightupcorner},
            {rightedge, center, leftedge},
            {leftdowncorner, downedge, rightdownedge}
        };

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.tutorial1_activity_java_surface_view);

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

        mOpenCvCameraView.setCvCameraViewListener(this);
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

        int w = mRgba.width();
        int h = mRgba.height();
        double min = (double)Math.min(w, h);
        double step = min * 0.15;
        Point stepCenter = new Point(w * 0.5 - step * 0.5 - step, h * 0.5 - step * 0.5);
        Rect[] rois = new Rect[9];
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

        int index = 0;
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
            {
                Point pt1 = new Point(stepCenter.x + step * i, stepCenter.y + step * j);
                Point pt2 = new Point(stepCenter.x + step + step * i, stepCenter.y + step + step * j);
                Imgproc.rectangle(mRgba,
                        pt1,
                        pt2,
                        Scalar.all(255.0));
                Imgproc.putText(mRgba, String.valueOf(index), pt1, 4, 2, scalars[index], 4);
                rois[index] = new Rect(pt1, pt2);
                index++;
            }

        Mat dsIMG = new Mat();
        Mat usIMG = new Mat();
        for (int i = 0; i < 9; i++) {
            Rect roi = rois[i];
            Mat ROI = new Mat(mRgba, roi);
            Mat gray = new Mat();
            Imgproc.cvtColor(ROI, gray, Imgproc.COLOR_BGR2GRAY);
            Imgproc.pyrDown(gray, dsIMG, new Size((double) gray.cols() / 2, (double) gray.rows() / 2));
            Imgproc.pyrUp(dsIMG, usIMG, gray.size());

            Mat dst = new Mat();
            Mat lines = new Mat();
            Mat cdst = new Mat();
            Imgproc.Canny(usIMG, dst, threshold1.getProgress(), threshold2.getProgress());
            Imgproc.HoughLines(dst, lines, 1, Math.PI / 180, 150);
            Imgproc.cvtColor(dst, cdst, Imgproc.COLOR_GRAY2BGR);


            Scalar color = Core.mean(ROI);

            for (int x = 0; x < lines.rows(); x++) {
                double rho = lines.get(x, 0)[0],
                        theta = lines.get(x, 0)[1];
                double a = Math.cos(theta), b = Math.sin(theta);
                double x0 = a * rho, y0 = b * rho;
                Point pt1 = new Point(roi.x + Math.round(x0 + 1000 * (-b)), roi.y + Math.round(y0 + 1000 * (a)));
                Point pt2 = new Point(roi.x + Math.round(x0 - 1000 * (-b)), roi.y + Math.round(y0 - 1000 * (a)));
                //Imgproc.line(mRgba, pt1, pt2, scalars[i], 2, Imgproc.LINE_AA, 0);
            }
        }

//        Mat gray = new Mat();
//        Mat dst = new Mat();
//        Imgproc.cvtColor(mRgba, gray, Imgproc.COLOR_BGR2GRAY);
//        Imgproc.Canny(gray, dst, threshold1.getProgress(), threshold2.getProgress());
        return mRgba;
    }


    public Drawable GetCenter(String[] colors){
        /*
        @params colors: ["W", "O"] or ["W"]
         */
        List<String> listColors = Arrays.asList(colors);
        if(listColors.contains("W") && listColors.contains("R")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.wr_center, null);
        }
        else if(listColors.contains("W") && listColors.contains("G")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.wg_center, null);
        }
        else if(listColors.contains("G") && listColors.contains("R")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.rg_center, null);
        }
        else if(listColors.contains("B") && listColors.contains("O")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.bo_center, null);
        }
        else if(listColors.contains("B") && listColors.contains("Y")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.by_center, null);
        }
        else if(listColors.contains("Y") && listColors.contains("O")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.yo_center, null);
        }
        else return ResourcesCompat.getDrawable(getResources(), R.drawable.center, null);
    }

    public Drawable GetEdge(String colors){
        /*
        @params colors: "WO" or "W"
         */
        if (colors.split(" ").length == 2){
            return Get2ColorsEdge(colors.split(" "));
        }
        else{
            return Get1ColorEdge(colors);
        }
    }

    public Drawable GetCorner(String colors){
        /*
        @params colors: "WO" or "W"
         */
        if (colors.split(" ").length == 2){
            return Get2ColorCorner(colors.split(" "));
        }
        else{
            return Get1ColorCorner(colors);
        }
    }


    public Drawable Get2ColorsEdge(String[] colors){
        /*
        @params colors: ["W", "O"] or ["W"]
         */
        List<String> listColors = Arrays.asList(colors);
        if(listColors.contains("W") && listColors.contains("O")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.wo_edge_lda, null);
        }
        else if(listColors.contains("W") && listColors.contains("B")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.wb_edge_lda, null);
        }
        else if(listColors.contains("B") && listColors.contains("R")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.br_edge_lda, null);
        }
        else if(listColors.contains("Y") && listColors.contains("R")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.yr_edge_lda, null);
        }
        else if(listColors.contains("Y") && listColors.contains("G")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.yg_edge_lda, null);
        }
        else if(listColors.contains("G") && listColors.contains("O")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.go_edge_lda, null);
        }
        else return ResourcesCompat.getDrawable(getResources(), R.drawable.two_color_edge_lda, null);
    }

    public Drawable Get1ColorEdge(String color){
        /*
        @params colors: "W"
         */
        Drawable edge = ResourcesCompat.getDrawable(getResources(),
                R.drawable.one_color_edge_lda,null);
        if(edge != null) {
            if (color.equals("W")) edge.setColorFilter(getColor(R.color.white),
                    PorterDuff.Mode.MULTIPLY);
            if (color.equals("B")) edge.setColorFilter(getColor(R.color.blue),
                    PorterDuff.Mode.MULTIPLY);
            if (color.equals("O")) edge.setColorFilter(getColor(R.color.orange),
                    PorterDuff.Mode.MULTIPLY);
            if (color.equals("Y")) edge.setColorFilter(getColor(R.color.yellow),
                    PorterDuff.Mode.MULTIPLY);
            if (color.equals("R")) edge.setColorFilter(getColor(R.color.red),
                    PorterDuff.Mode.MULTIPLY);
            if (color.equals("G")) edge.setColorFilter(getColor(R.color.green),
                    PorterDuff.Mode.MULTIPLY);
        }
        return edge;
    }

    public Drawable Get1ColorCorner(String color){
        /*
        @params colors: "W"
         */
        Drawable corner = ResourcesCompat.getDrawable(getResources(),
                R.drawable.one_color_corner,null);
        if (corner != null){
        if(color.equals("W")) corner.setColorFilter(getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        if(color.equals("B")) corner.setColorFilter(getColor(R.color.blue), PorterDuff.Mode.MULTIPLY);
        if(color.equals("O")) corner.setColorFilter(getColor(R.color.orange), PorterDuff.Mode.MULTIPLY);
        if(color.equals("Y")) corner.setColorFilter(getColor(R.color.yellow), PorterDuff.Mode.MULTIPLY);
        if(color.equals("R")) corner.setColorFilter(getColor(R.color.red), PorterDuff.Mode.MULTIPLY);
        if(color.equals("G")) corner.setColorFilter(getColor(R.color.green), PorterDuff.Mode.MULTIPLY);
        }
        return corner;
    }

    public Drawable Get2ColorCorner(String[] colors){
        List<String> listColors = Arrays.asList(colors);
        if(listColors.contains("R") && listColors.contains("W")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.rw_corner, null);
        }
        else if(listColors.contains("B") && listColors.contains("O")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.bo_corner, null);
        }
        else if(listColors.contains("G") && listColors.contains("R")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.gr_corner, null);
        }
        else if(listColors.contains("Y") && listColors.contains("O")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.yo_corner, null);
        }
        else if(listColors.contains("G") && listColors.contains("W")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.gw_corner, null);
        }
        else if(listColors.contains("Y") && listColors.contains("B")){
            return ResourcesCompat.getDrawable(getResources(), R.drawable.yb_corner, null);
        }
        else return ResourcesCompat.getDrawable(getResources(), R.drawable.three_color_corner, null);
    }

    public String getColor(Scalar color) {
        String tempString;

        if (color.val[0] >= 100 && color.val[1] < 100 && color.val[2] < 100) {
            tempString = "R";
        } else if (color.val[0] < 100 && color.val[1] >= 100 && color.val[2] < 100) {
            tempString = "G";
        } else if (color.val[0] < 100 && color.val[1] < 100 && color.val[2] >= 50) {
            tempString = "B";
        } else if (color.val[0] >= 150 && color.val[1] >= 170 && color.val[2] < 100) {
            tempString = "Y";
        } else if (color.val[0] >= 150 && color.val[1] >= 80 && color.val[1]<=200  && color.val[2] < 100) {
            tempString = "O";
        } else if (color.val[0] > 150 && color.val[1] > 150 && color.val[2] > 150) {
            tempString = "W";
        } else {
            tempString = "F";
        }
        return tempString;
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