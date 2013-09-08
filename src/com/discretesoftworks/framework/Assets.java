package com.discretesoftworks.framework;

import com.discretesoftworks.TestGame.R;

public class Assets {
	
	public static Sprite emptySprite, sprGlass, sprWall, sprPointer;
	
	public static GameFont fntNormal;
	
	public static void load(){
		sprGlass = new Sprite(R.drawable.test, 1);
		sprWall = new Sprite(R.drawable.glass, 1);
		sprPointer = new Sprite(R.drawable.arrow_down, 1);
		emptySprite = new Sprite(R.drawable.test, 1);
		fntNormal = new GameFont(R.drawable.fontbeta, "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz ");
	}
}
