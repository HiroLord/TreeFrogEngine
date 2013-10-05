package com.discretesoftworks.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;

public class ModelLoader {
	public static String convertStreamToString(InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
	public static RenderModel loadOBJ(RenderModel object, String filename, AssetManager assets) throws IOException {
		String file = convertStreamToString(assets.open(filename));
		String[] lines = file.split("\\r?\\n");
		String line;
		
		Sprite sprite = null;
		
		ArrayList<Float> positionCoords = new ArrayList<Float>();
		ArrayList<Float> textureCoords = new ArrayList<Float>();
		ArrayList<Float> normalCoords = new ArrayList<Float>();
		ArrayList<Float> verticies = new ArrayList<Float>();
		ArrayList<Short> indicies = new ArrayList<Short>();
		Hashtable<Long, Short> indiciesIndexMap = new Hashtable<Long, Short>();
		
		for (int i = 0; i < lines.length; i++) {
			line = lines[i];
			switch(line.charAt(0)) {
			case '#':
				System.out.println("Comment!");
				break;
			case 'v':
				//Vertex, or texture coord, or vertex normal
				String[] coords = line.split(" ");
				if (line.charAt(1) == 't') {
					//texture
					textureCoords.add(Float.parseFloat(coords[1]));
					textureCoords.add(-Float.parseFloat(coords[2]));
				} else if (line.charAt(1) == 'n') {
					//normal
					normalCoords.add(Float.parseFloat(coords[1]));
					normalCoords.add(-Float.parseFloat(coords[3]));
					//Out of order to make z up
					normalCoords.add(Float.parseFloat(coords[2]));
				} else if (line.charAt(1) == 'p') {
					//Parameter Space
				} else {
					//Regular vertex
					positionCoords.add(Float.parseFloat(coords[1]));
					positionCoords.add(-Float.parseFloat(coords[3]));
					//Out of order to make z be up
					positionCoords.add(Float.parseFloat(coords[2]));
				}
				break;
			case 'f':
				//face
				String[] tripIndic = line.split(" ");
				for (short j = 1; j < 4; j++) {
					short positionIndex = (short)(Short.parseShort(tripIndic[j].split("/")[0]) - 1);
					short texIndex = (short)(Short.parseShort(tripIndic[j].split("/")[1]) - 1);
					short normIndex;
					if (tripIndic[j].split("/").length == 3)
						normIndex = (short)(Short.parseShort(tripIndic[j].split("/")[2]) - 1);
					else
						normIndex = 0;
					
					Short vertexIndex = indiciesIndexMap.get(((long)positionIndex)|((long)texIndex)<<16|((long)normIndex)<<32);
					if (vertexIndex == null) {
						vertexIndex = (short)(verticies.size()/8);
						indiciesIndexMap.put(((long)positionIndex)|((long)texIndex)<<16|((long)normIndex)<<32, vertexIndex);
						verticies.add(positionCoords.get(positionIndex*3));
						verticies.add(positionCoords.get(positionIndex*3+1));
						verticies.add(positionCoords.get(positionIndex*3+2));
						
						if (normalCoords.size() == 0) {
							normalCoords.add(1f);
							normalCoords.add(1f);
							normalCoords.add(1f);
						}
						verticies.add(normalCoords.get(normIndex*3));
						verticies.add(normalCoords.get(normIndex*3+1));
						verticies.add(normalCoords.get(normIndex*3+2));

						
						verticies.add(textureCoords.get(texIndex*2));
						verticies.add(textureCoords.get(texIndex*2+1));
					}
					indicies.add(vertexIndex);
				}
				
				break;
			case 'm':
			{
				//mtllib
				String[] mtllibFileName = line.split(" ");
				String[] mtllibFile = convertStreamToString(assets.open(mtllibFileName[1])).split("\\r?\\n");
				String texFileName = "";
				String[] mtllibFileLine = null;
				for (int j = 0; j < mtllibFile.length; j++) {
					mtllibFileLine = mtllibFile[j].split(" ");
					if (mtllibFileLine[0].equals("map_Kd")) {
						texFileName = mtllibFileLine[1];
						break;
					}
				}
				sprite = new Sprite(BitmapFactory.decodeStream(assets.open(texFileName)),1);
			}
				break;
			case 'u':
				//usemtl
				break;
			case 'o':
				//object
				break;
			default:
				//s off, etc
					
			}
		}
		float[] verticiesArray = new float[(verticies.size()/8)*3];
		float[] normalsArray = new float[(verticies.size()/8)*3];
		float[] texArray = new float[(verticies.size()/8)*2];
		short[] indiciesArray = new short[indicies.size()];

		for (int i = 0; i < indicies.size(); i++) {
			indiciesArray[i] = indicies.get(i);
			verticiesArray[indicies.get(i)*3] = verticies.get(indicies.get(i)*8);
			verticiesArray[indicies.get(i)*3+1] = verticies.get(indicies.get(i)*8+1);
			verticiesArray[indicies.get(i)*3+2] = verticies.get(indicies.get(i)*8+2);
			
			normalsArray[indicies.get(i)*3] = verticies.get(indicies.get(i)*8+3);
			normalsArray[indicies.get(i)*3+1] = verticies.get(indicies.get(i)*8+4);
			normalsArray[indicies.get(i)*3+2] = verticies.get(indicies.get(i)*8+5);
			
			texArray[indicies.get(i)*2] = verticies.get(indicies.get(i)*8+6);
			texArray[indicies.get(i)*2+1] = verticies.get(indicies.get(i)*8+7);
		}
		object.setupModel(verticiesArray, normalsArray, texArray, indiciesArray);
		object.setSprite(sprite);
		
		return object;
	}

}
