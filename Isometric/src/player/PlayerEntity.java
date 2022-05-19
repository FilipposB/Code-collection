package player;

import java.awt.Graphics;

import entities.Entity;

public class PlayerEntity extends Entity{
	
	public PlayerEntity(int x,int y,int wid,int hei,String img){
		super(x,y,wid,hei,img);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		sprite.draw(g, (int)(xPos+0.5), (int)(yPos+0.5));
	}
	
	

}
