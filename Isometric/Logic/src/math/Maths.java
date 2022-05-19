package math;

import java.awt.Rectangle;

public class Maths {

	public static boolean clickInCircle(int x1,int x0,int y1,int y0,int r){
		return Math.sqrt((x1-x0)*(x1-x0) + (y1-y0)*(y1-y0)) < r;
	}
	
	public static boolean clickInRectangle(int x1,int x0,int y1,int y0,int width,int height){
		Rectangle rec = new Rectangle();
		rec.setBounds(x0, y0, width, height);		
		return rec.contains(x1, y1);
	}
	
	public static boolean rectangleAreConnected(int x1,int y1,int width1,int height1,int x0,int y0,int width0,int height0){
		Rectangle rec0 = new Rectangle();
		rec0.setBounds(x0, y0, width0, height0);	
		return rec0.intersects(x1, y1, width1, height1);
	}
}
