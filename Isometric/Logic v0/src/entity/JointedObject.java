package entity;

import java.util.ArrayList;
import java.util.List;

public class JointedObject {
	
	private int id;

	private float x;
	private float y;
	
	private List<Joint> joints = new ArrayList<Joint>();
	
	public JointedObject(int x,int y,int id){
		this.x=x;
		this.y=y;
		
		this.id=id;
	}
	
	protected void setXY(float x,float y){
		float xOffset=this.x-x;
		float yOffset=this.y-y;
		for (Joint j:joints){
			j.moveXY(xOffset, yOffset);
		}
		this.x=x;
		this.y=y;
	}
	
	protected void moveXY(float xOffset,float yOffset){
		for (Joint j:joints){
			j.moveXY(xOffset, yOffset);
		}
		x-=xOffset;
		y-=yOffset;
	}
	
	protected void addJoint(int x,int y,int id){
		joints.add(new Joint(x,y,id));
	}
	
	
	
	protected List<Joint> getJoints(){
		return joints;
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
