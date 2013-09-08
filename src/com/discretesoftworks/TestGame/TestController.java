package com.discretesoftworks.TestGame;

import tv.ouya.console.api.OuyaController;
import android.graphics.PointF;

import com.discretesoftworks.OUYAframework.OuyaGameController;
import com.discretesoftworks.framework.View;

public class TestController extends OuyaGameController{

	private View view;
	
	public static int playerNum = 0;
	
	public TestController(){
		view = new View(0,-5.5f,5.5f);
	}
	
	public void init(){
		
		for (int i = 0; i < 4; i++)
			if (OuyaController.getControllerByPlayer(i) != null){
				playerNum = i;
				System.out.println("Using controller "+i+" (0-3)");
				break;
			}
		
		new Player(0, 2f, 0f, 1f, 1f);
		new Floor(0, 0f, 0f, 1, 1);
		new Floor(-1f, 0f, 0f, 1, 1);
		new Floor(1f, 0f, 0f, 1, 1);
		new Floor(-2f, 0f, 0f, 1, 1);
		new Floor(2f, 0f, 0f, 1, 1);
		new Floor(-3f, 1f, 0f, 1, 1);
		new Floor(3f, 1f, 0f, 1, 1);
		new Pointer(0,0,0);
		super.init();
	}
	
	@Override
	public View getView() {
		return view;
	}
	
	private void grabControllerInfo(OuyaController c){
		
		if (c == null)
			return;
		
		if (!OuyaGameController.stickInDeadzone(c, 2)){
			PointF point = OuyaGameController.getStickValues(c, 2);
			view.changeX(point.x/8f);
			view.changeCenterX(point.x/8f);
			view.changeY(-point.y/8f);
			view.changeCenterY(-point.y/8f);
		}
		
		float rTrigger = c.getAxisValue(OuyaController.AXIS_R2);
		float lTrigger = c.getAxisValue(OuyaController.AXIS_L2);
		
		if (lTrigger > .3f && view.getZ() < 20f){
			view.changeY(-.15f * lTrigger);
			view.changeZ( .20f * lTrigger);
		}
		else if (rTrigger > .3f && view.getZ() > 2f){
			view.changeY( .15f * rTrigger);
			view.changeZ(-.20f * rTrigger);
		}
	}

	@Override
	public void update(float deltaTime) {
		for (int i = 0; i < 4; i++)
			if (OuyaController.getControllerByPlayer(i) != null){
				if (OuyaController.getControllerByPlayer(i).getButton(OuyaController.BUTTON_U)){
					if (playerNum != i)
						System.out.println("New controller: "+i);
					playerNum = i;
				}
			}
		grabControllerInfo(OuyaController.getControllerByPlayer(playerNum));
		//view.update(deltaTime);
		super.update(deltaTime);
	}

	@Override
	public void paint() {
	}
}
