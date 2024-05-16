package com.example.intcube;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CubeGLRenderer implements GLSurfaceView.Renderer {
    public native void initScene(int width, int height, int size, AssetManager mgr);
    public native void render();
    public native void resize(int w, int h);
    public native void changeCube(int type);
    public native void executeMove(int move, boolean inverse);
    public native void applyMove(int move, boolean inverse);
    public native void setClearColor(float r, float g, float b, float a);

    private Context m_context;

    public CubeGLRenderer(Context context) {
        super();
        m_context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        initScene(1000, 1000, 3, m_context.getAssets());
        int currentNightMode = m_context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            setClearColor(0, 0, 0, 1);
        }
        else {
            int color = m_context.getColor(R.color.gray_400);
            setClearColor(1,1,1,1);
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int w, int h) {
        resize(w, h);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        render();
    }
}
