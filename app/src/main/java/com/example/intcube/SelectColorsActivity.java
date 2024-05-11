package com.example.intcube;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
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
            if (newColor == color) {
                Cube.CountColors.put(color, Cube.CountColors.get(color) - 1);
                Element.setBackgroundWithBorder(new ColorDrawable(Color.GRAY));
            }
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

    private void checkSolveButton(){
        Button solveButton = findViewById(R.id.solveCube);
        solveButton.setEnabled(Cube.cubeIsFill());
    }

    public void solveCube(View v){
    }
}