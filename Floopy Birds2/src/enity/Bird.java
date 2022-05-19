package enity;

import game.Game;
import neuralNetwork.SimpleNeuralNetwork;

public class Bird extends Entity {
	public boolean jump;
	private float ySpeed=1;
	private float yAccel=0;
	public SimpleNeuralNetwork brain;
	public Bird(int xN, int yN, int width, int height,SimpleNeuralNetwork brainCopy,boolean load) {
		super(xN, yN, width, height);
		// TODO Auto-generated constructor stub
		if (brainCopy!=null){
			brain=new SimpleNeuralNetwork(brainCopy);
			if (!load)brain.mutate(0.004f);
		}
		else{
			brain = new SimpleNeuralNetwork(4,3,2,1);
		}
		
		jump=false;
	}

	@Override
	public void update(Game game) {
		ySpeed=-15;
		brain.updateInputLayer((y+hei/2)/game.getHeight(),(game.tubes[0].x-60)/game.getWidth(),(game.tubes[0].y+game.tubes[0].hei)/game.getHeight(),(game.tubes[1].y/game.getHeight()));
		if (brain.getOutputNode(0)>0.5)jump=true;
		// TODO Auto-generated method stub
		if (jump){
			yAccel=ySpeed;
			jump=false;
		}
			yAccel+=1;
			y+=yAccel;
			if (yAccel>10)yAccel=10;
			if (y>650)y=650;
			if (y<0)y=0;
		
	}

}
