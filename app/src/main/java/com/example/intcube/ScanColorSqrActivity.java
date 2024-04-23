package com.example.intcube;

import static org.opencv.android.NativeCameraView.TAG;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class ScanColorSqrActivity extends CameraActivity implements CvCameraViewListener2 {

    private static final int CAMERA_PERMISSION_CODE = 100;

    private CameraBridgeViewBase mOpenCvCameraView;

    private Mat bwIMG;
    private Mat dsIMG;
    private Mat usIMG;
    private Mat cIMG;
    private Mat hovIMG;
    private MatOfPoint2f approxCurve;
    private ArrayList<Mat> previewMats;
    private int previewMatIndex;
    private SeekBar threshold1, threshold2, epsilon, minArea;

    private Scalar[] referenceColors;
    private boolean resultDialog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_color_sqr);

        if (OpenCVLoader.initLocal()) {
            Log.i(TAG, "OpenCV loaded successfully");
        } else {
            Log.e(TAG, "OpenCV initialization failed!");
            (Toast.makeText(this, "OpenCV initialization failed!", Toast.LENGTH_LONG)).show();
        }

        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.typeCamera);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        findViewById(R.id.matButton).setOnClickListener(v -> {
            previewMatIndex = (previewMatIndex + 1) % (previewMats.size() + 1);
        });

        threshold1 = findViewById(R.id.secondbar);
        threshold2 = findViewById(R.id.firstbar);
        epsilon = findViewById(R.id.seekBar9);
        minArea = findViewById(R.id.seekBar10);

        threshold1.setMax(250);
        threshold1.setProgress(60);

        threshold2.setMax(250);
        threshold2.setProgress(40);

        epsilon.setMax(500);
        epsilon.setProgress(250);

        minArea.setMax(1000);
        minArea.setProgress(500);

        bwIMG = new Mat();
        dsIMG = new Mat();
        usIMG = new Mat();
        cIMG = new Mat();
        hovIMG = new Mat();

        previewMats = new ArrayList<Mat>();
        previewMats.add(bwIMG);

        approxCurve = new MatOfPoint2f();

        referenceColors = new Scalar[6];

        referenceColors[0] = new Scalar(255, 255, 255); // Белый
        referenceColors[1] = new Scalar(255, 255, 60); // Желтый

        referenceColors[2] = new Scalar(255, 0, 0); // Красный
        referenceColors[3] = new Scalar(255, 125, 0); // Оранжевый

        referenceColors[4] = new Scalar(0, 0, 200); // Синий
        referenceColors[5] = new Scalar(0, 255, 0); // Зеленый
    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(ScanColorSqrActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(ScanColorSqrActivity.this, new String[] { permission }, requestCode);
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
                Toast.makeText(ScanColorSqrActivity.this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ScanColorSqrActivity.this, "Для сканирования необходимо разрешение для использования камеры", Toast.LENGTH_SHORT).show();
            }
        }
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
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        Mat gray = inputFrame.gray();
        Mat dst = inputFrame.rgba();

        int w = dst.width();
        int h = dst.height();
        double min = (double) Math.min(w, h);
        double step = min * 0.15;
        Point stepCenter = new Point(w * 0.5 - step * 0.5 - step, h * 0.5 - step * 0.5);
        Rect[] rois = new Rect[9];

        int index = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = 1; j >= -1; j--) {
                Point pt1 = new Point(stepCenter.x + step * i, stepCenter.y + step * j);
                Point pt2 = new Point(stepCenter.x + step + step * i, stepCenter.y + step + step * j);
                Imgproc.rectangle(dst,
                        pt1,
                        pt2,
                        Scalar.all(255.0));
                rois[index] = new Rect(pt1, pt2);
                index++;
            }
        }

        Imgproc.pyrDown(gray, dsIMG, new Size((double) gray.cols() / 2, (double) gray.rows() / 2));
        Imgproc.pyrUp(dsIMG, usIMG, gray.size());

        // Неизвестные мне параметры, 1-й и 2-й сверху вниз ползунки
        Imgproc.Canny(usIMG, bwIMG, threshold1.getProgress(), threshold2.getProgress());

        Imgproc.dilate(bwIMG, bwIMG, new Mat(), new Point(-1, 1), 1);

        List<MatOfPoint> contours = new ArrayList<>();

        cIMG = bwIMG.clone();

        // RECT_TREE находит вложенные контуры
        Imgproc.findContours(cIMG, contours, hovIMG, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        List<MatOfPoint> squares = new ArrayList<>();
        List<MatOfPoint> tris = new ArrayList<>();

        for (MatOfPoint cnt : contours) {

            MatOfPoint2f curve = new MatOfPoint2f(cnt.toArray());

            double length = Imgproc.arcLength(curve, true);

            // Неизвестный мне параметр, 3-й сверху вниз ползунок
            Imgproc.approxPolyDP(curve, approxCurve, 0.1 * (double)epsilon.getProgress() / 255.0 * length, true);

            int numberVertices = (int) approxCurve.total();

            double contourArea = Imgproc.contourArea(cnt);

            // Минимальная площадь контура, 4-й ползунок, можно также добавить максимальную площадь
            if (Math.abs(contourArea) < minArea.getProgress()) {
                continue;
            }

            // Обрезаем квадратик чтобы при повороте квадратика Rect не выходил за рамки и не цеплял другие цвета
            Rect r = Imgproc.boundingRect(cnt);
            r.x += r.width / 4;
            r.y += r.height / 4;
            r.width /= 2;
            r.height /= 2;
            Mat mask = new Mat(dst, r);
            Scalar mean = Core.mean(mask);

            if (numberVertices == 4) {
                // Как я понял автор проверяет насколько 4-х угольник является квадратом
                List<Double> cos = new ArrayList<>();

                for (int j = 2; j < numberVertices + 1; j++) {
                    cos.add(angle(approxCurve.toArray()[j % numberVertices], approxCurve.toArray()[j - 2], approxCurve.toArray()[j - 1]));
                }

                Collections.sort(cos);

                double mincos = cos.get(0);
                double maxcos = cos.get(cos.size() - 1);


                if (checkDistance(squares, cnt, 50)
                        && checkDistance(squares, cnt, 50)) {
                    setLabel(dst, r, mean, "Z");
                    squares.add(cnt);
                }
            }

            // Треугольники
            if (numberVertices == 3) {
                setLabel(dst, r, mean, "Y");
                tris.add(cnt);
            }
        }

        int[] colors = new int[9];

        for (int i = 0; i < 9; i++)
            colors[i] = -1;

        for (int i = 0; i < 9; i++)
        {
            Rect roi = rois[i];

            for (MatOfPoint sqr : squares) {

                Rect br = Imgproc.boundingRect(sqr);
                Point center = new Point(br.x +  (double) br.width / 2, br.y + (double) br.height / 2);

                if (roi.contains(center)) {
                    Rect r = br.clone();
                    r.x += r.width / 4;
                    r.y += r.height / 4;
                    r.width /= 2;
                    r.height /= 2;
                    Mat mask = new Mat(dst, r);
                    Scalar mean = Core.mean(mask);

                    double maxDiff = 999;
                    int bestColor = -1;
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

                    if (bestColor != -1) {
                        colors[i] = bestColor;
                    }

                    break;
                }
            }

            if (colors[i] == -1)
                break;

            Scalar color = referenceColors[colors[i]];
            Imgproc.putText(dst, String.valueOf(i), roi.tl(), 4, 2, color, 4);
        }

        boolean allFound = true;
        for (int color : colors)
            if (color == -1)
            {
                allFound = false;
                break;
            }

        if (allFound && !resultDialog) {
            resultDialog = true;
            final int[] colorsCopy = Arrays.copyOf(colors, 9);
            runOnUiThread(() -> showResultDialog(colorsCopy));
        }

        if (previewMatIndex == 0)
            return dst;
        else
            return previewMats.get(previewMatIndex - 1);
    }

    private void showResultDialog(int[] colors) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Результат");

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < 9; i++) {
            if (colors[i] == 0)
                result.append('Б');
            if (colors[i] == 1)
                result.append('Ж');
            if (colors[i] == 2)
                result.append('К');
            if (colors[i] == 3)
                result.append('О');
            if (colors[i] == 4)
                result.append('С');
            if (colors[i] == 5)
                result.append('З');

            if (i == 2 || i == 5)
                result.append('\n');
        }

        builder.setMessage(result.toString());

        builder.setPositiveButton("Следующая сторона", (dialog, id) -> resultDialog = false);

        builder.setNeutralButton("Ручной ввод", (dialog, id) -> resultDialog = false);

        builder.setNegativeButton("Повторить попытку", (dialog, id) -> {
            resultDialog = false;
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    // Проверка на дистанцию между контурами
    private boolean checkDistance(List<MatOfPoint> cnts, MatOfPoint cnt, double minDistance) {
        Rect rect = Imgproc.boundingRect(cnt);
        for (MatOfPoint otherCnt : cnts) {
            Rect otherRect = Imgproc.boundingRect(otherCnt);
            int diffX = rect.x + rect.width / 2 - otherRect.x - otherRect.width / 2;
            int diffY = rect.y + rect.height / 2 - otherRect.y - otherRect.height / 2;
            if (diffX * diffX + diffY * diffY < minDistance * minDistance) {
                return false;
            }
        }
        return true;
    }

    private static double angle(Point pt1, Point pt2, Point pt0) {
        double dx1 = pt1.x - pt0.x;
        double dy1 = pt1.y - pt0.y;
        double dx2 = pt2.x - pt0.x;
        double dy2 = pt2.y - pt0.y;
        return (dx1 * dx2 + dy1 * dy2) / Math.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2) + 1e-10);
    }

    private void setLabel(Mat im, Rect r, Scalar color, String label) {
        int fontface = 4;
        double scale = 3;//0.4;
        int thickness = 3;//1;
        int[] baseline = new int[1];
        Size text = Imgproc.getTextSize(label, fontface, scale, thickness, baseline);
        Point pt = new Point(r.x + ((r.width - text.width) / 2),r.y + ((r.height + text.height) / 2));
        Imgproc.putText(im, label, pt, fontface, scale, Scalar.all(0), 8);
        Imgproc.putText(im, label, pt, fontface, scale, color, thickness);
    }

    public void BackActivityMain(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void startActivityScanColors(View v){
        Intent intent = new Intent(this, ScanColorActivity.class);
        startActivity(intent);
    }
}