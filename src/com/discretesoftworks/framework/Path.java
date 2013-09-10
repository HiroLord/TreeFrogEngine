package com.discretesoftworks.framework;

import java.util.ArrayList;

import android.graphics.PointF;

public class Path {

	private ArrayList<PointF> path;
	private int currentPoint;
	private PointF destination;
	
	private float totalDistance;
	
	public Path(){
		path = new ArrayList<PointF>();
		totalDistance = 0;
		currentPoint = 0;
	}
	
	public void next(){
		currentPoint++;
	}
	
	public boolean hasNext(){
		return currentPoint < path.size() - 1;
	}
	
	public PointF getPoint(int i){
		return path.get(i);
	}
	
	public int length(){
		return path.size();
	}
	
	public void clear(){
		path.clear();
		currentPoint = 0;
	}
	
	public void add(PointF p, float distance){
		path.add(p);
		totalDistance += distance;
	}
	
	public float getTotalDistance(){
		return totalDistance;
	}
	
	public void remove(PointF p){
		path.remove(p);
	}
	
	public void setFinalDestination(PointF p){
		this.destination = p;
	}
	
	public PointF getFinalDestination(){
		return destination;
	}
	
	public PointF getCurrentDestination(){
		return path.get(currentPoint);
	}
	
}
