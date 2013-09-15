package com.discretesoftworks.framework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Sprite extends GriddedObject{
	
	private int[] frames;
	private float maskWidth, maskHeight;
	
	public Sprite(Bitmap source, int frames){
		super(0,0,0,2,2);
		generateSprite(source,frames);
	}
	
	public Sprite(final int resourceId, int frames){
		super(0,0,0,2,2);
		Context context = (Context)GameRenderer.s_instance.getGame();
		final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;   // No pre-scaling
		final Bitmap source = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
		generateSprite(source,frames);
	}
	
	public void generateSprite(Bitmap source, int frames){
		// Read in the resource
        Bitmap[] bitmaps = new Bitmap[frames];
        this.frames = new int[frames];
        this.maskWidth = source.getHeight();
        this.maskHeight = source.getWidth()/frames;
        int width = (int)maskWidth;
        int height = (int)maskHeight;
        for (int i = 0; i < bitmaps.length; i++){
        	bitmaps[i] = Bitmap.createBitmap(source,i*width,0,width,height);
        	this.frames[i] = MyGLRenderer.loadTexture(bitmaps[i]); // May need to call s_instance
        }
        setMaskWidth(width);
        setMaskHeight(height);
        setDimensions(width,height);
	}
	
	public void setManualDimensions(int width, int height){
		setWidth(width);
		setLength(height);
	}
	
	public int getSprite(int imageSingle){
		return frames[imageSingle];
	}
	
	public int getSpriteLength(){
		return frames.length;
	}
	
	public float getMaskWidth(){
		return maskWidth;
	}
	
	public float getMaskHeight(){
		return maskHeight;
	}
	
	public void setMask(float width, float height){
		this.maskWidth = width;
		this.maskHeight = height;
	}
	
	public void setMaskWidth(int width){
		this.maskWidth = width;
	}
	
	public void setMaskHeight(int height){
		this.maskHeight = height;
	}
}
