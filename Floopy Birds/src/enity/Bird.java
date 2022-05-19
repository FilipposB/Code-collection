package enity;

import game.Game;
import neuralNetwork.NeuralNetwork;

public class Bird extends Entity {
	public boolean jump;
	private double ySpeed=1;
	private double yAccel=0;
	public NeuralNetwork brain;
	public Bird(int xN, int yN, int width, int height,NeuralNetwork brainCopy,boolean load) {
		super(xN, yN, width, height);
		// TODO Auto-generated constructor stub
		if (brainCopy!=null){
			brain=new NeuralNetwork(brainCopy);
			if (!load)brain.mutate(0.05);
		}
		else{
			brain = new NeuralNetwork(4,3,1);
		}
		
		jump=false;
	}

	@Override
	public void update(Game game) {
		ySpeed=-15;
		double inputs[] ={(y+hei/2)/game.getHeight(),(game.tubes[0].x-60)/game.getWidth(),(game.tubes[0].y+game.tubes[0].hei)/game.getHeight(),game.tubes[1].y/game.getHeight()};
		brain.updateNetwork(inputs);
		if (brain.network[2].layer[0].value>0.5)jump=true;
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
