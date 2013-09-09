package com.discretesoftworks.framework;

import android.annotation.SuppressLint;

// Allows an object to be added to the "grid"
//	- Basically allows the object to have coordinates and be moved
public class GriddedObject {

	private float x, y, z;
	private int depth;
	private float width, height;
	
	
	public GriddedObject(float x, float y, float z){
		this(x,y,z,1,1);
	}
	
	@SuppressLint("UseValueOf")
	public GriddedObject(float x, float y, float z, float width, float height){
		this.x = x;
		this.y = y;
		this.z = z;
		this.depth = new Integer(0); // Temporary code
		setWidth(width);
		setHeight(height);
	}
	
	public void setDepth(int depth){
		this.depth = depth;
	}
	
	public Integer getDepth(){
		return depth;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
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
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getZ(){
		return z;
	}
	
	public float getLeft(){
		return (x - width/2);
	}
	
	public float getTop(){
		return (y - height/2);
	}
	
	public float getRight(){
		return (x + width/2);
	}
	
	public float getBottom(){
		return (y + height/2);
	}
	
	public void setCorner(float x, float y){
		setLeft(x);
		setTop(y);
	}
	
	public void setLeft(float l){
		x = l + width/2;
	}
	
	public void setTop(float t){
		y = t + height/2;
	}
	
	public void setRight(float r){
		x = r - width/2;
	}
	
	public void setBottom(float b){
		y = b - height/2;
	}
	
	public void setDimensions(float width, float height){
		setWidth(width);
		setHeight(height);
	}
	
	public void setCoordinates(float x, float y){
		setX(x);
		setY(y);
	}
	
	public void setWidth(float width){
		this.width = width;
	}
	
	public void setHeight(float height){
		this.height = height;
	}
	
	public void changeWidth(float dWidth){
		setWidth(width+dWidth);
	}
	
	public void changeHeight(float dHeight){
		setHeight(height+dHeight);
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
}
