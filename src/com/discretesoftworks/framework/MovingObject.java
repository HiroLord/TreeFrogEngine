package com.discretesoftworks.framework;

public class MovingObject extends GameObject{
	
	private float dx, dy;
	
	private float speed;
	private float dir;
	
	private float acceleration = .005f;
	
	private boolean autoMove;
	
	public MovingObject(float x, float y, float z, float width, float height, Sprite sprite){
		super(x,y,z,width,height,sprite);
		
		autoMove = false;
		speed = 0f;
		dir = 0f;
	}
	
	public void setAutoMove(boolean m){
		autoMove = m;
	}
	
	public boolean getAutoMove(){
		return autoMove;
	}
	
	public void setSpeed(float speed){
		this.speed = speed;
	}
	
	public float getSpeed(){
		return speed;
	}
	
	public void setDir(float dir){
		this.dir = dir;
	}
	
	public float getDir(){
		return dir;
	}
	
	public void presetMovements(){
		dx = Directional.lengthDirX(dir, speed);
		dy = Directional.lengthDirY(dir, speed);
	}
	
	public void moveWithoutCollisions(){
		if (autoMove)
			presetMovements();
		
		changeX(getdx());
		changeY(getdy());
	}
	
	public void moveCheckCollisions(){
		if (autoMove)
			presetMovements();
		
		changeY(getdy());
		float moveY = getdy() > 0 ? -acceleration : acceleration;
		while (Directional.checkAllCollisions(this, GameRenderer.s_instance.solidObjects) != null){
			changeY(moveY);
			setdy(0);
		}
		
		changeX(getdx());
		float moveX = getdx() > 0 ? -acceleration : acceleration;
		while (Directional.checkAllCollisions(this, GameRenderer.s_instance.solidObjects) != null){
			changeX(moveX);
			setdx(0);
		}
	}
	
	public void setdx(float dx){
		this.dx = dx;
	}
	
	public void setdy(float dy){
		this.dy = dy;
	}
	
	public float getdx(){
		return dx;
	}
	
	public float getdy(){
		return dy;
	}
	
	public void changedx(float ddx){
		dx += ddx;
	}
	
	public void changedy(float ddy){
		dy += ddy;
	}
	
}
