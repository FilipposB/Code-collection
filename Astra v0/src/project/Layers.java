package project;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Layers {
	private int x;
	private int y;
	private int width;
	private int height;
	private int[] pixels;
	private boolean lock;
	private boolean visible;
	private BufferedImage image;
	
	protected Layers(int xN,int yN,int wid,int hei,boolean lockN,boolean vis,Color col){
		x=xN;
		y=yN;
		width=wid;
		height=hei;
		lock=lockN;
		visible=vis;
		pixels = new int[width*height];
		fillLayer(col);
	}
	
	protected int[] getPixels(){
		return pixels;
	}	
	protected boolean isVisible(){
		return visible;
	}
	
	protected boolean isLocked(){
		return lock;
	}
	
	protected Image getImage(){
		return image;
	}
	
	protected int getX(){
		return x;
	}
	
	protected int getY(){
		return y;
		
	}
	
	protected int getWidth(){
		return width;
	}
	
	protected int getHeight(){
		return height;
	}
	
	private void fillLayer(Color col){
		for (int i=0;i<height*width;i++){
			pixels[i] = (255 << 24) | (col.getRed() << 16) | (col.getBlue() << 8) | col.getGreen();
		}
	}
}
