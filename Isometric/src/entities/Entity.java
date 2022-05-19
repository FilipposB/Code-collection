package entities;

import java.awt.Graphics;


import graphics.Sprite;

public abstract class Entity {
	//Id of Entity
	private int entityId;
    private static int nextEntityID;
    
	//Position of Entity
	public double xPos;
	public double yPos;
	
	//Size of Entity
	public int width;
	public int height;
	
	//Speed of Entity
	public double speed;
	
	//Sprite of Entity
	public Sprite sprite;
	
	public Entity(int x,int y,int wid,int hei,String img){
		this.entityId=nextEntityID++;
		this.xPos=x;
		this.yPos=y;
		this.width=wid;
		this.height=hei;
		this.sprite=graphics.SpriteStore.getSprite(img);
	}
	
	public abstract void render(Graphics g);
	public abstract void tick();
	
	public int getId(){
		return this.entityId;
	}
	
	
}
