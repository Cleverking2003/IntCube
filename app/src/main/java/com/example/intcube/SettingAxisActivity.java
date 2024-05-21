package com.example.intcube;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;

import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SettingAxisActivity extends AppCompatActivity{

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        //Распаковка интента
                        Intent intent = result.getData();
                        assert intent != null;
                        HashMap<Integer, String> preview =
                                (HashMap<Integer, String>)
                                        intent.getSerializableExtra("sideInfo");
                        createSide(preview);
                    }
                    else{
                        //Наверное ничего
                    }
                }
            });

    private class ChoosingElement{
        Button Element;
        Drawable Drawable;
        AxisMI.PositionFrontSide Position;

        @SuppressLint("UseCompatLoadingForDrawables")
        public void setBackgroundWithBorder(){
            Drawable background = Cube.ViewSide.getDrawable(Position, IsSelectColors);
            Drawable[] layers = new Drawable[2];
            Drawable = layers[0] = background != null ? background : getDrawable(R.drawable.element_empty);
            layers[1] = SettingAxisActivity.this.getDrawable(R.drawable.border_button);
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            Element.setBackground(layerDrawable);
        }

        public void choosingNewElement(Button newButton){
            deleteBorder();
            String tag = (String) newButton.getTag();
            int row = Integer.parseInt(tag.split(" ")[0]);
            int column = Integer.parseInt(tag.split(" ")[1]);
            Position = AxisMI.PositionFrontSide.getPosition(row, column, Cube.Size - 1, Cube.Size - 1);
            if(Position == AxisMI.PositionFrontSide.Center && IsSelectColors)
            {
                Position = null;
                return;
            }
            Element = newButton;
            setBackgroundWithBorder();
        }

        public void deleteBorder(){
            if(Element != null) {
                Drawable background = Cube.ViewSide.getDrawable(Position, false);
                Element.setBackground(background != null ? background : getDrawable(R.drawable.element_empty));
                Element = null;
                Drawable = null;
            }
        }

        public boolean elementIsNotSelected(){
            return Element == null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    public static Context Context;

    AxisMI Cube;
    ChoosingElement Element = new ChoosingElement();
    boolean IsSelectColors = false;
    HashMap<String, ImageButton> ButtonsNavigationCube = new HashMap<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_axis);
        Context = this;
        Cube = new AxisMI();
        Cube.createCenters();
        ButtonsNavigationCube.put("L", findViewById(R.id.leftAxis));
        ButtonsNavigationCube.put("U", findViewById(R.id.upAxis));
        ButtonsNavigationCube.put("R", findViewById(R.id.rightAxis));
        ButtonsNavigationCube.put("D", findViewById(R.id.downAxis));
        checkSelectColorsButton();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
            createSide((HashMap<Integer, String>) getIntent().getSerializableExtra("sideInfo"));
    }

    private void createSide(HashMap<Integer, String> dataFromScan){
        Cube.createCurrentView();
        if(dataFromScan != null)
            Cube.ViewSide.setElements(dataFromScan);
        showSide();
    }

    private void showSide(){
        GridLayout layout = findViewById(R.id.axisView);
        layout.removeAllViews();
        int verticalPadding = getMargins(layout.getWidth(), layout.getHeight());
        layout.setPadding(0, verticalPadding, 0, verticalPadding);
        int sizeButton = getSizeButtons(layout.getWidth());
        layout.setRowCount(Cube.Size);
        layout.setColumnCount(Cube.Size);
        int row = 0;
        int column = 0;
        for(AxisMI.PositionFrontSide position : AxisMI.PositionFrontSide.getAll()){
            GridLayout.LayoutParams layoutParams = createLayoutParams();
            layoutParams.rowSpec = GridLayout.spec(row);
            layoutParams.columnSpec = GridLayout.spec(column);
            addViewOnGrid(layout, layoutParams, Cube.ViewSide.getDrawable(position, false), row + " " + column, sizeButton);
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
        for (Map.Entry<String, Drawable> oneSide : sideColor.entrySet())
            ButtonsNavigationCube.get(oneSide.getKey()).setBackground(oneSide.getValue());
    }

    private int getMargins(int width, int height){
        return (height - width) / 2;
    }

    public void toPreviousActivity(View v){
        showAlert(
                "Данные не сохранятся, покинуть окно?",
                "Нет",
                "Да",
                this::finish);
    }

    public void startActivityScanAxis(View v){
        Intent intent = new Intent(this, ScanAxisColorActivity.class);

        HashMap<String, Drawable> centers = Cube.ViewSide.getColorsNeighboringSidesCenters();

        if (centers.get("L") == null | centers.get("R") == null) return;
        Drawable left = centers.get("L");
        Drawable right = centers.get("R");
        Drawable main = Cube.ViewSide.ViewCenter.getDrawable();

        byte[] leftAsBytes = drawable2Bytes(left);
        byte[] rightAsBytes = drawable2Bytes(right);
        byte[] mainAsBytes = drawable2Bytes(main);

        intent.putExtra("left", leftAsBytes);
        intent.putExtra("right", rightAsBytes);
        intent.putExtra("main", mainAsBytes);

        mStartForResult.launch(intent);
    }

    public Dialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Справка")
                .setMessage(R.string.scanAxisManualNote);
        return builder.create();
    }

    public void CreateAndShowDialog(View view) {
        androidx.appcompat.app.AlertDialog dialogInfo = (androidx.appcompat.app.AlertDialog) createDialog();
        dialogInfo.show();
    }

    public void choosingElement(View v){
        Element.choosingNewElement((Button) v);
    }

    public void goUpSide(View v){
        Element.deleteBorder();
        Cube.turnUp();
        createSide(null);
    }

    public void goLeftSide(View v){
        Element.deleteBorder();
        Cube.turnLeft();
        createSide(null);
    }

    public void goDownSide(View v){
        Element.deleteBorder();
        Cube.turnDown();
        createSide(null);
    }

    public void goRightSide(View v){
        Element.deleteBorder();
        Cube.turnRight();
        createSide(null);
    }

    public void addOneColorCorner(View v) {
        if (Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else if(AxisMI.PositionFrontSide.isPositionCorner(Element.Position)) {
            if (Objects.equals(Cube.CountCorners.get(AxisMI.TypeCorner.OneColor), Cube.MaxCountCorners.get(AxisMI.TypeCorner.OneColor)))
                Toast.makeText(this, "Достигнут максимум этих элементов", Toast.LENGTH_SHORT).show();
            else if (Cube.ViewSide.getDrawable(Element.Position, false) == null) {
                Cube.ViewSide.addCorner(Element.Position, AxisMI.TypeCorner.OneColor);
                Element.setBackgroundWithBorder();
                checkSelectColorsButton();
            }
            else
                Toast.makeText(this, "Удалите текущий угол, чтобы добавить", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Здесь не может быть угла", Toast.LENGTH_SHORT).show();
    }

    public void addThreeColorsCorner(View v) {
        if (Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else if(AxisMI.PositionFrontSide.isPositionCorner(Element.Position)) {
            if (Objects.equals(Cube.CountCorners.get(AxisMI.TypeCorner.ThreeColors), Cube.MaxCountCorners.get(AxisMI.TypeCorner.ThreeColors)))
                Toast.makeText(this, "Достигнут максимум этих элементов", Toast.LENGTH_SHORT).show();
            else if (Cube.ViewSide.getDrawable(Element.Position, false) == null) {
                Cube.ViewSide.addCorner(Element.Position, AxisMI.TypeCorner.ThreeColors);
                Element.setBackgroundWithBorder();
                checkSelectColorsButton();
            }
            else
                Toast.makeText(this, "Удалите текущий угол, чтобы добавить", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Здесь не может быть угла", Toast.LENGTH_SHORT).show();
    }

    public void addOneColorEdge(View v) {
        if (Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else if(AxisMI.PositionFrontSide.isPositionEdge(Element.Position)) {
            if(Objects.equals(Cube.CountEdges.get(AxisMI.TypeEdge.OneColor), Cube.MaxCountEdges.get(AxisMI.TypeEdge.OneColor)))
                Toast.makeText(this, "Достигнут максимум этих элементов", Toast.LENGTH_SHORT).show();
            else  if(Cube.ViewSide.getDrawable(Element.Position, false) == null){
                Cube.ViewSide.addEdge(Element.Position, AxisMI.TypeEdge.OneColor);
                Element.setBackgroundWithBorder();
                checkSelectColorsButton();
            }
            else
                Toast.makeText(this, "Удалите текущee ребро, чтобы добавить", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Здесь не может быть ребра", Toast.LENGTH_SHORT).show();
    }

    public void addTwoColorsEdge(View v) {
        if (Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else if(AxisMI.PositionFrontSide.isPositionEdge(Element.Position)){
            if(Objects.equals(Cube.CountEdges.get(AxisMI.TypeEdge.TwoColors), Cube.MaxCountEdges.get(AxisMI.TypeEdge.TwoColors)))
                Toast.makeText(this, "Достигнут максимум этих элементов", Toast.LENGTH_SHORT).show();
            else  if(Cube.ViewSide.getDrawable(Element.Position, false) == null){
                Cube.ViewSide.addEdge(Element.Position, AxisMI.TypeEdge.TwoColors);
                Element.setBackgroundWithBorder();
                checkSelectColorsButton();
            }
            else
                Toast.makeText(this, "Удалите текущee ребро, чтобы добавить", Toast.LENGTH_SHORT).show();
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
                Element.setBackgroundWithBorder();
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
                Element.setBackgroundWithBorder();
                checkSelectColorsButton();
            }
        }
    }

    private Button[] getColorButtons(){
        Context context = this;
        return new Button[]{ new Button(context), new Button(context), new Button(context), new Button(context), new Button(context), new Button(context) };
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private HashMap<Button, Drawable> getDrawableColorButtons(Button[] colorButtons){
        return new HashMap<Button, Drawable>(){
            {put(colorButtons[0], getDrawable(R.drawable.radius_red));}
            {put(colorButtons[1], getDrawable(R.drawable.radius_orange));}
            {put(colorButtons[2], getDrawable(R.drawable.radius_green));}
            {put(colorButtons[3], getDrawable(R.drawable.radius_blue));}
            {put(colorButtons[4], getDrawable(R.drawable.radius_white));}
            {put(colorButtons[5], getDrawable(R.drawable.radius_yellow));}
        };
    }

    private HashMap<Button, View.OnClickListener> getMethodButtons(Button[] colorButtons){
        return new HashMap<Button, View.OnClickListener>(){
            {put(colorButtons[0], v -> selectRed(colorButtons[0]));}
            {put(colorButtons[1], v -> selectOrange(colorButtons[1]));}
            {put(colorButtons[2], v -> selectGreen(colorButtons[2]));}
            {put(colorButtons[3], v -> selectBlue(colorButtons[3]));}
            {put(colorButtons[4], v -> selectWhite(colorButtons[4]));}
            {put(colorButtons[5], v -> selectYellow(colorButtons[5]));}
        };
    }

    private void showAlert(String message, String positiveButton, String negativeButton, Runnable negativeAction){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Подтверждение");
        builder.setMessage(message);
        builder.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                negativeAction.run();
            }
        });
        builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void goSelectColors(View v) {
        showAlert(
                "Далее изменить разворот деталей будет невозможно, вы уверены?",
                "Проверю еще раз",
                "Да",
                this::changeGrid);
    }

    private void changeGrid(){
        Element.deleteBorder();
        findViewById(R.id.startScan).setEnabled(false);
        IsSelectColors = true;
        GridLayout grid = findViewById(R.id.workWithAxis);
        grid.removeAllViews();
        grid.setColumnCount(3);
        grid.setRowCount(4);
        Button[] colorButtons = getColorButtons();
        HashMap<Button, Drawable> drawablesButtons = getDrawableColorButtons(colorButtons);
        HashMap<Button, View.OnClickListener> methodsButtons = getMethodButtons(colorButtons);
        for(int row = 0; row < 4; row++)
            for(int column = 0; column < 3; column++){
                Button currentButton;
                boolean columnSpan = false;
                if(row * 3 + column < colorButtons.length) {
                    currentButton = colorButtons[row * 3 + column];
                    currentButton.setBackground(drawablesButtons.get(currentButton));
                    currentButton.setOnClickListener(methodsButtons.get(currentButton));
                }
                else if(row == 2 && column == 0){
                    currentButton = new Button(this);
                    currentButton.setText(R.string.clearColorsElement);
                    currentButton.setOnClickListener(this::clearColors);
                    columnSpan = true;
                }
                else if(row == 3 && column == 0){
                    currentButton = new Button(this);
                    currentButton.setId(R.id.solveCube);
                    currentButton.setText(R.string.textToSolveButton);
                    currentButton.setOnClickListener(this::solveCube);
                    columnSpan = true;
                }
                else
                    continue;
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(row);
                if(columnSpan)
                    params.columnSpec = GridLayout.spec(0, 3, 1f);
                else
                    params.columnSpec = GridLayout.spec(column, 1f);
                params.width = 0;
                params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                params.setMargins(20, 5, 20, 5);
                grid.addView(currentButton, params);
            }
    }

    public void selectRed(View v) {
        if (Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else if (Element.Position == AxisMI.PositionFrontSide.Center)
            Toast.makeText(this, "Здесь изменить цвета нельзя", Toast.LENGTH_SHORT).show();
        else if (Cube.CountColors.get(Color.RED) == Cube.MaxCountColor)
            Toast.makeText(this, "Достигнуто максимальное количество", Toast.LENGTH_SHORT).show();
        else{
            Cube.ViewSide.addColorElement(Element.Position, Color.RED);
            Element.setBackgroundWithBorder();
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
            Element.setBackgroundWithBorder();
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
            Element.setBackgroundWithBorder();
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
            Element.setBackgroundWithBorder();
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
            Element.setBackgroundWithBorder();
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
            Element.setBackgroundWithBorder();
            checkSolveButton();
        }
    }

    public void clearColors(View v){
        if(Element.elementIsNotSelected())
            Toast.makeText(this, "Выберите элемент", Toast.LENGTH_SHORT).show();
        else if(Element.Position == AxisMI.PositionFrontSide.Center)
            Toast.makeText(this, "Здесь очистить цвета нельзя", Toast.LENGTH_SHORT).show();
        else {
            Cube.ViewSide.clearColors(Element.Position);
            Element.setBackgroundWithBorder();
            checkSolveButton();
        }
    }

    private void checkSelectColorsButton(){
        Button selectButton = findViewById(R.id.selectColorsAxis);
        selectButton.setEnabled(Cube.cubeIsFillElements());
    }

    private void checkSolveButton(){
        Button solveButton = findViewById(R.id.solveCube);
        if(solveButton != null)
            solveButton.setEnabled(Cube.cubeIsFillColors());
    }

    public static byte[] drawable2Bytes(Drawable d) {
        Bitmap bitmap = drawable2Bitmap(d);
        return bitmap2Bytes(bitmap);
    }/*from  www . ja  v  a 2s.  com*/

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    private char getColor(Integer color){
        if(color == Color.RED)
            return 'R';
        else if(color == Color.parseColor("#FFA500"))
            return 'O';
        else if(color == Color.GREEN)
            return 'G';
        else if(color == Color.BLUE)
            return 'B';
        else if(color == Color.WHITE)
            return 'W';
        else
            return 'Y';
    }

    private char getDirection(String side){
        AxisMI.DirectionCenter[] directions = new AxisMI.DirectionCenter[]{ AxisMI.DirectionCenter.TopLeft, AxisMI.DirectionCenter.TopRight, AxisMI.DirectionCenter.BottomRight, AxisMI.DirectionCenter.BottomLeft };
        AxisMI.DirectionCenter rightDirection = Cube.RightDirectionCenters.get(side);
        AxisMI.DirectionCenter currentDirection = Cube.Centers.get(side).Direction;
        int first = Arrays.asList(directions).indexOf(rightDirection);
        if(currentDirection == rightDirection)
            return 'U';
        else if(currentDirection == directions[(first + 1) % directions.length])
            return 'R';
        else if(currentDirection == directions[(first + directions.length - 1) % directions.length])
            return 'L';
        else
            return 'D';
    }

    private char getColorCorner(String location, String side){
        AxisMI.Corner corner = Cube.Corners.get(Cube.getSortedString(location));
        if(corner.Type == AxisMI.TypeCorner.OneColor){

        }
        else{

        }
        return 'K';
    }

    public void solveCube(View v){
        Intent intent = new Intent(this, SolutionActivity.class);
        char[][][] colors = new char[6][3][3];
        char[] directions = new char[6];
        colors[0][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("FLU")).Colors.get("F"));
        colors[0][0][1] = getColor(Cube.Edges.get(Cube.getSortedString("UF")).Colors.get("F"));
        colors[0][0][2] = getColor(Cube.Corners.get(Cube.getSortedString("RUF")).Colors.get("F"));
        colors[0][1][0] = getColor(Cube.Edges.get(Cube.getSortedString("LF")).Colors.get("F"));
        colors[0][1][1] = getColor(Cube.Centers.get("F").Colors[0]);
        colors[0][1][2] = getColor(Cube.Edges.get(Cube.getSortedString("RF")).Colors.get("F"));
        colors[0][2][0] = getColor(Cube.Corners.get(Cube.getSortedString("LDF")).Colors.get("F"));
        colors[0][2][1] = getColor(Cube.Edges.get(Cube.getSortedString("DF")).Colors.get("F"));
        colors[0][2][2] = getColor(Cube.Corners.get(Cube.getSortedString("RDF")).Colors.get("F"));
        directions[0] = getDirection("F");

        colors[1][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("LUB")).Colors.get("U"));
        colors[1][0][1] = getColor(Cube.Edges.get(Cube.getSortedString("UB")).Colors.get("U"));
        colors[1][0][2] = getColor(Cube.Corners.get(Cube.getSortedString("RBU")).Colors.get("U"));
        colors[1][1][0] = getColor(Cube.Edges.get(Cube.getSortedString("LU")).Colors.get("U"));
        colors[1][1][1] = getColor(Cube.Centers.get("U").Colors[0]);
        colors[1][1][2] = getColor(Cube.Edges.get(Cube.getSortedString("UR")).Colors.get("U"));
        colors[1][2][0] = getColor(Cube.Corners.get(Cube.getSortedString("LFU")).Colors.get("U"));
        colors[1][2][1] = getColor(Cube.Edges.get(Cube.getSortedString("FU")).Colors.get("U"));
        colors[1][2][2] = getColor(Cube.Corners.get(Cube.getSortedString("RFU")).Colors.get("U"));
        directions[1] = getDirection("U");

        colors[2][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("BUR")).Colors.get("R"));
        colors[2][0][1] = getColor(Cube.Edges.get(Cube.getSortedString("RB")).Colors.get("R"));
        colors[2][0][2] = getColor(Cube.Corners.get(Cube.getSortedString("BDR")).Colors.get("R"));
        colors[2][1][0] = getColor(Cube.Edges.get(Cube.getSortedString("RU")).Colors.get("R"));
        colors[2][1][1] = getColor(Cube.Centers.get("R").Colors[0]);
        colors[2][1][2] = getColor(Cube.Edges.get(Cube.getSortedString("RD")).Colors.get("R"));
        colors[2][2][0] = getColor(Cube.Corners.get(Cube.getSortedString("FUR")).Colors.get("R"));
        colors[2][2][1] = getColor(Cube.Edges.get(Cube.getSortedString("FR")).Colors.get("R"));
        colors[2][2][2] = getColor(Cube.Corners.get(Cube.getSortedString("FDR")).Colors.get("R"));
        directions[2] = getDirection("R");

        colors[3][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("BDR")).Colors.get("D"));
        colors[3][0][1] = getColor(Cube.Edges.get(Cube.getSortedString("DB")).Colors.get("D"));
        colors[3][0][2] = getColor(Cube.Corners.get(Cube.getSortedString("BDL")).Colors.get("D"));
        colors[3][1][0] = getColor(Cube.Edges.get(Cube.getSortedString("RD")).Colors.get("D"));
        colors[3][1][1] = getColor(Cube.Centers.get("D").Colors[0]);
        colors[3][1][2] = getColor(Cube.Edges.get(Cube.getSortedString("LD")).Colors.get("D"));
        colors[3][2][0] = getColor(Cube.Corners.get(Cube.getSortedString("FDR")).Colors.get("D"));
        colors[3][2][1] = getColor(Cube.Edges.get(Cube.getSortedString("FD")).Colors.get("D"));
        colors[3][2][2] = getColor(Cube.Corners.get(Cube.getSortedString("FDL")).Colors.get("D"));
        directions[3] = getDirection("D");

        colors[4][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("BDL")).Colors.get("L"));
        colors[4][0][1] = getColor(Cube.Edges.get(Cube.getSortedString("LB")).Colors.get("L"));
        colors[4][0][2] = getColor(Cube.Corners.get(Cube.getSortedString("BUL")).Colors.get("L"));
        colors[4][1][0] = getColor(Cube.Edges.get(Cube.getSortedString("LD")).Colors.get("L"));
        colors[4][1][1] = getColor(Cube.Centers.get("L").Colors[0]);
        colors[4][1][2] = getColor(Cube.Edges.get(Cube.getSortedString("LU")).Colors.get("L"));
        colors[4][2][0] = getColor(Cube.Corners.get(Cube.getSortedString("FDL")).Colors.get("L"));
        colors[4][2][1] = getColor(Cube.Edges.get(Cube.getSortedString("FL")).Colors.get("L"));
        colors[4][2][2] = getColor(Cube.Corners.get(Cube.getSortedString("FUL")).Colors.get("L"));
        directions[4] = getDirection("L");

        colors[5][0][0] = getColor(Cube.Corners.get(Cube.getSortedString("BDR")).Colors.get("B"));
        colors[5][0][1] = getColor(Cube.Edges.get(Cube.getSortedString("BR")).Colors.get("B"));
        colors[5][0][2] = getColor(Cube.Corners.get(Cube.getSortedString("BUR")).Colors.get("B"));
        colors[5][1][0] = getColor(Cube.Edges.get(Cube.getSortedString("BD")).Colors.get("B"));
        colors[5][1][1] = getColor(Cube.Centers.get("B").Colors[0]);
        colors[5][1][2] = getColor(Cube.Edges.get(Cube.getSortedString("BU")).Colors.get("B"));
        colors[5][2][0] = getColor(Cube.Corners.get(Cube.getSortedString("BDL")).Colors.get("B"));
        colors[5][2][1] = getColor(Cube.Edges.get(Cube.getSortedString("BL")).Colors.get("B"));
        colors[5][2][2] = getColor(Cube.Corners.get(Cube.getSortedString("BUL")).Colors.get("B"));
        directions[5] = getDirection("B");

        intent.putExtra("type", 2);
        intent.putExtra("colors", colors);
        intent.putExtra("directions", directions);
        startActivity(intent);
    }
}