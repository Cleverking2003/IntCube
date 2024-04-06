package com.example.intcube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SolutionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);
    }

    /**
     * Функция решения кубика
     * Будет получать тип кубика и массив с отсканированными цветами
     * Будет возвращать строку для решения
     */
    public static void solve() {
        Cube2x2 cube2x2 = new Cube2x2();
        String movesFinishBottomLayer = cube2x2.finishBottomLayer();
        String movesPutTopCorners = cube2x2.putTopCorners();
        String movesFinishTopLayer = cube2x2.finishTopLayer();
        Cube3x3 cube3x3 = new Cube3x3();
        String movesSunflower = cube3x3.makeSunflower();
        String movesWhiteCross = cube3x3.makeWhiteCross();
        String movesFinishWhiteLayer = cube3x3.finishWhiteLayer();
        String movesInsertAllEdges = cube3x3.insertAllEdges();
        String movesMakeYellowCross = cube3x3.makeYellowCross();
        String movesOrientLastLayer = cube3x3.orientLastLayer();
        String movesPermuteLastLayer = cube3x3.permuteLastLayer();
    }
}

