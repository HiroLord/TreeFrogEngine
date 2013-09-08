package com.discretesoftworks.TestGame;

import tv.ouya.console.api.OuyaController;
import android.graphics.PointF;

import com.discretesoftworks.OUYAframework.OuyaGameController;
import com.discretesoftworks.framework.View;

public class TestController extends OuyaGameController{

	private View view;
	
	public TestController(){
		view = new View(0,0);
	}
	
	public void init(){
		new Player(0, 2f, 1f, 1f);
		new Floor(0, 0f, 1, 1);
		new Floor(-1f, 0f, 1, 1);
		new Floor(1f, 0f, 1, 1);
		new Floor(-2f, 0f, 1, 1);
		new Floor(2f, 0f, 1, 1);
		super.init();
	}
	
	@Override
	public View getView() {
		return view;
	}
	
	private void grabControllerInfo(OuyaController c){
		
		if (!OuyaGameController.stickInDeadzone(c, 2)){
			PointF point = OuyaGameController.getStickValues(c, 2);
			view.changeX(point.x/2f);
			view.changeY(point.y/2f);
		}
	}

	@Override
	public void update(float deltaTime) {
		grabControllerInfo(OuyaController.getControllerByPlayer(0));
		super.update(deltaTime);
	}

	@Override
	public void paint() {
	}
}
