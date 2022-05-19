package level;

public class Floor {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private int sprite;
	private int spriteWidth;
	private int spriteHeight;
	private boolean pass;
	
	public Floor(int newX,int newY,int wid,int hei,int spr,int sprW,int sprH,boolean pas){
		setX(newX);
		setY(newY);
		setWidth(wid);
		setHeight(hei);
		sprite = spr;
		spriteWidth=sprW;
		spriteHeight=sprH;
		pass=pas;
	}

	public int getX() {
		return x;
	}
	
	public int getSprWidth() {
		return spriteWidth;
	}
	public int getSprHeight() {
		return spriteHeight;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getSprite(){
		return sprite;
	}
	
	public boolean getPass(){
		return pass;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
