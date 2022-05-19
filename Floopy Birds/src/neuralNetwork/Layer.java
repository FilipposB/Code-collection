package neuralNetwork;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Random;

public class Layer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Node layer[];

	public Layer(int nodes, int nextLen) {
		layer = new Node[nodes];
		initializeRandomLayer(nextLen);
	}
	
	public Layer(Layer lay){
		layer = new Node[lay.layer.length];
		for (int i=0;i<layer.length;i++){
			layer[i]=new Node(lay.layer[i].weightsArray,lay.layer[i].bias);
		}
	}

	private void initializeRandomLayer(int nextLen) {
		Random rand = new Random();
		for (int i = 0; i < layer.length; i++) {
			layer[i] = new Node(randomWeights(nextLen),rand.nextDouble());

		}
	}

	private double[] randomWeights(int x) {
		Random rand = new Random();
		double weights[] = new double[x];
		for (int i = 0; i < x; i++) {
			weights[i] = rand.nextGaussian();
		}
		return weights;
	}
	

	protected void drawLayer(Graphics2D g,int xArr, int x,int y, Layer next) {
		for (int i = 0; i < layer.length; i++) {
			g.setColor(Color.BLACK);
			if (next != null) {
				for (int j = 0; j < next.layer.length && j < 10; j++) {
					g.drawLine(160*xArr+x, 70 * i+y, 160*(xArr+1)+x, 70 * j+y);
				}
			}
			if (next==null){
				if (layer[i].value>0.5)
				g.setColor(Color.GREEN);
				else
				g.setColor(Color.YELLOW);

			}
			g.fillOval(160*xArr+x-30,70 * i+y-30, 60, 60);
			g.setColor(Color.RED);
			g.drawString("" + Double.parseDouble(new DecimalFormat("##.####").format(layer[i].value)), 160*xArr+x-20, 70 * i+y);

		}
	}

}
