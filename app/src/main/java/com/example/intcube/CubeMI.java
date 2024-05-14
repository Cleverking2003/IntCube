package com.example.intcube;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class CubeMI{

    enum Side {
        Front,
        Left,
        Up,
        Right,
        Down,
        Back
    }

    class Corner
    {
        public Map<String, ColorDrawable> Colors = new HashMap<>();
        public Corner(String location) {
            for (char side : location.toCharArray())
                Colors.put(String.valueOf(side), new ColorDrawable(Color.GRAY));
        }
    }

    class Edges
    {
        class Edge{
            public Map<String, ColorDrawable> Colors = new HashMap<>();

            public Edge(String location){
                for(char side : location.toCharArray())
                    Colors.put(String.valueOf(side), new ColorDrawable(Color.GRAY));
            }
        }
        public Edges.Edge[] Elements = new Edges.Edge[Size - 2];
        public Edges(String location){
            for(int i = 0; i < Size - 2; i++)
                Elements[i] = new Edge(location);
        }
    }

    class Centers{
        class Center{
            ColorDrawable Color;

            public Center(int color){
                Color = new ColorDrawable(color);
            }
        }
        String[] Orientation = new String[4];
        Centers.Center[][] Elements;

        public Centers(String[] sides, int length, int color){
            Elements = new Centers.Center[length][length];
            for(int row = 0; row < length; row++)
                for(int column = 0; column < length; column++)
                    if(row == length / 2 && column == length / 2 && length % 2 == 1) {
                        Elements[row][column] = new Center(color);
                        CountColors.put(color, CountColors.get(color) + 1);
                    }
                    else
                        Elements[row][column] = new Center(Color.GRAY);
            for(int i = 0; i < sides.length; i++)
                Orientation[i] = sides[i];
        }

        public void checkOrientation(String[] orientation){
            if(!Objects.equals(orientation[0], Orientation[0]))
                if(Objects.equals(orientation[1], Orientation[0]))
                    rotateElements(Side.Right, 1);
                else if(Objects.equals(orientation[3], Orientation[0]))
                    rotateElements(Side.Left, 1);
                else
                    rotateElements(Side.Left, 2);
            for(int i = 0; i < orientation.length; i++)
                Orientation[i] = orientation[i];
        }

        private void rotateElements(Side where, int count){
            Center[][] source = new Center[Elements.length][Elements.length];
            for(int row = 0; row < source.length; row++)
                for(int column = 0; column < source.length; column++)
                    source[row][column] = Elements[row][column];
            for(int row = 0; row < source.length; row++)
                for(int column = 0; column < source.length; column++)
                    if(count == 1)
                        if(where == Side.Left)
                            Elements[row][column] = source[column][Elements.length - 1 - row];
                        else
                            Elements[row][column] = source[Elements.length - 1 - column][row];
                    else
                        Elements[row][column] = source[Elements.length - 1 - row][Elements.length - 1 - column];
        }
    }

    int Size;
    int MaxCountColor;
    Map<Integer, Integer> CountColors = new HashMap<Integer, Integer>(){
        {put(Color.RED, 0);}
        {put(Color.BLUE, 0);}
        {put(Color.parseColor("#FFA500"), 0);}
        {put(Color.GREEN, 0);}
        {put(Color.WHITE, 0);}
        {put(Color.YELLOW, 0);}
    };

    Map<Side, String> LocationSides = new HashMap<Side, String>(){
        {put(Side.Left, "L");}
        {put(Side.Up, "U");}
        {put(Side.Right, "R");}
        {put(Side.Down, "D");}
        {put(Side.Front, "F");}
        {put(Side.Back, "B");}
    };
    public static Map<String, Corner> Corners = new HashMap<>();
    public static Map<String, Edges> Edges = new HashMap<>();
    public static Map<String, Centers> Centers = new HashMap<>();

    public CubeMI(int size) {
        Size = size;
        MaxCountColor = Size * Size;
    }

    public void createCube(){
        String[] locationsCorners = new String[]{ "FLU", "FRU", "FLD", "FRD", "BLU", "BRU", "BLD", "BRD" };
        String[] locationsEdges = new String[]{ "FL", "FU", "FR", "FD", "BL", "BU", "BR", "BD", "RU", "RD", "LU", "LD" };
        String[] nameSides = new String[]{ "F", "R", "B", "L", "U", "D" };
        HashMap<String, Integer> centresColors = new HashMap<String, Integer>(){
            {put("F", Color.WHITE);}
            {put("R", Color.RED);}
            {put("B", Color.YELLOW);}
            {put("L", Color.parseColor("#FFA500"));}
            {put("U", Color.BLUE);}
            {put("D", Color.GREEN);}
        };
        HashMap<String, String[]> centresOrientation = new HashMap<String, String[]>(){
            {put("F", new String[]{ "L", "U", "R", "D" });}
            {put("R", new String[]{ "F", "U", "B", "D" });}
            {put("B", new String[]{ "R", "U", "L", "D" });}
            {put("L", new String[]{ "B", "U", "F", "D" });}
            {put("U", new String[]{ "L", "B", "R", "F" });}
            {put("D", new String[]{ "L", "F", "R", "B" });}
        };
        for(String locCorner : locationsCorners)
            Corners.put(getSortedString(locCorner), new Corner(getSortedString(locCorner)));
        if(Size < 3)
            return;
        for(String locEdge : locationsEdges)
            Edges.put(getSortedString(locEdge), new Edges(getSortedString(locEdge)));
        for(String side : nameSides){
            int color = centresColors.get(side);
            String[] centerOrientation = centresOrientation.get(side);
            Centers.put(side, new Centers(centerOrientation, Size - 2, color));
        }
    }

    public Drawable[][] getCurrentView(){
        Drawable[][] result = new Drawable[Size + 2][Size + 2];
        for(int row = 1; row < Size + 1; row++)
            for(int column = 1; column < Size + 1; column++){
                if((row == column && (row == 1 || row == Size)) || Math.abs(row - column) == Size - 1){
                    if(row == column && row == 1) {
                        String locCorner = getSortedString(LocationSides.get(Side.Front) + LocationSides.get(Side.Left) + LocationSides.get(Side.Up));
                        Corner corner = Corners.get(locCorner);
                        result[row][column] = corner.Colors.get(LocationSides.get(Side.Front));
                        result[row][column - 1] = corner.Colors.get(LocationSides.get(Side.Left));
                        result[row - 1][column] = corner.Colors.get(LocationSides.get(Side.Up));
                        result[row - 1][column - 1] = null;
                    }
                    else if(row == column && row == Size){
                        String locCorner = getSortedString(LocationSides.get(Side.Front) + LocationSides.get(Side.Right) + LocationSides.get(Side.Down));
                        Corner corner = Corners.get(locCorner);
                        result[row][column] = corner.Colors.get(LocationSides.get(Side.Front));
                        result[row][column + 1] = corner.Colors.get(LocationSides.get(Side.Right));
                        result[row + 1][column] = corner.Colors.get(LocationSides.get(Side.Down));
                        result[row + 1][column + 1] = null;
                    }
                    else if(row == 1 && column == Size){
                        String locCorner = getSortedString(LocationSides.get(Side.Front) + LocationSides.get(Side.Right) + LocationSides.get(Side.Up));
                        Corner corner = Corners.get(locCorner);
                        result[row][column] = corner.Colors.get(LocationSides.get(Side.Front));
                        result[row][column + 1] = corner.Colors.get(LocationSides.get(Side.Right));
                        result[row - 1][column] = corner.Colors.get(LocationSides.get(Side.Up));
                        result[row - 1][column + 1] = null;
                    }
                    else{
                        String locCorner = getSortedString(LocationSides.get(Side.Front) + LocationSides.get(Side.Left) + LocationSides.get(Side.Down));
                        Corner corner = Corners.get(locCorner);
                        result[row][column] = corner.Colors.get(LocationSides.get(Side.Front));
                        result[row][column - 1] = corner.Colors.get(LocationSides.get(Side.Left));
                        result[row + 1][column] = corner.Colors.get(LocationSides.get(Side.Down));
                        result[row + 1][column - 1] = null;
                    }
                }
                else if(row == 1){
                    String locEdges = getSortedString(LocationSides.get(Side.Front) + LocationSides.get(Side.Up));
                    Edges edges = Edges.get(locEdges);
                    result[row][column] = edges.Elements[column - 2].Colors.get(LocationSides.get(Side.Front));
                    result[row - 1][column] = edges.Elements[column - 2].Colors.get(LocationSides.get(Side.Up));
                }
                else if(column == 1){
                    String locEdges = getSortedString(LocationSides.get(Side.Front) + LocationSides.get(Side.Left));
                    Edges edges = Edges.get(locEdges);
                    result[row][column] = edges.Elements[Size - row - 1].Colors.get(LocationSides.get(Side.Front));
                    result[row][column - 1] = edges.Elements[row - 2].Colors.get(LocationSides.get(Side.Left));
                }
                else if(row == Size){
                    String locEdges = getSortedString(LocationSides.get(Side.Front) + LocationSides.get(Side.Down));
                    Edges edges = Edges.get(locEdges);
                    result[row][column] = edges.Elements[Size - column - 1].Colors.get(LocationSides.get(Side.Front));
                    result[row + 1][column] = edges.Elements[column - 2].Colors.get(LocationSides.get(Side.Down));
                }
                else if(column == Size){
                    String locEdges = getSortedString(LocationSides.get(Side.Front) + LocationSides.get(Side.Right));
                    Edges edges = Edges.get(locEdges);
                    result[row][column] = edges.Elements[row - 2].Colors.get(LocationSides.get(Side.Front));
                    result[row][column + 1] = edges.Elements[row - 2].Colors.get(LocationSides.get(Side.Right));
                }
                else
                    result[row][column] = Centers.get(LocationSides.get(Side.Front)).Elements[row - 2][column - 2].Color;
            }
        return result;
    }

    public boolean cubeIsFill(){
        for(Map.Entry<Integer, Integer> countColor : CountColors.entrySet())
            if(countColor.getValue() < MaxCountColor)
                return false;
        return true;
    }

    public HashMap<String, Integer> getColorsNeighboringSidesCenters(){
        return new HashMap<String, Integer>(){
            {put("L", Centers.get(LocationSides.get(Side.Left)).Elements[(Size - 2) / 2][(Size - 2) / 2].Color.getColor());}
            {put("U", Centers.get(LocationSides.get(Side.Up)).Elements[(Size - 2) / 2][(Size - 2) / 2].Color.getColor());}
            {put("R", Centers.get(LocationSides.get(Side.Right)).Elements[(Size - 2) / 2][(Size - 2) / 2].Color.getColor());}
            {put("D", Centers.get(LocationSides.get(Side.Down)).Elements[(Size - 2) / 2][(Size - 2) / 2].Color.getColor());}
        };
    }

    public void addColor(int row, int column, Drawable background){
        if((row == column && (row == 0 || row == Size - 1) ) || Math.abs(row - column) == Size - 1){
            String locCorner;
            if(row == 0 && column == 0)
                locCorner = getSortedString(LocationSides.get(Side.Front) + LocationSides.get(Side.Left) + LocationSides.get(Side.Up));
            else if(row == 0 && column == Size - 1)
                locCorner = getSortedString(LocationSides.get(Side.Front) + LocationSides.get(Side.Right) + LocationSides.get(Side.Up));
            else if(row == Size - 1 && column == 0)
                locCorner = getSortedString(LocationSides.get(Side.Front) + LocationSides.get(Side.Left) + LocationSides.get(Side.Down));
            else
                locCorner = getSortedString(LocationSides.get(Side.Front) + LocationSides.get(Side.Right) + LocationSides.get(Side.Down));
            Corners.get(locCorner).Colors.put(LocationSides.get(Side.Front), (ColorDrawable) background);
        }
        else if(row > 0 && row < Size - 1 && column > 0 && column < Size - 1)
            Centers.get(LocationSides.get(Side.Front)).Elements[row - 1][column - 1].Color = (ColorDrawable) background;
        else{
            String locEdges;
            int index;
            if(row == 0) {
                locEdges = getSortedString(LocationSides.get(Side.Front) + LocationSides.get(Side.Up));
                index = column - 1;
            }
            else if(column == 0) {
                locEdges = getSortedString(LocationSides.get(Side.Front) + LocationSides.get(Side.Left));
                index = Size - row - 2;
            }
            else if(row == Size - 1) {
                locEdges = getSortedString(LocationSides.get(Side.Front) + LocationSides.get(Side.Down));
                index = Size - column - 2;
            }
            else {
                locEdges = getSortedString(LocationSides.get(Side.Front) + LocationSides.get(Side.Right));
                index = row - 1;
            }
            Edges.get(locEdges).Elements[index].Colors.put(LocationSides.get(Side.Front), (ColorDrawable) background);
        }
    }

    public String getSortedString(String source){
        char[] chars = source.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    private void checkOrientationCenters(){
        Centers.get(LocationSides.get(Side.Left)).checkOrientation(new String[]{ LocationSides.get(Side.Back), LocationSides.get(Side.Up), LocationSides.get(Side.Front), LocationSides.get(Side.Down) });
        Centers.get(LocationSides.get(Side.Back)).checkOrientation(new String[]{ LocationSides.get(Side.Right), LocationSides.get(Side.Up), LocationSides.get(Side.Left), LocationSides.get(Side.Down) });
        Centers.get(LocationSides.get(Side.Right)).checkOrientation(new String[]{ LocationSides.get(Side.Front), LocationSides.get(Side.Up), LocationSides.get(Side.Back), LocationSides.get(Side.Down) });
        Centers.get(LocationSides.get(Side.Up)).checkOrientation(new String[]{ LocationSides.get(Side.Left), LocationSides.get(Side.Back), LocationSides.get(Side.Right), LocationSides.get(Side.Front) });
        Centers.get(LocationSides.get(Side.Down)).checkOrientation(new String[]{ LocationSides.get(Side.Left), LocationSides.get(Side.Front), LocationSides.get(Side.Right), LocationSides.get(Side.Back) });
    }

    public void turnUp(){
        Side[] sidesSwap = new Side[]{ Side.Front, Side.Up, Side.Back, Side.Down };
        HashMap<Side, String> sourceSides = new HashMap<>();
        for(int i = 0; i < sidesSwap.length; i++)
            sourceSides.put(sidesSwap[i], LocationSides.get(sidesSwap[i]));
        for(int i = 0; i < sidesSwap.length; i++)
            LocationSides.put(sidesSwap[i], sourceSides.get(sidesSwap[(i + 1) % sidesSwap.length]));
        if(Size > 3)
            checkOrientationCenters();
    }

    public void turnRight(){
        Side[] sidesSwap = new Side[]{ Side.Front, Side.Right, Side.Back, Side.Left };
        HashMap<Side, String> sourceSides = new HashMap<>();
        for(int i = 0; i < sidesSwap.length; i++)
            sourceSides.put(sidesSwap[i], LocationSides.get(sidesSwap[i]));
        for(int i = 0; i < sidesSwap.length; i++)
            LocationSides.put(sidesSwap[i], sourceSides.get(sidesSwap[(i + 1) % sidesSwap.length]));
        if(Size > 3)
            checkOrientationCenters();
    }

    public void turnDown(){
        Side[] sidesSwap = new Side[]{ Side.Front, Side.Down, Side.Back, Side.Up };
        HashMap<Side, String> sourceSides = new HashMap<>();
        for(int i = 0; i < sidesSwap.length; i++)
            sourceSides.put(sidesSwap[i], LocationSides.get(sidesSwap[i]));
        for(int i = 0; i < sidesSwap.length; i++)
            LocationSides.put(sidesSwap[i], sourceSides.get(sidesSwap[(i + 1) % sidesSwap.length]));
        if(Size > 3)
            checkOrientationCenters();
    }
    public void turnLeft(){
        Side[] sidesSwap = new Side[]{ Side.Front, Side.Left, Side.Back, Side.Right };
        HashMap<Side, String> sourceSides = new HashMap<>();
        for(int i = 0; i < sidesSwap.length; i++)
            sourceSides.put(sidesSwap[i], LocationSides.get(sidesSwap[i]));
        for(int i = 0; i < sidesSwap.length; i++)
            LocationSides.put(sidesSwap[i], sourceSides.get(sidesSwap[(i + 1) % sidesSwap.length]));
        if(Size > 3)
            checkOrientationCenters();
    }
}