package com.discretesoftworks.framework;

import java.util.ArrayList;


public abstract class GameRenderer {
	
	public ArrayList<GameObject> destructibleObjects, toAdd, toRemove;
	public ArrayList<GriddedObject> solidObjects;
	
	public RaggedDictionary<Integer, GameObject> renderObjects;
	
	public static GameRenderer s_instance;
	
	private int screenWidth = 1920, screenHeight = 1080;
	
	private boolean toClean = false, toClear = false;
	
	private AndroidGame game;
	
	public GameRenderer(){
		renderObjects = new RaggedDictionary<Integer, GameObject>(1000);
		toAdd = new ArrayList<GameObject>();
		toRemove = new ArrayList<GameObject>();
		solidObjects = new ArrayList<GriddedObject>();
		destructibleObjects = new ArrayList<GameObject>();
	}
	
	public void update(float deltaTime){
		// Adding MUST come before removing!
		if (toClear) {
			toClear = false;
			for (GameObject o :renderObjects.getCompiledData())
				remove(o);
			renderObjects.clear();
			solidObjects.clear();
		}
		synchronized (renderObjects) {
			synchronized (toAdd) {
				for (int a = 0; a < toAdd.size(); a++){
					renderObjects.add(toAdd.get(a).getDepth(),toAdd.get(a));
				}
				toAdd.clear();
			}
			synchronized (toRemove) {
				for (int r = 0; r < toRemove.size(); r++){
					renderObjects.removeAll(toRemove.get(r).clear());
				}
				toRemove.clear();
			}
		
		
			if (toClean){
				toClean = false;
				System.gc();
			}
	
			int i = 0;
			ArrayList<GameObject> objects = renderObjects.getCompiledData();
			for (i = 0; i < objects.size(); i++){
				objects.get(i).update(deltaTime);
				if (objects.get(i).getLife() < 1)
					toRemove.add(objects.get(i));
				else if (objects.get(i).getDepthChanged())
					renderObjects.move(objects.get(i), objects.get(i).getDepth());
			}
		}
	}
	
	public void add(GameObject o){
		System.out.println("Adding to add.");
		toAdd.add(o);
	}
	
	public ArrayList<GriddedObject> getSolidObjects(){
		return solidObjects;
	}
	
	public void addSolid(GameObject s){
		System.out.println("Adding solid!");
		solidObjects.add(s);
		for (GriddedObject q : solidObjects)
			System.out.println(q);
	}
	
	public void removeSolid(GameObject s){
		System.out.println("Removing solid!");
		solidObjects.remove(s);
	}
	
	public void setGame(AndroidGame game){
		this.game = game;
	}
	
	public AndroidGame getGame(){
		return game;
	}
	
	public void clean(){
		toClean = true;
	}
	
	public void clear(){
		toClear = true;
	}
	
	public void remove(GameObject o){
		toRemove.add(o);
	}
	
	public void setScreenWidth(int screenWidth){
		this.screenWidth = screenWidth;
	}
	
	public void setScreenHeight(int screenHeight){
		this.screenHeight = screenHeight;
	}
	
	public int getScreenWidth(){
    	return screenWidth;
    }
    
    public int getScreenHeight(){
    	return screenHeight;
    }
    
    public abstract int getViewWidth();
    public abstract int getViewHeight();
    public abstract float getViewScale();
	
	public abstract RenderModel getNewModel(int x, int y, Sprite sprite);
	public abstract void freeModel(RenderModel model);
	public abstract void setBGColor(float r, float g, float b, float a);
	public abstract boolean getSurfaceCreated();
	public abstract void setSurfaceCreated(boolean s);
}
