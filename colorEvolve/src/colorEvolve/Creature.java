package colorEvolve;

import java.awt.Color;
import java.util.Random;

public class Creature {
	private int red;
	private int green;
	private int blue;
	private Color color;
	private int score;
	private boolean alive;
	private final int per = 100;

	public Creature(Color x) {
		Random rand = new Random();
		red = rand.nextInt(256);
		green = rand.nextInt(256);
		blue = rand.nextInt(256);
		color = new Color(red,green,blue);
		alive = true;
		score = Math.abs(x.getGreen() - green) + Math.abs(x.getRed() - red) + Math.abs(x.getBlue() - blue);
	}

	public Creature(Creature y, Color x) {
		Random rand = new Random();
		red = y.getRed() + rand.nextInt(1+2*per) - per;
		green = y.getGreen() + rand.nextInt(1+2*per) - per;
		blue = y.getBlue() + rand.nextInt(1+2*per) -per;
		if (red > 255)
			red = 255;
		else if (red < 0)
			red = 0;
		if (green > 255)
			green = 255;
		else if (green < 0)
			green = 0;
		if (blue > 255)
			blue = 255;
		else if (blue < 0)
			blue = 0;
		color = new Color(red,green,blue);
		alive = true;
		score = Math.abs(x.getGreen() - green) + Math.abs(x.getRed() - red) + Math.abs(x.getBlue() - blue);
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}

	public int getScore() {
		return score;
	}
	
	public Color getColor(){
		return color;
	}
	
	public boolean getAlive(){
		return alive;
	}
	
	public void setDead(){
		alive=false;
	}
}
