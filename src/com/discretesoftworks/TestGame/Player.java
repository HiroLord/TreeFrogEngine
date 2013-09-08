package com.discretesoftworks.TestGame;

import java.util.Random;

import tv.ouya.console.api.OuyaController;

import com.discretesoftworks.OUYAframework.OuyaGameController;
import com.discretesoftworks.framework.Assets;
import com.discretesoftworks.framework.Directional;
import com.discretesoftworks.framework.GameRenderer;
import com.discretesoftworks.framework.MovingObject;

public class Player extends MovingObject{

	private boolean left, right, jump;
	
	private boolean click;
	
	private float acceleration, maxSpeed;
	
	private int pNum;
	
	private Random rand;
	
	public Player(float x, float y, float width, float height) {
		super(x, y, width, height, Assets.sprGlass);
		left = false;
		right = false;
		jump = false;
		pNum = 0;
		
		rand = new Random();
		
		click = false;
		
		acceleration = 0.005f;
		maxSpeed = .06f;
	}

	private void grabControllerInfo(OuyaController c){
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
			new Floor((rand.nextFloat()*6)-3f, (rand.nextFloat())-0.5f, 1, 1);
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
		
		if (placeFree(getX(), getY() + MovingObject.gravity)){
			changedy(MovingObject.gravity);
		} else if (jump) {
			System.out.println("Jump!");
			setdy(.1f);
		}
		
		moveCheckCollisions();
		
		super.update(deltaTime);
	}
	
	private boolean placeFree(float x, float y){
		float dx = x-getX();
		float dy = y-getY();
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
