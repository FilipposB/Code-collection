package neuralNetwork;

import java.io.Serializable;

public class Node implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public double value;
	public double weightsArray[];
	
	public Node(double weights[]){
		weightsArray = weights;
		value=0;
	}
	
	public void setValue(double newVal){
		value=newVal;
	}
	
	public void addWeightedValue(double newVal){
		value+=newVal;
	}
	
	public void resetNode(){
		value=0;
	}
	
	public double getWeight(int i){
		return value*weightsArray[i];
	}
}
