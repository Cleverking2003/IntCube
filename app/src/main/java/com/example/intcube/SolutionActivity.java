package com.example.intcube;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class SolutionActivity extends AppCompatActivity {

    TextView stageText;
    TextView moveText;
    Button nextButton;
    Button prevButton;
    Button fullDescription;
    Button langOfTurns;
    static String solution;
    private int currentStep = 0;
    private String[] moves;
    int flagMoves = 0;
    HashMap<String, String> movesAlternative = new HashMap<String, String>(Map.ofEntries(
            new AbstractMap.SimpleEntry<String, String>("R", "Поверните правую грань вверх"),
            new AbstractMap.SimpleEntry<String, String>("R'", "Поверните правую грань вниз"),
            new AbstractMap.SimpleEntry<String, String>("L", "Поверните левую грань вниз"),
            new AbstractMap.SimpleEntry<String, String>("L'", "Поверните левую грань вверх"),
            new AbstractMap.SimpleEntry<String, String>("U", "Поверните верхнюю грань влево"),
            new AbstractMap.SimpleEntry<String, String>("U'", "Поверните верхнюю грань вправо"),
            new AbstractMap.SimpleEntry<String, String>("D", "Поверните нижнюю грань направо"),
            new AbstractMap.SimpleEntry<String, String>("D'", "Поверните нижнюю грань налево"),
            new AbstractMap.SimpleEntry<String, String>("F", "Поверните переднюю грань направо"),
            new AbstractMap.SimpleEntry<String, String>("F'", "Поверните переднюю грань налево"),
            new AbstractMap.SimpleEntry<String, String>("B", "Поверните заднюю грань налево"),
            new AbstractMap.SimpleEntry<String, String>("B'", "Поверните заднюю грань направо"),
            new AbstractMap.SimpleEntry<String, String>("y", "Перехватите кубик по часовой стрелке"),
            new AbstractMap.SimpleEntry<String, String>("y'", "Перехватите кубик против часовой стрелки"),
            new AbstractMap.SimpleEntry<String, String>("x", "Перехватите кубик по часовой стрелке сбоку")));
    static LinkedHashMap<String, Integer> stages = new LinkedHashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);
        stageText = findViewById(R.id.stageText);
        moveText = findViewById(R.id.moveText);
        nextButton = findViewById(R.id.buttonNext);
        prevButton = findViewById(R.id.buttonPrev);
        fullDescription = findViewById(R.id.btn_full_desc);
        langOfTurns = findViewById(R.id.btn_lang_of_turns);
        solve();

        moves = solution.trim().split("\\s+");

        onNextStepClicked();
        nextButton.setOnClickListener(v -> onNextStepClicked());
        prevButton.setOnClickListener(v -> onPreviousStepClicked());
        fullDescription.setOnClickListener(v -> onFullDescriptionClicked());
        langOfTurns.setOnClickListener(v -> onLangOfTUrnsClicked());
    }




    private void onNextStepClicked() {
        if (currentStep < moves.length - 1) {
            currentStep++;
            String currentMove = moves[currentStep];
            setMove(currentMove);
            if (stages.containsValue(currentStep)) {
                setStage(currentStep);
            }
        }
    }


    private void onPreviousStepClicked() {
        if (currentStep > 1) {
            currentStep--;
            String currentMove = moves[currentStep];
            setMove(currentMove);
            if (stages.containsValue(currentStep + 1)) {
                setStage(currentStep);
            }
        }
    }

    private void setMove(String move) {
        if (flagMoves == 1) {
            moveText.setText(move);
        }
        else {
            if (move.length() > 1 && move.charAt(1) == '2') {
                String text = movesAlternative.get(move.substring(0,1)) + " дважды";
                moveText.setText(text);
            }
            else {
                moveText.setText(movesAlternative.get(move.substring(0,1)));
            }
        }
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void onLangOfTUrnsClicked() {
        if (flagMoves != 1) {
            flagMoves = 1;
            String currentMove = moveText.getText().toString();
            String sub = currentMove.substring(currentMove.length() - 6);
            if (currentMove.substring(currentMove.length() - 6).equals("дважды")) {
                currentMove = currentMove.substring(0, currentMove.length() - 7);
                String moveInLangOfTurns = getKeyByValue(movesAlternative, currentMove);
                String text = moveInLangOfTurns + "2";
                setMove(text);
            }
            else {
                String moveInLangOfTurns = getKeyByValue(movesAlternative, currentMove);
                setMove(moveInLangOfTurns);
            }
        }
    }

    private void onFullDescriptionClicked() {
        if (flagMoves != 0) {
            flagMoves = 0;
            String currentMove = moveText.getText().toString();
            setMove(currentMove);
        }

    }

    private void setStage(int currentStep) {
        stages.forEach((key, value) -> {
            if (currentStep == value) {
                stageText.setText(key);
            }
            else if (currentStep > value) {
                stageText.setText(key);
            }
        });
    }

    /**
     * Shows the window saying that the words are over.
     */
    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Нерешаемый кубик!");
        builder.setMessage(message);
        builder.setPositiveButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
            finish();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Функция решения кубика
     * Будет получать тип кубика и массив с отсканированными цветами
     * Будет возвращать строку для решения
     */
    public void solve() {
        String scramble = "R U ";
        int type = 1;
        if (type == 1) {
            Cube2x2 cube2x2 = new Cube2x2();
            cube2x2.performMoves(scramble);
            solution = solve2x2Cube(cube2x2);
        }
        else if (type == 2) {
            Cube3x3 cube3x3 = new Cube3x3();
            solution = solve3x3Cube(cube3x3);
        }
        else if (type == 3) {
            AxisCube axisCube = new AxisCube();
            solution = solveAxisCube(axisCube);
        }
//        cube2x2.performMoves(scramble);
//        cube3x3.performMoves(scramble);
//        axisCube.performMoves(scramble);
//        solution = solve3x3Cube(cube3x3);
//        String solution2x2 = solve2x2Cube(cube2x2);
//        String solution3x3 = solve3x3Cube(cube3x3);
//        String solutionAxisCube = solveAxisCube(axisCube);
    }





    public String solve2x2Cube(Cube2x2 cube) {
        String solution = "";
        boolean solved = cube.checkCubeIsSolved();
        if (solved) {
            this.showDialog("Кубик уже собран");
            return "";
        }
        String movesFinishBottomLayer = cube.finishBottomLayer();
        if (movesFinishBottomLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesFinishBottomLayer;
        String movesPutTopCorners = cube.putTopCorners();
        if (movesPutTopCorners.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesPutTopCorners;
        String movesFinishTopLayer = cube.finishTopLayer();
        if (movesFinishTopLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesFinishTopLayer;
        solved = cube.checkCubeIsSolved();
        if (!(solved)) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        int numOfSteps1 = movesFinishBottomLayer.trim().split("\\s+").length;
        int numOfSteps2 = movesPutTopCorners.trim().split("\\s+").length + numOfSteps1;
        stages.put("Сборка нижнего слоя", 1);
        stages.put("Постановка верхних углов", numOfSteps1 + 1);
        stages.put("Переворачивание углов", numOfSteps2 + 1);
        return solution;
    }

    public String solve3x3Cube(Cube3x3 cube) {
        String solution = "";
        boolean solved = cube.checkCubeIsSolved();
        if (solved) {
            this.showDialog("Кубик уже собран");
            return "";
        }
        String movesSunflower = cube.makeSunflower();
        if (movesSunflower.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesSunflower;
        String movesWhiteCross = cube.makeWhiteCross();
        if (movesWhiteCross.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesWhiteCross;
        String movesFinishWhiteLayer = cube.finishWhiteLayer();
        if (movesFinishWhiteLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesFinishWhiteLayer;
        String movesInsertAllEdges = cube.solveSecondLayer();
        if (movesInsertAllEdges.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesInsertAllEdges;
        String movesMakeYellowCross = cube.makeYellowCross();
        if (movesMakeYellowCross.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesMakeYellowCross;
        String movesOrientLastLayer = cube.orientYellowCross();
        if (movesOrientLastLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesOrientLastLayer;
        String movesPermuteLastLayer = cube.putYellowCorners();
        if (movesPermuteLastLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesPermuteLastLayer;
        solved = cube.checkCubeIsSolved();
        if (!(solved)) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        int numOfSteps1 = movesSunflower.trim().split("\\s+").length;
        int numOfSteps2 = movesWhiteCross.trim().split("\\s+").length + numOfSteps1;
        int numOfSteps3 = movesFinishWhiteLayer.trim().split("\\s+").length + numOfSteps2;
        int numOfSteps4 = movesInsertAllEdges.trim().split("\\s+").length + numOfSteps3;
        int numOfSteps5 = movesMakeYellowCross.trim().split("\\s+").length + numOfSteps4;
        int numOfSteps6 = movesOrientLastLayer.trim().split("\\s+").length + numOfSteps5;
        stages.put("Сборка ромашки", 1);
        stages.put("Сборка белого креста", numOfSteps1 + 1);
        stages.put("Сборка белого слоя", numOfSteps2 + 1);
        stages.put("Сборка второго слоя", numOfSteps3 + 1);
        stages.put("Сборка желтого креста", numOfSteps4 + 1);
        stages.put("Ориентирование желтого креста", numOfSteps5 + 1);
        stages.put("Постановка желтых углов", numOfSteps6 + 1);
        return solution;
    }

    public String solveAxisCube(AxisCube cube) {
        String solution = "";
        boolean solved = cube.checkCubeIsSolved();
        if (solved) {
            this.showDialog("Кубик уже собран");
            return "";
        }
        String movesSunflower = cube.makeSunflower();
        if (movesSunflower.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesSunflower;
        String movesWhiteCross = cube.makeWhiteCross();
        if (movesWhiteCross.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesWhiteCross;
        String movesWhiteLayer = cube.finishWhiteLayer();
        if (movesWhiteLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesWhiteLayer;
        String movesOrientVertCenters = cube.orientVerticalCenters();
        if (movesOrientVertCenters.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesOrientVertCenters;
        String movesSecondLayer = cube.solveSecondLayer();
        if (movesSecondLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesSecondLayer;
        String movesYellowCross = cube.makeYellowCross();
        if (movesYellowCross.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesYellowCross;
        String movesOrientCross = cube.orientYellowCross();
        if (movesOrientCross.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesOrientCross;
        String movesOrientYellowCenter = cube.orientYellowCenter();
        if (movesOrientYellowCenter.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += movesOrientYellowCenter;
        String lastLayer = cube.putYellowCorners();
        if (lastLayer.equals("Wrong cube")) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        solution += lastLayer;
        solved = cube.checkCubeIsSolved();
        if (!(solved)) {
            // Вернуть пользователя на экран сканирования/ручного ввода
            this.showDialog("Кубик введен неправильно");
            return "";
        }
        int numOfSteps1 = movesSunflower.trim().split("\\s+").length;
        int numOfSteps2 = movesWhiteCross.trim().split("\\s+").length + numOfSteps1;
        int numOfSteps3 = movesWhiteLayer.trim().split("\\s+").length + numOfSteps2;
        int numOfSteps4 = movesOrientVertCenters.trim().split("\\s+").length + numOfSteps3;
        int numOfSteps5 = movesSecondLayer.trim().split("\\s+").length + numOfSteps4;
        int numOfSteps6 = movesYellowCross.trim().split("\\s+").length + numOfSteps5;
        int numOfSteps7 = movesOrientCross.trim().split("\\s+").length + numOfSteps6;
        int numOfSteps8 = movesOrientYellowCenter.trim().split("\\s+").length + numOfSteps7;
        stages.put("Сборка ромашки", 1);
        stages.put("Сборка белого креста", numOfSteps1 + 1);
        stages.put("Сборка белого слоя", numOfSteps2 + 1);
        stages.put("Поворот вертикальных углов", numOfSteps3 + 1);
        stages.put("Сборка второго слоя", numOfSteps4 + 1);
        stages.put("Сборка желтого креста", numOfSteps5 + 1);
        stages.put("Ориентирование желтого креста", numOfSteps6 + 1);
        stages.put("Ориентирование желтого центра", numOfSteps7 + 1);
        stages.put("Постановка углов", numOfSteps8 + 1);
        return solution;
    }
}

