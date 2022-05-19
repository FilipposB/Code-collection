package enity;

import java.awt.Graphics;
import java.awt.Rectangle;


import game.Game;

public abstract class Entity {
	public float x;
	public float y;
	
	public int wid;
	public int hei;
	
	private static int id;
	private int entID;
	
	private Rectangle me = new Rectangle();
	/** The rectangle used for other entities during collision resolution */
	private Rectangle him = new Rectangle();
	
	public Entity(int xN,int yN,int width,int height){
		entID=id;
		id++;
		x=xN;
		y=yN;
		wid=width;
		hei=height;
	}
	
	public int getID(){
		return entID;
	}
	
	public void paint(Graphics g){
		g.fillRect((int)(x+0.5), (int)(y+0.5), wid, hei);
		g.setColor(g.getColor().darker());
		g.drawRect((int)(x+0.5), (int)(y+0.5), wid, hei);
	}
	
	public abstract void update(Game game);
	
	public boolean collidesWith(Entity other) {
		me.setBounds((int) x,(int) y,wid,hei);
		him.setBounds((int) other.x,(int) other.y,other.wid,other.hei);

		return me.intersects(him);
	}
}
