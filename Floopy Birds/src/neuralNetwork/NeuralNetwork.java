package neuralNetwork;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.Random;


public class NeuralNetwork implements Serializable {

	private static final long serialVersionUID = 1L;
	/*
	 * Each array represents a layer of our neural network inputLayer is the
	 * layer that we give info to hiddenLayer is the layer that only does logic
	 * without our interference outputLayer is the layer that we can extract our
	 * fianl value from
	 */
	public Layer network[];

	// Constructor for a random Network
	public NeuralNetwork(int l1, int l2, int... layers) {
		int total = 2 + layers.length;

		network = new Layer[total];

		for (int i = 0; i < total - 1; i++) {
			if (i == 0) {
				network[i] = new Layer(l1, l2);
			} else if (i == 1) {
				network[i] = new Layer(l2, layers[0]);
			} else {
				network[i] = new Layer(layers[i - 2], layers[i - 1]);
			}
		}

		network[network.length - 1] = new Layer(layers[layers.length - 1], 0);
	}

	// Constructor for NeuralNetwork from another NeuralNetwork
	public NeuralNetwork(NeuralNetwork ogNet) {
		network = new Layer[ogNet.network.length];
		for (int i = 0; i < network.length; i++) {
			network[i] = new Layer(ogNet.network[i]);
		}
	}

	// Randomly causes mutations in the network in the range of (-rate,rate)
	public void mutate(double rate) {
		Random rand = new Random();
		for (int i = 0; i < network.length; i++) {
			for (int j = 0; j < network[i].layer.length; j++) {
				for (int k = 0; k < network[i].layer[j].weightsArray.length; k++) {
					double randomValue = rate * rand.nextDouble();
					if (rand.nextBoolean())
						randomValue = -randomValue;
					network[i].layer[j].weightsArray[k] += randomValue;
				}
				double randomValue = rate * rand.nextDouble();
				if (rand.nextBoolean())
					randomValue = -randomValue;
				network[i].layer[j].bias +=  randomValue;
			}
		}
	}

	// Updates all layers of the neural network
	public void updateNetwork(double inputs[]) {
		for (int i = 0; i < inputs.length; i++) {
			network[0].layer[i].setValue(inputs[i]);
		}
		for (int i = 1; i < network.length; i++) {
			network[i] = updateLayer(network[i], network[i - 1]);
		}

	}

	// Updates a layer specified from the updateNetwork function and returns the
	// updated Array
	private Layer updateLayer(Layer layer, Layer layerPrev) {
		for (int i = 0; i < layer.layer.length; i++) {
			layer.layer[i].resetNode();
			for (int j = 0; j < layerPrev.layer.length; j++) {
				layer.layer[i].addWeightedValue(layerPrev.layer[j].getWeight(i));

			}

			if (layer.layer[i].value != 0)
				layer.layer[i].setValue(Tools.sigmoid(layer.layer[i].value));

		}
		return layer;
	}

	// draws a representation of the network
	public void drawNetwork(Graphics2D g,int x,int y) {
		for (int i = 0; i < network.length - 1; i++) {
			network[i].drawLayer(g, i,x,y, network[i + 1]);
		}
		network[network.length - 1].drawLayer(g, network.length - 1,x,y, null);
	}
	
	

}