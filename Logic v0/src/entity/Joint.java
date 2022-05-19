package entity;

import java.util.ArrayList;
import java.util.List;

public class Joint {

	private int id;

	private float x;
	private float y;
	
	private List<Integer> connected = new ArrayList<Integer>();
	
	public Joint(int x,int y,int id){
		this.x=x;
		this.y=y;
		
		this.id=id;
	}
	
	protected void setXY(float x,float y){
		this.x=x;
		this.y=y;
	}
	
	protected void moveXY(float x,float y){
		this.x-=x;
		this.y-=y;
	}
	
	protected void connectWithJoint(int id){
		if (!connected.contains(id)){
			connected.add(id);
		}
	}
	
	protected boolean contains(int id){
		return connected.contains(id);
	}
	
	protected int getID(){
		return id;
	}
	
	protected float getX(){
		return x;
	}
	
	protected float getY(){
		return y;
	}
}
