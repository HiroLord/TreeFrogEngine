package com.discretesoftworks.framework;

import android.view.KeyEvent;
import android.view.MotionEvent;


// A controller is what runs code to make the game run.

public abstract class GameController {

	private boolean init;
	
	public GameController(){
		init = false;
	}
	
	/* ALWAYS CALL UP */
	public void init(){
		if (init)
			System.err.println("Over-initialized controller class");
		this.init = true;
	}
	
	public boolean getInit(){
		return init;
	}
	
	// Update is called by the game loop inside MyGLRenderer
	// Call up!
	public void update(float deltaTime){
		if (!getInit())
			init();
	}
	
	public abstract View getView();
	public abstract void paint();
	public abstract boolean onKeyDown(int keyCode, KeyEvent event);
	public abstract boolean onKeyUp(int keyCode, KeyEvent event);
	public abstract boolean onGenericMotionEvent(MotionEvent event);
}
