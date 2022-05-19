package enity;

import game.Game;

public class Tube extends Entity{
	float speedX=10;
	public Tube(int xN, int yN, int width, int height) {
		super(xN, yN, width, height);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void update(Game game) {
		// TODO Auto-generated method stub
		x-=speedX;
	}
	
	
}
