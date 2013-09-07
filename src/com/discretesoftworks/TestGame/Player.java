package com.discretesoftworks.TestGame;

import tv.ouya.console.api.OuyaController;

import com.discretesoftworks.OUYAframework.OuyaGameController;
import com.discretesoftworks.framework.Assets;
import com.discretesoftworks.framework.Directional;
import com.discretesoftworks.framework.GameRenderer;
import com.discretesoftworks.framework.MovingObject;

public class Player extends MovingObject{

	private boolean left, right, jump;
	
	private float acceleration, maxSpeed;
	
	private int pNum;
	
	public Player(int x, int y, int width, int height) {
		super(x, y, width, height, Assets.sprGlass);
		left = false;
		right = false;
		jump = false;
		pNum = 0;
		
		acceleration = 0.5f;
		maxSpeed = 6f;
	}

	private void grabControllerInfo(OuyaController c){
		right = false;
		left = false;
		jump = false;
		
		if (c.getButton(OuyaController.BUTTON_O))
			jump = true;
		
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
		
		if (placeFree(getX(),getY()+1)){
			changedy(MovingObject.gravity);
		} else if (jump) {
			setdy(-11f);
		}
		
		moveCheckCollisions();
		
		super.update(deltaTime);
	}
	
	private boolean placeFree(int x, int y){
		int dx = x-getX();
		int dy = y-getY();
		changeX(dx);
		changeY(dy);
		
		boolean noCollide = true;
		
		if (Directional.checkAllCollisions(this, GameRenderer.s_instance.solidObjects) != null)
			noCollide = false;
		
		changeX(-dx);
		changeY(-dy);
		return noCollide;
	}

}
