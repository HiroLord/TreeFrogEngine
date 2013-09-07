package com.discretesoftworks.framework;


public class View extends GriddedObject{
	
	private int newX, newY;
	
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
	
	public void setNewX(int newX){
		this.newX = newX;
	}
	
	public void setNewY(int newY){
		this.newY = newY;
	}
	
	public int getNewX(){
		return newX;
	}
	
	public int getNewY(){
		return newY;
	}
	
	public void setSpeed(float speed){
		this.speed = speed;
	}
	
	public float getSpeed(){
		return speed;
	}
	
	@Override
	public void setX(int x){
		super.setX(x);
		setNewX(x);
	}
	
	@Override
	public void setY(int y){
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