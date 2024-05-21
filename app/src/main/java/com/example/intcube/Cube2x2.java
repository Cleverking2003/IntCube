package com.example.intcube;


import java.lang.reflect.AccessibleObject;

public class Cube2x2 {
    public Cubie[][][] cubiePos = new Cubie[2][2][2];

    private int posOfCorrectCornerX;
    private int posOfCorrectCornerY;

    public Cube2x2() {
        //Up, Front Row
        cubiePos[0][0][0] = new Cubie(0, 0, 0,
                new CubieColor[]{new CubieColor('Y', 'U'), new CubieColor('R', 'L'), new CubieColor('G', 'F')}, true, false);
        cubiePos[1][0][0] = new Cubie(1, 0, 0,
                new CubieColor[]{new CubieColor('Y', 'U'), new CubieColor('G', 'F'), new CubieColor('O', 'R')}, true, false);


        //Down, Front Row
        cubiePos[0][0][1] = new Cubie(0, 0, 1,
                new CubieColor[]{new CubieColor('W', 'D'), new CubieColor('R', 'L'), new CubieColor('G', 'F')}, true, false);
        cubiePos[1][0][1] = new Cubie(1, 0, 1,
                new CubieColor[]{new CubieColor('W', 'D'), new CubieColor('G', 'F'), new CubieColor('O', 'R')}, true, false);


        //Up, Back Row
        cubiePos[0][1][0] = new Cubie(0, 1, 0,
                new CubieColor[]{new CubieColor('Y', 'U'), new CubieColor('R', 'L'), new CubieColor('B', 'B')}, true, false);
        cubiePos[1][1][0] = new Cubie(1, 1, 0,
                new CubieColor[]{new CubieColor('Y', 'U'), new CubieColor('B', 'B'), new CubieColor('O', 'R')}, true, false);


        //Down, Back Row
        cubiePos[0][1][1] = new Cubie(0, 1, 1,
                new CubieColor[]{new CubieColor('W', 'D'), new CubieColor('R', 'L'), new CubieColor('B', 'B')}, true, false);
        cubiePos[1][1][1] = new Cubie(1, 1, 1,
                new CubieColor[]{new CubieColor('W', 'D'), new CubieColor('B', 'B'), new CubieColor('O', 'R')}, true, false);
    }

    public Cube2x2(char[][][] colors) {
        //Up, Front Row
        cubiePos[0][0][0] = new Cubie(0,0,0,
                new CubieColor[]{ new CubieColor(colors[5][0][0],'U') , new CubieColor(colors[2][0][1],'L'), new CubieColor(colors[3][0][0],'F')}, true, false);

        cubiePos[1][0][0] = new Cubie(2,0,0,
                new CubieColor[]{ new CubieColor(colors[5][1][0],'U') , new CubieColor(colors[3][0][1],'F'), new CubieColor(colors[4][0][0],'R')}, true, false);

        //Down, Front Row
        cubiePos[0][0][1] = new Cubie(0,0,2,
                new CubieColor[]{ new CubieColor(colors[0][1][1],'D') , new CubieColor(colors[2][1][1],'L'), new CubieColor(colors[3][1][0],'F')}, true, false);

        cubiePos[1][0][1] = new Cubie(2,0,2,
                new CubieColor[]{ new CubieColor(colors[0][1][0],'D') , new CubieColor(colors[3][1][1],'F'), new CubieColor(colors[4][1][0],'R')}, true, false);

        //Up, Back Row
        cubiePos[0][1][0] = new Cubie(0,2,0,
                new CubieColor[]{ new CubieColor(colors[5][0][1],'U') , new CubieColor(colors[2][0][0],'L'), new CubieColor(colors[1][0][1],'B')}, true, false);

        cubiePos[1][1][0] = new Cubie(2,2,0,
                new CubieColor[]{ new CubieColor(colors[5][1][1],'U') , new CubieColor(colors[1][0][0],'B'), new CubieColor(colors[4][0][1],'R')}, true, false);

        //Down, Back Row
        cubiePos[0][1][1] = new Cubie(0,2,2,
                new CubieColor[]{ new CubieColor(colors[0][0][1],'D') , new CubieColor(colors[2][1][0],'L'), new CubieColor(colors[1][1][1],'B')}, true, false);

        cubiePos[1][1][1] = new Cubie(2,2,2,
                new CubieColor[]{ new CubieColor(colors[0][0][0],'D') , new CubieColor(colors[1][1][0],'B'), new CubieColor(colors[4][1][1],'R')}, true, false);
    }

    private boolean checkInfLoop(long startTime) {
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - startTime;
        return timeElapsed > 1000;
    }

    public boolean checkCubeIsSolved() {
        char upColor = cubiePos[0][0][0].getColorOfDir('U');
        char downColor = cubiePos[0][0][1].getColorOfDir('D');
        char leftColor = cubiePos[0][0][0].getColorOfDir('L');
        char rightColor = cubiePos[1][0][0].getColorOfDir('R');
        char frontColor = cubiePos[0][0][0].getColorOfDir('F');
        char backColor = cubiePos[0][1][0].getColorOfDir('B');
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                for (int z = 0; z < 2; z++) {
                    if (cubiePos[x][y][0].getDirOfColor(upColor) != 'U') {
                        return false;
                    }
                    if (cubiePos[x][y][1].getDirOfColor(downColor) != 'D') {
                        return false;
                    }
                    if (cubiePos[0][y][z].getDirOfColor(leftColor) != 'L') {
                        return false;
                    }
                    if (cubiePos[1][y][z].getDirOfColor(rightColor) != 'R') {
                        return false;
                    }
                    if (cubiePos[x][0][z].getDirOfColor(frontColor) != 'F') {
                        return false;
                    }
                    if (cubiePos[x][1][z].getDirOfColor(backColor) != 'B') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Вспомогательный метод для finishBottomLayer
     * Ищет деталь с такими же цветами, как нижний и передний у левой нижней детали,
     * и ставит на правильное место в нижней грани
     *
     * @return шаги для постановки угла в нижнюю грань
     */
    private String completeRightBottomCubie(char bottomColor) {
        String moves = "";
        char frontColor = cubiePos[0][0][1].getColorOfDir('F');
        boolean firstFaceSolved = false;
        boolean cubieOnTop = false;
        // Проверяем, нет ли подходящей детали снизу
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                if (x != 0 || y != 0) {
                    // Если снизу есть деталь с нужными цветами
                    if (cubiePos[x][y][1].cornerHasColor(bottomColor) && cubiePos[x][y][1].cornerHasColor(frontColor)) {
                        // Если деталь стоит на своем месте
                        if (x == 1 && y == 0) {
                            // Если деталь неправильно развернута
                            if (cubiePos[x][y][1].getColorOfDir('D') != bottomColor || cubiePos[x][y][1].getColorOfDir('F') != frontColor) {
                                moves += performMoves("R U R' U' ");
                            } else {
                                firstFaceSolved = true;
                                break;
                            }
                        }
                        // Деталь снизу не на своем месте
                        else {
                            if (x == 0) {
                                moves += performMoves("B' ");
                            }
                            else moves += performMoves("R' ");
                            cubieOnTop = true;
                            break;
                        }
                    }
                }
            }
            if (firstFaceSolved) break;
            if (cubieOnTop) break;
        }

        if (!(firstFaceSolved)) {
            long startTime = System.currentTimeMillis();
            while (!(cubiePos[1][0][0].cornerHasColor(bottomColor) && cubiePos[1][0][0].cornerHasColor(frontColor))) {
                if (checkInfLoop(startTime)) return "Wrong cube";
                moves += performMoves("U ");
            }
            if (cubiePos[1][0][0].getDirOfColor(bottomColor) == 'R')
                moves += performMoves("R U R' U' ");
            else if (cubiePos[1][0][0].getDirOfColor(bottomColor) == 'U')
                moves += performMoves("R U R' U' R U R' U' R U R' U' ");
            else moves += performMoves("R U R' U' R U R' U' R U R' U' R U R' U' R U R' U' ");
        }

        return moves;
    }

    /**
     * Собирает нижнюю грань
     *
     * @return шаги для сборки нижней грани
     */
    public String finishBottomLayer() {
        String moves = "";
        char bottomColor = cubiePos[0][0][1].getColorOfDir('D');
        for (int i = 0; i < 3; i++) {
            String res = completeRightBottomCubie(bottomColor);
            if (res.equals("Wrong cube")) {
                return res;
            }
            moves += res;
            moves += performMoves("y ");
        }
        return optimizeMoves(moves);
    }

    /**
     * Вспомогательный метод для putTopCorners.
     * Считает количество стоящих на своем месте углов верхней грани.
     *
     * @return количество стоящих на своем месте углов верхней грани.
     */
    private int countCorrectCorners() {
        int cornersInCorrectPositions = 1;
        // Ищем правильно стоящие углы
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                int colorHasCount = 0;
                if (x != 1 || y != 0) {
                    // Цикл по цветам нижнего угла
                    for (int i = 0; i < 3; i++) {
                        char bottomCubieColorName = cubiePos[x][y][1].getColors()[i].getColor();
                        if (cubiePos[x][y][1].getDirOfColor(bottomCubieColorName) != 'D') {
                            if (cubiePos[x][y][0].cornerHasColor(bottomCubieColorName)) {
                                colorHasCount++;
                            }
                        }
                    }
                }
                    if (colorHasCount == 2) {
                        cornersInCorrectPositions++;
                        posOfCorrectCornerX = x;
                        posOfCorrectCornerY = y;
                    }
            }
        }
        return cornersInCorrectPositions;
    }

    /**
     * Вспомогательный метод для checkCorners
     * Когда два угла по одну сторону стоят на правильных местах,
     * повернуть кубик так, чтоб они оказались слева
     * @return шаги, чтоб правильно стоящие углы оказались слева
     */
    private String putCorrectCornersOnLeft() {
        int posOfCubieX1 = -1;
        int posOfCubieY1 = -1;
        int posOfCubieX2 = -1;
        int posOfCubieY2 = -1;
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                CubieColor[] colorsOfBottomCubie = cubiePos[x][y][1].getColors();
                int numberOfEq = 0;
                for (CubieColor color : colorsOfBottomCubie) {
                    if (cubiePos[x][y][0].cornerHasColor(color.getColor())) {
                        numberOfEq++;
                    }
                }
                if (numberOfEq == 2) {
                    if (posOfCubieX1 == -1) {
                        posOfCubieX1 = x;
                        posOfCubieY1 = y;
                    }
                    else {
                        posOfCubieX2 = x;
                        posOfCubieY2 = y;
                    }
                }
            }
        }

        if ((posOfCubieX1 == 0) && (posOfCubieY1 == 0)) {
            if ((posOfCubieX2 == 1) && (posOfCubieY2 == 0)) {
                return performMoves("y ");
            }
            if ((posOfCubieX2 == 0) && (posOfCubieY2 == 1)) {
                return "";
            }
        }
        if ((posOfCubieX1 == 0) && (posOfCubieY1 == 1)) {
            if ((posOfCubieX2 == 1) && (posOfCubieY2 == 1)) {
                return performMoves("y' ");
            }
        }
//        if ((posOfCubieX1 == 1) && (posOfCubieY1 == 0)) {
//            if ((posOfCubieX2 == 1) && (posOfCubieY2 == 1)) {
//                return performMoves("y2 ");
//            }
//        }
        return performMoves("y2 ");
    }

    /**
     * Проверяет положение углов в правильном месте и в зависимости от этого проделывает алгоритм
     * @param cornersInCorrectPositions количество правильных углов
     * @return шаги для постановки оставшихся углов в правильные места
     */
    private String checkCorners(int cornersInCorrectPositions) {
        String moves = "";
        if (cornersInCorrectPositions == 4) return moves;
        // Если другой угол на правильном месте находится по диагонали от правого левого
        if (posOfCorrectCornerX == 0 && posOfCorrectCornerY == 1) {
            moves += performMoves("R U R' U' R U R' U' R U R' U' y L' U' L U L' U' L U L' U' L U ");

        }
        moves += putCorrectCornersOnLeft();
        moves += performMoves("R U R' U' R U R' U' R U R' U' y L' U' L U L' U' L U L' U' L U ");
        return moves;
    }

    /**
     * Вспомогательный метод для putTopCorners
     * Крутит верхнюю грань, пока правая верхняя деталь не встанет на свое место
     * @return шаги для постановки правой верхней детали в нужное место
     */
    private String putRightCornerInCorrectPosition() {
        int countOfU = 0;
        char frontColor = cubiePos[1][0][1].getColorOfDir('F');
        char sideColor = cubiePos[1][0][1].getColorOfDir('R');
        // Ставим угол сверху спереди справа
        long startTime = System.currentTimeMillis();
        while (!(cubiePos[1][0][0].cornerHasColor(frontColor) && cubiePos[1][0][0].cornerHasColor(sideColor))) {
            if (checkInfLoop(startTime)) return "Wrong cube";
            performMoves("U");
            countOfU++;
        }
        if (countOfU == 1) {
            return "U ";
        }
        if (countOfU == 2) {
            return "U2 ";
        }
        if (countOfU == 3) {
            return "U' ";
        }
        return "";
    }




    /**
     * Расставляет углы на верхней грани на нужное место
     * @return шаги для расстановки углов на верхней грани
     */
    public String putTopCorners() {
        String moves = "";
        // Ставим правый верхний угол в правильное место
        String res = putRightCornerInCorrectPosition();
        if (res.equals("Wrong cube")) {
            return res;
        }
        moves += res;
        int cornersInCorrectPositions = countCorrectCorners();
        if (cornersInCorrectPositions != 1) {
            moves += checkCorners(cornersInCorrectPositions);
            res = putRightCornerInCorrectPosition();
            if (res.equals("Wrong cube")) {
                return res;
            }
            moves += res;
            return optimizeMoves(moves);
        }


        // Ставим два угла на свое место
        long startTime = System.currentTimeMillis();
        while (cornersInCorrectPositions < 2) {
            if (checkInfLoop(startTime)) return "Wrong cube";
            moves += performMoves("y ");
            res = putRightCornerInCorrectPosition();
            if (res.equals("Wrong cube")) {
                return res;
            }
            moves += res;
            cornersInCorrectPositions = countCorrectCorners();
        }

        moves += checkCorners(cornersInCorrectPositions);
        res = putRightCornerInCorrectPosition();
        if (res.equals("Wrong cube")) {
            return res;
        }
        moves += res;
        return moves;

    }

    public String finishTopLayer() {
        String moves = "";
        char topColor = 'A';
        for (int i = 0; i < 3; i++) {
                char topCubieColorName = cubiePos[0][0][0].getColors()[i].getColor();
                if (!(cubiePos[0][0][1].cornerHasColor(topCubieColorName))) {
                    topColor = topCubieColorName;
                    break;
                }
        }
        // Перевернуть вверх ногами
        moves += performMoves("x2 ");

        for (int i = 0; i < 4; i++) {
            if (cubiePos[1][0][1].getDirOfColor(topColor) == 'F') {
                moves += performMoves("R U R' U' R U R' U' R U R' U' R U R' U' ");
                moves += performMoves("D' ");
                continue;
            }
            if (cubiePos[1][0][1].getDirOfColor(topColor) == 'R') {
                moves += performMoves("R U R' U' R U R' U' ");
                moves += performMoves("D' ");
                continue;
            }
            moves += performMoves("D' ");
        }
        long startTime = System.currentTimeMillis();
        while (cubiePos[0][0][0].getColorOfDir('F') != cubiePos[0][0][1].getColorOfDir('F')) {
            if (checkInfLoop(startTime)) return "Wrong cube";
            moves += performMoves("D' ");
        }

        int countX = 0;

        for (int i = 0; i < 4; i++) {
            if (cubiePos[0][0][0].getColorOfDir('U') != 'W') {
                performMoves("x");
                countX++;
            }
        }
        if (cubiePos[0][0][0].getColorOfDir('U') == 'W') {
            if (countX == 1) {
                moves += "x ";
            }
            else if (countX == 2) {
                moves += "x2 ";
            }
            else if (countX == 3) {
                moves += "x' ";
            }
        }
        else {
            moves += performMoves("y ");
            startTime = System.currentTimeMillis();
            while (cubiePos[0][0][0].getColorOfDir('U') != 'W') {
                if (checkInfLoop(startTime)) return "Wrong cube";
                moves += performMoves("x ");
            }
        }


////        startTime = System.currentTimeMillis();
//        while (cubiePos[0][0][0].getColorOfDir('U') != 'W') {
////            if (checkInfLoop(startTime)) return "Wrong cube";
//            moves += performMoves("x ");
//        }

        startTime = System.currentTimeMillis();
        while (cubiePos[0][0][0].getColorOfDir('F') != 'R') {
            if (checkInfLoop(startTime)) return "Wrong cube";
            moves += performMoves("y ");
        }

        return optimizeMoves(moves);
    }

    public void turn(String turn) {
        //See the first case (B) to understand how all cases work
        char[] preChange; //Directions prior to turning
        char[] postChange; //What the directions change to after the turn
        Cubie[][] matrix = new Cubie[2][2]; //matrix to be rotated

        switch (turn) {
            case "B":
                preChange = new char[]{'B', 'U', 'R', 'D', 'L'};
                postChange = new char[]{'B', 'L', 'U', 'R', 'D'};
                //Transfer cubie data into matrix to be rotated
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrix[i][j] = cubiePos[Math.abs(j - 1)][1][i];
                    }
                }
                //Rotate the matrix
                matrix = rotateMatrix(matrix, 90, preChange, postChange);
                //Reset the actual cube's cubies to those of the rotated matrix
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        cubiePos[Math.abs(j - 1)][1][i] = matrix[i][j];
                    }
                }
                break;

            case "B'":
                preChange = new char[]{'B', 'U', 'R', 'D', 'L'};
                postChange = new char[]{'B', 'R', 'D', 'L', 'U'};
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrix[i][j] = cubiePos[Math.abs(j - 1)][1][i];
                    }
                }
                matrix = rotateMatrix(matrix, -90, preChange, postChange);

                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        cubiePos[Math.abs(j - 1)][1][i] = matrix[i][j];
                    }
                }
                break;

            case "D":
                preChange = new char[]{'D', 'L', 'B', 'R', 'F'};
                postChange = new char[]{'D', 'F', 'L', 'B', 'R'};
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrix[i][j] = cubiePos[j][i][1];
                    }
                }
                matrix = rotateMatrix(matrix, 90, preChange, postChange);

                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        cubiePos[j][i][1] = matrix[i][j];
                    }
                }
                break;

            case "D'":
                preChange = new char[]{'D', 'F', 'L', 'B', 'R'};
                postChange = new char[]{'D', 'L', 'B', 'R', 'F'};
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrix[i][j] = cubiePos[j][i][1];
                    }
                }
                matrix = rotateMatrix(matrix, -90, preChange, postChange);

                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        cubiePos[j][i][1] = matrix[i][j];
                    }
                }
                break;


            case "F":
                preChange = new char[]{'F', 'U', 'R', 'D', 'L'};
                postChange = new char[]{'F', 'R', 'D', 'L', 'U'};
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrix[i][j] = cubiePos[j][0][i];
                    }
                }
                matrix = rotateMatrix(matrix, 90, preChange, postChange);

                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        cubiePos[j][0][i] = matrix[i][j];
                    }
                }
                break;

            case "F'":
                preChange = new char[]{'F', 'U', 'R', 'D', 'L'};
                postChange = new char[]{'F', 'L', 'U', 'R', 'D'};
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrix[i][j] = cubiePos[j][0][i];
                    }
                }
                matrix = rotateMatrix(matrix, -90, preChange, postChange);

                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        cubiePos[j][0][i] = matrix[i][j];
                    }
                }
                break;

            case "L":
                preChange = new char[]{'L', 'B', 'D', 'F', 'U'};
                postChange = new char[]{'L', 'U', 'B', 'D', 'F'};
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrix[i][j] = cubiePos[0][Math.abs(j - 1)][i];
                    }
                }
                matrix = rotateMatrix(matrix, 90, preChange, postChange);

                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        cubiePos[0][Math.abs(j - 1)][i] = matrix[i][j];
                    }
                }
                break;

            case "L'":
                preChange = new char[]{'L', 'U', 'B', 'D', 'F'};
                postChange = new char[]{'L', 'B', 'D', 'F', 'U'};
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrix[i][j] = cubiePos[0][Math.abs(j - 1)][i];
                    }
                }
                matrix = rotateMatrix(matrix, -90, preChange, postChange);

                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        cubiePos[0][Math.abs(j - 1)][i] = matrix[i][j];
                    }
                }
                break;


            case "R":
                preChange = new char[]{'R', 'U', 'B', 'D', 'F'};
                postChange = new char[]{'R', 'B', 'D', 'F', 'U'};
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrix[i][j] = cubiePos[1][j][i];
                    }
                }
                matrix = rotateMatrix(matrix, 90, preChange, postChange);

                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        cubiePos[1][j][i] = matrix[i][j];
                    }
                }
                break;

            case "R'":
                preChange = new char[]{'R', 'B', 'D', 'F', 'U'};
                postChange = new char[]{'R', 'U', 'B', 'D', 'F'};
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrix[i][j] = cubiePos[1][j][i];
                    }
                }
                matrix = rotateMatrix(matrix, -90, preChange, postChange);

                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        cubiePos[1][j][i] = matrix[i][j];
                    }
                }
                break;


            case "U":
                preChange = new char[]{'U', 'F', 'L', 'B', 'R'};
                postChange = new char[]{'U', 'L', 'B', 'R', 'F'};
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrix[i][j] = cubiePos[j][Math.abs(i - 1)][0];
                    }
                }
                matrix = rotateMatrix(matrix, 90, preChange, postChange);

                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        cubiePos[j][Math.abs(i - 1)][0] = matrix[i][j];
                    }
                }
                break;

            case "U'":
                preChange = new char[]{'U', 'L', 'B', 'R', 'F'};
                postChange = new char[]{'U', 'F', 'L', 'B', 'R'};
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrix[i][j] = cubiePos[j][Math.abs(i - 1)][0];
                    }
                }
                matrix = rotateMatrix(matrix, -90, preChange, postChange);

                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        cubiePos[j][Math.abs(i - 1)][0] = matrix[i][j];
                    }
                }
                break;

            case "x":
                performMoves("R L'");
                //turn("R"); turn("M'"); turn("L'");
                break;

            case "x'":
                performMoves("R' M L");
                //turn("R'"); turn("M"); turn("L");
                break;

            case "y":
                performMoves("U D'");
                //turn("U"); turn("E'"); turn("D'");
                break;

            case "y'":
                performMoves("U' D");
                //turn("U'"); turn("E"); turn("D");
                break;

            case "z":
                performMoves("F S B'");
                //turn("F"); turn("S"); turn("B'");
                break;

            case "z'":
                performMoves("F' S' B");
                //turn("F'"); turn("S'"); turn("B");
                break;

        }


    }

    /**
     * Rotates a given 2D matrix as specified by {@code degrees}, where {@code degrees}
     * can either be 90, indicating a clockwise rotation, or -90, indicating a counterclockwise
     * rotation. {@code postChange} dictates how the direction of a cubie's colors should
     * change after the rotation, the original directions being denoted by {@code preChange}.
     *
     * @param orig       the original matrix
     * @param degrees    degrees by which to rotate, can be 90 or -90
     * @param preChange  the set of direction prior to rotation
     * @param postChange the corresponding set of direction to change the {@code preChange} directions to
     * @return the rotated matrix
     */
    private Cubie[][] rotateMatrix(Cubie[][] orig, int degrees, char[] preChange,
                                   char[] postChange) {
        Cubie[][] rotated = new Cubie[2][2];
        if (degrees == 90) {
            //Transpose the matrix
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    rotated[i][j] = orig[j][i];
                }
            }
            //Reverse all the rows
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < rotated[0].length / 2; j++) {
                    Cubie tempCubie = rotated[i][2 - j - 1];
                    rotated[i][2 - j - 1] = rotated[i][j];
                    rotated[i][j] = tempCubie;
                }
            }
        } else if (degrees == -90) {
            //Transpose the matrix
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    rotated[i][j] = orig[j][i];
                }
            }

            //Reverse all the columns
            for (int i = 0; i < rotated[0].length / 2; i++) {
                for (int j = 0; j < 2; j++) {
                    Cubie tempCubie = rotated[2 - i - 1][j];
                    rotated[2 - i - 1][j] = rotated[i][j];
                    rotated[i][j] = tempCubie;
                }
            }
        }

        //Change the direction of all colors appropriately as well before returning the array
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                CubieColor[] tempColors = rotated[i][j].getColors();
                for (int k = 0; k < tempColors.length; k++) {
                    int index = 6;
                    for (int x = 0; x < preChange.length; x++) {
                        if (tempColors[k].getDir() == preChange[x]) {
                            index = x;
                        }
                    }
                    if (index < postChange.length)
                        tempColors[k].setDir(postChange[index]);
                }
                rotated[i][j].setColors(tempColors);
            }
        }
        return rotated;
    }

    /**
     * Loops through the characters in a String of standard turning notation to apply the set of moves to the cube
     * Checks for clockwise, double, and counterclockwise turns
     *
     * @param moves the moves to be applied to the cube
     * @return the moves performed on the cube (same as {@code moves})
     */
    public String performMoves(String moves) {
        for (int i = 0; i < moves.length(); i++) {
            if (moves.substring(i, i + 1) != " ") { //Only check if there is a meaningful character
                if (i != moves.length() - 1) {
                    if (moves.substring(i + 1, i + 2).compareTo("2") == 0) {
                        //Turning twice ex. U2
                        turn(moves.substring(i, i + 1));
                        turn(moves.substring(i, i + 1));
                        i++; //Skip the "2" for the next iteration
                    } else if (moves.substring(i + 1, i + 2).compareTo("'") == 0) {
                        //Making a counterclockwise turn ex. U'
                        turn(moves.substring(i, i + 2));
                        i++; //Skip the apostrophe for the next iteration
                    } else {
                        //Regular clockwise turning
                        turn(moves.substring(i, i + 1));
                    }
                } else {
                    //Nothing is after the turn letter, so just perform the turn
                    turn(moves.substring(i, i + 1));
                }
            }
        }
        return moves;
    }

    public String optimizeMoves(String moves) {
        for(int i = 0; i<moves.length(); i++) {
            String move = moves.substring(i, i+1);
            if(!move.equals(" ") && !move.equals("'") && !move.equals("2")) { //Only check if there is a meaningful turn/rotation
                if(i <= moves.length()-3) {
                    if(moves.substring(i+1, i+2).compareTo("2") == 0) { //Double turn
                        if(i <= moves.length()-4 && moves.charAt(i+3) == moves.charAt(i)) {
                            if(i <= moves.length()-5) {
                                if(moves.substring(i+4, i+5).compareTo("2") == 0) {
                                    //Ex. "U2 U2" gets negated
                                    moves = moves.substring(0, i) + moves.substring(i+5);
                                    i--;
                                } else if(moves.substring(i+4, i+5).compareTo("'") == 0) {
                                    //Ex. "U2 U'" --> "U"
                                    moves = moves.substring(0, i) + moves.charAt(i)
                                            + moves.substring(i+5);
                                    i--;
                                } else {
                                    //Ex. "U2 U" --> "U'"
                                    moves = moves.substring(0, i) + moves.charAt(i) + "'"
                                            + moves.substring(i+4);
                                    i--;
                                }
                            } else {
                                //Ex. "U2 U" --> "U'"
                                moves = moves.substring(0, i) + moves.charAt(i) + "'"
                                        + moves.substring(i+4);
                                i--;
                            }
                        }
                    }
                    else if(moves.substring(i+1,i+2).compareTo("'") == 0) { //Clockwise turn
                        if(i <= moves.length()-4 && moves.charAt(i+3) == moves.charAt(i)) {
                            if(i <= moves.length()-5) {
                                if(moves.substring(i+4, i+5).compareTo("2") == 0) {
                                    //Ex. "U' U2" --> "U"
                                    moves = moves.substring(0, i) + moves.charAt(i)
                                            + moves.substring(i+5);
                                    i--;
                                } else if(moves.substring(i+4, i+5).compareTo("'") == 0) {
                                    //Ex. "U' U'" --> "U2"
                                    moves = moves.substring(0, i) + moves.charAt(i) + "2"
                                            + moves.substring(i+5);
                                    i--;
                                } else {
                                    //Ex. "U' U" gets negated
                                    moves = moves.substring(0, i) + moves.substring(i+4);
                                    i--;
                                }
                            } else {
                                //Ex. "U' U" gets negated
                                moves = moves.substring(0, i) + moves.substring(i+4);
                                i--;
                            }
                        }
                    }
                    else { //Clockwise turn
                        if(i <= moves.length()-3 && moves.charAt(i+2) == moves.charAt(i)) {
                            if(i <= moves.length()-4) {
                                if(moves.substring(i+3, i+4).compareTo("2") == 0) {
                                    //Ex. "U U2" --> "U' "
                                    moves = moves.substring(0, i) + moves.charAt(i) + "'"
                                            + moves.substring(i+4);
                                    i--;
                                } else if(moves.substring(i+3, i+4).compareTo("'") == 0) {
                                    //Ex. "U U'" gets negated
                                    moves = moves.substring(0, i) + moves.substring(i+4);
                                    i--;
                                } else {
                                    //Ex. "U U" --> "U2"
                                    moves = moves.substring(0, i) + moves.charAt(i) + "2"
                                            + moves.substring(i + 3);
                                    i--;
                                }
                            } else {
                                //Ex. "U U" --> "U2"
                                moves = moves.substring(0, i) + moves.charAt(i) + "2"
                                        + moves.substring(i + 3);
                                i--;
                            }
                        }

                    }
                }
            }
        }

        return moves;
    }


}
 