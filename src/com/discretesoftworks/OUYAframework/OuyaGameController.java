package com.discretesoftworks.OUYAframework;

import tv.ouya.console.api.OuyaController;
import android.graphics.PointF;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.discretesoftworks.framework.GameRenderer;
import com.discretesoftworks.networking.NetworkGameController;

public abstract class OuyaGameController extends NetworkGameController{

	@Override
	public void update(float deltaTime){
		OuyaController.startOfFrame();
		GameRenderer.s_instance.update(deltaTime);
		super.update(deltaTime);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        return OuyaController.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return OuyaController.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
		return OuyaController.onGenericMotionEvent(event);
	}
	
	public static PointF getStickValues(OuyaController c, int stick){
		float dx = 0;
		float dy = 0;
		if (stick == 1){
			dx = c.getAxisValue(OuyaController.AXIS_LS_X);
			dy = c.getAxisValue(OuyaController.AXIS_LS_Y);
		}
		else {
			dx = c.getAxisValue(OuyaController.AXIS_RS_X);
			dy = c.getAxisValue(OuyaController.AXIS_RS_Y);
		}
		return new PointF(dx,dy);
	}
	
	public static boolean stickInDeadzone(OuyaController c, int stick){
		PointF stickM = getStickValues(c,stick);
		float dx = stickM.x;
		float dy = stickM.y;
		float deadzone = .27f;
		if (Math.abs(dx) > deadzone || Math.abs(dy) > deadzone || getStickMagnitude(c,stick) > deadzone)
			return false;
		return true;
	}
	
	public static float getStickMagnitude(OuyaController c, int stick){
		PointF stickM = getStickValues(c,stick);
		float dx = stickM.x;
		float dy = stickM.y;
		float mag = (float)Math.sqrt(dx*dx + dy*dy);
		if (mag <= 1)
			return mag;
		return 1;
	}
	
	public static float getStickDirection(OuyaController c, int stick){
		PointF stickM = getStickValues(c,stick);
		float axisX = stickM.x;
		float axisY = stickM.y;
		float dir = (float) Math.toDegrees( Math.atan2(-axisX, axisY) );
		return dir;
	}
	
}
