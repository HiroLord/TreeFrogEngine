package com.discretesoftworks.TestGame;

import tv.ouya.console.api.OuyaController;

import com.discretesoftworks.framework.Assets;
import com.discretesoftworks.framework.GameObject;

public class Pointer extends GameObject{

	public Pointer(float x, float y, float z){
		super(x,y,z,1,1,Assets.sprPointer);
		float squareCoords[] = { -0.5f,  0f, 1f,
				 -0.5f, -0f, 0f,
				  0.5f, -0f, 0f,
				  0.5f,  0f, 1f } ;
		getModel().remake3DModel(squareCoords);
	}
	
	private void grabControllerInfo(OuyaController c){
		if (c == null)
			return;
		
		if (c.getButton(OuyaController.BUTTON_DPAD_LEFT))
			changeX(-.1f);
		else if (c.getButton(OuyaController.BUTTON_DPAD_RIGHT))
			changeX(.1f);
		else if (c.getButton(OuyaController.BUTTON_DPAD_UP))
			changeY(.1f);
		else if (c.getButton(OuyaController.BUTTON_DPAD_DOWN))
			changeY(-.1f);
	}
	
	@Override
	public void update(float deltaTime){
		int pNum = TestController.playerNum;
		grabControllerInfo(OuyaController.getControllerByPlayer(pNum));
		super.update(deltaTime);
	}
	
}
