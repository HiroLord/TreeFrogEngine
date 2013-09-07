package com.discretesoftworks.framework;

public class MovingObject extends GameObject{
	
	private float dx, dy;
	
	public static float gravity = 0.45f;
	
	public MovingObject(int x, int y, int width, int height, Sprite sprite){
		super(x,y,width,height,sprite);
	}
	
	public void moveWithoutCollisions(){
		changeX(getdx());
		changeY(getdy());
	}
	
	public void moveCheckCollisions(){
		changeX(getdx());
		int moveX = getdx() > 0 ? -1 : 1;
		while (Directional.checkAllCollisions(this, GameRenderer.s_instance.solidObjects) != null){
			changeX(moveX);
			setdx(0);
		}
		
		changeY(getdy());
		int moveY = getdy() > 0 ? -1 : 1;
		while (Directional.checkAllCollisions(this, GameRenderer.s_instance.solidObjects) != null){
			changeY(moveY);
			setdy(0);
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
