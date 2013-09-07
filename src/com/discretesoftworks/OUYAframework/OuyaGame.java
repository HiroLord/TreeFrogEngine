package com.discretesoftworks.OUYAframework;

import tv.ouya.console.api.OuyaController;
import tv.ouya.console.api.OuyaFacade;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.discretesoftworks.framework.AndroidGame;

public abstract class OuyaGame extends AndroidGame{
	
	private String devID;
	
	public OuyaGame(String devID){
		this.devID = devID;
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Grab and initialice the Ouya Facade
        OuyaFacade facade = OuyaFacade.getInstance();
        facade.init(this, devID);
        
        // Initialize the Ouya Controller
        OuyaController.init(this);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (getController() != null && getController().getInit()){
    		return getController().onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    	}
    	return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (getController() != null && getController().getInit()){
			return getController().onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
		}
		return super.onKeyUp(keyCode, event);
	}
	
	@Override
    public boolean onGenericMotionEvent(MotionEvent event) {
		if (getController() != null && getController().getInit())
			return getController().onGenericMotionEvent(event) || super.onGenericMotionEvent(event);
        return super.onGenericMotionEvent(event);
    }
	
	
}
