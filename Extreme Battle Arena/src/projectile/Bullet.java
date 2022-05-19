package projectile;

public class Bullet {

	private double x;
	private int oldX;
	private int y;
	private int width;
	private int height;
	private boolean direction;
	private boolean redShooter;
	private double dmg;
	private double speed;
	private double dmgAmp;

	public Bullet(int xt, int yt, boolean dir, int wid, int hei, boolean red, double dm, double spe, double dmAmp) {
		x = xt;
		oldX=(int) x;
		y = yt;
		direction = dir;
		width = wid;
		height = hei;
		redShooter = red;
		dmg = dm;
		speed = spe;
		dmgAmp = dmAmp;
	}
	
	public int getOldX(){
		return  oldX;
	}

	public int getX() {
		return (int)x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public double getDmg() {
		return dmg;
	}
	
	public int getSpeed(){
		return (int)speed;
	}
	
	public boolean getDirection(){
		return direction;
	}

	public void bulletUpdate(double delta) {
		oldX=(int) x;
		if (direction) {
			x += speed*delta;
		} else {
			x -= speed*delta;
		}
		dmg+=dmgAmp*delta;
		if (dmgAmp==0.5)
			width+= width*delta*dmgAmp/10;
		if (dmg<0)dmg=0;
		
	}

	public boolean getShooter() {
		return redShooter;
	}

}
