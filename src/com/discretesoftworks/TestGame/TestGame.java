package com.discretesoftworks.TestGame;

import android.os.Bundle;

import com.discretesoftworks.OUYAframework.OuyaGame;

public class TestGame extends OuyaGame{

	public TestGame(){
		super("2cf4c0c1-1c40-46ac-82fe-deda647f6544");
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setController(new TestController());
	}
	
}
