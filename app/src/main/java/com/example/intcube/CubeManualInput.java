package com.example.intcube;

import android.graphics.Color;

import java.util.HashMap;

public class CubeManualInput {
    private class Side {
        private Integer[][] Elements = new Integer[Size][Size];
        public void setElements(Integer[][] elements){
            Elements = elements;
        }

        public void turnLeft(){
            Integer[][] source = new Integer[Elements.length][Elements.length];
            for(int i = 0; i < Elements.length; i++)
                System.arraycopy(Elements[i], 0, source[i], 0, Elements.length);
            for(int i = 0; i < source.length; i++)
                for(int j = 0; j < source[i].length; j++)
                    Elements[i][j] = source[j][source.length - i - 1];
        }

        public void turnRight(){
            Integer[][] source = new Integer[Elements.length][Elements.length];
            for(int i = 0; i < source.length; i++)
                System.arraycopy(Elements[i], 0, source[i], 0, source[i].length);
            for(int i = 0; i < source.length; i++)
                for(int j = 0; j < source[i].length; j++)
                    Elements[i][j] = source[source.length - j - 1][i];
        }
    }

    public int Size = 0;
    public int MaxCountColors = 0;
    public final int[] Colors = new int[]{Color.RED, Color.BLUE, Color.parseColor("#FFA500"), Color.GREEN, Color.WHITE, Color.YELLOW };
    private final String[] NameEdgeForAdd = new String[]{"F", "R", "B", "L", "U", "D"};
    public HashMap<Integer, Integer> ColorsCount = new HashMap<>();
    private final HashMap<String, Side> cube = new HashMap<>();

    public CubeManualInput(String typeCube){
        switch(typeCube){
            case "2x2":
                Size = 2;
                MaxCountColors = 4;
                break;
            case "3x3":
                Size = 3;
                MaxCountColors = 9;
                break;
            case "4x4":
                Size = 4;
                MaxCountColors = 16;
                break;
            case "axis":
                break;
        }
        for(int color : Colors)
            ColorsCount.put(color, 0);
    }

    public void createSides() {
        for (int orderEdge = 0; orderEdge < 6; orderEdge++) {
            Integer[][] colorsOnEdge = new Integer[Size][Size];
            for (int row = 0; row < Size; row++) {
                for (int column = 0; column < Size; column++) {
                    int colorElement = 0;
                    if (Size % 2 == 1 && row == 1 && column == 1)
                    {
                        colorElement = Colors[orderEdge];
                        ColorsCount.put(Colors[orderEdge], 1);
                    }
                    else
                        colorElement = Color.GRAY;
                    colorsOnEdge[row][column] = colorElement;
                }
            }
            Side edge = new Side();
            edge.setElements(colorsOnEdge);
            cube.put(NameEdgeForAdd[orderEdge], edge);
        }
    }


    public Integer[][] getCurrentView(){
        Integer[][] result = new Integer[Size + 2][Size + 2];
        for(int i = 0; i < Size + 2; i++){
            for(int j = 0; j < Size + 2; j++){
                if(i == 0 || j == 0 || i == Size + 1 || j == Size + 1){
                    if(i == 0 && j > 0 && j < Size + 1)
                        result[i][j] = cube.get("U").Elements[Size - 1][j - 1];
                    else if(i == Size + 1 && j > 0 && j < Size + 1)
                        result[i][j] = cube.get("D").Elements[0][j - 1];
                    else if(j == 0 && i > 0 && i < Size + 1)
                        result[i][j] = cube.get("L").Elements[i - 1][Size - 1];
                    else if(j == Size + 1 && i > 0 && i < Size + 1)
                        result[i][j] = cube.get("R").Elements[i - 1][0];
                    else
                        result[i][j] = Integer.MAX_VALUE;
                }
                else
                    result[i][j] = cube.get("F").Elements[i - 1][j - 1];
            }
        }
        return result;
    }

    public void changeColor(int i, int j, Integer color){
        cube.get("F").Elements[i][j] = color;
    }

    public void rotateLeft(){
        replace(new String[]{ "F", "R", "B", "L" });
        cube.get("U").turnRight();
        cube.get("D").turnLeft();
    }

    public void rotateUp(){
        replace(new String[]{ "F", "D", "B", "U" });
        cube.get("D").turnLeft();
        cube.get("D").turnLeft();
        cube.get("L").turnLeft();
        cube.get("R").turnRight();
    }

    public void rotateRight(){
        replace(new String[]{ "F", "L", "B", "R" });
        cube.get("U").turnLeft();
        cube.get("D").turnRight();
    }

    public void rotateDown(){
        replace(new String[]{ "F", "U", "B", "D" });
        cube.get("U").turnLeft();
        cube.get("U").turnLeft();
        cube.get("L").turnRight();
        cube.get("R").turnLeft();
    }

    private void replace(String[] namesEdges){
        Integer[][][] source = new Integer[namesEdges.length][][];
        for(int i = 0; i < namesEdges.length; i++)
            source[i] = cube.get(namesEdges[i]).Elements;
        for(int i = 0; i < namesEdges.length; i++)
            cube.get(namesEdges[i]).setElements(source[(i + 1) % namesEdges.length]);
    }
}