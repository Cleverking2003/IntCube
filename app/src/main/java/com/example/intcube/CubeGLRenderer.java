package com.example.intcube;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CubeGLRenderer implements GLSurfaceView.Renderer {
    public native int initGL();
    public native void initScene(int width, int height, int size, AssetManager mgr);
    public native void render();

    private Context m_context;

    public CubeGLRenderer(Context context) {
        super();
        m_context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        initGL();
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        initScene(i, i1, 0, m_context.getAssets());
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        render();
    }
}
