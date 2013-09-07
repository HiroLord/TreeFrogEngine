package com.discretesoftworks.TestGame;

import com.discretesoftworks.framework.Assets;
import com.discretesoftworks.framework.GameObject;

public class Floor extends GameObject{

	public Floor(int x, int y, int width, int height){
		super(x,y,width,height,Assets.sprWall);
		
		setSolid(true);
	}
	
}
