package com.discretesoftworks.TestGame;

import java.util.Random;

import tv.ouya.console.api.OuyaController;

import com.discretesoftworks.OUYAframework.OuyaGameController;
import com.discretesoftworks.framework.Assets;
import com.discretesoftworks.framework.GameRenderer;
import com.discretesoftworks.framework.MovingObject;

public class Player extends MovingObject{

	private boolean left, right, jump;
	
	private boolean click;
	
	private float acceleration, maxSpeed;
	
	private int pNum;
	
	private Random rand;
	
	public Player(float x, float y, float z, float width, float height) {
		super(x, y, z, width, height, null);
		getModel().setSprite(Assets.sprPointer);
		left = false;
		right = false;
		jump = false;
		pNum = TestController.playerNum;
		
		rand = new Random();
		
		click = false;
		
		acceleration = 0.005f;
		maxSpeed = .06f;
	}

	private void grabControllerInfo(OuyaController c){
		
		if (c == null)
			return;
		
		right = false;
		left = false;
		jump = false;
		
		if (c.getButton(OuyaController.BUTTON_O))
			jump = true;
		
		if (c.getButton(OuyaController.BUTTON_Y)){
			if (!click){
				click = true;
				System.out.println(GameRenderer.s_instance.renderObjects.getCompiledData().size());
			}
		} else
			click = false;
		
		if (c.getButton(OuyaController.BUTTON_A)){
			new Floor((rand.nextFloat()*6)-3f, (rand.nextFloat())-0.5f, 0f, 1, 1);
		}
		
		if (!OuyaGameController.stickInDeadzone(c, 1)){
			float xMag = OuyaGameController.getStickValues(c, 1).x;
			if (xMag > 0f)
				right = true;
			else if (xMag < 0f)
				left = true;
		}
	}
	
	@Override
	public void update(float deltaTime) {
		pNum = TestController.playerNum;
		grabControllerInfo(OuyaController.getControllerByPlayer(pNum));
		
		if (left)
			changedx(-acceleration);
		else if (right)
			changedx(acceleration);
		else{
			if (getdx() > 0)
				changedx(-acceleration);
			else if (getdx() < 0)
				changedx(acceleration);
			if (Math.abs(getdx()) < acceleration)
				setdx(0);
		}
		if (getdx() > maxSpeed)
			setdx(maxSpeed);
		else if (getdx() < -maxSpeed)
			setdx(-maxSpeed);
		
		float gravity = -.005f;
		
		if (placeFree(getX(), getY() + gravity)){
			changedy(gravity);
		} else if (jump) {
			setdy(.1f);
		}
		
		moveCheckCollisions(deltaTime);
		
		super.update(deltaTime);
	}
}
