package com.discretesoftworks.framework;

import java.util.Random;

public class Terrain extends GameObject{

	private NumericalMatrix heightMap;
	
	private Random rand;
	
	public Terrain(float x, float y, float stepWidth, float stepHeight, int width, int height){
		super(x,y,0,stepWidth*width,stepHeight*height,null);
		rand = new Random();
		heightMap = new NumericalMatrix(height, width);
		generateRandomHeightMap();
	}
	
	public void generateRandomHeightMap(){
		for (int r = 0; r < heightMap.getRowDimension(); r++){
			for (int c = 0; c < heightMap.getColumnDimension(); c++){
				heightMap.put(rand.nextFloat(), r, c);
			}
		}
	}
	
	public void renderHeightMap(){
		float[] verts = heightMap.getArray();
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
	
}
