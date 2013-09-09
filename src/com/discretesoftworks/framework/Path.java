package com.discretesoftworks.framework;

import android.graphics.PointF;

public class Path {

	private PointF[] path;
	private int currentPoint;
	private PointF destination;
	
	public Path(){
		path = new PointF[10];
	}
	
	public void addPoint(PointF p){
		
	}
	
	public void setFinalDestination(PointF p){
		this.destination = p;
	}
	
	public PointF getFinalDestination(){
		return destination;
	}
	
	public PointF getCurrentDestination(){
		return path[currentPoint];
	}
	
}
