package com.discretesoftworks.framework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

// Parses and Holds a PNG font and its sprites
public class GameFont {
	
	private Sprite[] fontLetters;
	private String letters;
	public Sprite holder;
	
	public GameFont(int resourceId, String str){
		letters = str;
		
		int len = letters.length();
		fontLetters = new Sprite[len];
		
		/* Pixel marker RGB: 254, 183, 213 */
		int colorMarkerOne = Color.rgb(1,255,1);
		int colorMarkerTwo = Color.rgb(255,1,1);
		
		Context context = (Context)GameRenderer.s_instance.getGame();
		final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;   // No pre-scaling
		final Bitmap fontBitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
		Bitmap.Config config = Bitmap.Config.ARGB_8888;
		int height = 0;
		while (fontBitmap.getPixel(0,height) != colorMarkerTwo){
			height += 1;
		}
		int start = 0;
		int end = 0;
		int x = 0;
		int y = height + 2;
		
		
		for (int i = 0; i < len; i++){
			start = end;
			while (fontBitmap.getPixel(start, y) != colorMarkerOne){
				start += 1;
				if (start >= fontBitmap.getWidth() - 2){
					start = 0;
					y += height + 1;
				}
			}
			end = start+1;
			while (fontBitmap.getPixel(end, y) != colorMarkerTwo){
				end += 1;
				if (end >= fontBitmap.getWidth() - 2){
					end = 0;
					y += height + 1;
				}
			}
			int manWidth = (end-start)+1;
			int width = 64; // FIX LATER
			Bitmap srcBitmap = Bitmap.createBitmap(fontBitmap,x,y+1,manWidth,height);
			
			Bitmap newBitmap = Bitmap.createBitmap(width, height, config);
			Canvas can = new Canvas(newBitmap);
			//can.drawARGB(FF,FF,FF,FF); //This represents White color
			can.drawBitmap(srcBitmap, 0, 0, null);
			
			//Bitmap newBitmap = Bitmap.createBitmap(srcBitmap,0,0,width,height);
			fontLetters[i] = new Sprite(newBitmap,1);
			fontLetters[i].setMaskWidth(manWidth);
			x += manWidth;
		}
		holder = fontLetters[0];
	}
	
	// Grab the sprite of our particular char
	public Sprite getLetter(char c){
		if (letters.indexOf(c) >= 0)
			return fontLetters[letters.indexOf(c)];
		else
			return fontLetters[0];
	}
	
}
