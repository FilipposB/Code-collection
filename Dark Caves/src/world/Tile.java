package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.Game;

public abstract class Tile {
	private static long entityId;
	public long id;
	
	protected Game game;
	
	protected int x;
	protected int y;
	protected Color color;
	
	protected double timeToBreak;
	
	public Tile(int x,int y,double timeToBreak,Color col,Game game){
		this.x=x;
		this.y=y;
		this.timeToBreak=timeToBreak;
		this.color=col;
		this.game=game;
		this.id=entityId++;
	}
	
	protected abstract void draw(Graphics2D g);
	
	public abstract Rectangle getHitBox();
}
