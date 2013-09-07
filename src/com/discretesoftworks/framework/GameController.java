package com.discretesoftworks.framework;

import android.view.KeyEvent;
import android.view.MotionEvent;

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
	
	public abstract View getView();
	public void update(float deltaTime){
		if (!getInit())
			init();
	}
	public abstract void paint();
	public abstract boolean onKeyDown(int keyCode, KeyEvent event);
	public abstract boolean onKeyUp(int keyCode, KeyEvent event);
	public abstract boolean onGenericMotionEvent(MotionEvent event);
}
