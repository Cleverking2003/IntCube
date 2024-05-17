package com.example.intcube;

import android.content.Context;
import android.content.res.Configuration;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CubeGLView extends GLSurfaceView {

    private CubeGLRenderer renderer;
    private boolean mDisableTouch = false;
    public native void handleDragStart(int x, int y);

    public native void handleMouseMovement(int x, int y);
    public native void handleDragStop(int x, int y);
    public native void setClearColor(float r, float g, float b, float a);
    public native void setDistance(float z);

    public CubeGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);

        renderer = new CubeGLRenderer(context);
        setRenderer(renderer);
        setLongClickable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDisableTouch) return true;
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

    void disableTouch() {
        mDisableTouch = true;
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
        private boolean m_inverse;
        public ExecuteMove(int type, boolean inverse) {
            m_type = type;
            m_inverse = inverse;
        }
        @Override
        public void run() {
            renderer.executeMove(m_type, m_inverse);
        }
    }
    public class ApplyMove implements Runnable {
        private int m_type;
        private boolean m_inverse;
        public ApplyMove(int type, boolean inverse) {
            m_type = type;
            m_inverse = inverse;
        }
        @Override
        public void run() {
            renderer.applyMove(m_type, m_inverse);
        }
    }
    public void changeCube(int type) {
        queueEvent(new CreateCube(type));
    }
    public void executeMove(int type, boolean inverse) {
        queueEvent(new ExecuteMove(type, inverse));
    }
    public void applyMove(int type, boolean inverse) {
        queueEvent(new ApplyMove(type, inverse));
    }
}
