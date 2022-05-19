package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.Game;

public class SolidTile extends Tile {

	public SolidTile(int x, int y, double timeToBreak, Color col, Game game) {
		super(x, y, timeToBreak, col, game);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		int xPos=(int) ((x*game.tileWidth)-game.worldX);
		int yPos=(int) ((y*game.tileHeight)-game.worldY);
		g.setColor(color);
		g.fillRect(xPos, yPos, game.tileWidth, game.tileHeight);
	}

	@Override
	public Rectangle getHitBox(){
		return new Rectangle((int)(x+0.5),(int)(y+0.5),game.tileWidth,game.tileHeight);
	}

}
