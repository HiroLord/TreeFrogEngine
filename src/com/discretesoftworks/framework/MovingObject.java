package com.discretesoftworks.framework;

public class MovingObject extends GameObject{
	
	private float dx, dy;
	
	public static float gravity = -0.005f;
	
	public MovingObject(float x, float y, float width, float height, Sprite sprite){
		super(x,y,width,height,sprite);
	}
	
	public void moveWithoutCollisions(){
		changeX(getdx());
		changeY(getdy());
	}
	
	public void moveCheckCollisions(){
		changeY(getdy());
		float moveY = getdy() > 0 ? gravity : -gravity;
		while (Directional.checkAllCollisions(this, GameRenderer.s_instance.solidObjects) != null){
			changeY(moveY);
			setdy(0);
		}
		
		changeX(getdx());
		float moveX = getdx() > 0 ? gravity : -gravity;
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
