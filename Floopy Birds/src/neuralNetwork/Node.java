package neuralNetwork;

import java.io.Serializable;

public class Node implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public double value;
	public double bias;
	public double weightsArray[];
	
	public Node(double weights[],double bias){
		weightsArray = weights;
		value=bias;
		this.bias=bias;
	}
	
	public void setValue(double newVal){
		value=newVal+bias;
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
