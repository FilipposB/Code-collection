package camera;

import java.awt.Rectangle;

public class Camera {
	private Rectangle map;
	private double x;
	private double y;
	private double speed;
	
	private boolean moveTo[];
	
	public Camera(int mapW,int mapH,double speed){
		this.moveTo=new boolean[4];
		this.speed=speed;
		stopMoving();
	}
	
	private void stopMoving(){
		for (int i = 0; i < moveTo.length; i++) {
			 moveTo[i]=false;
		}
	}
	
	public void tick(){
		if (moveTo[0]){
			x-=speed;
		}
		if (moveTo[1]){
			y-=speed;
		}
		if (moveTo[2]){
			x+=speed;
		}
		if (moveTo[3]){
			y+=speed;
		}
	}
	
	public int getX(){
		return (int)(x+0.5);
	}
	
	public int getY(){
		return (int)(y+0.5);
	}
	
	public void changeDirection(int dir,boolean state){
		moveTo[dir]=state;
	}
}
