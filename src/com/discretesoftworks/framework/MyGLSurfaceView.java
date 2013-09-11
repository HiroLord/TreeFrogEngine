package com.discretesoftworks.framework;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class MyGLSurfaceView extends GLSurfaceView {

	//public final MyGLRenderer mRenderer;
	private float scale = 1f;
	
	public MyGLSurfaceView(Context context, AttributeSet attrs){
		super(context,attrs);
		init(context, scale);
	}
	
    public MyGLSurfaceView(Context context) {
        super(context);
        init(context, scale);
    }
    
    public void init(Context context, float scale){
    	// OpenGL ES 2.0
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        setFocusable(true);
        setFocusableInTouchMode(true);
        
        setRenderer(new MyGLRenderer(AndroidGame.s_instance, scale));
        

        // ~60 renders per second
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}