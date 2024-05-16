package com.example.intcube;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;

import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class SettingAxisActivity extends AppCompatActivity{

    private class ChoosingElement{
        Button Element;
        Drawable Drawable;
        AxisMI.PositionFrontSide Position;

        @SuppressLint("UseCompatLoadingForDrawables")
        public void setBackgroundWithBorder(Drawable drawable){
            String[] indexes = ((String) Element.getTag()).split(" ");
            Drawable[] layers = new Drawable[2];
            Drawable = layers[0] = drawable;
            layers[1] = SettingAxisActivity.this.getDrawable(R.drawable.border_button);
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            Element.setBackground(layerDrawable);
        }

        public void choosingNewElement(Button newButton){
            deleteBorder();
            Element = newButton;
            String tag = (String) Element.getTag();
            int row = Integer.parseInt(tag.split(" ")[0]);
            int column = Integer.parseInt(tag.split(" ")[1]);
            Position = AxisMI.PositionFrontSide.getPosition(row, column, Cube.Size - 1, Cube.Size - 1);
            setBackgroundWithBorder(Element.getBackground());
        }

        public void deleteBorder(){
            if(Element != null) {
                Element.setBackground(Drawable);
                Element = null;
                Drawable = null;
            }
        }

        public boolean elementIsNotSelected(){
            return Element == null;
        }
    }

    AxisMI Cube;
    ChoosingElement Element = new ChoosingElement();

    HashMap<String, ImageButton> buttonsNavigationCube = new HashMap<>();

    Button buttonToScanAxis;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_axis);
        buttonToScanAxis = findViewById(R.id.startScan);
        buttonToScanAxis.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ScanAxisColorActivity.class);
            startActivity(intent);
        });
        Cube = new AxisMI();
        Cube.createCenters();
        buttonsNavigationCube.put("L", findViewById(R.id.leftAxis));
        buttonsNavigationCube.put("U", findViewById(R.id.upAxis));
        buttonsNavigationCube.put("R", findViewById(R.id.rightAxis));
        buttonsNavigationCube.put("D", findViewById(R.id.downAxis));
        checkSelectColorButton();
    }

    public void startActivityScanAxis(){
        Intent intent = new Intent(this, ScanAxisColorActivity.class);
        startActivity(intent);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            showSide();
        }
    }

    private void showSide(){
        GridLayout layout = findViewById(R.id.axisView);
        layout.removeAllViews();
        int verticalPadding = getMargins(layout.getWidth(), layout.getHeight());
        layout.setPadding(0, verticalPadding, 0, verticalPadding);
        int sizeButton = getSizeButtons(layout.getWidth());
        Cube.createCurrentView();
        layout.setRowCount(Cube.Size);
        layout.setColumnCount(Cube.Size);
        int row = 0;
        int column = 0;
        for(AxisMI.PositionFrontSide position : AxisMI.PositionFrontSide.getAll()){
            GridLayout.LayoutParams layoutParams = createLayoutParams();
            layoutParams.rowSpec = GridLayout.spec(row);
            layoutParams.columnSpec = GridLayout.spec(column);
            addViewOnGrid(layout, layoutParams, Cube.ViewSide.getDrawable(position), row + " " + column, sizeButton);
            column++;
            if(column == Cube.Size)
            {
                column = 0;
                row++;
            }
        }
        changeBackgroundNavigationButtons();
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void addViewOnGrid(GridLayout layout, GridLayout.LayoutParams params, Drawable background, String tag, int sizeButton){
        Button button = new Button(this);
        if(background == null)
            button.setBackground(getDrawable(R.drawable.element_empty));
        else
            button.setBackground(background);
        button.setTag(tag);
        button.setOnClickListener(this::choosingElement);
        params.width = sizeButton;
        params.height = sizeButton;
        layout.addView(button, params);
    }

    private GridLayout.LayoutParams createLayoutParams(){
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setMargins(10, 10, 10, 10);
        return params;
    }

    private int getSizeButtons(int width){
        return (width - (Cube.Size) * 20) / Cube.Size;
    }

    private void changeBackgroundNavigationButtons() {
        HashMap<String, Drawable> sideColor = Cube.ViewSide.getColorsNeighboringSidesCenters();
        for (Map.Entry<String, Drawable> oneSide : sideColor.entrySet()) {
            buttonsNavigationCube.get(oneSide.getKey()).setBackground(oneSide.getValue());
        }
    }

    private int getMargins(int width, int height){
        return (height - width) / 2;
    }

    public void toPreviousActivity(View v){
        finish();
    }

    public void choosingElement(View v){
        Element.choosingNewElement((Button) v);
    }

    public void goUpSide(View v){
        Element.deleteBorder();
        Cube.turnUp();
        showSide();
    }

    public void goLeftSide(View v){
        Element.deleteBorder();
        Cube.turnLeft();
        showSide();
    }

    public void goDownSide(View v){
        Element.deleteBorder();
        Cube.turnDown();
        showSide();
    }

    public void goRightSide(View v){
        Element.deleteBorder();
        Cube.turnRight();
        showSide();
    }

    public void addOneColorCorner(View v) {
        if (Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else if(AxisMI.PositionFrontSide.isPositionCorner(Element.Position))
            if(Cube.CountCorners.get(AxisMI.TypeCorner.OneColor) == Cube.MaxCountCorners.get(AxisMI.TypeCorner.OneColor))
                Toast.makeText(this, "Достигнут максимум этих элементов", Toast.LENGTH_SHORT).show();
            else {
                Cube.ViewSide.addCorner(Element.Position, AxisMI.TypeCorner.OneColor);
                Element.setBackgroundWithBorder(Cube.ViewSide.getDrawable(Element.Position));
                checkSelectColorButton();
            }
        else
            Toast.makeText(this, "Здесь не может быть угла", Toast.LENGTH_SHORT).show();
    }

    public void addThreeColorsCorner(View v) {
        if (Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else if(AxisMI.PositionFrontSide.isPositionCorner(Element.Position))
            if(Cube.CountCorners.get(AxisMI.TypeCorner.ThreeColors) == Cube.MaxCountCorners.get(AxisMI.TypeCorner.ThreeColors))
                Toast.makeText(this, "Достигнут максимум этих элементов", Toast.LENGTH_SHORT).show();
            else {
                Cube.ViewSide.addCorner(Element.Position, AxisMI.TypeCorner.ThreeColors);
                Element.setBackgroundWithBorder(Cube.ViewSide.getDrawable(Element.Position));
                checkSelectColorButton();
            }
        else
            Toast.makeText(this, "Здесь не может быть угла", Toast.LENGTH_SHORT).show();
    }

    public void addOneColorEdge(View v) {
        if (Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else if(AxisMI.PositionFrontSide.isPositionEdge(Element.Position)) {
            if(Cube.CountEdges.get(AxisMI.TypeEdge.OneColor) == Cube.MaxCountEdges.get(AxisMI.TypeEdge.OneColor))
                Toast.makeText(this, "Достигнут максимум этих элементов", Toast.LENGTH_SHORT).show();
            else {
                Cube.ViewSide.addEdge(Element.Position, AxisMI.TypeEdge.OneColor);
                Element.setBackgroundWithBorder(Cube.ViewSide.getDrawable(Element.Position));
                checkSelectColorButton();
            }
        }
        else
            Toast.makeText(this, "Здесь не может быть ребра", Toast.LENGTH_SHORT).show();
    }

    public void addTwoColorsEdge(View v) {
        if (Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else if(AxisMI.PositionFrontSide.isPositionEdge(Element.Position))
            if(Cube.CountEdges.get(AxisMI.TypeEdge.TwoColors) == Cube.MaxCountEdges.get(AxisMI.TypeEdge.TwoColors))
                Toast.makeText(this, "Достигнут максимум этих элементов", Toast.LENGTH_SHORT).show();
            else {
                Cube.ViewSide.addEdge(Element.Position, AxisMI.TypeEdge.TwoColors);
                Element.setBackgroundWithBorder(Cube.ViewSide.getDrawable(Element.Position));
                checkSelectColorButton();
            }
        else
            Toast.makeText(this, "Здесь не может быть ребра", Toast.LENGTH_SHORT).show();
    }

    public void rotateElement(View v){
        if(Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else {
            if(Cube.ViewSide.elementIsNotAdd(Element.Position))
                Toast.makeText(this, "Нет элемента", Toast.LENGTH_SHORT).show();
            else {
                Cube.ViewSide.rotateElement(Element.Position);
                Element.setBackgroundWithBorder(Cube.ViewSide.getDrawable(Element.Position));
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void deleteElement(View v){
        if(Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else {
            if (Element.Position == AxisMI.PositionFrontSide.Center)
                Toast.makeText(this, "Центр удалить нельзя", Toast.LENGTH_SHORT).show();
            else {
                if (Cube.ViewSide.elementIsNotAdd(Element.Position))
                    Toast.makeText(this, "Нет элемента", Toast.LENGTH_SHORT).show();
                else if (AxisMI.PositionFrontSide.isPositionCorner(Element.Position))
                    Cube.ViewSide.deleteCorner(Element.Position);
                else
                    Cube.ViewSide.deleteEdge(Element.Position);
                Element.setBackgroundWithBorder(getDrawable(R.drawable.element_empty));
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void goSelectColors(View v) {
        GridLayout grid = findViewById(R.id.workWithAxis);
        grid.removeAllViews();
        grid.setColumnCount(3);
        grid.setRowCount(3);
        Context context = this;
        Button[] colorButtons = new Button[]{ new Button(context), new Button(context), new Button(context), new Button(context), new Button(context), new Button(context) };
        @SuppressLint("UseCompatLoadingForDrawables") HashMap<Button, Drawable> drawablesButtons = new HashMap<Button, Drawable>(){
            {put(colorButtons[0], getDrawable(R.drawable.radius_red));}
            {put(colorButtons[1], getDrawable(R.drawable.radius_orange));}
            {put(colorButtons[2], getDrawable(R.drawable.radius_green));}
            {put(colorButtons[3], getDrawable(R.drawable.radius_blue));}
            {put(colorButtons[4], getDrawable(R.drawable.radius_white));}
            {put(colorButtons[5], getDrawable(R.drawable.radius_yellow));}
        };
        HashMap<Button, View.OnClickListener> methodsButtons = new HashMap<Button, View.OnClickListener>(){
            {put(colorButtons[0], v -> selectRed(colorButtons[0]));}
            {put(colorButtons[1], v -> selectOrange(colorButtons[1]));}
            {put(colorButtons[2], v -> selectGreen(colorButtons[2]));}
            {put(colorButtons[3], v -> selectBlue(colorButtons[3]));}
            {put(colorButtons[4], v -> selectWhite(colorButtons[4]));}
            {put(colorButtons[5], v -> selectYellow(colorButtons[5]));}
        };
        for(int row = 0; row < 3; row++)
            for(int column = 0; column < 3; column++){
                Button currentButton;
                if(row * 3 + column < colorButtons.length) {
                    currentButton = colorButtons[row * 3 + column];
                    currentButton.setBackground(drawablesButtons.get(currentButton));
                    currentButton.setOnClickListener(methodsButtons.get(currentButton));
                }
                else if(column == 0){
                    currentButton = new Button(context);
                    currentButton.setText(R.string.clearColorsElement);
                    currentButton.setOnClickListener(this::clearColors);
                }
                else if(column == 2){
                    currentButton = new Button(context);
                    currentButton.setId(R.id.solveCube);
                    currentButton.setText(R.string.textToSolveButton);
                    currentButton.setOnClickListener(this::solveCube);
                }
                else
                    continue;
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(row);
                params.columnSpec = GridLayout.spec(column, 1f);
                params.width = 0;
                params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                params.setMargins(20, 5, 20, 5);
                grid.addView(currentButton, params);
            }
    }

    public void toPreviousActivityFromSettingAxis(View v){
        finish();
    }

    public void solveCube(View v){

    }

    public void selectRed(View v) {
        Log.wtf("COunt", String.valueOf(Cube.CountColors.get(Color.RED)));
        if (Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else if (Element.Position == AxisMI.PositionFrontSide.Center)
            Toast.makeText(this, "Здесь изменить цвета нельзя", Toast.LENGTH_SHORT).show();
        else if (Cube.CountColors.get(Color.RED) == Cube.MaxCountColor)
            Toast.makeText(this, "Достигнуто максимальное количество", Toast.LENGTH_SHORT).show();
        else{
            Cube.ViewSide.addColorElement(Element.Position, Color.RED);
            Element.setBackgroundWithBorder(Cube.ViewSide.getDrawable(Element.Position));
            checkSolveButton();
        }
    }

    public void selectOrange(View v) {
        if (Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else if (Element.Position == AxisMI.PositionFrontSide.Center)
            Toast.makeText(this, "Здесь изменить цвета нельзя", Toast.LENGTH_SHORT).show();
        else if (Cube.CountColors.get(Color.parseColor("#FFA500")) == Cube.MaxCountColor)
            Toast.makeText(this, "Достигнуто максимальное количество", Toast.LENGTH_SHORT).show();
        else{
            Cube.ViewSide.addColorElement(Element.Position, Color.parseColor("#FFA500"));
            Element.setBackgroundWithBorder(Cube.ViewSide.getDrawable(Element.Position));
            checkSolveButton();
        }
    }

    public void selectGreen(View v){
        if(Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else if(Element.Position == AxisMI.PositionFrontSide.Center)
            Toast.makeText(this, "Здесь изменить цвета нельзя", Toast.LENGTH_SHORT).show();
        else if(Cube.CountColors.get(Color.GREEN) == Cube.MaxCountColor)
            Toast.makeText(this, "Достигнуто максимальное количество", Toast.LENGTH_SHORT).show();
        else {
            Cube.ViewSide.addColorElement(Element.Position, Color.GREEN);
            Element.setBackgroundWithBorder(Cube.ViewSide.getDrawable(Element.Position));
            checkSolveButton();
        }
    }

    public void selectBlue(View v){
        if(Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else if(Element.Position == AxisMI.PositionFrontSide.Center)
            Toast.makeText(this, "Здесь изменить цвета нельзя", Toast.LENGTH_SHORT).show();
        else if(Cube.CountColors.get(Color.BLUE) == Cube.MaxCountColor)
            Toast.makeText(this, "Достигнуто максимальное количество", Toast.LENGTH_SHORT).show();
        else{
            Cube.ViewSide.addColorElement(Element.Position, Color.BLUE);
            Element.setBackgroundWithBorder(Cube.ViewSide.getDrawable(Element.Position));
            checkSolveButton();
        }
    }

    public void selectWhite(View v) {
        if (Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else if (Element.Position == AxisMI.PositionFrontSide.Center)
            Toast.makeText(this, "Здесь изменить цвета нельзя", Toast.LENGTH_SHORT).show();
        else if (Cube.CountColors.get(Color.WHITE) == Cube.MaxCountColor)
            Toast.makeText(this, "Достигнуто максимальное количество", Toast.LENGTH_SHORT).show();
        else{
            Cube.ViewSide.addColorElement(Element.Position, Color.WHITE);
            Element.setBackgroundWithBorder(Cube.ViewSide.getDrawable(Element.Position));
            checkSolveButton();
        }
    }

    public void selectYellow(View v){
        if(Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else if(Element.Position == AxisMI.PositionFrontSide.Center)
            Toast.makeText(this, "Здесь изменить цвета нельзя", Toast.LENGTH_SHORT).show();
        else if(Cube.CountColors.get(Color.YELLOW) == Cube.MaxCountColor)
            Toast.makeText(this, "Достигнуто максимальное количество", Toast.LENGTH_SHORT).show();
        else {
            Cube.ViewSide.addColorElement(Element.Position, Color.YELLOW);
            Element.setBackgroundWithBorder(Cube.ViewSide.getDrawable(Element.Position));
            checkSolveButton();
        }
    }

    public void clearColors(View v){
        if(Element.Position == AxisMI.PositionFrontSide.Center)
            Toast.makeText(this, "Здесь очистить цвета нельзя", Toast.LENGTH_SHORT).show();
        else {
            Cube.ViewSide.clearColorsCorner(Element.Position);
            Cube.ViewSide.clearColorsEdge(Element.Position);
            Element.setBackgroundWithBorder(Cube.ViewSide.getDrawable(Element.Position));
        }
    }

    private void checkSelectColorButton(){
        Button selectButton = findViewById(R.id.selectColorsAxis);
        selectButton.setEnabled(Cube.cubeIsFillElements());
    }

    private void checkSolveButton(){
        Button solveButton = findViewById(R.id.solveCube);
        solveButton.setEnabled(Cube.cubeIsFillColors());
    }
}