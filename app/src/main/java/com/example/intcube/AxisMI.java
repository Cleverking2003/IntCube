package com.example.intcube;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class AxisMI{

    public static class Drawables{
        static HashMap<PositionFrontSide, HashMap<DirectionCorner, Integer[][][]>> OneColorCornerPoints = new HashMap<PositionFrontSide, HashMap<DirectionCorner, Integer[][][]>>()
        {
            {put(PositionFrontSide.TopLeft, new HashMap<DirectionCorner, Integer[][][]>(){
                {put(DirectionCorner.Front, new Integer[][][]{ { {50, 50} }, { {25, 50}, {50, 25} } });}
                {put(DirectionCorner.Horizontal, new Integer[][][]{ { {50, 50} }, { {0, 50}, {50, 20} } });}
                {put(DirectionCorner.Vertical, new Integer[][][]{ { {50, 50} }, { {20, 50}, {50, 0} } });}
            });}
            {put(PositionFrontSide.TopRight, new HashMap<DirectionCorner, Integer[][][]>(){
                {put(DirectionCorner.Front, new Integer[][][]{ { {0, 50} }, { {25, 50}, {0, 25} } });}
                {put(DirectionCorner.Horizontal, new Integer[][][]{ { {0, 50} }, { {50, 50}, {0, 20} } });}
                {put(DirectionCorner.Vertical, new Integer[][][]{ { {0, 50} }, { {0, 0}, {30, 50} } });}
            });}
            {put(PositionFrontSide.BottomLeft, new HashMap<DirectionCorner, Integer[][][]>(){
                {put(DirectionCorner.Front, new Integer[][][]{ { {50, 0} }, { {25, 0}, {50, 25} } });}
                {put(DirectionCorner.Horizontal, new Integer[][][]{ { {50, 0} }, { {0, 0}, {50, 30} } });}
                {put(DirectionCorner.Vertical, new Integer[][][]{ { {50, 0} }, { {50, 50}, {20, 0} } });}
            });}
            {put(PositionFrontSide.BottomRight, new HashMap<DirectionCorner, Integer[][][]>(){
                {put(DirectionCorner.Front, new Integer[][][]{ { {0, 0} }, { {25, 0}, {0, 25} } });}
                {put(DirectionCorner.Horizontal, new Integer[][][]{ { {0, 0} }, { {50, 0}, {0, 30} } });}
                {put(DirectionCorner.Vertical, new Integer[][][]{ { {0, 0} }, { {30, 0}, {0, 50} } });}
            });}
        };
        static HashMap<PositionFrontSide, Integer[][][]> ThreeColorCornerPoints = new HashMap<PositionFrontSide, Integer[][][]>(){
            {put(PositionFrontSide.TopLeft, new Integer[][][]{ { {50, 50} }, { {0, 50}, {10, 10} }, { {50, 0}, {10, 10} } });}
            {put(PositionFrontSide.TopRight, new Integer[][][]{ { {0, 50} }, { {0, 0}, {40, 10} }, { {50, 50}, {40, 10} } });}
            {put(PositionFrontSide.BottomLeft, new Integer[][][]{ { {50, 0} }, { {0, 0}, {10, 40} }, { {50, 50}, {10, 40} } });}
            {put(PositionFrontSide.BottomRight, new Integer[][][]{ { {0, 0} }, { {0, 50}, {40, 40} }, { {50, 0}, {40, 40} } });}
        };
        static HashMap<PositionFrontSide, HashMap<DirectionEdge, Integer[][][]>> OneColorEdgePoints = new HashMap<PositionFrontSide, HashMap<DirectionEdge, Integer[][][]>>(){
            {put(PositionFrontSide.Left, new HashMap<DirectionEdge, Integer[][][]>(){
                {put(DirectionEdge.Left, new Integer[][][]{ { {50, 50} }, { {0, 50}, {20, 0}, {50, 0} }  });}
                {put(DirectionEdge.Right, new Integer[][][]{ { {50, 0} }, { {0, 0}, {20, 50}, {50, 50} } });}
            });}
            {put(PositionFrontSide.Top, new HashMap<DirectionEdge, Integer[][][]>(){
                {put(DirectionEdge.Left, new Integer[][][]{ { {0, 50} }, { {0, 0}, {50, 20}, {50, 50} }  });}
                {put(DirectionEdge.Right, new Integer[][][]{ { {50, 50} }, { {50, 0}, {0, 20}, {0, 50} } });}
            });}
            {put(PositionFrontSide.Bottom, new HashMap<DirectionEdge, Integer[][][]>(){
                {put(DirectionEdge.Left, new Integer[][][]{ { {50, 0} }, { {50, 50}, {0, 30}, {0, 0} } });}
                {put(DirectionEdge.Right, new Integer[][][]{ { {0, 0} }, { {0, 50}, {50, 30}, {50, 0} } });}
            });}
            {put(PositionFrontSide.Right, new HashMap<DirectionEdge, Integer[][][]>(){
                {put(DirectionEdge.Left, new Integer[][][]{ { {0, 0} }, { {50, 0}, {30, 50}, {0, 50} } });}
                {put(DirectionEdge.Right, new Integer[][][]{ { {0, 50} }, { {50, 50}, {30, 0}, {0, 0} } });}
            });}
        };
        static HashMap<PositionFrontSide, HashMap<DirectionEdge, Integer[][][]>> TwoColorEdgePoints = new HashMap<PositionFrontSide, HashMap<DirectionEdge, Integer[][][]>>(){
            {put(PositionFrontSide.Left, new HashMap<DirectionEdge, Integer[][][]>(){
                {put(DirectionEdge.Left, new Integer[][][]{ { {50, 0} }, { {50, 50}, {0, 50} }, { {20, 0}, {0, 50} }  });}
                {put(DirectionEdge.Right, new Integer[][][]{ { {50, 50} }, { {50, 0}, {0, 0} }, { {20, 50}, {0, 0}} });}
            });}
            {put(PositionFrontSide.Top, new HashMap<DirectionEdge, Integer[][][]>(){
                {put(DirectionEdge.Left, new Integer[][][]{ { {50, 50} }, { {0, 50}, {0, 0} }, { {0, 0}, {50, 20} }  });}
                {put(DirectionEdge.Right, new Integer[][][]{ { {0, 50} }, { {50, 50}, {50, 0} }, { {0, 20}, {50, 0} } });}
            });}
            {put(PositionFrontSide.Bottom, new HashMap<DirectionEdge, Integer[][][]>(){
                {put(DirectionEdge.Left, new Integer[][][]{ { {0, 0} }, { {50, 0}, {50, 50} }, { {0, 30}, {50, 50} } });}
                {put(DirectionEdge.Right, new Integer[][][]{ { {50, 0} }, { {0, 0}, {0, 50} }, { {50, 30}, {0, 50} } });}
            });}
            {put(PositionFrontSide.Right, new HashMap<DirectionEdge, Integer[][][]>(){
                {put(DirectionEdge.Left, new Integer[][][]{ { {0, 50} }, { {0, 0}, {50, 0} }, { {30, 50}, {50, 0} } });}
                {put(DirectionEdge.Right, new Integer[][][]{ { {0, 0} }, { {0, 50}, {50, 50} }, { {30, 0}, {50, 50} } });}
            });}
        };

        static HashMap<DirectionCenter, Integer[][][]> CenterPoints = new HashMap<DirectionCenter, Integer[][][]>(){
            {put(DirectionCenter.TopLeft, new Integer[][][]{ { {50, 0} }, { {0, 50}, {0, 0} }, { {50, 50}, {0, 50} } });}
            {put(DirectionCenter.TopRight, new Integer[][][]{ { {50, 50} }, { {0, 0}, {50, 0} }, { {0, 50}, {0, 0} } });}
            {put(DirectionCenter.BottomRight, new Integer[][][]{ { {50, 0} }, { {50, 50}, {0, 50} }, { {0, 50}, {0, 0} } });}
            {put(DirectionCenter.BottomLeft, new Integer[][][]{ { {50, 50} }, { {0, 50}, {0, 0} }, { {0, 0}, {50, 0} } });}
        };

        public static Drawable getCornerDrawable(TypeCorner type, DirectionCorner direction, PositionFrontSide position, Integer[] colors){
            if(type == TypeCorner.OneColor){
                Integer[][][] points = OneColorCornerPoints.get(position).get(direction);
                return getOneColorDrawable(points[0][0], points[1], colors[0]);
            }
            else{
                Integer[][][] points = ThreeColorCornerPoints.get(position);
                return getTwoColorDrawable(points[0][0], points[1], points[2], colors);
            }
        }

        public static Drawable getEdgeDrawable(TypeEdge type, DirectionEdge direction, PositionFrontSide position, Integer[] colors){
            if(type == TypeEdge.OneColor){
                Integer[][][] points = OneColorEdgePoints.get(position).get(direction);
                return getOneColorDrawable(points[0][0], points[1], colors[0]);
            }
            else{
                Integer[][][] points = TwoColorEdgePoints.get(position).get(direction);
                return getTwoColorDrawable(points[0][0], points[1], points[2], colors);
            }
        }

        public static Drawable getCenterDrawable(DirectionCenter pos, Integer[] colors){
            Integer[][][] points = CenterPoints.get(pos);
            return getTwoColorDrawable(points[0][0], points[1], points[2], colors);
        }

        private static Drawable getOneColorDrawable(Integer[] startPoint, Integer[][] lines, Integer color){
            Path path = new Path();
            path.moveTo(startPoint[0], startPoint[1]);
            for(int i = 0; i < lines.length; i++)
                path.lineTo(lines[i][0], lines[i][1]);
            path.close();
            ShapeDrawable shapeDrawable = new ShapeDrawable(new PathShape(path, 50, 50));
            shapeDrawable.getPaint().setColor(color);
            return shapeDrawable;
        }

        private static Drawable getTwoColorDrawable(Integer[] startPoint, Integer[][] firstLines, Integer[][] secondLines, Integer[] colors){
            Path first = new Path();
            Path second = new Path();
            first.moveTo(startPoint[0], startPoint[1]);
            second.moveTo(startPoint[0], startPoint[1]);
            for(int i = 0; i < firstLines.length; i++){
                first.lineTo(firstLines[i][0], firstLines[i][1]);
                second.lineTo(secondLines[i][0], secondLines[i][1]);
            }
            first.close();
            second.close();
            ShapeDrawable firstDrawable = new ShapeDrawable(new PathShape(first, 50, 50));
            ShapeDrawable secondDrawable = new ShapeDrawable(new PathShape(second, 50, 50));
            firstDrawable.getPaint().setColor(colors[0]);
            secondDrawable.getPaint().setColor(colors[1]);
            return new LayerDrawable(new Drawable[]{ firstDrawable, secondDrawable });
        }
    }

    enum TypeCorner{
        OneColor,
        ThreeColors
    }

    enum TypeEdge{
        OneColor,
        TwoColors
    }

    enum DirectionCorner{
        Front,
        Horizontal,
        Vertical
    }

    enum OrientationCorner{
        Front,
        LeftRight,
        TopBottom
    }

    enum DirectionEdge{
        Left,
        Right;

        public static DirectionEdge getNewAfterRotate(DirectionEdge old){
            return old == Left ? Right : Left;
        }
    }

    enum DirectionCenter {
        TopLeft,
        TopRight,
        BottomLeft,
        BottomRight;

        public static DirectionCenter getNewAfterRotateLeft(DirectionCenter old){
            return old == TopLeft ? BottomLeft : old == BottomLeft ? BottomRight : old == BottomRight ? TopRight : TopLeft;
        }

        public static DirectionCenter getNewAfterRotateRight(DirectionCenter old){
            return old == TopLeft ? TopRight : old == TopRight ? BottomRight : old == BottomRight ? BottomLeft : TopLeft;
        }

    }

    enum PositionFrontSide {
        TopLeft,
        Top,
        TopRight,
        Left,
        Center,
        Right,
        BottomLeft,
        Bottom,
        BottomRight;

        public static PositionFrontSide[] getAll(){
            return new PositionFrontSide[]{ TopLeft, Top, TopRight, Left, Center, Right, BottomLeft, Bottom, BottomRight };
        }

        public static PositionFrontSide getPosition(int row, int column, int widht, int height){
            if(row == 0 && column == 0)
                return TopLeft;
            if(row == 0 && column == widht)
                return TopRight;
            if(row == height && column == 0)
                return BottomLeft;
            if(row == height && column == widht)
                return BottomRight;
            if(row == 0)
                return Top;
            if(column == 0)
                return Left;
            if(column == height)
                return Right;
            if(row == widht)
                return Bottom;
            return Center;
        }

        public static boolean isPositionEdge(PositionFrontSide position){
            return position == AxisMI.PositionFrontSide.Left ||
                    position == AxisMI.PositionFrontSide.Top ||
                    position == AxisMI.PositionFrontSide.Right ||
                    position == AxisMI.PositionFrontSide.Bottom;
        }

        public static boolean isPositionCorner(PositionFrontSide position){
            return position == AxisMI.PositionFrontSide.TopLeft ||
                    position == AxisMI.PositionFrontSide.TopRight ||
                    position == AxisMI.PositionFrontSide.BottomRight ||
                    position == AxisMI.PositionFrontSide.BottomLeft;
        }
    }

    enum Side {
        Front,
        Left,
        Up,
        Right,
        Down,
        Back
    }

    class ViewSide{
        protected HashMap<PositionFrontSide, Corner> ViewCorners = new HashMap<>();
        protected HashMap<PositionFrontSide, Edge> ViewEdges = new HashMap<>();
        protected Center ViewCenter;

        public HashMap<String, Drawable> getColorsNeighboringSidesCenters(){
            return new HashMap<String, Drawable>(){
                {put("L", Centers.get(LocationSides.get(Side.Left)).getDrawable());}
                {put("U", Centers.get(LocationSides.get(Side.Up)).getDrawable());}
                {put("R", Centers.get(LocationSides.get(Side.Right)).getDrawable());}
                {put("D", Centers.get(LocationSides.get(Side.Down)).getDrawable());}
            };
        }

        public void addCorner(PositionFrontSide position, TypeCorner type){
            String locCorner = getLocationCorner(position);
            String locCornerSorted = getSortedString(locCorner);
            if(Corners.containsKey(getSortedString(locCornerSorted)))
                CountCorners.put(Corners.get(locCornerSorted).Type, CountCorners.get(Corners.get(locCornerSorted).Type) - 1);
            Corners.put(locCornerSorted, new Corner(locCorner, type));
            ViewCorners.put(position, Corners.get(locCornerSorted));
            CountCorners.put(type, CountCorners.get(type) + 1);
        }

        public void addEdge(PositionFrontSide position, TypeEdge type){
            String locEdge = getLocationEdge(position);
            String locEdgeSorted = getSortedString(locEdge);
            if(Edges.containsKey(locEdgeSorted))
                CountEdges.put(Edges.get(locEdgeSorted).Type, CountEdges.get(Edges.get(locEdgeSorted).Type) - 1);
            Edges.put(locEdgeSorted, new Edge(locEdge, type));
            ViewEdges.put(position, Edges.get(locEdgeSorted));
            CountEdges.put(type, CountEdges.get(type) + 1);
        }

        public void deleteCorner(PositionFrontSide positionFrontSide){
            String locCorner = getSortedString(getLocationCorner(positionFrontSide));
            CountCorners.put(Corners.get(locCorner).Type, CountCorners.get(Corners.get(locCorner).Type) - 1);
            ViewCorners.remove(positionFrontSide);
            Corners.remove(locCorner);
        }

        public void deleteEdge(PositionFrontSide positionFrontSide){
            String locEdge = getSortedString(getLocationEdge(positionFrontSide));
            CountEdges.put(Edges.get(locEdge).Type, CountEdges.get(Edges.get(locEdge).Type) - 1);
            ViewEdges.remove(positionFrontSide);
            Edges.remove(locEdge);
        }

        public TypeCorner getCornerType(PositionFrontSide positionFrontSide){
            if(ViewCorners.containsKey(positionFrontSide) && ViewCorners.get(positionFrontSide) != null)
                return ViewCorners.get(positionFrontSide).Type;
            else
                return null;
        }

        public TypeEdge getEdgeType(PositionFrontSide positionFrontSide){
            if(ViewEdges.containsKey(positionFrontSide) && ViewEdges.get(positionFrontSide) != null)
                return ViewEdges.get(positionFrontSide).Type;
            else
                return null;
        }

        public void rotateElement(PositionFrontSide positionFrontSide){
            if(ViewCorners.containsKey(positionFrontSide) && ViewCorners.get(positionFrontSide) != null)
                ViewCorners.get(positionFrontSide).rotateCorner();
            else if(ViewEdges.containsKey(positionFrontSide) && ViewEdges.get(positionFrontSide) != null)
                ViewEdges.get(positionFrontSide).rotateEdge();
            else
                ViewCenter.rotateCenter();
        }

        public boolean elementIsNotAdd(PositionFrontSide positionFrontSide){
            if(ViewCorners.containsKey(positionFrontSide) && ViewCorners.get(positionFrontSide) != null)
                return false;
            else if(ViewEdges.containsKey(positionFrontSide) && ViewEdges.get(positionFrontSide) != null)
                return false;
            else return !(positionFrontSide == PositionFrontSide.Center);
        }

        public Drawable getDrawable(PositionFrontSide positionFrontSide){
            if(ViewCorners.containsKey(positionFrontSide) && ViewCorners.get(positionFrontSide) != null)
                return ViewCorners.get(positionFrontSide).getDrawable(LocationSides.get(Side.Front), positionFrontSide);
            else if(ViewEdges.containsKey(positionFrontSide) && ViewEdges.get(positionFrontSide) != null)
                return ViewEdges.get(positionFrontSide).getDrawable(LocationSides.get(Side.Front), positionFrontSide);
            else if(positionFrontSide == PositionFrontSide.Center)
                return ViewCenter.getDrawable();
            else
                return null;
        }

        public void addColorElement(PositionFrontSide positionFrontSide, Integer color){
            Corner corner;
            Integer oldColor;
            if(ViewCorners.containsKey(positionFrontSide) && (corner = ViewCorners.get(positionFrontSide)) != null)
                corner.addColor(positionFrontSide, LocationSides.get(Side.Front), color);
            else
                ViewEdges.get(positionFrontSide).addColor(LocationSides.get(Side.Front), color);
        }

        public void clearColorsCorner(PositionFrontSide positionFrontSide){
            String locCorner;
            if((locCorner = getLocationCorner(positionFrontSide)) != null) {
                locCorner = getSortedString(locCorner);
                Corners.get(locCorner).clearColors();
            }
        }

        public void clearColorsEdge(PositionFrontSide positionFrontSide){
            String locEdge;
            if((locEdge = getLocationEdge(positionFrontSide)) != null) {
                locEdge = getSortedString(locEdge);
                Edges.get(locEdge).clearColors();
            }
        }

        protected void clear(){
            ViewCorners.clear();
            ViewEdges.clear();
            ViewCenter = null;
        }
    }

    class Corner
    {
        TypeCorner Type;
        public String Direction;
        public Map<String, Integer> Colors = new HashMap<>();
        public HashMap<OrientationCorner, String> Orientation = new HashMap<>();
        private final OrientationCorner[] OrientationsCorner = new OrientationCorner[]{ OrientationCorner.Front, OrientationCorner.LeftRight, OrientationCorner.TopBottom };

        public Corner(String location, TypeCorner type) {
            Type = type;
            for(int i = 0; i < location.length(); i++){
                String side = String.valueOf(location.charAt(i));
                Orientation.put(OrientationsCorner[i], side);
                Colors.put(side, Color.GRAY);
                if(Direction == null)
                    Direction = side;
            }
        }

        public Drawable getDrawable(String side, PositionFrontSide position) {
            Integer[] colors = Type == TypeCorner.OneColor ? new Integer[]{Colors.get(side)}
                    : position == PositionFrontSide.TopLeft ? new Integer[]{ Colors.get(Orientation.get(OrientationCorner.Front)), Colors.get(Orientation.get(OrientationCorner.TopBottom)) }
                    : position == PositionFrontSide.TopRight ? new Integer[]{ Colors.get(Orientation.get(OrientationCorner.Front)), Colors.get(Orientation.get(OrientationCorner.LeftRight)) }
                    : position == PositionFrontSide.BottomRight ? new Integer[]{ Colors.get(Orientation.get(OrientationCorner.TopBottom)), Colors.get(Orientation.get(OrientationCorner.Front)) }
                    : new Integer[]{ Colors.get(Orientation.get(OrientationCorner.LeftRight)), Colors.get(Orientation.get(OrientationCorner.Front)) };
            return Drawables.getCornerDrawable(Type, getDirection(), position, colors);
        }

        public void rotateCorner(){
            if(Type == TypeCorner.ThreeColors)
                return;
            int index = 0;
            while(!Objects.equals(Orientation.get(OrientationsCorner[index]), Direction))
                index++;
            Direction = Orientation.get(OrientationsCorner[(index + 1) % OrientationsCorner.length]);
        }

        public void changeOrientation(String[] newOrientation){
            for(int i = 0; i < OrientationsCorner.length; i++)
                Orientation.put(OrientationsCorner[i], newOrientation[i]);
        }

        public DirectionCorner getDirection(){
            if(Objects.equals(Orientation.get(OrientationsCorner[0]), Direction))
                return DirectionCorner.Front;
            else if(Objects.equals(Orientation.get(OrientationsCorner[1]), Direction))
                return DirectionCorner.Horizontal;
            else
                return DirectionCorner.Vertical;
        }

        public void addColor(PositionFrontSide positionFrontSide, String side, int color){
            if(Type == TypeCorner.OneColor && Colors.get(side) == Color.GRAY) {
                Colors.replaceAll((k, v) -> color);
                CountColors.put(color, CountColors.get(color) + 1);
            }
            else{
                if(Colors.get(side) == Color.GRAY) {
                    Colors.put(side, color);
                    CountColors.put(color, CountColors.get(color) + 1);
                }
                else{
                    if(positionFrontSide == PositionFrontSide.TopLeft || positionFrontSide == PositionFrontSide.BottomRight) {
                        if (Colors.get(Orientation.get(OrientationCorner.TopBottom)) == Color.GRAY && Colors.get(side) != color) {
                            Colors.put(Orientation.get(OrientationCorner.TopBottom), color);
                            CountColors.put(color, CountColors.get(color) + 1);
                        }
                    }
                    else if(positionFrontSide == PositionFrontSide.TopRight || positionFrontSide == PositionFrontSide.BottomLeft) {
                        if (Colors.get(Orientation.get(OrientationCorner.LeftRight)) == Color.GRAY && Colors.get(side) != color) {
                            Colors.put(Orientation.get(OrientationCorner.LeftRight), color);
                            CountColors.put(color, CountColors.get(color) + 1);
                        }
                    }
                }
            }
        }

        public void clearColors(){
            if(Type == TypeCorner.OneColor) {
                CountColors.put(Colors.get(Colors.get(OrientationsCorner[0])), CountColors.get(Colors.get(Colors.get(OrientationsCorner[0]))) - 1);
                Colors.replaceAll((k, v) -> Color.GRAY);
            }
            for(String key : Colors.keySet()) {
                if(Colors.get(key) != Color.GRAY)
                    CountColors.put(Colors.get(key), CountColors.get(Colors.get(key)) - 1);
                Colors.put(key, Color.GRAY);
            }
        }
    }

    class Edge{
        TypeEdge Type;
        String Location;
        public Map<String, DirectionEdge> Directions = new HashMap<>();
        public Map<String, Integer[]> Colors = new HashMap<>();

        public Edge(String location, TypeEdge type){
            Location = location;
            Type = type;
            DirectionEdge direction = DirectionEdge.Left;
            for(int i = 0; i < location.length(); i++){
                String side = String.valueOf(location.charAt(i));
                Colors.put(side, new Integer[]{ Color.GRAY, Color.GRAY });
                if(i == 0)
                    Directions.put(side, DirectionEdge.Left);
                else {
                    if(type == TypeEdge.OneColor)
                        Directions.put(side, DirectionEdge.getNewAfterRotate(Directions.get(String.valueOf(location.charAt(i - 1)))));
                    else
                        Directions.put(side, Directions.get(String.valueOf(location.charAt(i - 1))));
                }
            }
        }

        public Drawable getDrawable(String side, PositionFrontSide position){
            return Drawables.getEdgeDrawable(Type, Directions.get(side), position, Colors.get(side));
        }

        public void rotateEdge(){
            for(String key : Directions.keySet())
                Directions.put(key, DirectionEdge.getNewAfterRotate(Directions.get(key)));
        }

        public void addColor(String side, Integer color){
            if(Colors.get(side)[0] != Color.GRAY) {
                if(Objects.equals(Colors.get(side)[0], color))
                    return;
                if(Colors.get(side)[1] != Color.GRAY)
                    return;
                CountColors.put(color, CountColors.get(color) + 1);
                Colors.get(side)[1] = color;
                if(Objects.equals(side, String.valueOf(Location.charAt(0))))
                    Colors.get(String.valueOf(Location.charAt(1)))[0] = color;
                else
                    Colors.get(String.valueOf(Location.charAt(0)))[0] = color;
            }
            else {
                CountColors.put(color, CountColors.get(color) + 1);
                Colors.get(side)[0] = color;
                if(Objects.equals(side, String.valueOf(Location.charAt(0))))
                    Colors.get(String.valueOf(Location.charAt(1)))[1] = color;
                else
                    Colors.get(String.valueOf(Location.charAt(0)))[1] = color;
            }
        }

        public void clearColors(){
            for(String key : Colors.keySet()) {
                if(Colors.get(key)[0] != Color.GRAY)
                    CountColors.put(Colors.get(key)[0], CountColors.get(Colors.get(key)[0]) - 1);
                if(Colors.get(key)[1] != Color.GRAY)
                    CountColors.put(Colors.get(key)[1], CountColors.get(Colors.get(key)[1]) - 1);
                Colors.put(key, new Integer[]{Color.GRAY, Color.GRAY});
            }
        }
    }

    class Center{
        Integer[] Colors;
        DirectionCenter Direction;
        String[] Orientation;

        public Center(String[] orientation, Integer[] colors){
            Colors = colors;
            Orientation = orientation;
            Direction = DirectionCenter.TopLeft;
        }

        public Drawable getDrawable(){
            return Drawables.getCenterDrawable(Direction, Colors);
        }

        public void rotateCenter(){
            Direction = DirectionCenter.getNewAfterRotateLeft(Direction);
        }

        public void checkOrientation(String[] orientation){

            if(!Objects.equals(Orientation[0], orientation[0]))
            {
                if(Objects.equals(Orientation[1], orientation[0]))
                    Direction = DirectionCenter.getNewAfterRotateLeft(Direction);
                else if(Objects.equals(Orientation[2], orientation[0]))
                    Direction = DirectionCenter.getNewAfterRotateRight(DirectionCenter.getNewAfterRotateRight(Direction));
                else
                    Direction = DirectionCenter.getNewAfterRotateRight(Direction);
            }
            System.arraycopy(orientation, 0, Orientation, 0, Orientation.length);
        }
    }

    int Size = 3;
    int MaxCountColor = 8;
    Map<Integer, Integer> CountColors = new HashMap<Integer, Integer>(){
        {put(Color.RED, 0);}
        {put(Color.BLUE, 0);}
        {put(Color.parseColor("#FFA500"), 0);}
        {put(Color.GREEN, 0);}
        {put(Color.WHITE, 0);}
        {put(Color.YELLOW, 0);}
    };
    Map<TypeCorner, Integer> CountCorners = new HashMap<TypeCorner, Integer>(){
        {put(TypeCorner.OneColor, 0);}
        {put(TypeCorner.ThreeColors, 0);}
    };
    Map<TypeCorner, Integer> MaxCountCorners = new HashMap<TypeCorner, Integer>(){
        {put(TypeCorner.OneColor, 6);}
        {put(TypeCorner.ThreeColors, 2);}
    };
    Map<TypeEdge, Integer> CountEdges = new HashMap<TypeEdge, Integer>(){
        {put(TypeEdge.OneColor, 0);}
        {put(TypeEdge.TwoColors, 0);}
    };
    Map<TypeEdge, Integer> MaxCountEdges = new HashMap<TypeEdge, Integer>(){
        {put(TypeEdge.OneColor, 6);}
        {put(TypeEdge.TwoColors, 6);}
    };
    Map<Side, String> LocationSides = new HashMap<Side, String>(){
        {put(Side.Left, "L");}
        {put(Side.Up, "U");}
        {put(Side.Right, "R");}
        {put(Side.Down, "D");}
        {put(Side.Front, "F");}
        {put(Side.Back, "B");}
    };
    ViewSide ViewSide = new ViewSide();
    private Map<String, Corner> Corners = new HashMap<>();
    private Map<String, Edge> Edges = new HashMap<>();
    private Map<String, Center> Centers = new HashMap<>();

    public void createCenters(){
        HashMap<String, Integer[]> centersColor = new HashMap<String, Integer[]>(){
            {put("F", new Integer[]{Color.RED, Color.WHITE});}
            {put("R", new Integer[]{Color.RED, Color.GREEN});}
            {put("B", new Integer[]{Color.parseColor("#FFA500"), Color.YELLOW});}
            {put("L", new Integer[]{Color.parseColor("#FFA500"), Color.BLUE});}
            {put("U", new Integer[]{Color.BLUE, Color.YELLOW});}
            {put("D", new Integer[]{Color.WHITE, Color.GREEN});}
        };
        HashMap<String, String[]> centresOrientation = new HashMap<String, String[]>(){
            {put("F", new String[]{ "L", "U", "R", "D" });}
            {put("R", new String[]{ "F", "U", "B", "D" });}
            {put("B", new String[]{ "R", "U", "L", "D" });}
            {put("L", new String[]{ "B", "U", "F", "D" });}
            {put("U", new String[]{ "L", "B", "R", "F" });}
            {put("D", new String[]{ "R", "F", "L", "B" });}
        };
        for(Map.Entry<String, Integer[]> center : centersColor.entrySet()) {
            Centers.put(center.getKey(), new Center(centresOrientation.get(center.getKey()), center.getValue()));
            CountColors.put(center.getValue()[0], CountColors.get(center.getValue()[0]) + 1);
            CountColors.put(center.getValue()[1], CountColors.get(center.getValue()[1]) + 1);
        }
    }

    public void createCurrentView(){
        ViewSide.clear();
        fillViewSide();
    }

    private void fillViewSide() {
        for(PositionFrontSide position : PositionFrontSide.getAll()) {
            if (position == PositionFrontSide.TopLeft || position == PositionFrontSide.TopRight
                    || position == PositionFrontSide.BottomLeft || position == PositionFrontSide.BottomRight){
                String locCorner = getLocationCorner(position);
                Corner corner;
                if(Corners.containsKey(getSortedString(locCorner)) && (corner = Corners.get(getSortedString(locCorner))) != null) {
                    String[] orientation = new String[3];
                    for(int i = 0; i < locCorner.length(); i++)
                        orientation[i] = String.valueOf(locCorner.charAt(i));
                    corner.changeOrientation(orientation);
                }
                ViewSide.ViewCorners.put(position, Corners.get(getSortedString(getLocationCorner(position))));

            }
            else if (position == PositionFrontSide.Left || position == PositionFrontSide.Top
                    || position == PositionFrontSide.Right || position == PositionFrontSide.Bottom)
                ViewSide.ViewEdges.put(position, Edges.get(getSortedString(getLocationEdge(position))));
            else
                ViewSide.ViewCenter = Centers.get(LocationSides.get(Side.Front));
        }
    }

    public boolean cubeIsFillElements(){
        for(Map.Entry<TypeCorner, Integer> countCorner : CountCorners.entrySet())
            if(!Objects.equals(MaxCountCorners.get(countCorner.getKey()), countCorner.getValue()))
                return false;
        for(Map.Entry<TypeEdge, Integer> countEdge : CountEdges.entrySet())
            if(!Objects.equals(MaxCountEdges.get(countEdge.getKey()), countEdge.getValue()))
                return false;
        return true;
    }

    public boolean cubeIsFillColors(){
        for(Map.Entry<Integer, Integer> countColor : CountColors.entrySet())
            if(countColor.getValue() != MaxCountColor)
                return false;
        return true;
    }

    private String getLocationCorner(PositionFrontSide positionFrontSide){
        if(positionFrontSide == PositionFrontSide.TopLeft)
            return LocationSides.get(Side.Front) + LocationSides.get(Side.Left) + LocationSides.get(Side.Up);
        else if(positionFrontSide == PositionFrontSide.TopRight)
            return LocationSides.get(Side.Front) + LocationSides.get(Side.Right) + LocationSides.get(Side.Up);
        else if(positionFrontSide == PositionFrontSide.BottomLeft)
            return LocationSides.get(Side.Front) + LocationSides.get(Side.Left) + LocationSides.get(Side.Down);
        else if(positionFrontSide == PositionFrontSide.BottomRight)
            return LocationSides.get(Side.Front) + LocationSides.get(Side.Right) + LocationSides.get(Side.Down);
        else
            return null;
    }

    private String getLocationEdge(PositionFrontSide positionFrontSide){
        if(positionFrontSide == PositionFrontSide.Left)
            return LocationSides.get(Side.Front) + LocationSides.get(Side.Left);
        else if(positionFrontSide == PositionFrontSide.Top)
            return LocationSides.get(Side.Front) + LocationSides.get(Side.Up);
        else if(positionFrontSide == PositionFrontSide.Right)
            return LocationSides.get(Side.Front) + LocationSides.get(Side.Right);
        else if(positionFrontSide == PositionFrontSide.Bottom)
            return LocationSides.get(Side.Front) + LocationSides.get(Side.Down);
        else
            return null;
    }

    private String getSortedString(String source){
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
        checkOrientationCenters();
    }

    public void turnRight(){
        Side[] sidesSwap = new Side[]{ Side.Front, Side.Right, Side.Back, Side.Left };
        HashMap<Side, String> sourceSides = new HashMap<>();
        for(int i = 0; i < sidesSwap.length; i++)
            sourceSides.put(sidesSwap[i], LocationSides.get(sidesSwap[i]));
        for(int i = 0; i < sidesSwap.length; i++)
            LocationSides.put(sidesSwap[i], sourceSides.get(sidesSwap[(i + 1) % sidesSwap.length]));
        checkOrientationCenters();
    }

    public void turnDown(){
        Side[] sidesSwap = new Side[]{ Side.Front, Side.Down, Side.Back, Side.Up };
        HashMap<Side, String> sourceSides = new HashMap<>();
        for(int i = 0; i < sidesSwap.length; i++)
            sourceSides.put(sidesSwap[i], LocationSides.get(sidesSwap[i]));
        for(int i = 0; i < sidesSwap.length; i++)
            LocationSides.put(sidesSwap[i], sourceSides.get(sidesSwap[(i + 1) % sidesSwap.length]));
        checkOrientationCenters();
    }

    public void turnLeft(){
        Side[] sidesSwap = new Side[]{ Side.Front, Side.Left, Side.Back, Side.Right };
        HashMap<Side, String> sourceSides = new HashMap<>();
        for(int i = 0; i < sidesSwap.length; i++)
            sourceSides.put(sidesSwap[i], LocationSides.get(sidesSwap[i]));
        for(int i = 0; i < sidesSwap.length; i++)
            LocationSides.put(sidesSwap[i], sourceSides.get(sidesSwap[(i + 1) % sidesSwap.length]));
        checkOrientationCenters();
    }
}