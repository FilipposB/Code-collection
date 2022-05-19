package gui.widgets;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import gui.display.Display;

public class ToolButton extends ButtonWidget{
	
	private int signal;
	
	public ToolButton(Display dis, int x,int y,int wid,int hei,int sig){
		super(new Rectangle(x,y,wid,hei),dis);
		
		signal=sig;
	}

	@Override
	public boolean isClicked(int x, int y) {
		return box.contains(x, y);
	}

	@Override
	public void paint(Graphics2D g) {
		g.fill(this.box);
	}
	
	public int getSignal(){
		return signal;
	}

}
