package com.discretesoftworks.TestGame;

import com.discretesoftworks.OUYAframework.OuyaGameController;
import com.discretesoftworks.framework.View;

public class TestController extends OuyaGameController{

	private View view;
	
	public TestController(){
		view = new View(0,0);
		view.center();
	}
	
	public void init(){
		new Player(128,128,512,512);
		Floor f = new Floor(0,0,1024,64);
		f.setCorner(16, 512);
		super.init();
	}
	
	@Override
	public View getView() {
		return view;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}

	@Override
	public void paint() {
	}
}
