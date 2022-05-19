package camera;

import game.ClientGame;

public class Camera {
	public int x;
	public int y;
	
	private ClientGame game;
	
	public Camera(int x,int y,ClientGame gam){
		this.x=x;
		this.y=y;
		game = gam;
	}
	
	public void moveCamera(int x,int y){
		this.x=x-game.getWidth()/2;
		this.y=y-game.getHeight()/2;
	}
}
