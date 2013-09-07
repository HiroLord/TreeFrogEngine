package com.discretesoftworks.framework;

import android.annotation.SuppressLint;

public class GriddedObject {

	private float x, y, z;
	private int depth;
	private int width, height;
	
	
	public GriddedObject(int x, int y){
		this(x,y,2,2);
	}
	
	@SuppressLint("UseValueOf")
	public GriddedObject(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.depth = new Integer(0);
		setWidth(width);
		setHeight(height);
	}
	
	public void setDepth(int depth){
		this.depth = depth;
	}
	
	
	
	public Integer getDepth(){
		return depth;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setZ(float z){
		this.z = z;
	}
	
	public void changeX(float dx){
		this.x += dx;
	}
	
	public void changeY(float dy){
		this.y += dy;
	}
	
	public void changeZ(float dz){
		this.z += dz;
	}
	
	public int getX(){
		return (int)x;
	}
	
	public int getY(){
		return (int)y;
	}
	
	public float getZ(){
		return z;
	}
	
	public int getLeft(){
		return (int)(x - width/2);
	}
	
	public int getTop(){
		return (int)(y - height/2);
	}
	
	public int getRight(){
		return (int)(x + width/2);
	}
	
	public int getBottom(){
		return (int)(y + height/2);
	}
	
	public void setCorner(int x, int y){
		setLeft(x);
		setTop(y);
	}
	
	public void setLeft(int l){
		x = l + width/2;
	}
	
	public void setTop(int t){
		y = t + height/2;
	}
	
	public void setRight(int r){
		x = r - width/2;
	}
	
	public void setBottom(int b){
		y = b - height/2;
	}
	
	public void setDimensions(int width, int height){
		setWidth(width);
		setHeight(height);
	}
	
	public void setCoordinates(int x, int y){
		setX(x);
		setY(y);
	}
	
	public void setWidth(int width){
		if (width%2 == 1)
			width += 1;
		this.width = width;
	}
	
	public void setHeight(int height){
		if (height%2 == 1)
			height += 1;
		this.height = height;
	}
	
	public void changeWidth(int dWidth){
		setWidth(width+dWidth);
	}
	
	public void changeHeight(int dHeight){
		setHeight(height+dHeight);
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
}
