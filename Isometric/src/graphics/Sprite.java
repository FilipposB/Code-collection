package graphics;

import java.awt.Graphics;
import java.awt.Image;

public class Sprite {
	//Image
	private Image image;
	
	public Sprite(Image image){
		this.image=image;
	}
	
	public void draw(Graphics g,int x,int y){
		g.drawImage(image,x,y,null);
	}
}