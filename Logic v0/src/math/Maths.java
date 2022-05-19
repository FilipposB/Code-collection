package math;

public class Maths {

	public static boolean clickInCircle(int x1,int x0,int y1,int y0,int r){
		return Math.sqrt((x1-x0)*(x1-x0) + (y1-y0)*(y1-y0)) < r;
	}
}
