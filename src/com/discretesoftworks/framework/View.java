package com.discretesoftworks.framework;

// Controls camera position and possible movement
public class View extends GriddedObject{
	
	private float newX, newY, newZ;
	private float centerX, centerY, centerZ;
	
	private static final float baseZ = 5.5f;
	private float speed;
	
	public View(float x, float y, float z){
		super(x,y,z);
		setZ(baseZ);
		speed = 16;
		
		centerX = centerY = centerZ = 0f;
	}
	
	public float getCenterX(){
		return centerX;
	}
	
	public float getCenterY(){
		return centerY;
	}
	
	public float getCenterZ(){
		return centerZ;
	}
	
	public void setCenter(float cX, float cY, float cZ){
		this.centerX = cX;
		this.centerY = cY;
		this.centerZ = cZ;
	}
	
	public void changeCenterX(float dcX){
		centerX += dcX;
	}
	
	public void changeCenterY(float dcY){
		centerY += dcY;
	}
	
	public void changeCenterZ(float dcZ){
		centerZ += dcZ;
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
	
	public void setNewZ(float newZ){
		this.newZ = newZ;
	}
	
	public float getNewX(){
		return newX;
	}
	
	public float getNewY(){
		return newY;
	}
	
	public float getNewZ(){
		return newZ;
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
	
	@Override
	public void setZ(float z){
		super.setZ(z);
		setNewZ(z);
	}
	
	public void update(float deltaTime){
		setX((newX+getX())/speed);
		setY((newY+getX())/speed);
		setZ((newZ+getZ())/speed);
	}
}