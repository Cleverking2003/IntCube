package com.example.intcube;

public class AxisCube {
    public Cubie[][][] cubiePos = new Cubie[3][3][3];

    public AxisCube() {
        //Up, Front Row
        cubiePos[0][0][0] = new Cubie(0,0,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('R','L'), new CubieColor('G','F')}, true, false);
        cubiePos[1][0][0] = new Cubie(1,0,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('G','F')}, false, true);
        cubiePos[2][0][0] = new Cubie(2,0,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('G','F'), new CubieColor('O','R')}, true, false);

        //Front, E Row
        cubiePos[0][0][1] = new Cubie(0,0,1,
                new CubieColor[]{ new CubieColor('R','L'), new CubieColor('G','F')}, false, true);
        cubiePos[1][0][1] = new Cubie(1,0,1,
                new CubieColor[]{ new CubieColor('G','F')}, false, false, 'U');
        cubiePos[2][0][1] = new Cubie(2,0,1,
                new CubieColor[]{ new CubieColor('G','F'), new CubieColor('O','R')}, false, true);

        //Down, Front Row
        cubiePos[0][0][2] = new Cubie(0,0,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('R','L'), new CubieColor('G','F')}, true, false);
        cubiePos[1][0][2] = new Cubie(1,0,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('G','F')}, false, true);
        cubiePos[2][0][2] = new Cubie(2,0,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('G','F'), new CubieColor('O','R')}, true, false);

        //Up, S Row
        cubiePos[0][1][0] = new Cubie(0,1,0,
                new CubieColor[]{ new CubieColor('R','L'), new CubieColor('Y','U')}, false, true);
        cubiePos[1][1][0] = new Cubie(1,1,0,
                new CubieColor[]{ new CubieColor('Y','U')}, false, false, 'U');
        cubiePos[2][1][0] = new Cubie(2,1,0,
                new CubieColor[]{ new CubieColor('Y','U'), new CubieColor('O','R')}, false, true);

        //E, S Row
        cubiePos[0][1][1] = new Cubie(0,1,1,
                new CubieColor[]{ new CubieColor('R','L')}, false, false, 'U');
        cubiePos[1][1][1] = new Cubie(1,1,1,
                new CubieColor[]{ new CubieColor('A','A')}, //Just giving random, non-legitimate values for color and direction
                false, false);
        cubiePos[2][1][1] = new Cubie(2,1,1,
                new CubieColor[]{ new CubieColor('O','R')}, false, false, 'U');

        //Down, S Row
        cubiePos[0][1][2] = new Cubie(0,1,2,
                new CubieColor[]{ new CubieColor('R','L'), new CubieColor('W','D')}, false, true);
        cubiePos[1][1][2] = new Cubie(1,1,2,
                new CubieColor[]{ new CubieColor('W','D')}, false, false, 'U');
        cubiePos[2][1][2] = new Cubie(2,1,2,
                new CubieColor[]{ new CubieColor('W','D'), new CubieColor('O','R')}, false, true);

        //Up, Back Row
        cubiePos[0][2][0] = new Cubie(0,2,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('R','L'), new CubieColor('B','B')}, true, false);
        cubiePos[1][2][0] = new Cubie(1,2,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('B','B')}, false, true);
        cubiePos[2][2][0] = new Cubie(2,2,0,
                new CubieColor[]{ new CubieColor('Y','U') , new CubieColor('B','B'), new CubieColor('O','R')}, true, false);

        //E, Back Row
        cubiePos[0][2][1] = new Cubie(0,2,1,
                new CubieColor[]{ new CubieColor('R','L'), new CubieColor('B','B')}, false, true);
        cubiePos[1][2][1] = new Cubie(1,2,1,
                new CubieColor[]{ new CubieColor('B','B')}, false, false, 'U');
        cubiePos[2][2][1] = new Cubie(2,2,1,
                new CubieColor[]{ new CubieColor('B','B'), new CubieColor('O','R')}, false, true);

        //Down, Back Row
        cubiePos[0][2][2] = new Cubie(0,2,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('R','L'), new CubieColor('B','B')}, true, false);
        cubiePos[1][2][2] = new Cubie(1,2,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('B','B')}, false, true);
        cubiePos[2][2][2] = new Cubie(2,2,2,
                new CubieColor[]{ new CubieColor('W','D') , new CubieColor('B','B'), new CubieColor('O','R')}, true, false);

    }

    private boolean checkInfLoop(long startTime) {
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - startTime;
        return timeElapsed > 10000;
    }



        /**
         * Takes a String value of a turn or rotation in standard Rubik's Cube notation and applies the turn or rotation to
         * the cube. Valid turns currently include any turn in the following planes: U, D, F, B, L, R, M, E, S
         * Valid rotations are x, y, and z rotations.
         * @param turn the turn to be performed
         */
    public void turn (String turn, boolean turnWholeCube) {
        //See the first case (B) to understand how all cases work
        char[] preChange; //Directions prior to turning
        char[] postChange; //What the directions change to after the turn
        Cubie[][] matrix = new Cubie[3][3]; //matrix to be rotated

        switch(turn) {
            case "B":
                preChange = new char[] {'B', 'U', 'R', 'D', 'L'};
                postChange = new char[] {'B', 'L', 'U', 'R', 'D'};
                //Transfer cubie data into matrix to be rotated
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[Math.abs(j-2)][2][i];
                    }
                }
                //Rotate the matrix
                matrix = rotateMatrix(matrix, 90, preChange, postChange, turnWholeCube);
                //Reset the actual cube's cubies to those of the rotated matrix
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[Math.abs(j-2)][2][i] = matrix[i][j];
                    }
                }
                break;

            case "B'":
                preChange = new char[] {'B', 'U', 'R', 'D', 'L'};
                postChange = new char[] {'B', 'R', 'D', 'L', 'U'};
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[Math.abs(j-2)][2][i];
                    }
                }
                matrix = rotateMatrix(matrix, -90, preChange, postChange, turnWholeCube);

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[Math.abs(j-2)][2][i] = matrix[i][j];
                    }
                }
                break;

            case "D" :
                preChange = new char[] {'D', 'L', 'B', 'R', 'F'};
                postChange = new char[] {'D', 'F', 'L', 'B', 'R'};
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[j][i][2];
                    }
                }
                matrix = rotateMatrix(matrix, 90, preChange, postChange, turnWholeCube);

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[j][i][2] = matrix[i][j];
                    }
                }
                break;

            case "D'" :
                preChange = new char[] {'D', 'F', 'L', 'B', 'R'};
                postChange = new char[] {'D', 'L', 'B', 'R', 'F'};
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[j][i][2];
                    }
                }
                matrix = rotateMatrix(matrix, -90, preChange, postChange, turnWholeCube);

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[j][i][2] = matrix[i][j];
                    }
                }
                break;

            case "E" :
                preChange = new char[] {'L', 'B', 'R', 'F'};
                postChange = new char[] {'F', 'L', 'B', 'R'};
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[j][i][1];
                    }
                }
                matrix = rotateMatrix(matrix, 90, preChange, postChange, turnWholeCube);

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[j][i][1] = matrix[i][j];
                    }
                }
                break;

            case "E'" :
                preChange = new char[] {'F', 'L', 'B', 'R'};
                postChange = new char[] {'L', 'B', 'R', 'F'};
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[j][i][1];
                    }
                }
                matrix = rotateMatrix(matrix, -90, preChange, postChange, turnWholeCube);

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[j][i][1] = matrix[i][j];
                    }
                }
                break;

            case "F":
                preChange = new char[] {'F', 'U', 'R', 'D', 'L'};
                postChange = new char[] {'F', 'R', 'D', 'L', 'U'};
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[j][0][i];
                    }
                }
                matrix = rotateMatrix(matrix, 90, preChange, postChange, turnWholeCube);

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[j][0][i] = matrix[i][j];
                    }
                }
                break;

            case "F'":
                preChange = new char[] {'F', 'U', 'R', 'D', 'L'};
                postChange = new char[] {'F', 'L', 'U', 'R', 'D'};
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[j][0][i];
                    }
                }
                matrix = rotateMatrix(matrix, -90, preChange, postChange, turnWholeCube);

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[j][0][i] = matrix[i][j];
                    }
                }
                break;

            case "L":
                preChange = new char[] {'L', 'B', 'D', 'F', 'U'};
                postChange = new char[] {'L', 'U', 'B', 'D', 'F'};
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[0][Math.abs(j-2)][i];
                    }
                }
                matrix = rotateMatrix(matrix, 90, preChange, postChange, turnWholeCube);

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[0][Math.abs(j-2)][i] = matrix[i][j];
                    }
                }
                break;

            case "L'":
                preChange = new char[] {'L', 'U', 'B', 'D', 'F'};
                postChange = new char[] {'L', 'B', 'D', 'F', 'U'};
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[0][Math.abs(j-2)][i];
                    }
                }
                matrix = rotateMatrix(matrix, -90, preChange, postChange, turnWholeCube);

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[0][Math.abs(j-2)][i] = matrix[i][j];
                    }
                }
                break;

            case "M":
                preChange = new char[] {'B', 'D', 'F', 'U'};
                postChange = new char[] {'U', 'B', 'D', 'F'};
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[1][Math.abs(j-2)][i];
                    }
                }
                matrix = rotateMatrix(matrix, 90, preChange, postChange, turnWholeCube);

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[1][Math.abs(j-2)][i] = matrix[i][j];
                    }
                }
                break;

            case "M'":
                preChange = new char[] {'U', 'B', 'D', 'F'};
                postChange = new char[] {'B', 'D', 'F', 'U'};
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[1][Math.abs(j-2)][i];
                    }
                }
                matrix = rotateMatrix(matrix, -90, preChange, postChange, turnWholeCube);

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[1][Math.abs(j-2)][i] = matrix[i][j];
                    }
                }
                break;

            case "R":
                preChange = new char[] {'R', 'U', 'B', 'D', 'F'};
                postChange = new char[] {'R', 'B', 'D', 'F', 'U'};
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[2][j][i];
                    }
                }
                matrix = rotateMatrix(matrix, 90, preChange, postChange, turnWholeCube);

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[2][j][i] = matrix[i][j];
                    }
                }
                break;

            case "R'":
                preChange = new char[] {'R', 'B', 'D', 'F', 'U'};
                postChange = new char[] {'R', 'U', 'B', 'D', 'F'};
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[2][j][i];
                    }
                }
                matrix = rotateMatrix(matrix, -90, preChange, postChange, turnWholeCube);

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[2][j][i] = matrix[i][j];
                    }
                }
                break;

            case "S":
                preChange = new char[] {'U', 'R', 'D', 'L'};
                postChange = new char[] {'R', 'D', 'L', 'U'};
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[j][1][i];
                    }
                }
                matrix = rotateMatrix(matrix, 90, preChange, postChange, turnWholeCube);

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[j][1][i] = matrix[i][j];
                    }
                }
                break;

            case "S'":
                preChange = new char[] {'U', 'R', 'D', 'L'};
                postChange = new char[] {'L', 'U', 'R', 'D'};
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[j][1][i];
                    }
                }
                matrix = rotateMatrix(matrix, -90, preChange, postChange, turnWholeCube);

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[j][1][i] = matrix[i][j];
                    }
                }
                break;

            case "U" :
                preChange = new char[] {'U', 'F', 'L', 'B', 'R'};
                postChange = new char[] {'U', 'L', 'B', 'R', 'F'};
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[j][Math.abs(i-2)][0];
                    }
                }
                matrix = rotateMatrix(matrix, 90, preChange, postChange, turnWholeCube);

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[j][Math.abs(i-2)][0] = matrix[i][j];
                    }
                }
                break;

            case "U'" :
                preChange = new char[] {'U', 'L', 'B', 'R', 'F'};
                postChange = new char[] {'U', 'F', 'L', 'B', 'R'};
                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        matrix[i][j] = cubiePos[j][Math.abs(i-2)][0];
                    }
                }
                matrix = rotateMatrix(matrix, -90, preChange, postChange, turnWholeCube);

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        cubiePos[j][Math.abs(i-2)][0] = matrix[i][j];
                    }
                }
                break;

            case "x":
                performMoves("R M' L'", true);
                //turn("R"); turn("M'"); turn("L'");
                break;

            case "x'":
                performMoves("R' M L", true);
                //turn("R'"); turn("M"); turn("L");
                break;

            case "y":
                performMoves("U E' D'", true);
                //turn("U"); turn("E'"); turn("D'");
                break;

            case "y'":
                performMoves("U' E D", true);
                //turn("U'"); turn("E"); turn("D");
                break;

            case "z":
                performMoves("F S B'", true);
                //turn("F"); turn("S"); turn("B'");
                break;

            case "z'":
                performMoves("F' S' B", true);
                //turn("F'"); turn("S'"); turn("B");
                break;

        }
    }

    public void turn (String turn) {
        turn(turn, false);
    }


        /**
         * Rotates a given 2D matrix as specified by {@code degrees}, where {@code degrees}
         * can either be 90, indicating a clockwise rotation, or -90, indicating a counterclockwise
         * rotation. {@code postChange} dictates how the direction of a cubie's colors should
         * change after the rotation, the original directions being denoted by {@code preChange}.
         * @param orig the original matrix
         * @param degrees degrees by which to rotate, can be 90 or -90
         * @param preChange the set of direction prior to rotation
         * @param postChange the corresponding set of direction to change the {@code preChange} directions to
         * @return the rotated matrix
         */
    private Cubie[][] rotateMatrix(Cubie[][] orig, int degrees, char[] preChange,
                                   char[] postChange, boolean turnWholeCube) {
        Cubie[][] rotated = new Cubie[3][3];
        if(degrees == 90) {
            //Transpose the matrix
            for(int i = 0; i<3; i++) {
                for(int j = 0; j<3; j++) {
                    rotated[i][j] = orig[j][i];
                }
            }
            //Reverse all the rows
            for(int i = 0; i<3; i++) {
                for(int j = 0; j<rotated[0].length/2; j++) {
                    Cubie tempCubie = rotated[i][3-j-1];
                    rotated[i][3-j-1] = rotated[i][j];
                    rotated[i][j] = tempCubie;
                }
            }

            if (!(turnWholeCube)) {
                char[] preChangeCenters = {'U', 'D', 'L', 'R'};
                char[] postChangeCenters = {'R', 'L', 'U', 'D'};

                for(int i = 0; i<3; i++) {
                    for(int j = 0; j<3; j++) {
                        char centerDir = rotated[i][j].getCenterDir();
                        for (int k = 0; k < preChangeCenters.length; k++) {
                            if (centerDir == preChangeCenters[k]) {
                                rotated[i][j].setCenterDir(postChangeCenters[k]);
                                break;
                            }
                        }
                    }

                }
            }
        }
        else if(degrees == -90) {
            //Transpose the matrix
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    rotated[i][j] = orig[j][i];
                }
            }

            //Reverse all the columns
            for (int i = 0; i < rotated[0].length / 2; i++) {
                for (int j = 0; j < 3; j++) {
                    Cubie tempCubie = rotated[3 - i - 1][j];
                    rotated[3 - i - 1][j] = rotated[i][j];
                    rotated[i][j] = tempCubie;
                }
            }

            if (!(turnWholeCube)) {
                char[] preChangeCenters = {'U', 'D', 'L', 'R'};
                char[] postChangeCenters = {'L', 'R', 'D', 'U'};

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        char centerDir = rotated[i][j].getCenterDir();
                        for (int k = 0; k < preChangeCenters.length; k++) {
                            if (centerDir == preChangeCenters[k]) {
                                rotated[i][j].setCenterDir(postChangeCenters[k]);
                                break;
                            }
                        }
                    }

                }
            }
        }

        //Change the direction of all colors appropriately as well before returning the array
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                CubieColor[] tempColors = rotated[i][j].getColors();
                for(int k = 0; k<tempColors.length; k++) {
                    int index = 6;
                    for(int x = 0; x < preChange.length; x++) {
                        if(tempColors[k].getDir() == preChange[x]) {
                            index = x;
                        }
                    }
                    if(index<postChange.length)
                        tempColors[k].setDir(postChange[index]);
                }
                rotated[i][j].setColors(tempColors);
            }
        }
        return rotated;
    }

    private Cubie[][] rotateMatrix(Cubie[][] orig, int degrees, char[] preChange,
                                   char[] postChange) {
        return rotateMatrix(orig, degrees, preChange, postChange, false);
    }

    /**
     * Loops through the characters in a String of standard turning notation to apply the set of moves to the cube
     * Checks for clockwise, double, and counterclockwise turns
     * @param moves the moves to be applied to the cube
     * @return the moves performed on the cube (same as {@code moves})
     */
    public String performMoves(String moves) {
        return performMoves(moves, false);
    }

    public String performMoves(String moves, boolean turnWholeCube) {
        for(int i = 0; i<moves.length(); i++) {
            if(moves.substring(i, i+1) != " ") { //Only check if there is a meaningful character
                if(i != moves.length()-1) {
                    if(moves.substring(i+1, i+2).compareTo("2") == 0) {
                        //Turning twice ex. U2
                        turn(moves.substring(i, i+1), turnWholeCube);
                        turn(moves.substring(i, i+1), turnWholeCube);
                        i++; //Skip the "2" for the next iteration
                    }
                    else if(moves.substring(i+1,i+2).compareTo("'") == 0) {
                        //Making a counterclockwise turn ex. U'
                        turn(moves.substring(i, i+2), turnWholeCube);
                        i++; //Skip the apostrophe for the next iteration
                    }
                    else {
                        //Regular clockwise turning
                        turn(moves.substring(i, i+1), turnWholeCube);
                    }
                }
                else {
                    //Nothing is after the turn letter, so just perform the turn
                    turn(moves.substring(i, i+1), turnWholeCube);
                }
            }
        }
        return moves;
    }

    /**
     * Optimizes the {@code moves} inputed by reducing redundant and unnecessary turns or rotations.
     * For example, "U U'" would be negated; "U U2" is simplified to "U'"; and "U U" is simplified
     * to "U2".
     * @param moves the String of moves to be optimized
     * @return the optimized set of moves
     */
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
                                    moves = moves.substring(0, i) + moves.substring(i, i+1)
                                            + moves.substring(i+5);
                                    i--;
                                } else {
                                    //Ex. "U2 U" --> "U'"
                                    moves = moves.substring(0, i) + moves.substring(i, i+1) + "'"
                                            + moves.substring(i+4);
                                    i--;
                                }
                            } else {
                                //Ex. "U2 U" --> "U'"
                                moves = moves.substring(0, i) + moves.substring(i, i+1) + "'"
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
                                    moves = moves.substring(0, i) + moves.substring(i, i+1)
                                            + moves.substring(i+5);
                                    i--;
                                } else if(moves.substring(i+4, i+5).compareTo("'") == 0) {
                                    //Ex. "U' U'" --> "U2"
                                    moves = moves.substring(0, i) + moves.substring(i, i+1) + "2"
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
                                    moves = moves.substring(0, i) + moves.substring(i, i+1) + "'"
                                            + moves.substring(i+4);
                                    i--;
                                } else if(moves.substring(i+3, i+4).compareTo("'") == 0) {
                                    //Ex. "U U'" gets negated
                                    moves = moves.substring(0, i) + moves.substring(i+4);
                                    i--;
                                } else {
                                    //Ex. "U U" --> "U2"
                                    moves = new String(moves.substring(0, i) + moves.substring(i, i+1) + "2"
                                            + moves.substring(i+3));
                                    i--;
                                }
                            } else {
                                //Ex. "U U" --> "U2"
                                moves = new String(moves.substring(0, i) + moves.substring(i, i+1) + "2"
                                        + moves.substring(i+3));
                                i--;
                            }
                        }

                    }
                }
            }
        }

        return moves;
    }

    /**
     * Ромашка: желтый центр, белые ребра сверху.
     * Makes the sunflower (yellow center in the  middle with 4 white edges surrounding it).
     * The sunflower can then be used by makeCross() to make the white cross
     * @return moves used to make sunflower
     */
    public String makeSunflower() {
        String moves = new String();
        moves += orientWhiteCenter();

        //Если белый на детали смотрит вниз, подготавливаем место для нее сверху (отводим верхние белые детали) и поднимаем
        if(numWhiteEdgesOriented() < 5) {
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(cubiePos[i][j][2].isEdgeCubie()) { // По ребрам нижней грани
                        if(cubiePos[i][j][2].getDirOfColor('W') == 'D') {
                            moves += prepareSlot(i, j, 0, 'W'); // Делаем U, чтоб на месте детали не было белого цвета
                            //Get the vertical plane in which the cubie lies
                            char turnToMake = cubiePos[i][j][2].verticalFace(i, j); // Определяем, деталь на грани F, L, R или B
                            moves += performMoves("" + turnToMake + "2 "); // Дважды делаем движение F, L, R или B
                        }
                    }
                }
            }
        }

        // Если на нижнем слове есть ребро с белым, но белый смотрит не вниз, также подготавливаем
        // место для него сверху и правильно повораичваем
        if(numWhiteEdgesOriented() < 5) {
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(cubiePos[i][j][2].isEdgeCubie()) {
                        if(cubiePos[i][j][2].getDirOfColor('W') != 'A' && cubiePos[i][j][2].getDirOfColor('W') != 'D') {
                            char vert = cubiePos[i][j][2].verticalFace(i, j);
                            moves += prepareSlot(i, j, 0, 'W');
                            if(vert == 'F') {
                                moves += performMoves("F' U' R ");
                            } else if(vert == 'R') {
                                moves += performMoves("R' U' B ");
                            } else if(vert == 'B') {
                                moves += performMoves("B' U' L ");
                            } else if(vert == 'L') {
                                moves += performMoves("L' U' F ");
                            }
                        }
                    }
                }

            }
        }

        // Если ребро с белым находится в среднем слое, переворачиваем его
        if(numWhiteEdgesOriented() < 5) {
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(cubiePos[i][j][1].isEdgeCubie()) {
                        CubieColor[] tempColors = cubiePos[i][j][1].getColors();
                        for(int k = 0; k<2; k++) {
                            if(tempColors[k].getColor() == 'W') {
                                if(i == 0 && j == 0) {
                                    if(tempColors[k].getDir() == 'L') {
                                        String res = prepareSlot(1, 0, 0, 'W');
                                        if (res.equals("Wrong cube")) {
                                            return res;
                                        }
                                        moves += res + performMoves("F ");
                                    } else {
                                        String res = prepareSlot(0, 1, 0, 'W');
                                        if (res.equals("Wrong cube")) {
                                            return res;
                                        }
                                        moves += res + performMoves("L' ");
                                    }
                                }
                                else if(i == 2 && j == 0) {
                                    if(tempColors[k].getDir() == 'F') {
                                        String res = prepareSlot(2, 1, 0, 'W');
                                        if (res.equals("Wrong cube")) {
                                            return res;
                                        }
                                        moves += res + performMoves("R ");
                                    } else {
                                        String res = prepareSlot(1, 0, 0, 'W');
                                        if (res.equals("Wrong cube")) {
                                            return res;
                                        }
                                        moves += res + performMoves("F' ");
                                    }
                                }
                                else if(i == 2 && j == 2) {
                                    if(tempColors[k].getDir() == 'B') {
                                        String res = prepareSlot(2, 1, 0, 'W');
                                        if (res.equals("Wrong cube")) {
                                            return res;
                                        }
                                        moves += res + performMoves("R' ");
                                    } else {
                                        String res = prepareSlot(1, 2, 0, 'W');
                                        if (res.equals("Wrong cube")) {
                                            return res;
                                        }
                                        moves += res + performMoves("B ");
                                    }
                                }
                                else {
                                    if(tempColors[k].getDir() == 'B') {
                                        String res = prepareSlot(0, 1, 0, 'W');
                                        if (res.equals("Wrong cube")) {
                                            return res;
                                        }
                                        moves += res + performMoves("L ");
                                    } else {
                                        String res = prepareSlot(1, 2, 0, 'W');
                                        if (res.equals("Wrong cube")) {
                                            return res;
                                        }
                                        moves += res + performMoves("B' ");
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        // Если ребро в верхнем слое стоит неправильно
        if(numWhiteEdgesOriented() < 5) {
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(cubiePos[i][j][0].isEdgeCubie()) {
                        if(cubiePos[i][j][0].getDirOfColor('W') != 'A' && cubiePos[i][j][0].getDirOfColor('W') != 'U') {
                            char vert = cubiePos[i][j][0].verticalFace(i, j);
                            if(vert == 'F') {
                                moves += performMoves("F U' R ");
                            } else if(vert == 'R') {
                                moves += performMoves("R U' B ");
                            } else if(vert == 'B') {
                                moves += performMoves("B U' L ");
                            } else if(vert == 'L') {
                                moves += performMoves("L U' F ");
                            }
                        }
                    }
                }

            }
        }

        if(numWhiteEdgesOriented() < 4) {
            moves += makeSunflower();
        }

        return optimizeMoves(moves);
    }

    /**
     * Utility method for makeSunflower()
     * Prepares a slot in the U face for white edges to be brought up into the U layer without misorienting white
     * edges already in the U layer
     * @param x the x position of the cubie to prepare
     * @param y the y position
     * @param z the z position
     * @param color the color which should not remain in the prepared slot
     * @return moves used to prepare the edge slot
     */
    public String prepareSlot(int x, int y, int z, char color) {
        int numUTurns = 0;
        CubieColor[] tempColor = cubiePos[x][y][z].getColors();
        long startTime = System.currentTimeMillis();
        while((tempColor[0].getColor() == color || tempColor[1].getColor() == color) && numUTurns < 5){
            if (checkInfLoop(startTime)) return "Wrong cube";
            performMoves("U");
            tempColor = cubiePos[x][y][z].getColors();
            numUTurns++;
        }

        if(numUTurns == 0 || numUTurns == 4) {
            return "";
        }
        else if(numUTurns == 1) {
            return "U ";
        }
        else if (numUTurns == 2) {
            return "U2 ";
        }
        else return "U' ";
    }

    /**
     * Utility method for makeSunflower()
     * @return the number of white edges that are currently in the U layer
     */
    public int numWhiteEdgesOriented() {
        int numOriented = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(cubiePos[i][j][0].isEdgeCubie()) {
                    if(cubiePos[i][j][0].getDirOfColor('W') == 'U') {
                        numOriented++;
                    }
                }
            }
        }
        return numOriented;
    }

    private String orientWhiteCenter() {
        char whiteCenterDir = cubiePos[1][1][2].getCenterDir();
        switch (whiteCenterDir) {
            case 'L':
                return "D ";
            case 'D':
                return performMoves("D2 ");
            case 'R':
                return performMoves("D' ");
        }
        return "";
    }

    /**
     * Делает белый крест путем опускания верхних ребер вниз.
     */

    // Опускаем верхние ребра вниз, если они стоят правильно. Если неправильно, делаем U.
    public String makeWhiteCross() {
        String moves = new String();

        long startTime = System.currentTimeMillis();
        while(numWhiteEdgesOriented() != 0) {
            if (checkInfLoop(startTime)) return "Wrong cube";
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(cubiePos[i][j][0].isEdgeCubie()) {
                        CubieColor[] tempColors = cubiePos[i][j][0].getColors();
                        if(tempColors[0].getColor() == 'W' || tempColors[1].getColor() == 'W') {
                            for(int k = 0; k<2; k++) {
                                if((tempColors[k].getColor() == 'R' && tempColors[k].getDir() == 'L') ||
                                        (tempColors[k].getColor() == 'G' && tempColors[k].getDir() == 'F') ||
                                        (tempColors[k].getColor() == 'O' && tempColors[k].getDir() == 'R')||
                                        (tempColors[k].getColor() == 'B' && tempColors[k].getDir() == 'B')) {
                                    moves+=performMoves(cubiePos[i][j][0].verticalFace(i, j) + "2 ") ;
                                }
                            }
                        }
                    }
                }
            }
            //Turn U to try lining up edges that have not been turned down yet
            moves+=performMoves("U ");
        }
        return optimizeMoves(moves);
    }

    /**
     * Completes the white layer by inserting any white corners in the U layer and fixing misoriented
     * white corners until there are no more white corners in the U layer.
     * @return the moves used to complete the white layer
     */
    public String finishWhiteLayer() {
        String moves = new String();
        moves+=insertCornersInU();
        moves+="\n";
        String res = insertMisorientedCorners();
        if (res.equals("Wrong cube")) {
            return res;
        }
        moves+=res;
        moves+="\n";
        long startTime = System.currentTimeMillis();
        while(whiteCornerinU()) {
            if (checkInfLoop(startTime)) return "Wrong cube";
            res = insertCornersInU();
            if (res.equals("Wrong cube")) {
                return res;
            }
            moves+=res;
            moves+="\n";
            res = insertMisorientedCorners();
            if (res.equals("Wrong cube")) {
                return res;
            }
            moves+=res;
            moves+="\n";
        }
        return optimizeMoves(moves);
    }

    /**
     * Inserts any white corners that are in the U layer. First positions them to the position (2, 0, 0), then
     * makes U turns and y rotations until the white corner is above its respective slot, and finally inserts
     * the corner by repetitively executing R U R' U'. This is repeated of all whit corners in the U layer.
     * @return moves used to insert white corners that are in the U layer
     */
    public String insertCornersInU() {
        String moves = new String();

        for(int y = 0; y<3; y++) {
            for(int x = 0; x<3; x++) {
                if(cubiePos[x][y][0].isCornerCubie() && cubiePos[x][y][0].isWhiteCorner()) {
                    //Make U turns until cubie is at (2, 0, 0)
                    if(x==0) {
                        if(y==0) {
                            moves+=performMoves("U' ");
                        }
                        else {
                            moves+=performMoves("U2 ");
                        }
                    }
                    else {
                        if(y==2) {
                            moves+=performMoves("U ");
                        }
                    }
                    //Set x and y = 0 for the next loop to avoid using while loop
                    y=0; x=0;

                    //Get cubie above respective slot in first layer
                    int numUTurns = 0;
                    int yRotations = 0;
                    long startTime = System.currentTimeMillis();
                    while(!whiteCornerPrepared()) {
                        if (checkInfLoop(startTime)) return "Wrong cube";
                        performMoves("U y'"); numUTurns++; yRotations++;
                    }
                    if(numUTurns == 1) {
                        moves += "U ";
                    } else if(numUTurns == 2) {
                        moves += "U2 ";
                    } else if(numUTurns == 3) {
                        moves += "U' ";
                    }
                    if(yRotations == 1) {
                        moves+="y' ";
                    } else if(yRotations == 2) {
                        moves += "y2 ";
                    } else if(yRotations == 3) {
                        moves += "y ";
                    }

                    startTime = System.currentTimeMillis();
                    while(!cornerInserted(2, 0, 2)){
                        if (checkInfLoop(startTime)) return "Wrong cube";
                        performMoves("R U R' U'");
                        moves += "R U R' U' ";
                    }
//					if(numSexyMoves == 5) { //5 sexy moves can be condensed into "U R U' R'"
//						moves += "U R U' R' ";
//					}
//					else
//					}
                }
            }
        }

        return moves;
    }

    /**
     * Properly inserts white corners that are in the first layer but not oriented correctly
     * @return moves used to properly orient misoriented white corners
     */
    public String insertMisorientedCorners() {
        String moves = new String();
        for(int i = 0; i<4; i++) {
            moves += performMoves("y ");
            if(!cornerInserted(2,0,2)) {
                if(cubiePos[2][0][2].isWhiteCorner()) {
                    if(!cornerInserted(2,0,2)) {
                        //Use R U R' U' to get corner to U layer, then insert it in appropriate slot
                        moves+=performMoves("R U R' U' ");
                        String res = insertCornersInU();
                        if (res.equals("Wrong cube")) {
                            return res;
                        }
                        moves+=res;
                    }
                }
            }
        }
        return moves;
    }

    /**
     * Utility method for insertCornersInU().
     * Checks for whether the corner cubie at (2, 0, 0) belongs in (2, 0, 2).
     * @return true if cubie at (2, 0, 0) belongs in (2, 0, 2), else false
     */
    public boolean whiteCornerPrepared() {
        boolean whiteUp = false;

        //Figure out whether the corner cubie is even a white corner
        if(cubiePos[2][0][0].isCornerCubie() && cubiePos[2][0][0].getDirOfColor('W') == 'A') {
            return false;
        }

        //If the cubie is a white corner, figure out whether the white sticker is facing up
        if(cubiePos[2][0][0].getDirOfColor('W') == 'U')
            whiteUp = true;

        //Based on whether white is up, check accordingly if the corner is above the appropriate slot
        if(whiteUp) {
            return (cubiePos[2][0][0].getColorOfDir('R') == cubiePos[1][0][1].getColors()[0].getColor() &&
                    cubiePos[2][0][0].getColorOfDir('F') == cubiePos[2][1][1].getColors()[0].getColor()	);
        }
        else {
            /*Either the color on the right of the cubie matches its respective center piece OR
             *the color on the front of the cubie matches its respective center piece
             *It is not possible for both to match because if white is not facing up, it will either be facing front or right
             */
            return (cubiePos[2][0][0].getColorOfDir('R') == cubiePos[2][1][1].getColors()[0].getColor() ||
                    cubiePos[2][0][0].getColorOfDir('F') == cubiePos[1][0][1].getColors()[0].getColor());
        }
    }

    /**
     * Correctly checks whether the corner at (2, 0, 2) is solved.
     * @param x the position to check for (although this method is only called with (2, 0, 2).
     * @param y see above
     * @param z see above
     * @return true if corner is solved, else false
     */
    public boolean cornerInserted(int x, int y, int z) {
        if(cubiePos[x][y][z].isCornerCubie()) {
            return (cubiePos[x][y][z].getColorOfDir('D') == cubiePos[1][1][2].getColors()[0].getColor() &&
                    cubiePos[x][y][z].getColorOfDir('F') == cubiePos[1][0][1].getColors()[0].getColor() &&
                    cubiePos[x][y][z].getColorOfDir('R') == cubiePos[2][1][1].getColors()[0].getColor());
        }
        return false;
    }

    /**
     * Utility method for insertCornersinU()
     * @return if there are any white corners in the U layer
     */
    public boolean whiteCornerinU() {
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                if(cubiePos[i][j][0].isCornerCubie()) {
                    //If a cubie does not have a color, getDirOfColor returns 'A'
                    if(cubiePos[i][j][0].getDirOfColor('W') != 'A')
                        return true;
                }
            }
        }
        return false;
    }

    public String orientVerticalCenters() {
        String moves = "";
        for (int i = 0; i < 4; i++) {
            char centerDir = cubiePos[1][0][1].getCenterDir();
            if (centerDir == 'U') {
                moves += performMoves("y ");
                continue;
            }
            moves += performMoves("F2 U2 ");
            if (centerDir == 'R') {
                moves += performMoves("F' ");
            }
            else if (centerDir == 'L') {
                moves += performMoves("F ");
            }
            else moves += performMoves("F2 ");
            moves += performMoves("U' U' F2 ");
            moves += performMoves("y ");
        }
        return moves;
    }

    public String putEdgeInSecondLayer(int x, int y) {
        String moves = "";
        if(y == 1) {
            if(x == 0) {
                moves += performMoves("U' ");
            } else {
                moves += performMoves("U ");
            }
        }
        else if(y == 2){
            moves += performMoves("U2 ");
        }
        int numUTurns = 0;
        int yRotations = 0;
        long startTime = System.currentTimeMillis();
        while(cubiePos[1][0][0].getColorOfDir('F') != cubiePos[1][0][1].getColorOfDir('F')) {
            if (checkInfLoop(startTime)) return "Wrong cube";
            performMoves("U y' "); numUTurns++; yRotations++;
        }

        if(numUTurns == 1) {
            moves += "U ";
        } else if(numUTurns == 2) {
            moves += "U2 ";
        } else if(numUTurns == 3) {
            moves += "U' ";
        }
        if (yRotations == 1) {
            moves+="y' ";
        } else if (yRotations == 2) {
            moves += "y2 ";
        } else if (yRotations == 3) {
            moves += "y ";
        }

        // Ставим во второй слой в зависимости от верхнего цвета детали
        if (cubiePos[1][0][0].getColorOfDir('U') == cubiePos[0][1][1].getColorOfDir('L')) {
            moves += performMoves("U' L' U' L U y' R U R' U' ");
        }
        else if (cubiePos[1][0][0].getColorOfDir('U') == cubiePos[2][1][1].getColorOfDir('R')){
            moves += performMoves("U R U R' U' y L' U' L U ");
        }
        return moves;
    }

    private String checkEdgesInSecondLayer() {
        int numYTurns = 0;
        for (int i = 0; i < 4; i++) {
            if ((cubiePos[0][0][1].getColorOfDir('F') != cubiePos[1][0][1].getColorOfDir('F')) ||
                    (cubiePos[0][0][1].getColorOfDir('L') != cubiePos[0][1][1].getColorOfDir('L'))) {
                if (numYTurns == 1) {
                    return performMoves("y L' U' L U y' R U R' U' ");
                }
                else if (numYTurns == 2) {
                    return performMoves("y2 L' U' L U y' R U R' U' ");
                }
                else if (numYTurns == 3) {
                    return performMoves("y' L' U' L U y' R U R' U' ");
                }
            }
            else if ((cubiePos[2][0][1].getColorOfDir('F') != cubiePos[1][0][1].getColorOfDir('F')) ||
                    (cubiePos[2][0][1].getColorOfDir('R') != cubiePos[2][1][1].getColorOfDir('R'))) {
                if (numYTurns == 1) {
                    return performMoves("y R U R' U' y L' U' L U ");
                }
                else if (numYTurns == 2) {
                    return performMoves("y2 R U R' U' y L' U' L U ");
                }
                else if (numYTurns == 3) {
                    return performMoves("y' R U R' U' y L' U' L U ");
                }
            }
            performMoves("y ");
            numYTurns++;
        }
        return "";
    }

    private String checkEdgesInYellowLayer() {
        String moves = "";
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (cubiePos[x][y][0].isEdgeCubie()) {
                    if (!(cubiePos[x][y][0].edgeHasColor('Y'))) {
                        String res = putEdgeInSecondLayer(x, y);
                        if (res.equals("Wrong cube")) {
                            return res;
                        }
                        moves += res;
                    }
                }
            }
        }
        return moves;
    }


    public String solveSecondLayer() {
        String moves = "";
        String movesSecondLayer = checkEdgesInSecondLayer();
        long startTime = System.currentTimeMillis();
        while (!(movesSecondLayer.isEmpty())) {
            if (checkInfLoop(startTime)) return "Wrong cube";
            moves += movesSecondLayer;
            moves += checkEdgesInYellowLayer();
            movesSecondLayer = checkEdgesInSecondLayer();
        }
        return moves;
    }

    /**
     * Utility method for yellowEdgeOrientation() and makeYellowCross()
     * @return the number of yellow edges that are already oriented in the U layer
     */
    public int numYellowEdgesOriented(){
        int numOriented = 0;
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                if(cubiePos[i][j][0].isEdgeCubie() && cubiePos[i][j][0].getDirOfColor('Y') == 'U')
                    numOriented++;
            }
        }
        return numOriented;
    }



    /**
     * Utility method for makeYellowCross(). Determines the shape that the oriented
     * yellow edges make.
     * @return Dot, L, Bar, or Cross
     */
    public String yellowEdgeOrientation() {
        String status = new String();
        // Количество деталей, у которых желтый цвет сверху
        int numOriented = numYellowEdgesOriented();

        if(numOriented == 4) {
            status = "Cross";
        }
        else if(numOriented == 0) {
            status = "Dot";
        }
        else if(numOriented == 2) {
            // Если ориентированы две детали, то это либо галка, либо палка
            int[] xValues = new int[2];
            int index = 0;
            for(int i = 0; i<3; i++) {
                for(int j = 0; j<3; j++) {
                    if(cubiePos[i][j][0].isEdgeCubie() && cubiePos[i][j][0].getDirOfColor('Y') == 'U') {
                        xValues[index] = i; index++;
                    }
                }
            }
            if(Math.abs(xValues[0]-xValues[1])%2 == 0) {
                status = "Bar";
            } else {
                status = "L";
            }
        }

        return status;
    }

    /**
     * Делает желтый неориентированный крест
     */
    public String makeYellowCross() {
        String moves = new String();
        String status = yellowEdgeOrientation();

        if(status.compareTo("Dot") == 0) {
            // Делаем галку
            moves += performMoves("F R U R' U' F' U2 F U R U' R' F' ");
        }
        else if(status.compareTo("L") == 0) {
            //Пока галка не смотрит налево и назад, делаем U
            long startTime = System.currentTimeMillis();
            while(cubiePos[0][1][0].getDirOfColor('Y') != 'U' || cubiePos[1][2][0].getDirOfColor('Y') != 'U') {
                if (checkInfLoop(startTime)) return "Wrong cube";
                moves += performMoves("U ");
            }
            moves += performMoves("F U R U' R' F' ");
        }
        else if(status.compareTo("Bar") == 0) {
            // Если палка смотри не горизонтально, делаем U
            long startTime = System.currentTimeMillis();
            while(cubiePos[0][1][0].getDirOfColor('Y') != 'U' || cubiePos[2][1][0].getDirOfColor('Y') != 'U') {
                if (checkInfLoop(startTime)) return "Wrong cube";
                moves += performMoves("U ");
            }
            moves += performMoves("F R U R' U' F' ");
        }
        return optimizeMoves(moves);
    }



    public String orientYellowCross() {
        String moves = new String();
        int numOriented = numYellowEdgesOrientedCorrectly();
        long startTime = System.currentTimeMillis();
        while (numOriented != 4) {
            if (checkInfLoop(startTime)) return "Wrong cube";
            if (numOriented < 2) {
                // Пока на левой детали желтый не смотрит налево, делаем U
                String res = putYellowEdgeAndOrient();
                if (res.equals("Wrong cube")) {
                    return res;
                }
                moves += res;
            }
            else {
                // Ставим деталь с желтым сверху слева спереди
                long startTime2 = System.currentTimeMillis();
                int tryNum = 0;
                while (!((cubiePos[2][1][0].getColorOfDir('R') == cubiePos[2][1][1].getColorOfDir('R')) &&
                        ((cubiePos[1][2][0].getColorOfDir('B') == cubiePos[1][2][1].getColorOfDir('B'))))) {
                    if (checkInfLoop(startTime2)) return "Wrong cube";
                    performMoves("y ");
                    tryNum++;
                    if (tryNum == 4) break;
                }
                if (tryNum == 1) {
                    moves += "y ";
                }
                else if (tryNum == 2) {
                    moves += "y2 ";
                }
                else if (tryNum == 3) {
                    moves += "y' ";
                }
                else if (tryNum == 4) {
                    moves += performMoves("y ");
                }
                String res = putYellowEdgeAndOrient();
                if (res.equals("Wrong cube")) {
                    return res;
                }
                moves += res;
            }
            numOriented = numYellowEdgesOrientedCorrectly();
        }
        return optimizeMoves(moves);
    }

    public int numYellowEdgesOrientedCorrectly() {
        int numOriented = 0;
        for (int i = 0; i < 4; i++) {
            if (cubiePos[1][0][0].getColorOfDir('F') == cubiePos[1][0][1].getColorOfDir('F')) {
                numOriented++;
            }
            performMoves("y ");
        }
        return numOriented;
    }

    public String putYellowEdgeAndOrient() {
        String moves = "";
        int uTurns = 0;
        moves += performMoves("R U R' U R U2 R' ");
        int numOr = numYellowEdgesOrientedCorrectly();
        long startTime = System.currentTimeMillis();
        while (numOr == 0) {
            if (checkInfLoop(startTime)) return "Wrong cube";
            performMoves("U ");
            uTurns++;
            numOr = numYellowEdgesOrientedCorrectly();
        }
        if (uTurns == 1) {
            moves += "U ";
        }
        else if (uTurns == 2) {
            moves += "U2 ";
        }
        else if (uTurns == 3) {
            moves += "U' ";
        }
        return moves;
    }

    public String orientYellowCenter() {
        String moves = "";
        char centerDir = cubiePos[1][1][0].getCenterDir();
        if (centerDir == 'U') return "";
        moves += performMoves("R U R' U R U2 R' U R U R' U R U2 R' U ");
        return moves;
    }

    private int countCorrectCorners() {
        int num = 0;
        for (int i = 0; i < 4; i++) {
            char sideColor = cubiePos[0][1][1].getColorOfDir('L');
            char frontColor = cubiePos[1][0][1].getColorOfDir('F');
            if (cubiePos[0][0][0].cornerHasColor(sideColor) && cubiePos[0][0][0].cornerHasColor(frontColor)) {
                num++;
            }
            performMoves("y ");
        }
        return num;
    }


    public String putYellowCorners() {
        if (checkCubeIsSolved()) {
            return "";
        }
        String moves = "";
        int numOfCorrectCorners = countCorrectCorners();
        long startTime = System.currentTimeMillis();
        while (numOfCorrectCorners != 4) {
            if (checkInfLoop(startTime)) return "Wrong cube";
            if (numOfCorrectCorners == 0) {
                moves += performMoves("R U' L' U R' U' L U ");
            }
            else {
                char sideColor = cubiePos[0][1][1].getColorOfDir('L');
                char frontColor = cubiePos[1][0][1].getColorOfDir('F');

                int yTurns = 0;

                long startTime2 = System.currentTimeMillis();
                while (true) {
                    if (checkInfLoop(startTime2)) return "Wrong cube";
                    if (cubiePos[0][0][0].cornerHasColor(sideColor) && cubiePos[0][0][0].cornerHasColor(frontColor)) {
                        break;
                    }
                    else {
                        performMoves("y ");
                        yTurns++;
                        sideColor = cubiePos[0][1][1].getColorOfDir('L');
                        frontColor = cubiePos[1][0][1].getColorOfDir('F');
                    }
                }

                if (yTurns == 1) {
                    moves += "y ";
                }
                else if (yTurns == 2) {
                    moves += "y2 ";
                }
                else if (yTurns == 3) {
                    moves += "y' ";
                }
                moves += performMoves("R U' L' U R' U' L U ");
            }
            numOfCorrectCorners = countCorrectCorners();
        }
        moves += performMoves("x2 ");
        for (int i = 0; i < 4; i++) {
            long startTime2 = System.currentTimeMillis();
            while (cubiePos[2][0][2].getDirOfColor('Y') != 'D') {
                if (checkInfLoop(startTime2)) return "Wrong cube";
                moves += performMoves("R U R' U' ");
            }
            moves += performMoves("D ");
        }

        long startTime2 = System.currentTimeMillis();
        while (cubiePos[0][0][2].getColorOfDir('F') != cubiePos[1][0][1].getColorOfDir('F')) {
            if (checkInfLoop(startTime2)) return "Wrong cube";
            moves += performMoves("D ");
        }
        return moves;
    }

    public boolean checkCubeIsSolved() {
        char upColor = cubiePos[0][0][0].getColorOfDir('U');
        char downColor = cubiePos[0][0][2].getColorOfDir('D');
        char leftColor = cubiePos[0][0][0].getColorOfDir('L');
        char rightColor = cubiePos[2][0][0].getColorOfDir('R');
        char frontColor = cubiePos[0][0][0].getColorOfDir('F');
        char backColor = cubiePos[0][2][0].getColorOfDir('B');
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    if (cubiePos[x][y][0].getDirOfColor(upColor) != 'U') {
                        return false;
                    }
                    if (cubiePos[x][y][2].getDirOfColor(downColor) != 'D') {
                        return false;
                    }
                    if (cubiePos[0][y][z].getDirOfColor(leftColor) != 'L') {
                        return false;
                    }
                    if (cubiePos[2][y][z].getDirOfColor(rightColor) != 'R') {
                        return false;
                    }
                    if (cubiePos[x][0][z].getDirOfColor(frontColor) != 'F') {
                        return false;
                    }
                    if (cubiePos[x][2][z].getDirOfColor(backColor) != 'B') {
                        return false;
                    }
                    if (cubiePos[x][y][z].isCenterCubie()) {
                        if (cubiePos[x][y][z].getCenterDir() != 'U') {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

}
