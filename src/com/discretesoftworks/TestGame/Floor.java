package com.discretesoftworks.TestGame;

import com.discretesoftworks.framework.Assets;
import com.discretesoftworks.framework.GameObject;

public class Floor extends GameObject{

	public Floor(float x, float y, float z, float width, float height){
		super(x,y,z,width,height,Assets.sprWall);

		setSolid(true);
	}
	
}
