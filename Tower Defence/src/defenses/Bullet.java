package defenses;

public class Bullet {
	private double x;
	private double y;
	private int targetX;
	private int targetY;
	private double speed;
	private double xSpeed;
	private double ySpeed;
	private int target;
	private boolean arrived;
	private double damage;

	public Bullet(int xNew, int yNew,int tar, double speedNew,double dmg) {
		x = xNew;
		y = yNew;
		speed = speedNew;
		target = tar;
		arrived=false;
		damage=dmg;
	}

	// Getters and Setters
	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public int getTarget() {
		return target;
	}
	
	public double getDamage(){
		return damage;
	}
	
	public boolean hasArrived(){
		return arrived;
	}

	public void setTargetPosition(int xN, int yN) {
		double xSp = 0;
		double ySp = 0;
		double x = this.x;
		double y = this.y;
		double maxDif;

		targetX = xN;
		targetY = yN;

		xSp = Math.abs(x - targetX);
		ySp = Math.abs(y - targetY);
		maxDif = xSp + ySp;

		if (ySp != 0)
			xSpeed = (xSp / maxDif);
		if (xSp != 0)
			ySpeed = (ySp / maxDif);

	}

	public void update(double delta) {
			if (Math.abs(x - targetX) <= speed * delta)
				x = targetX;
			else {
				if (x > targetX) {
					x -= xSpeed * delta * speed;
				} else if (x< targetX) {
					x += xSpeed * delta * speed;
				}
			}
			if (Math.abs(y - targetY) <= speed * delta)
				y = targetY;
			else {
				if (y > targetY) {
					y -= ySpeed * delta * speed;
				} else if (y < targetY) {
					y += ySpeed * delta * speed;
				}

			}
			if (x==targetX&&y==targetY)arrived=true;

		}
}