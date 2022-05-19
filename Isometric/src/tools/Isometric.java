package tools;

import java.awt.Point;
import java.awt.Polygon;

public abstract class Isometric {
	
	//2d point to isometric
	public  Point point2DToIso(Point pt){
		Point tempPt=new Point(0,0);
		tempPt.x=pt.x-pt.y;
		tempPt.y=(pt.x+pt.y)/2;
		return(tempPt);
	}
	
	//2d polygon to isometric
	public static  Polygon polygon2DToIso(Polygon pol,int x,int y){
		Polygon nPoly = new Polygon();
		for (int i=0;i<pol.npoints;i++){
			nPoly.addPoint(pol.xpoints[i]-pol.ypoints[i]+x, (pol.xpoints[i]+pol.ypoints[i])/2+y);

		}
		return nPoly;
	}
	
}
