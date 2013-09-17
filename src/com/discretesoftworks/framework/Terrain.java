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
		//generateSlopedHeightMap(4f);
		generateRandomHeightMap(4f);
		renderHeightMap();
	}
	
	public void generateSlopedHeightMap(float endHeight){
		float h = endHeight;
		for (int r = 0; r < heightMap.getRowDimension(); r++){
			h = endHeight;
			for (int c = 0; c < heightMap.getColumnDimension(); c++){
				heightMap.put(h, r, c);
				h -= (stepWidth/(float)getWidth())*(endHeight);
			}
		}
	}
	
	public void generateRandomHeightMap(float variance){
		for (int r = 0; r < heightMap.getRowDimension(); r++){
			for (int c = 0; c < heightMap.getColumnDimension(); c++){
				heightMap.put(rand.nextFloat()*variance, r, c);
			}
		}
	}
	
	public void renderHeightMap(){
		int i = 0;
		int k = 0;
		float[] verts = new float[heightMap.getRowDimension() * heightMap.getColumnDimension()*3];
		float[] textureCoords = new float[heightMap.getRowDimension() * heightMap.getColumnDimension()*2];
		for (int r = 0; r < heightMap.getRowDimension(); r++){
			for (int c = 0; c < heightMap.getColumnDimension(); c++){
				verts[i++] = c * stepHeight;
				verts[i++] = r * stepWidth;
				verts[i++] = heightMap.get(r,c);
				textureCoords[k++] = c / ((float)heightMap.getColumnDimension()-1);
				textureCoords[k++] = (1-(r / ((float)heightMap.getRowDimension()-1)));
			}
		}
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
		if (x < getX() || y < getY() || x >= getX() + getWidth() || y >= getY() + getLength())
			return 5f;
		int colLeft = (int)Math.floor(x/stepWidth);
		int colRight = colLeft + 1;
		int rowBottom = (int)Math.floor(y/stepHeight);
		int rowTop = rowBottom + 1;
		float percentX = (x%stepWidth)/stepWidth;
		float percentY = (y%stepHeight)/stepHeight;
		float leftHeight = (heightMap.get(rowBottom, colLeft) + heightMap.get(rowTop, colLeft))/2f;
		float rightHeight = (heightMap.get(rowBottom, colRight) + heightMap.get(rowTop, colRight))/2f;
		float topHeight = (heightMap.get(rowTop, colLeft) + heightMap.get(rowTop, colRight))/2f;
		float bottomHeight = (heightMap.get(rowBottom, colLeft) + heightMap.get(rowBottom, colRight))/2f;
		float outputx = leftHeight * (1f-percentX) + rightHeight * (percentX);
		float outputy = bottomHeight * (1f-percentY) + topHeight * (percentY);
		return (outputx); //+outputy)/2f;
	}
	
}
