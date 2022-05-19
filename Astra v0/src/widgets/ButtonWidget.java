package widgets;

import java.awt.Rectangle;

import display.Display;

public abstract class ButtonWidget extends Widget{
	
	public ButtonWidget(Rectangle bx,Display dis) {
		super(bx,dis);
	}

	public abstract boolean isClicked(int x,int y);
}
