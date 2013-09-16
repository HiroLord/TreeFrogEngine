package com.discretesoftworks.framework;

import java.util.Random;

public class Terrain extends GameObject{

	private NumericalMatrix heightMap;
	
	private Random rand;
	
	private float stepWidth, stepHeight;
	
	public Terrain(float x, float y, float stepWidth, float stepHeight, int width, int height){
		super(x,y,0,width,height,null);
		getModel().setSprite(Assets.sprWall);
		this.stepWidth = stepWidth;
		this.stepHeight = stepHeight;
		rand = new Random();
		heightMap = new NumericalMatrix((int)(height/stepHeight)+1,(int)(width/stepWidth)+1);
		generateRandomHeightMap();
		renderHeightMap();
	}
	
	public void generateRandomHeightMap(){
		for (int r = 0; r < heightMap.getRowDimension(); r++){
			for (int c = 0; c < heightMap.getColumnDimension(); c++){
				heightMap.put(rand.nextFloat(), r, c);
			}
		}
	}
	
	public void renderHeightMap(){
		int i = 0;
		float[] verts = new float[heightMap.getRowDimension() * heightMap.getColumnDimension()*3];
		for (int r = 0; r < heightMap.getRowDimension(); r++){
			for (int c = 0; c < heightMap.getColumnDimension(); c++){
				verts[i++] = r * stepWidth;
				verts[i++] = c * stepHeight;
				verts[i++] = heightMap.get(r,c);
			}
		}
		float[] textureCoords = new float[4];
		short[] indicies = new short[(heightMap.getColumnDimension()-1) * (heightMap.getRowDimension()-1) * 6];
		int j = 0;
		for (int r = 0; r < heightMap.getRowDimension()-1; r++){
			for (int c = 0; c < heightMap.getColumnDimension()-1; c++){
				indicies[j++] = (short)heightMap.getIndex(r, c);
				indicies[j++] = (short)heightMap.getIndex(r+1, c+1);
				indicies[j++] = (short)heightMap.getIndex(r, c+1);
				
				indicies[j++] = (short)heightMap.getIndex(r, c);
				indicies[j++] = (short)heightMap.getIndex(r+1, c);
				indicies[j++] = (short)heightMap.getIndex(r+1, c+1);
			}
		}
		getModel().setupModel(verts, textureCoords, indicies);
	}
	
	public void setHeight(float height, int r, int c){
		heightMap.put(height, r, c);
	}
	
	public float getHeight(int r, int c){
		return heightMap.get(r, c);
	}
	
	public float getHeight(float x, float y){
		int rowLeft = (int)Math.floor(x/stepWidth);
		int rowRight = (int)Math.ceil(x/stepWidth);
		float percentX = x%stepWidth;
		//float percentY = y%stepHeight;
		int colTop = (int)Math.ceil(y/stepHeight);
		int colBottom = (int)Math.floor(y/stepHeight);
		float outputx = (heightMap.get(rowLeft, colTop) + heightMap.get(rowRight, colTop))/2f;
		float outputy = (heightMap.get(rowLeft, colBottom) + heightMap.get(rowRight, colBottom))/2f;
		return (outputx+outputy)/2f;
	}
	
}
