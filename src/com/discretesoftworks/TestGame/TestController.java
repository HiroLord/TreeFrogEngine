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
		new Player(0, 2f, 1f, 1f);
		new Floor(0, 0f, 1, 1);
		new Floor(-1f, 0f, 1, 1);
		new Floor(1f, 0f, 1, 1);
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
