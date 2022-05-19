package gui.widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import gui.display.Display;

public class ProjectButton extends ButtonWidget{
	private Font font = new Font("Verdana", Font.BOLD, 16);
	
	private static int projectID;
	private int ID;
	
	private String name;

	public ProjectButton(Display dis, int x,int y,int wid,int hei,String nam){
		super(new Rectangle(x,y,wid,hei),dis);
		// TODO Auto-generated constructor stub
		this.ID=projectID++;
		this.name=nam;
	}

	@Override
	public boolean isClicked(int x, int y) {
		// TODO Auto-generated method stub
		return box.contains(x, y);
	}

	@Override
	public void paint(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setFont(font);
		g.setColor(Color.white);
		g.fill(box);
		g.setColor(Color.BLACK);
		g.drawString(name, box.x, box.y+16);
	}
	
	public int getID(){
		return ID;
	}

}
