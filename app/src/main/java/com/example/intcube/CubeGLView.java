package com.example.intcube;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CubeGLView extends GLSurfaceView {

    private CubeGLRenderer renderer;
    public native void handleDragStart(int x, int y);

    public native void handleMouseMovement(int x, int y);
    public native void handleDragStop(int x, int y);

    public CubeGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);

        renderer = new CubeGLRenderer(context);
        setRenderer(renderer);
        setLongClickable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleDragStart((int)x, (int)y);
                break;
            case MotionEvent.ACTION_MOVE:
                handleMouseMovement((int)x, (int)y);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                handleDragStop((int)x, (int)y);
                break;
        }
        return true;
    }

    public class CreateCube implements Runnable {
        private int m_type;
        public CreateCube(int type) {
            m_type = type;
        }
        @Override
        public void run() {
            renderer.changeCube(m_type);
        }
    }
    public class ExecuteMove implements Runnable {
        private int m_type;
        public ExecuteMove(int type) {
            m_type = type;
        }
        @Override
        public void run() {
            renderer.executeMove(m_type, false);
        }
    }
    public void changeCube(int type) {
        queueEvent(new CreateCube(type));
    }
    public void executeMove(int type) {
        queueEvent(new ExecuteMove(type));
    }
}
