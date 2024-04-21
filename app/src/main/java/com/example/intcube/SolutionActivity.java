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
        String scramble = "R U L' D2 B R' U2 D' L2 R2 ";
        Cube2x2 cube2x2 = new Cube2x2();
        Cube3x3 cube3x3 = new Cube3x3();
        AxisCube axisCube = new AxisCube();
        cube2x2.performMoves(scramble);
        cube3x3.performMoves(scramble);
        axisCube.performMoves(scramble);
        String solution2x2 = solve2x2Cube(cube2x2);
        String solution3x3 = solve3x3Cube(cube3x3);
        String solutionAxisCube = solveAxisCube(axisCube);

    }

    public static String solve2x2Cube(Cube2x2 cube) {
        String solution = "";
        boolean solved = cube.checkCubeIsSolved();
        if (solved) {
            return "Кубик уже собран";
        }
        String movesFinishBottomLayer = cube.finishBottomLayer();
        if (movesFinishBottomLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesFinishBottomLayer;
        String movesPutTopCorners = cube.putTopCorners();
        if (movesPutTopCorners.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesPutTopCorners;
        String movesFinishTopLayer = cube.finishTopLayer();
        if (movesFinishTopLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesFinishTopLayer;
        solved = cube.checkCubeIsSolved();
        if (!(solved)) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        return solution;
    }

    public static String solve3x3Cube(Cube3x3 cube) {
        String solution = "";
        boolean solved = cube.checkCubeIsSolved();
        if (solved) {
            return "Кубик уже собран";
        }
        String movesSunflower = cube.makeSunflower();
        if (movesSunflower.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesSunflower;
        String movesWhiteCross = cube.makeWhiteCross();
        if (movesWhiteCross.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesWhiteCross;
        String movesFinishWhiteLayer = cube.finishWhiteLayer();
        if (movesFinishWhiteLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesFinishWhiteLayer;
        String movesInsertAllEdges = cube.solveSecondLayer();
        if (movesInsertAllEdges.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesInsertAllEdges;
        String movesMakeYellowCross = cube.makeYellowCross();
        if (movesMakeYellowCross.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesMakeYellowCross;
        String movesOrientLastLayer = cube.orientYellowCross();
        if (movesOrientLastLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesOrientLastLayer;
        String movesPermuteLastLayer = cube.putYellowCorners();
        if (movesPermuteLastLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesPermuteLastLayer;
        solved = cube.checkCubeIsSolved();
        if (!(solved)) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        return solution;
    }

    public static String solveAxisCube(AxisCube cube) {
        String solution = "";
        boolean solved = cube.checkCubeIsSolved();
        if (solved) {
            return "Кубик уже собран";
        }
        String movesSunflower = cube.makeSunflower();
        if (movesSunflower.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesSunflower;
        String movesWhiteCross = cube.makeWhiteCross();
        if (movesWhiteCross.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesWhiteCross;
        String movesWhiteLayer = cube.finishWhiteLayer();
        if (movesWhiteLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesWhiteLayer;
        String movesOrientVertCenters = cube.orientVerticalCenters();
        if (movesOrientVertCenters.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesOrientVertCenters;
        String movesSecondLayer = cube.solveSecondLayer();
        if (movesSecondLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesSecondLayer;
        String movesYellowCross = cube.makeYellowCross();
        if (movesYellowCross.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesYellowCross;
        String movesOrientCross = cube.orientYellowCross();
        if (movesOrientCross.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesOrientCross;
        String movesOrientYellowCenter = cube.orientYellowCenter();
        if (movesOrientYellowCenter.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += movesOrientYellowCenter;
        String lastLayer = cube.putYellowCorners();
        if (lastLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        solution += lastLayer;
        solved = cube.checkCubeIsSolved();
        if (!(solved)) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            return "Wrong cube";
        }
        return solution;
    }
}

