package things;

import java.awt.Color;
import java.awt.Graphics2D;

import game.ClientGame;
import map.Map;

public class Bullet extends Solid{
	public int xS;
	public int yS;
	
	public double range;
	
	public double damage;
	
	public boolean toRemove;
	
	public Bullet(int x,int y,int siz,double ang,double spe,double ran,double dam){
		super(x,y,siz,spe,ang);
		xS=x;
		yS=y;
		range=ran;
		damage=dam;
	}
	
	public void paint(Graphics2D g, ClientGame game){
		g.setColor(Color.RED);
		g.fillOval((int)(x-0.5)-game.camera.x-size/2, (int)(y-0.5)-game.camera.y-size/2, size, size);
	}
	
	public void tick(Map map){
		x = (x + Math.cos(-angle)*speed);
		y = (y + Math.sin(-angle)*speed);
		if (Math.abs(xS-x)+Math.abs(yS-y)>range)toRemove=true;
		toRemove=!map.isInBounds(this);
	}
	
}
