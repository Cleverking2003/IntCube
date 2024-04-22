package com.example.intcube;

import android.content.Context;
import android.content.res.AssetManager;
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

    private Context m_context;

    public CubeGLRenderer(Context context) {
        super();
        m_context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        initScene(1000, 1000, 0, m_context.getAssets());
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
