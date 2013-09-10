package com.discretesoftworks.framework;

import android.graphics.PointF;

public class MovingObject extends GameObject{
	
	private float dx, dy, dz;
	
	private float speed;
	private float dir;
	
	private float acceleration = .005f;
	
	private boolean autoMove;
	
	public MovingObject(float x, float y, float z, float width, float height, Sprite sprite){
		super(x,y,z,width,height,sprite);
		dx = dy = dz = 0f;
		autoMove = false;
		speed = 0f;
		dir = 0f;
	}
	
	protected Path optimizePath(Path path){
		for (int i = 0; i < path.length(); i++){
			PointF a = path.getPoint(i);
			for (int j = i+2; j < path.length(); j++){
				PointF b = path.getPoint(j);
				if (checkPathCollision(a.x,a.y,b.x,b.y) < 0f)
					path.remove(path.getPoint(j-1));
			}
		}
		return path;
	}
	
	protected Path findPath(float endX, float endY, float dDir){
		Path path = new Path();
		
		//System.out.println("==================== THE LITTLE PATH FINDER THAT COULD ====================");
		
		boolean found = false;
		PointF point = new PointF(getX(),getY());
		int tries = 0;
		int maxTries = 100;
		while (!found && tries < maxTries){
			tries += 1;
			//System.out.println("RUN THROUGH - NUMBER "+tries);
			float dist = checkPathCollision(point.x,point.y,endX,endY);
			if (dist == -1f)
				found = true;
			else {
				//System.out.println("Distance: "+dist);
				
				float dir = Directional.pointDirection(point.x, point.y, endX, endY) + dDir;
				float ex = point.x + Directional.lengthDirX(dir, dist);
				float ey = point.y + Directional.lengthDirY(dir, dist);
				
				int tries2 = 1;
				//System.out.println("Attempting to find a new partial path.");
				int rotationTries = (int)(360 / Math.abs(dDir)) + 1;
				
				while ((dist = checkPathCollision(point.x, point.y, ex, ey) + .5f) >= 0f && tries2 < rotationTries){
					tries2 += 1;
					
					dir += dDir;
					ex = point.x + Directional.lengthDirX(dir, dist);
					ey = point.y + Directional.lengthDirY(dir, dist);
				}
				if (tries2 < rotationTries){
					//System.out.println("Found a new path in "+tries2+" tries.");
				}
				else {
					//System.out.println("Failed to find a new path.");
					tries = maxTries;
				}
				point = new PointF(ex,ey);
				path.add(point, dist);
			}
		}
		path.add(new PointF(endX,endY), 0);
		if (found == false)
			path = null;
		return path;
	}
	
	protected float checkPathCollision(float startX, float startY, float endX, float endY){
		float dist = .5f;
		float sx = startX;
		float sy = startY;
		float dir = Directional.pointDirection(sx, sy, endX, endY);
		float dx = Directional.lengthDirX(dir,dist);
		float dy = Directional.lengthDirY(dir,dist);
		int tries = 0;
		while (placeFree(sx, sy) && Directional.pointDistance(sx, sy, endX, endY) >= dist/2f && tries < 40){
			tries += 1;
			sx += dx;
			sy += dy;
		}
		
		if (!placeFree(sx, sy) || Directional.pointDistance(sx, sy, endX, endY) >= dist){
			//System.out.println("Found a collision in "+tries+" tries.");
			float newdist = Directional.pointDistance(startX,startY,sx,sy);
			if (newdist < dist)
				return dist;
			return newdist;
		}
		//System.out.println("Safe path in "+tries+" iterations.");
		return -1f;
	}
	
	public void setAutoMove(boolean m){
		autoMove = m;
	}
	
	public boolean getAutoMove(){
		return autoMove;
	}
	
	public void setSpeed(float speed){
		this.speed = speed;
	}
	
	public float getSpeed(){
		return speed;
	}
	
	public void setDir(float dir){
		this.dir = dir;
	}
	
	public float getDir(){
		return dir;
	}
	
	public void update(float deltaTime){
		//moveWithoutCollisions();
		moveCheckCollisions();
		super.update(deltaTime);
	}
	
	public void presetMovements(){
		if (speed == 0f){
			setdx(0);
			setdy(0);
		}
		else {
			dx = Directional.lengthDirX(dir, speed);
			dy = Directional.lengthDirY(dir, speed);
		}
	}
	
	public void moveWithoutCollisions(){
		if (autoMove)
			presetMovements();
		
		changeX(getdx());
		changeY(getdy());
		changeZ(getdz());
	}
	
	public void moveCheckCollisions(){
		if (autoMove)
			presetMovements();
		
		if (getdz() != 0f){
			changeZ(getdz());
			float moveZ = getdz() > 0 ? -acceleration : acceleration;
			while (Directional.checkAllCollisions(this, GameRenderer.s_instance.solidObjects) != null){
				changeZ(moveZ);
				setdz(0);
			}
		}
		
		if (getdy() != 0f){
			changeY(getdy());
			float moveY = getdy() > 0 ? -acceleration : acceleration;
			while (Directional.checkAllCollisions(this, GameRenderer.s_instance.solidObjects) != null){
				changeY(moveY);
				setdy(0);
			}
		}
		
		if (getdx() != 0f){
			changeX(getdx());
			float moveX = getdx() > 0 ? -acceleration : acceleration;
			while (Directional.checkAllCollisions(this, GameRenderer.s_instance.solidObjects) != null){
				changeX(moveX);
				setdx(0);
			}
		}
	}
	
	public void setdx(float dx){
		this.dx = dx;
	}
	
	public void setdy(float dy){
		this.dy = dy;
	}
	
	public void setdz(float dz){
		this.dz = dz;
	}
	
	public float getdx(){
		return dx;
	}
	
	public float getdy(){
		return dy;
	}
	
	public float getdz(){
		return dz;
	}
	
	public void changedx(float ddx){
		dx += ddx;
	}
	
	public void changedy(float ddy){
		dy += ddy;
	}
	
	public void changedz(float ddz){
		dz += ddz;
	}
	
}
