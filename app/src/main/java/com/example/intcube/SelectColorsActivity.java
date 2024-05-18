package com.example.intcube;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;

import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class SelectColorsActivity extends AppCompatActivity{

    private class ChoosingElement{
        Button element;
        Drawable background;

        @SuppressLint("UseCompatLoadingForDrawables")
        public void setBackgroundWithBorder(ColorDrawable color){
            String[] indexes = ((String)element.getTag()).split(" ");
            Cube.addColor(Integer.parseInt(indexes[0]), Integer.parseInt(indexes[1]), color);
            Drawable[] layers = new Drawable[2];
            background = layers[0] = color;
            layers[1] = getDrawable(R.drawable.border_button);
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            element.setBackground(layerDrawable);
        }

        public void choosingNewElement(Button newButton){
            deleteBorder();
            element = newButton;
            setBackgroundWithBorder((ColorDrawable) element.getBackground());
        }

        public void deleteBorder(){
            if(element != null) {
                element.setBackground(background);
                element = null;
                background = null;
            }
        }

        public LayerDrawable getBackground(){
            return (LayerDrawable) element.getBackground();
        }

        public boolean elementIsNotSelected(){
            return element == null;
        }
    }

    CubeMI Cube;
    ChoosingElement Element = new ChoosingElement();

    HashMap<String, ImageButton> buttonsNavigationCube = new HashMap<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_colors);
        String sizeCube = getIntent().getStringExtra("sizeCube");
        Cube = new CubeMI(Integer.parseInt(sizeCube));
        Cube.createCube();
        buttonsNavigationCube.put("L", findViewById(R.id.left));
        buttonsNavigationCube.put("U", findViewById(R.id.up));
        buttonsNavigationCube.put("R", findViewById(R.id.right));
        buttonsNavigationCube.put("D", findViewById(R.id.down));
        checkSolveButton();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            showSide();
        }
    }

    private void showSide(){
        GridLayout layout = findViewById(R.id.layoutForButtons);
        layout.removeAllViews();
        int verticalPadding = getMargins(layout.getWidth(), layout.getHeight());
        layout.setPadding(0, verticalPadding, 0, verticalPadding);
        int sizeButton = getSizeButtons(layout.getWidth());
        Drawable[][] colorsView = Cube.getCurrentView();
        layout.setRowCount(colorsView.length);
        layout.setColumnCount(colorsView[0].length);
        int[] row_column = new int[] { layout.getRowCount(), layout.getColumnCount() };
        for(int i = 0; i < row_column[0]; i++) {
            for (int j = 0; j < row_column[1]; j++) {
                if(colorsView[i][j] == null)
                    continue;
                GridLayout.LayoutParams layoutParams = createLayoutParams();
                layoutParams.rowSpec = GridLayout.spec(i);
                layoutParams.columnSpec = GridLayout.spec(j);
                addViewOnGrid(i, j, sizeButton, colorsView[i][j], layoutParams, layout);
            }
        }
        if(Cube.Size % 2 != 0)
            changeBackgroundNavigationButtons();
    }


    private void addViewOnGrid(int i, int j, int sizeButton, Drawable background, GridLayout.LayoutParams layoutParams, GridLayout layout){
        if(i > 0 && i < Cube.Size + 1 && j > 0 && j < Cube.Size + 1)
        {
            Button button = new Button(this);
            button.setBackground(background);
            if(!(Cube.Size % 2 == 1 && i == (Cube.Size + 2) / 2 && j == (Cube.Size + 2) / 2)) {
                button.setTag((i - 1) + " " + (j - 1));
                button.setOnClickListener(this::choosingElement);
            }
            layoutParams.width = sizeButton;
            layoutParams.height = sizeButton;
            layout.addView(button, layoutParams);
        }
        else
        {
            View viewToAdd = new View(this);
            viewToAdd.setBackground(background);
            if(i == 0 && j > 0 && j < Cube.Size + 1){
                layoutParams.width = sizeButton;
                layoutParams.height = 10;
            }
            if(i == Cube.Size + 1 && j > 0 && j < Cube.Size + 1){
                layoutParams.width = sizeButton;
                layoutParams.height = 10;
            }
            if(j == 0 && i > 0 && i < Cube.Size + 1){
                layoutParams.width = 10;
                layoutParams.height = sizeButton;
            }
            if(j == Cube.Size + 1 && i > 0 && i < Cube.Size + 1)
            {
                layoutParams.width = 10;
                layoutParams.height = sizeButton;
            }
            layout.addView(viewToAdd, layoutParams);
        }
    }

    private GridLayout.LayoutParams createLayoutParams(){
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setMargins(10, 10, 10, 10);
        return params;
    }

    private int getSizeButtons(int width){
        return (width - ((Cube.Size + 2) * 20) - 20) / Cube.Size;
    }

    private void changeBackgroundNavigationButtons() {
        HashMap<String, Integer> sideColor = Cube.getColorsNeighboringSidesCenters();
        for (Map.Entry<String, Integer> oneSide : sideColor.entrySet()) {
            buttonsNavigationCube.get(oneSide.getKey()).setBackground(getDrawableNavigationButton(oneSide.getValue()));
        }
    }

    private Drawable getDrawableNavigationButton(int color){
        switch (color){
            case Color.RED:
                return getDrawable(R.drawable.radius_red);
            case Color.GREEN:
                return getDrawable(R.drawable.radius_green);
            case Color.BLUE:
                return getDrawable(R.drawable.radius_blue);
            case Color.WHITE:
                return getDrawable(R.drawable.radius_white);
            case Color.YELLOW:
                return getDrawable(R.drawable.radius_yellow);
            default:
                return getDrawable(R.drawable.radius_orange);
        }
    }

    private int getMargins(int width, int height){
        return (height - width) / 2;
    }

    public void choosingYellow(View v){
        changeColor(Color.YELLOW);
    }

    public void choosingGreen(View v){
        changeColor(Color.GREEN);
    }

    public void choosingOrange(View v){
        changeColor(Color.parseColor("#FFA500"));
    }

    public void choosingBlue(View v){
        changeColor(Color.BLUE);
    }

    public void choosingRed(View v){
        changeColor(Color.RED);
    }

    public void choosingWhite(View v){
        changeColor(Color.WHITE);
    }

    private void changeColor(int newColor) {
        if (Element.elementIsNotSelected()) {
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
            return;
        }
        LayerDrawable layer = Element.getBackground();
        Drawable background = layer.getDrawable(0);
        int color = ((ColorDrawable) background).getColor();
        if (Cube.CountColors.containsKey(color)) {
            if (newColor == color)
                return;
            else
            {
                if(Cube.CountColors.get(newColor) == Cube.MaxCountColor) {
                    Toast.makeText(this, "Достигнуто максимальное количество", Toast.LENGTH_SHORT).show();
                    return;
                }
                Cube.CountColors.put(color, Cube.CountColors.get(color) - 1);
                Cube.CountColors.put(newColor, Cube.CountColors.get(newColor) + 1);
                Element.setBackgroundWithBorder(new ColorDrawable(newColor));
            }
        }
        else
        {
            if(Cube.CountColors.get(newColor) == Cube.MaxCountColor) {
                Toast.makeText(this, "Достигнуто максимальное количество", Toast.LENGTH_SHORT).show();
                return;
            }
            Cube.CountColors.put(newColor, Cube.CountColors.get(newColor) + 1);
            Element.setBackgroundWithBorder(new ColorDrawable(newColor));
        }
        checkSolveButton();
    }

    public void clearColor(View v){
        if (Element.elementIsNotSelected()) {
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
            return;
        }
        int color = ((ColorDrawable)Element.background).getColor();
        if(Cube.CountColors.containsKey(color)){
            Cube.CountColors.put(color, Cube.CountColors.get(color) - 1);
            Element.setBackgroundWithBorder(new ColorDrawable(Color.GRAY));
        }
    }

    public void choosingElement(View v){
        Element.choosingNewElement((Button) v);
    }

    public void goUp(View v){
        Element.deleteBorder();
        Cube.turnUp();
        showSide();
    }

    public void goLeft(View v){
        Element.deleteBorder();
        Cube.turnLeft();
        showSide();
    }

    public void goDown(View v){
        Element.deleteBorder();
        Cube.turnDown();
        showSide();
    }

    public void goRight(View v){
        Element.deleteBorder();
        Cube.turnRight();
        showSide();
    }

    public void toPreviousActivity(View v){
        finish();
    }

    public void startActivityScan(View view) {
        Intent intent;
        if(Cube.Size == 2)
            intent = new Intent(this, ScanColorsSqr2Activity.class);
        else
            intent = new Intent(this, ScanColorSqrActivity.class);
        startActivity(intent);
    }


    private void checkSolveButton(){
        Button solveButton = findViewById(R.id.solveCube);
        solveButton.setEnabled(Cube.cubeIsFill());
    }

    private char getColor(ColorDrawable colorDrawable) {
        switch (colorDrawable.getColor()) {
            case Color.RED:
                return 'R';
            case Color.GREEN:
                return 'G';
            case Color.BLUE:
                return 'B';
            case Color.WHITE:
                return 'W';
            case Color.YELLOW:
                return 'Y';
        }
        return 'O';
    }

    public void solveCube(View v) {
        Intent i = new Intent(SelectColorsActivity.this, SolutionActivity.class);
        if (Cube.Size == 2) {
            char[][][] colors = new char[6][2][2];
            colors[0][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("LUF")).Colors.get("F"));
            colors[0][0][1] = getColor(Cube.Corners.get(Cube.getSortedString("RUF")).Colors.get("F"));
            colors[0][1][0] = getColor(Cube.Corners.get(Cube.getSortedString("LDF")).Colors.get("F"));
            colors[0][1][1] = getColor(Cube.Corners.get(Cube.getSortedString("RDF")).Colors.get("F"));

            colors[1][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("BUL")).Colors.get("U"));
            colors[1][0][1] = getColor(Cube.Corners.get(Cube.getSortedString("BUR")).Colors.get("U"));
            colors[1][1][0] = getColor(Cube.Corners.get(Cube.getSortedString("FUL")).Colors.get("U"));
            colors[1][1][1] = getColor(Cube.Corners.get(Cube.getSortedString("FUR")).Colors.get("U"));

            colors[2][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("BUR")).Colors.get("R"));
            colors[2][0][1] = getColor(Cube.Corners.get(Cube.getSortedString("BDR")).Colors.get("R"));
            colors[2][1][0] = getColor(Cube.Corners.get(Cube.getSortedString("FUR")).Colors.get("R"));
            colors[2][1][1] = getColor(Cube.Corners.get(Cube.getSortedString("FDR")).Colors.get("R"));

            colors[3][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("BDR")).Colors.get("D"));
            colors[3][0][1] = getColor(Cube.Corners.get(Cube.getSortedString("BDL")).Colors.get("D"));
            colors[3][1][0] = getColor(Cube.Corners.get(Cube.getSortedString("FDR")).Colors.get("D"));
            colors[3][1][1] = getColor(Cube.Corners.get(Cube.getSortedString("FDL")).Colors.get("D"));

            colors[4][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("BDL")).Colors.get("L"));
            colors[4][0][1] = getColor(Cube.Corners.get(Cube.getSortedString("BUL")).Colors.get("L"));
            colors[4][1][0] = getColor(Cube.Corners.get(Cube.getSortedString("FDL")).Colors.get("L"));
            colors[4][1][1] = getColor(Cube.Corners.get(Cube.getSortedString("FUL")).Colors.get("L"));

            colors[5][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("BDR")).Colors.get("B"));
            colors[5][0][1] = getColor(Cube.Corners.get(Cube.getSortedString("BUR")).Colors.get("B"));
            colors[5][1][0] = getColor(Cube.Corners.get(Cube.getSortedString("BDL")).Colors.get("B"));
            colors[5][1][1] = getColor(Cube.Corners.get(Cube.getSortedString("BUL")).Colors.get("B"));

            i.putExtra("type", 0);
            i.putExtra("colors", colors);
        }
        else {
            char[][][] colors = new char[6][3][3];
            colors[0][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("LUF")).Colors.get("F"));
            colors[0][0][1] = getColor(Cube.Edges.get(Cube.getSortedString("UF")).Elements[0].Colors.get("F"));
            colors[0][0][2] = getColor(Cube.Corners.get(Cube.getSortedString("RUF")).Colors.get("F"));
            colors[0][1][0] = getColor(Cube.Edges.get(Cube.getSortedString("LF")).Elements[0].Colors.get("F"));
            colors[0][1][1] = 'W';
            colors[0][1][2] = getColor(Cube.Edges.get(Cube.getSortedString("RF")).Elements[0].Colors.get("F"));
            colors[0][2][0] = getColor(Cube.Corners.get(Cube.getSortedString("LDF")).Colors.get("F"));
            colors[0][2][1] = getColor(Cube.Edges.get(Cube.getSortedString("DF")).Elements[0].Colors.get("F"));
            colors[0][2][2] = getColor(Cube.Corners.get(Cube.getSortedString("RDF")).Colors.get("F"));

            colors[1][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("LUB")).Colors.get("U"));
            colors[1][0][1] = getColor(Cube.Edges.get(Cube.getSortedString("UB")).Elements[0].Colors.get("U"));
            colors[1][0][2] = getColor(Cube.Corners.get(Cube.getSortedString("RBU")).Colors.get("U"));
            colors[1][1][0] = getColor(Cube.Edges.get(Cube.getSortedString("LU")).Elements[0].Colors.get("U"));
            colors[1][1][1] = 'B';
            colors[1][1][2] = getColor(Cube.Edges.get(Cube.getSortedString("UR")).Elements[0].Colors.get("U"));
            colors[1][2][0] = getColor(Cube.Corners.get(Cube.getSortedString("LFU")).Colors.get("U"));
            colors[1][2][1] = getColor(Cube.Edges.get(Cube.getSortedString("FU")).Elements[0].Colors.get("U"));
            colors[1][2][2] = getColor(Cube.Corners.get(Cube.getSortedString("RFU")).Colors.get("U"));

            colors[2][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("BUR")).Colors.get("R"));
            colors[2][0][1] = getColor(Cube.Edges.get(Cube.getSortedString("RB")).Elements[0].Colors.get("R"));
            colors[2][0][2] = getColor(Cube.Corners.get(Cube.getSortedString("BDR")).Colors.get("R"));
            colors[2][1][0] = getColor(Cube.Edges.get(Cube.getSortedString("RU")).Elements[0].Colors.get("R"));
            colors[2][1][1] = 'R';
            colors[2][1][2] = getColor(Cube.Edges.get(Cube.getSortedString("RD")).Elements[0].Colors.get("R"));
            colors[2][2][0] = getColor(Cube.Corners.get(Cube.getSortedString("FUR")).Colors.get("R"));
            colors[2][2][1] = getColor(Cube.Edges.get(Cube.getSortedString("FR")).Elements[0].Colors.get("R"));
            colors[2][2][2] = getColor(Cube.Corners.get(Cube.getSortedString("FDR")).Colors.get("R"));

            colors[3][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("BDR")).Colors.get("D"));
            colors[3][0][1] = getColor(Cube.Edges.get(Cube.getSortedString("DB")).Elements[0].Colors.get("D"));
            colors[3][0][2] = getColor(Cube.Corners.get(Cube.getSortedString("BDL")).Colors.get("D"));
            colors[3][1][0] = getColor(Cube.Edges.get(Cube.getSortedString("RD")).Elements[0].Colors.get("D"));
            colors[3][1][1] = 'G';
            colors[3][1][2] = getColor(Cube.Edges.get(Cube.getSortedString("LD")).Elements[0].Colors.get("D"));
            colors[3][2][0] = getColor(Cube.Corners.get(Cube.getSortedString("FDR")).Colors.get("D"));
            colors[3][2][1] = getColor(Cube.Edges.get(Cube.getSortedString("FD")).Elements[0].Colors.get("D"));
            colors[3][2][2] = getColor(Cube.Corners.get(Cube.getSortedString("FDL")).Colors.get("D"));

            colors[4][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("BDL")).Colors.get("L"));
            colors[4][0][1] = getColor(Cube.Edges.get(Cube.getSortedString("LB")).Elements[0].Colors.get("L"));
            colors[4][0][2] = getColor(Cube.Corners.get(Cube.getSortedString("BUL")).Colors.get("L"));
            colors[4][1][0] = getColor(Cube.Edges.get(Cube.getSortedString("LD")).Elements[0].Colors.get("L"));
            colors[4][1][1] = 'O';
            colors[4][1][2] = getColor(Cube.Edges.get(Cube.getSortedString("LU")).Elements[0].Colors.get("L"));
            colors[4][2][0] = getColor(Cube.Corners.get(Cube.getSortedString("FDL")).Colors.get("L"));
            colors[4][2][1] = getColor(Cube.Edges.get(Cube.getSortedString("FL")).Elements[0].Colors.get("L"));
            colors[4][2][2] = getColor(Cube.Corners.get(Cube.getSortedString("FUL")).Colors.get("L"));

            colors[5][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("BDR")).Colors.get("B"));
            colors[5][0][1] = getColor(Cube.Edges.get(Cube.getSortedString("BR")).Elements[0].Colors.get("B"));
            colors[5][0][2] = getColor(Cube.Corners.get(Cube.getSortedString("BUR")).Colors.get("B"));
            colors[5][1][0] = getColor(Cube.Edges.get(Cube.getSortedString("BD")).Elements[0].Colors.get("B"));
            colors[5][1][1] = 'Y';
            colors[5][1][2] = getColor(Cube.Edges.get(Cube.getSortedString("BU")).Elements[0].Colors.get("B"));
            colors[5][2][0] = getColor(Cube.Corners.get(Cube.getSortedString("BDL")).Colors.get("B"));
            colors[5][2][1] = getColor(Cube.Edges.get(Cube.getSortedString("BL")).Elements[0].Colors.get("B"));
            colors[5][2][2] = getColor(Cube.Corners.get(Cube.getSortedString("BUL")).Colors.get("B"));

            i.putExtra("type", 1);
            i.putExtra("colors", colors);
        }
        startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            setResult(1);
            finish();
        }
    }

}