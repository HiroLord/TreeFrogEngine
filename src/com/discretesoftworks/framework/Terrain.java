package com.discretesoftworks.framework;

import java.util.Random;

public class Terrain extends GameObject{

	private NumericalMatrix heightMap;
	
	private Random rand;
	
	private float stepWidth, stepHeight;
	
	private float[] pointA, pointB, pointC;
	private float[] vectorA, vectorB, n;
	
	public Terrain(float x, float y, float stepWidth, float stepHeight, int width, int height){
		super(x,y,0,width,height,null);
		getModel().setSprite(Assets.sprWall);
		this.stepWidth = stepWidth;
		this.stepHeight = stepHeight;
		pointA = new float[3];
		pointB = new float[3];
		pointC = new float[3];
		vectorA = new float[3];
		vectorB = new float[3];
		n = new float[3];
		rand = new Random();
		heightMap = new NumericalMatrix((int)(height/stepHeight)+1,(int)(width/stepWidth)+1);
		//generateSlopedHeightMap(4f);
		generateRandomHeightMap(4f);
		//generateFlatHeightMap(0f);
		renderHeightMap();
	}
	
	public void raiseHeight(float amnt, float x, float y){
		int colLeft = (int)(x/stepWidth);
		int colRight = colLeft + 1;
		int rowBottom = (int)(y/stepHeight);
		int rowTop = rowBottom + 1;
		int c = colLeft;
		if (colRight*stepWidth - x < x - colLeft*stepWidth)
			c = colRight;
		int r = rowBottom;
		if (rowTop * stepHeight - y < y - rowBottom*stepHeight)
			r = rowTop;
		setHeight(getHeight(r,c)+amnt,r,c);
		renderHeightMap();
	}
	
	public float[] grabLastNormal(){
		return n;
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
	
	public void generateFlatHeightMap(float height){
		for (int r = 0; r < heightMap.getRowDimension(); r++){
			for (int c = 0; c < heightMap.getColumnDimension(); c++){
				heightMap.put(height, r, c);
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
		// Sets the order of the verticies, adjusting the direction of the triangles.
		int j = 0;
		for (int r = 0; r < heightMap.getRowDimension()-1; r++){
			for (int c = 0; c < heightMap.getColumnDimension()-1; c++){
				if (heightMap.isEvenBox(r, c)){
					indicies[j++] = (short)heightMap.getIndex(r, c);
					indicies[j++] = (short)heightMap.getIndex(r+1, c+1);
					indicies[j++] = (short)heightMap.getIndex(r, c+1);
					
					indicies[j++] = (short)heightMap.getIndex(r, c);
					indicies[j++] = (short)heightMap.getIndex(r+1, c);
					indicies[j++] = (short)heightMap.getIndex(r+1, c+1);
				} else {
					indicies[j++] = (short)heightMap.getIndex(r, c);
					indicies[j++] = (short)heightMap.getIndex(r+1, c);
					indicies[j++] = (short)heightMap.getIndex(r, c+1);
					
					indicies[j++] = (short)heightMap.getIndex(r, c+1);
					indicies[j++] = (short)heightMap.getIndex(r+1, c);
					indicies[j++] = (short)heightMap.getIndex(r+1, c+1);
				}
			}
		}
		//Nathan's Super Excellent Placeholder Code!
		//End Nathan's Super Excellent Placeholder Code!
		float[] normals = new float[verts.length];
		int no = 0;
		for (int r = 0; r < heightMap.getRowDimension(); r++){
			for (int c = 0; c < heightMap.getColumnDimension(); c++){
				getAverageNormalAroundPoint(r,c);
				normals[no++] = n[0];
				normals[no++] = n[1];
				normals[no++] = n[2];
			}
		}
		getModel().setupModel(verts, normals, textureCoords, indicies);
	}
	
	public float[] getAverageNormalAroundPoint(int r, int c){
		float[] normalsArray = new float[3];
		getNormal((c * stepWidth)-(stepWidth/2f), (r * stepHeight)+(stepHeight/4f));
		normalsArray[0] += n[0];
		normalsArray[1] += n[1];
		normalsArray[2] += n[2];
		getNormal((c * stepWidth)-(stepWidth/4f), (r * stepHeight)+(stepHeight/2f));
		normalsArray[0] += n[0];
		normalsArray[1] += n[1];
		normalsArray[2] += n[2];
		getNormal((c * stepWidth)+(stepWidth/4f), (r * stepHeight)+(stepHeight/2f));
		normalsArray[0] += n[0];
		normalsArray[1] += n[1];
		normalsArray[2] += n[2];
		getNormal((c * stepWidth)+(stepWidth/2f), (r * stepHeight)+(stepHeight/4f));
		normalsArray[0] += n[0];
		normalsArray[1] += n[1];
		normalsArray[2] += n[2];
		getNormal((c * stepWidth)+(stepWidth/2f), (r * stepHeight)-(stepHeight/4f));
		normalsArray[0] += n[0];
		normalsArray[1] += n[1];
		normalsArray[2] += n[2];
		getNormal((c * stepWidth)+(stepWidth/4f), (r * stepHeight)-(stepHeight/2f));
		normalsArray[0] += n[0];
		normalsArray[1] += n[1];
		normalsArray[2] += n[2];
		getNormal((c * stepWidth)-(stepWidth/4f), (r * stepHeight)-(stepHeight/2f));
		normalsArray[0] += n[0];
		normalsArray[1] += n[1];
		normalsArray[2] += n[2];
		getNormal((c * stepWidth)-(stepWidth/2f), (r * stepHeight)-(stepHeight/4f));
		normalsArray[0] += n[0];
		normalsArray[1] += n[1];
		normalsArray[2] += n[2];
		normalsArray[0] /= 8f;
		normalsArray[1] /= 8f;
		normalsArray[2] /= 8f;
		n[0] = normalsArray[0];
		n[1] = normalsArray[1];
		n[2] = normalsArray[2];
		return n;
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
		
		getNormal(x,y);
		return (n[0]*(x-pointA[0]) + n[1]*(y-pointA[1]) - n[2]*pointA[2])/(-n[2]);
	}
	
	public float[] getNormal(float x, float y){
		if (x < getX() || y < getY() || x >= getX() + getWidth() || y >= getY() + getLength()){
			n[0] = 0;
			n[1] = 0;
			n[2] = 1;
			return n;
		}
		
		int colLeft = (int)(x/stepWidth);
		int rowTop = (int)(y/stepHeight) + 1;
		if (x - (colLeft*stepWidth) > (rowTop*stepHeight) - y){
			pointA[0] = colLeft*stepWidth;
			pointA[1] = rowTop*stepHeight;
			pointA[2] = heightMap.get(rowTop, colLeft);
			
			pointC[0] = (colLeft+1)*stepWidth;
			pointC[1] = rowTop*stepHeight;
			pointC[2] = heightMap.get(rowTop, colLeft+1);
			
			pointB[0] = (colLeft+1)*stepWidth;
			pointB[1] = (rowTop-1)*stepHeight;
			pointB[2] = heightMap.get(rowTop-1, colLeft+1);
		} else {
			pointA[0] = colLeft*stepWidth;
			pointA[1] = rowTop*stepHeight;
			pointA[2] = heightMap.get(rowTop, colLeft);
			
			pointB[0] = colLeft*stepWidth;
			pointB[1] = (rowTop-1)*stepHeight;
			pointB[2] = heightMap.get(rowTop-1, colLeft);
			
			pointC[0] = (colLeft+1)*stepWidth;
			pointC[1] = (rowTop-1)*stepHeight;
			pointC[2] = heightMap.get(rowTop-1, colLeft+1);
		}
		
		vectorA[0] = pointB[0] - pointA[0];
		vectorA[1] = pointB[1] - pointA[1];
		vectorA[2] = pointB[2] - pointA[2];
		vectorB[0] = pointC[0] - pointA[0];
		vectorB[1] = pointC[1] - pointA[1];
		vectorB[2] = pointC[2] - pointA[2];
		
		n[0] =  (vectorA[1]*vectorB[2] - vectorA[2]*vectorB[1]);
		n[1] = -(vectorA[0]*vectorB[2] - vectorA[2]*vectorB[0]);
		n[2] =  (vectorA[0]*vectorB[1] - vectorA[1]*vectorB[0]);
		float mag = (float)Math.sqrt(n[0]*n[0] + n[1]*n[1] + n[2]*n[2]);
		n[0] /= mag;
		n[1] /= mag;
		n[2] /= mag;
		
		return n;
	}
	
}
