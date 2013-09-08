package com.discretesoftworks.framework;


public class View extends GriddedObject{
	
	private float newX, newY;
	
	private static final float baseZ = 3f;
	private float speed;
	
	public View(int x, int y){
		super(x,y);
		setZ(baseZ);
		speed = 16;
	}
	
	public float getBaseZ(){
		return baseZ;
	}
	
	public float getViewScale(){
		return getZ()/baseZ;
	}
	
	public void setNewX(float newX){
		this.newX = newX;
	}
	
	public void setNewY(float newY){
		this.newY = newY;
	}
	
	public float getNewX(){
		return newX;
	}
	
	public float getNewY(){
		return newY;
	}
	
	public void setSpeed(float speed){
		this.speed = speed;
	}
	
	public float getSpeed(){
		return speed;
	}
	
	@Override
	public void setX(float x){
		super.setX(x);
		setNewX(x);
	}
	
	@Override
	public void setY(float y){
		super.setY(y);
		setNewY(y);
	}
	
	public void center(){
		setX(GameRenderer.s_instance.getScreenWidth()/2);
		setNewX(getX());
		setY(GameRenderer.s_instance.getScreenHeight()/2);
		setNewY(getY());
	}
	
	public void update(float deltaTime){
		setX((newX+getX())/(int)speed);
		setY((newY+getX())/(int)speed);
	}
}