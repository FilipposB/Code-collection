package entities;

import java.awt.Color;
import java.awt.Graphics2D;

import game.Game;

public class PlayerEntity extends Entity {
	private boolean move[] = { false, false ,false};

	public PlayerEntity(int x, int y, int wid, int hei, double speed, Color col, Game game) {
		super(x, y, wid, hei, speed, col, game);

	}

	@Override
	public void draw(Graphics2D g) {
		int xPos = (game.getWidth() / 2) - (width / 2);
		int yPos = (game.getHeight() / 2) - (height / 2);
		g.setColor(color);
		g.fillRect(xPos, yPos, width, height);

	}

	@Override
	public void tick() {
		if(isOnGround&&move[2]){
			y+=10;
		}
		else if (isOnGround){
			yAccel=0;
		}
		else{
			yAccel+=gravity;
			y+=yAccel;
		}
		if (move[1]) {
			x-=speed;
		}
		if (move[0]) {
			x+=speed;
		}
		game.worldX=x;
		game.worldY=y;
	}
	
	public void doAction(int x,boolean pressed){
		move[x]=pressed;
	}
	
}
