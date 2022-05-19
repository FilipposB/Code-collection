package map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;

import game.ClientGame;
import things.Bullet;

public class PracticeMap extends Map{
	public Point decore[]=new Point[500];
	
	public PracticeMap() {
		super(0, 0, 2000, 2000,0);

		Random rand = new Random();
		for (int i=0;i<500;i++){
			decore[i]=new Point();
			decore[i].setLocation(rand.nextInt(2000), rand.nextInt(2000));;
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paint(ClientGame game, Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(Color.CYAN);
		g.fillRect(x-game.camera.x, y-game.camera.y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x-game.camera.x, y-game.camera.y, width, height);
		
		g.setColor(Color.GRAY);
		for (Point p:decore){
			g.fillRect(p.x-game.camera.x, p.y-game.camera.y, 5, 5);
		}
		
		for (Bullet b : bullet)
			b.paint(g, game);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		for (Bullet b : bullet)
			b.tick(this);

		for (int i = 0; i < bullet.size(); i++)
			if (bullet.get(i).toRemove)
				bullet.remove(i);
	}

	

}
