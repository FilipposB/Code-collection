package network;

public class Maths {
	//does the sigmoid function
		public static float sigmoid(float x) {
			return (float) (1 / (1 + Math.pow(Math.E, (-1 * x))));
		}

}
