package com.discretesoftworks.framework;

import java.util.ArrayList;

public abstract class Directional {

	// Gets the x-length of a total length in a given direction.
	public static float lengthDirX(float dir, float dist){
		return (float) Math.sin(Math.toRadians(-dir))*dist;
	}
	
	// Gets the y-length of a total length in a given direction.
	public static float lengthDirY(float dir, float dist){
		return (float) Math.cos(Math.toRadians(-dir))*dist;
	}
	
	public static float pointDistance(float x1, float y1, float x2, float y2){
		float dx = x2-x1;
		float dy = y2-y1;
		float mag = (float)Math.sqrt(dx*dx + dy*dy);
		return mag;
	}
	
	public static float pointDirection(float x1, float y1, float x2, float y2){
		float axisX = x2-x1;
		float axisY = y2-y1;
		float dir = (float) Math.toDegrees( Math.atan2(-axisX, axisY) );
		return dir;
	}
	
	public static GriddedObject checkAllCollisions(GriddedObject a, ArrayList<GriddedObject> b){
		for (int i = 0; i < b.size(); i++)
			if (checkCollision(a,b.get(i)))
				return b.get(i);
		return null;
	}
	
	public static boolean checkCollision(GriddedObject a, GriddedObject b){
		int xdist = Math.abs((int)(b.getX() - a.getX()));
		int ydist = Math.abs((int)(b.getY() - a.getY()));
		if ((xdist < a.getWidth()/2+b.getWidth()/2) && (ydist < a.getHeight()/2+b.getHeight()/2))
			return true;
		return false;
	}
	
}
