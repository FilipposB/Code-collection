package widgets;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import display.Display;

public abstract class Widget {
	
	public Widget(Rectangle bx,Display dis){
		box=bx;
		display=dis;
	}
	
	public Rectangle box;
	
	protected Display display;
	
	public abstract void paint(Graphics2D g);
}
