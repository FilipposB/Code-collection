package neuralNetwork;

import java.util.Random;


public class SimpleNeuralNetwork {

	// nodeArray holds all the nodes rows and columns
	private float nodeArray[][];

	// weightArray holds all the weights
	// the final layer doesn't have a weight column
	private float weightArray[][];

	// Randomly generates a neural network
	public SimpleNeuralNetwork(int col0, int col1, int... colArray) {
		int totalCol = colArray.length + 2;
		nodeArray = new float[totalCol][];

		// Create the nodes
		nodeArray[0] = new float[col0];
		nodeArray[1] = new float[col1];
		for (int i = 2; i < totalCol; i++) {
			nodeArray[i] = new float[colArray[i - 2]];
		}

		// Create the weights
		weightArray = new float[totalCol - 1][];
		for (int i = 0; i < weightArray.length; i++) {
			int totalWeights = nodeArray[i].length * nodeArray[i + 1].length;
			weightArray[i] = new float[totalWeights];
		}

		generateRandomWeights();
	}

	// Makes a new copy of an existing network
	public SimpleNeuralNetwork(SimpleNeuralNetwork net) {
		this.nodeArray = net.nodeArray;
		this.weightArray = net.weightArray;
	}

	// Randomly causes mutations in the network in the range of (-rate,rate)
	public void mutate(float rate) {
		if (rate != 0f) {
			Random rand = new Random();
			for (int i = 0; i < weightArray.length; i++) {
				for (int j = 0; j < weightArray[i].length; j++) {
					float randomValue = rate * rand.nextFloat();
					if (rand.nextBoolean())
						randomValue = -randomValue;
					weightArray[i][j] += randomValue;
				}
			}
		}
	}

	// Updates the network
	public void updateInputLayer(float... input) {
		for (int i = 0; (i < input.length && i < nodeArray[0].length); i++) {
			nodeArray[0][i] = input[i];
		}
		for (int i = 1; i < nodeArray.length; i++) {
			int weightAmount = nodeArray[i - 1].length;
			for (int j = 0; j < nodeArray[i].length; j++) {
				nodeArray[i][j] = 0;
				for (int k = 0; k < weightAmount; k++) {
					nodeArray[i][j] += nodeArray[i - 1][k] * weightArray[i - 1][k + (weightAmount * j)];
				}
				if (nodeArray[i][j] != 0)
					nodeArray[i][j] = Maths.sigmoid(nodeArray[i][j]);
			}
		}
	}

	// Gives most likely result
	public int getResult() {
		float max = -10;
		int place = -1;
		for (int i = 0; i < nodeArray[nodeArray.length - 1].length; i++) {
			if (nodeArray[nodeArray.length - 1][i] > max) {
				max = nodeArray[nodeArray.length - 1][i];
				place = i;
			}
		}
		return place;
	}

	// Random weight generator
	public void generateRandomWeights() {
		Random rand = new Random();
		for (int i = 0; i < weightArray.length; i++) {
			for (int j = 0; j < weightArray[i].length; j++) {
				weightArray[i][j] = (float) rand.nextGaussian();
			}
		}
	}
	
	//Get value of specific Node
	public float getOutputNode(int x){
		if (x>=0&&x<nodeArray[nodeArray.length-1].length){
			return nodeArray[nodeArray.length-1][x];
		}
		return 0;
	}

	// Display Network
	public void showWeights() {
		for (int i = 0; i < weightArray.length; i++) {
			for (int j = 0; j < weightArray[i].length; j++) {
				System.out.println(weightArray[i][j]);
			}
		}
	}

	public void showNodes() {
		for (int i = 0; i < nodeArray.length; i++) {
			for (int j = 0; j < nodeArray[i].length; j++) {
				System.out.println(nodeArray[i][j]);
			}
		}
	}
}
