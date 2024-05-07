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


class RetValue{
    private boolean isCorrectLine;
    private Point pt1;
    private Point pt2;

    public boolean IsCorrect(){
        return isCorrectLine;
    }

    public Point[] GetPoints(){
        return new Point[]{pt1, pt2};
    }

    public RetValue(boolean result, Point firstPoint, Point secondPoint){
        isCorrectLine = result;
        pt1 = firstPoint;
        pt2 = secondPoint;
    }

    public RetValue(boolean result){
        isCorrectLine = result;
        pt1 = new Point(0,0);
        pt2 = new Point(0,0);
    }
}

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

    private Scalar[] referenceColors;

    private int[] colors;
    Rect[] rois;

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


        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.CameraView);

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

        referenceColors = new Scalar[6];

        referenceColors[0] = new Scalar(255, 255, 255); // Белый
        referenceColors[1] = new Scalar(255, 255, 60); // Желтый

        referenceColors[2] = new Scalar(255, 0, 0); // Красный
        referenceColors[3] = new Scalar(255, 125, 0); // Оранжевый

        referenceColors[4] = new Scalar(0, 0, 200); // Синий
        referenceColors[5] = new Scalar(0, 255, 0); // Зеленый

        colors = new int[6];

        colors[0] = R.color.white;
        colors[1] = R.color.yellow;
        colors[2] = R.color.red;
        colors[3] = R.color.orange;
        colors[4] = R.color.blue;
        colors[5] = R.color.green;


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

        rois = DisplayGrid(mRgba, scalars);

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

            //Отрисовка линий
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

            ProcessingPreview(i, ROI, lines);
        }
        return mRgba;
    }


    public Rect[] DisplayGrid(Mat view, Scalar[] scalars){
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
                Imgproc.putText(view, String.valueOf(index), pt1, 4, 2, scalars[index], 4);
                pt1.x += 1; pt1.y += 1;
                pt2.x -= 1; pt2.y -= 1;
                rois[index] = new Rect(pt1, pt2);
                index++;
            }
        }
//        Определение pt1 в квадратиках
        for (int i = 0; i < 9; i++) {
            if (i == 0){
                Point pt = new Point(rois[i].tl().x + rois[i].width, rois[i].tl().y);
                Imgproc.circle(view, pt, 5, new Scalar(0, 255, 255),4);
            }
            if (i == 2){
                Point pt = new Point(rois[i].tl().x + rois[i].width, rois[i].tl().y + rois[i].width);
                Imgproc.circle(view, pt, 5, new Scalar(0, 255, 255),4);
            }
            if (i == 6){
                Point pt = new Point(rois[i].tl().x, rois[i].tl().y);
                Imgproc.circle(view, pt, 5, new Scalar(0, 255, 255),4);
            }
            if (i == 8){
                Point pt = new Point(rois[i].tl().x, rois[i].tl().y + rois[i].width);
                Imgproc.circle(view, pt, 5, new Scalar(0, 255, 255),4);
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
                RetValue ret = IsCorrectline(ROI, lines, i);
                runOnUiThread(() -> preview[i].setImageResource(
                        GetCenter(Get2Color(ROI, ret.GetPoints(), i))));
                return;
            }
            if(lines.empty())
                runOnUiThread(() ->
                {
                    preview[i].setImageResource(R.drawable.one_color_corner);
                    DrawableCompat.setTint(preview[i].getDrawable(),
                            ContextCompat.getColor(getApplicationContext(), getColor(ROI)));
                });
            else {
                RetValue ret = IsCorrectline(ROI, lines, i);
                if (ret.IsCorrect()) runOnUiThread(() -> preview[i].setImageResource(
                        Get2ColorsCorner(Get2Color(ROI, ret.GetPoints(), i))));
            }
        }
        else{
            if(lines.empty())
                runOnUiThread(() ->
                {
                    preview[i].setImageResource(R.drawable.one_color_edge_lda);
                    DrawableCompat.setTint(preview[i].getDrawable(),
                            ContextCompat.getColor(getApplicationContext(), getColor(ROI)));
                });
            else {
                RetValue ret = IsCorrectline(ROI, lines, i);
                if (ret.IsCorrect())
                    runOnUiThread(() -> {
                    preview[i].setImageResource(Get2ColorsEdge(Get2Color(ROI, ret.GetPoints(), i)));
                });
            }
        }
    }


    public RetValue IsCorrectline(Mat roi, Mat lines, int i){
        Mat dst;
        double minDistance = 400;
        double distance1;
        double distance2;
        boolean flag = false;
        for(int x = 0; x < lines.rows(); x++){
            double rho = lines.get(x, 0)[0],
                    theta = lines.get(x, 0)[1];
            double a = Math.cos(theta), b = Math.sin(theta);
            double x0 = a * rho, y0 = b * rho;
            Point pt1 = new Point(rois[i].x + Math.round(x0 + 1000 * (-b)), rois[i].y + Math.round(y0 + 1000 * (a)));
            Point pt2 = new Point(rois[i].x + Math.round(x0 - 1000 * (-b)), rois[i].y + Math.round(y0 - 1000 * (a)));
            double k = (pt2.y - pt1.y) / (pt2.x - pt1.x);
            switch (i) {
                case 0:
                case 8: {
                    if(Double.compare(k,-1) == 0) flag = true;
                    break;
                }
                case 2:
                case 6: {
                    if(Double.compare(k,1) == 0) flag = true;
                    break;
                }
                case 1:
                case 7:
                case 3:
                case 5:
                    flag = true;

            }
            if (!flag){
                return new RetValue(false);
            }
            else{
                return new RetValue(true, pt1, pt2);
            }
        }
        return new RetValue(false);
    }

    public String[] Get2Color(Mat roi, Point[] pts, int i){
        Point pt1 = pts[0];
        Point pt2 = pts[1];
        if(pt1.x == pt2.x & pt1.y == pt2.y) return new String[]{"W", "B"};
        Point midpoint = GetMidPointInRoiByLine(i, pt1, pt2);
        Point pt2subroi1 = new Point(0,
                0);
        Point pt2subroi2 = new Point(rois[i].width - 1,
                rois[i].height - 1);

        midpoint.x -= rois[i].x;
        midpoint.y -= rois[i].y;

        if(midpoint.y > rois[i].y && midpoint.y < rois[i].y + rois[i].height &&
                midpoint.x > rois[i].x && midpoint.x < rois[i].x + rois[i].width) {
            Rect rect1 = new Rect(midpoint, pt2subroi1);
            Rect rect2 = new Rect(midpoint, pt2subroi2);
            Mat subroi = roi.clone();
            Mat subroi1 = new Mat(roi, rect1);
            Mat subroi2 = new Mat(roi, rect2);

            return new String[]{ColorInterface(getColor(subroi1)), ColorInterface(getColor(subroi2))};
        }
        else
        {
            return new String[]{"W", "Y"};
        }
    }

    public Point GetMidPointInRoiByLine(int i, Point pt1, Point pt2){
        double k = (pt2.y - pt1.y) / (pt2.x - pt1.x);
        double b = pt1.y - k*pt1.x;
        double x = rois[i].tl().x + (double) rois[i].width / 2;
        double y = k*x + b;
        return new Point(x, y);
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
        Scalar mean = Core.mean(roi);
        double maxDiff = 999;
        int bestColor = 0;
        for (int j = 0; j < 6; j++) {
            Scalar color = referenceColors[j];
            double diff =
                    Math.abs(color.val[0] - mean.val[0])
                            + Math.abs(color.val[1] - mean.val[1])
                            + Math.abs(color.val[2] - mean.val[2]);
            if (diff < maxDiff) {
                maxDiff = diff;
                bestColor = j;
            }
        }
        return colors[bestColor];
    }

    public String ColorInterface(int Rvalue){
        switch (Rvalue)
        {
            case R.color.red:
                return "R";
            case R.color.green:
                return "G";
            case R.color.blue:
                return "B";
            case R.color.orange:
                return "O";
            case R.color.yellow:
                return "Y";
            case R.color.white:
                return "W";
        }
        return "W";
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