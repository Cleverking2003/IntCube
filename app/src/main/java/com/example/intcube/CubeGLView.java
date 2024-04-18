package com.example.intcube;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class CubeGLView extends GLSurfaceView {

    private CubeGLRenderer renderer;
    private float prev_x = 0, prev_y = 0;

    public native void handleMouseMovement(int x, int y);

    public CubeGLView(Context context) {
        super(context);
        setEGLContextClientVersion(3);

        renderer = new CubeGLRenderer(context);
        setRenderer(renderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - prev_x;
                float dy = y - prev_y;

                handleMouseMovement((int)dx, (int)dy);
        }

        prev_x = x;
        prev_y = y;
        return true;
    }
}
