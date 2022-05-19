package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.Game;

public abstract class Entity {
	private static long entityId;
	public long id;
	
	protected Game game;
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	protected static final double gravity=2;
	protected double yAccel;
	
	public boolean isOnGround;
	
	protected double speed;
	
	protected Color color;
	
	public Entity(int x,int y,int wid,int hei,double speed,Color col,Game game){
		this.x=x;
		this.y=y;
		this.width=wid;
		this.height=hei;
		this.speed=speed;
		this.color=col;
		this.game=game;
		this.isOnGround=false;
		this.id=entityId++;
	}
	
	public abstract void draw(Graphics2D g);
	public abstract void tick();
	
	public Rectangle getHitBox(){
		return new Rectangle((int)(x+0.5),(int)(y+0.5),width,height);
	}
}
