package com.discretesoftworks.framework;


// Game object class
// All visible objects in the game should extend this class
public abstract class GameObject extends GriddedObject{

	private float life;
	private boolean solid;
	private boolean depthChanged;
	
	private RenderModel[] myModel;
	
	private String[] objectNames;
	
	private boolean init;
	
	private float dir;
	
	private boolean needUpdate = true;
	
	public GameObject(float x, float y, float z, float width, float length, String[] objectNames){
		super(x,y,z,width,length);
		this.life = 1;
		this.objectNames = objectNames;
		if (this.objectNames == null){
			this.objectNames = new String[1];
			this.objectNames[0] = null;
		}
		dir = 0;
		myModel = new RenderModel[this.objectNames.length];
		depthChanged = false;
		init = false;
		solid = false;
		init();
		addSelf();
	}
	
	public void init(){
		for (int i = 0; i < myModel.length; i++)
			myModel[i] = GameRenderer.s_instance.getNewModel(getX(),getY(),getWidth(),getLength(),objectNames[i]);
		setDimensions(getWidth(),getLength());
		init = true;
	}
	
	public void setDir(float dir){
		this.dir = dir;
		for (int i = 0; i < myModel.length; i++)
			myModel[i].setNewDir(2, dir);
	}
	
	public float getDir(){
		return dir;
	}
	
	@Override
	public void setDepth(int depth){
		if (depth != getDepth())
			depthChanged = true;
		super.setDepth(depth);
	}
	
	public boolean getDepthChanged(){
		if (depthChanged){
			depthChanged = false;
			return true;
		}
		return false;
	}
	
	public void addSelf(){
		GameRenderer.s_instance.add(this);
	}
	
	public void setHudElement(boolean h){
		for (int i = 0; i < myModel.length; i++)
			myModel[i].setHudElement(h);
	}
	
	public GameObject clear(){
		init = false;
		for (int i = 0; i < myModel.length; i++)
			GameRenderer.s_instance.freeModel(myModel[i]);
		return this;
	}
	
	public void setSolid(boolean solid){
		this.solid = solid;
		if (solid)
			GameRenderer.s_instance.addSolid(this);
		else
			GameRenderer.s_instance.removeSolid(this);
	}
	
	public boolean getSolid(){
		return solid;
	}
	
	public void setLife(float life){
		this.life = life;
	}
	
	public void changeLife(float dLife){
		this.life += dLife;
	}
	
	public int getLife(){
		return (int)life;
	}
	
	public void setVisible(boolean v){
		for (int i = 0; i < myModel.length; i++)
			myModel[i].setVisible(v);
	}
	
	public void setColor(float r, float g, float b, float a){
		for (int i = 0; i < myModel.length; i++)
			myModel[i].setColor(r,g,b,a);
	}
	
	public RenderModel getModel(int i){
		return myModel[i];
	}
	
	@Override
	public void setX(float x){
		super.setX(x);
		needUpdate = true;
	}
	
	@Override
	public void setY(float y){
		super.setY(y);
		needUpdate = true;
	}
	
	@Override
	public void setZ(float z){
		super.setZ(z);
		needUpdate = true;
	}
	
	@Override
	public void changeX(float dx){
		super.changeX(dx);
		needUpdate = true;
	}
	
	@Override
	public void changeY(float dy){
		super.changeY(dy);
		needUpdate = true;
	}
	
	@Override
	public void changeZ(float dz){
		super.changeZ(dz);
		needUpdate = true;
	}
	
	public int getModelAmount(){
		return myModel.length;
	}
	
	public void updateModel(){
		if (!needUpdate)
			return;
		for (int i = 0; i < myModel.length; i++){
			myModel[i].setX(getX());
			myModel[i].setY(getY());
			myModel[i].setZ(getZ());
			myModel[i].setWidth(getWidth());
			myModel[i].setLength(getLength());
			myModel[i].remakeModelMatrix();
		}
		needUpdate = false;
	}
	
	public void updateModel(int i){
		if (!needUpdate)
			return;
		myModel[i].setX(getX());
		myModel[i].setY(getY());
		myModel[i].setZ(getZ());
		myModel[i].setWidth(getWidth());
		myModel[i].setLength(getLength());
		myModel[i].remakeModelMatrix();
		needUpdate = false;
	}
	
	public boolean placeFree(float x, float y){
		float dx = x-getX();
		float dy = y-getY();
		changeX(dx);
		changeY(dy);
		
		boolean noCollide = true;
		
		if (Directional.checkAllCollisions(this, GameRenderer.s_instance.solidObjects) != null)
			noCollide = false;
		
		changeX(-dx);
		changeY(-dy);
		return noCollide;
	}
	
	//Recommended override and super call
	public void draw(float[] vpMatrix){
		if (init){
			for (int i = 0; i < myModel.length; i++)
				myModel[i].draw(vpMatrix);
		}
		else
			init();
	}
	
	// Override and super call
	public void update(float deltaTime){
		if (init)
			updateModel();
		else
			init();
	}
	
}
