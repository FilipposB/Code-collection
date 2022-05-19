package hero;

public class Heart {
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean sprite;
	
	public Heart(int xP,int yP,int wid,int hei,boolean spr){
		x=xP;
		y=yP;
		width = wid;
		height = hei;
		sprite=spr;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getSprite(){
		if (sprite){
			return 1;
		}
		else{
			return 2;
		}
	}
	
	public void setSprite(){
		sprite=!sprite;
	}
}
