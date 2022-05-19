package gooz.engine;

import java.awt.Color;
import java.util.ArrayList;

import gooz.project.Cols;

public class SelectedColors {
	
	public Cols colors = new Cols();
	public Color primary;
	public Color secondary;
	public Color test;
	public int[] RGB = new int[3];
	
	public ArrayList<Color> colArc = new ArrayList<Color>();
	
	public SelectedColors(){
		primary = Color.decode("0x"+"000000");
		secondary = Color.decode("0x"+"FFFFFF");
		test = primary;
		RGB[0]=RGB[1]=RGB[2]=0;
	}
	
	public void swapColors(){
		Color temp = primary;
		primary = secondary;
		secondary = temp;
		test = primary;
	}
	
	public void changeColor(boolean x,int pos){
		if (x){
			primary = colors.col.get(pos);
		}
		else{
			secondary = colors.col.get(pos);
		}
	}
	
}
