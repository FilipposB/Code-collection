package entity;

import java.util.ArrayList;
import java.util.List;

import connector.InputConnector;
import connector.OutputConnector;

public abstract class LogicObject {
	
	private int id;

	private float x;
	private float y;
	
	private int size;
	
	protected List<InputConnector>  input = new ArrayList<InputConnector>();
	protected List<OutputConnector>  output = new ArrayList<OutputConnector>();

	public LogicObject(int x,int y,int id){
		this.x=x;
		this.y=y;
		this.id=id;
		this.size=1;
	}
	
	protected void setXY(float x,float y){
		this.x=x;
		this.y=y;
	}
	
	protected void moveXY(float xOffset,float yOffset){
		x-=xOffset;
		y-=yOffset;
	}
	
	protected abstract void update();
	
	
	protected int getID(){
		return id;
	}
	
	protected float getX(){
		return x;
	}
	
	protected float getY(){
		return y;
	}
	
	protected int getSize(){
		return size;
	}
	public int getInputConnectorCount(){
		return input.size();
	}
	public int getOutputConnectorCount(){
		return output.size();
	}
	
	protected void addConnectors(int inputAmount,int outputAmount){
		for (int i=0;i<inputAmount;i++){
			input.add(new InputConnector());
		}
		for (int i=0;i<outputAmount;i++){
			output.add(new OutputConnector());
		}
		if (input.size()>size)size=input.size();
		if (output.size()>size)size=output.size();

	}
	
	
}
