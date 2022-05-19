package objects;

import java.util.Random;

public class LootCrate {
	private double x;
	private double y;
	private int height;
	private int weapon;
	private int spr;
	private boolean falling;
	
	public LootCrate(int sprite,int weaponSize){
		Random rand = new Random();
		falling=true;
		x=20+rand.nextInt(1110);
		y=-31;
		spr=sprite;
		height=32;
		weapon=1+rand.nextInt(weaponSize-1);
	}
	
	public LootCrate(int sprite){
		Random rand = new Random();
		falling=true;
		x=100+rand.nextInt(1100);
		y=-31;
		height=32;
		spr=sprite;
	}
	public int getX(){
		return (int) x;
	}
	
	public int getY(){
		return (int)y;
	}
	
	public int getSprite(){
		return spr;
	}
	
	public int getWeapon(){
		return weapon;
	}
	
	public int getHeight(){
		return height;
	}
	public boolean getFalling(){
		return falling;
	}
	public void setFalling(boolean x){
		falling=x;
	}
	
	public void setY(double y){
		this.y=y;
	}

	public void updateCrate(double delta){
		if (falling)y+=1.5*delta;
	}
	
}
