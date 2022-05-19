package map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import game.ClientGame;
import things.Bullet;
import things.Solid;

public abstract class Map {
	private int mapID;
	public int x;
	public int y;
	public int width;
	public int height;
	
	//Thing
	public ArrayList<Bullet> bullet = new ArrayList<Bullet>();
	
	public Map(int xPos,int yPos ,int wid,int hei,int id){
		x=xPos;
		y=yPos;
		width=wid;
		height=hei;
		mapID=id;
	}
	
	public abstract void tick();
	
	public abstract void paint(ClientGame game,Graphics2D g);
	
	public int getMapID(){
		return mapID;
	}
	
	public boolean isInBounds(Solid obj){
		Rectangle map = new Rectangle();
		map.setRect(x, y, width, height);
		
		boolean flag=true;

		if(obj.x+obj.size/2>x+width){
			obj.x=x+width-obj.size/2;
			flag=false;
		}
		else if(obj.x-obj.size/2 < x){
			obj.x=x+obj.size/2;
			flag=false;
		}
		if(obj.y+obj.size/2>y+height){
		    obj.y=y+height-obj.size/2;
			flag=false;
		}
		else if(obj.y - obj.size/2 < y){
		    obj.y=y+obj.size/2;
			flag=false;
		}
		
		return flag;
		
	}
	
}
