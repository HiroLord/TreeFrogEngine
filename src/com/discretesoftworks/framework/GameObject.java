package com.discretesoftworks.framework;



public abstract class GameObject extends GriddedObject{

	private float life;
	private boolean solid;
	private boolean depthChanged;
	
	private RenderModel myModel;
	
	private Sprite sprite;
	
	private boolean init;
	
	public GameObject(float x, float y, float width, float height, Sprite sprite){
		super(x,y,width,height);
		this.life = 1;
		this.sprite = sprite;
		depthChanged = false;
		init = false;
		myModel = null;
		solid = false;
		init();
		addSelf();
	}
	
	public void init(){
		myModel = GameRenderer.s_instance.getNewModel(getX(),getY(),sprite);
		setDimensions(getWidth(),getHeight());
		sprite.setMask(getWidth(),getHeight());
		init = true;
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
		myModel.setHudElement(h);
	}
	
	public GameObject clear(){
		init = false;
		GameRenderer.s_instance.freeModel(myModel);
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
		myModel.setVisible(v);
	}
	
	public void setColor(float r, float g, float b, float a){
		myModel.setColor(r,g,b,a);
	}
	
	public void updateModel(){
		myModel.setX(getX());
		myModel.setY(getY());
		myModel.setZ(getZ());
		myModel.setWidth(getWidth());
		myModel.setHeight(getHeight());
	}
	
	//Recommended override and super call
	public void draw(float[] vpMatrix, float centerX, float centerY, float centerZ){
		if (init)
			myModel.draw(vpMatrix, centerX, centerY, centerZ);
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
