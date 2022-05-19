package neuralNetwork;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class Tools {
	//does the sigmoid function
	protected static double sigmoid(double x) {
		return (1 / (1 + Math.pow(Math.E, (-1 * x))));
	}

	// saves network in a file
	public static void saveNetwork(NeuralNetwork net, String fileName) throws IOException {
		
		FileOutputStream fos = new FileOutputStream(fileName);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(net);
		oos.close();
	}

	// loads neuralNetwork from file
	public static NeuralNetwork loadNetwork(String fileName) throws IOException, ClassNotFoundException {
		FileInputStream fin = new FileInputStream(fileName);
		ObjectInputStream ois = new ObjectInputStream(fin);
		NeuralNetwork net = (NeuralNetwork) ois.readObject();
		ois.close();
		return net;
	}
}
