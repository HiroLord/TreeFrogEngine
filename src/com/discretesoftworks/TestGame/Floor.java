package com.discretesoftworks.TestGame;

import com.discretesoftworks.framework.Assets;
import com.discretesoftworks.framework.GameObject;

public class Floor extends GameObject{

	public Floor(float x, float y, float width, float height){
		super(x,y,width,height,Assets.sprWall);
		setZ(1);
		setSolid(true);
	}
	
}
