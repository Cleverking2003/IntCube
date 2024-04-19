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
        String scramble = "R ";
        Cube2x2 cube2x2 = new Cube2x2();
        cube2x2.performMoves(scramble);
        boolean solved = cube2x2.checkCubeIsSolved();
        if (solved) {
            System.out.println("Кубик уже собран");
        }
        String movesFinishBottomLayer = cube2x2.finishBottomLayer();
        if (movesFinishBottomLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            System.out.println("Wrong cube");
        }
        String movesPutTopCorners = cube2x2.putTopCorners();
        if (movesPutTopCorners.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            System.out.println("Wrong cube");
        }
        String movesFinishTopLayer = cube2x2.finishTopLayer();
        if (movesFinishTopLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            System.out.println("Wrong cube");
        }
        solved = cube2x2.checkCubeIsSolved();
        if (!(solved)) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            System.out.println("Wrong cube");
        }
        Cube3x3 cube3x3 = new Cube3x3();
        cube3x3.performMoves(scramble);
        solved = cube3x3.checkCubeIsSolved();
        if (solved) {
            System.out.println("Кубик уже собран");
        }
        String movesSunflower = cube3x3.makeSunflower();
        if (movesSunflower.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            System.out.println("Wrong cube");
        }
        String movesWhiteCross = cube3x3.makeWhiteCross();
        if (movesWhiteCross.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            System.out.println("Wrong cube");
        }
        String movesFinishWhiteLayer = cube3x3.finishWhiteLayer();
        if (movesFinishWhiteLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            System.out.println("Wrong cube");
        }
        String movesInsertAllEdges = cube3x3.insertAllEdges();
        if (movesInsertAllEdges.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            System.out.println("Wrong cube");
        }
        String movesMakeYellowCross = cube3x3.makeYellowCross();
        if (movesMakeYellowCross.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            System.out.println("Wrong cube");
        }
        String movesOrientLastLayer = cube3x3.orientLastLayer();
        if (movesOrientLastLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            System.out.println("Wrong cube");
        }
        String movesPermuteLastLayer = cube3x3.permuteLastLayer();
        if (movesPermuteLastLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            System.out.println("Wrong cube");
        }

        solved = cube3x3.checkCubeIsSolved();
        if (!(solved)) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            System.out.println("Wrong cube");
        }

        AxisCube axisCube = new AxisCube();
    }
}

