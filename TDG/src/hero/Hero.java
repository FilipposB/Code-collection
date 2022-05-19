package hero;


public class Hero {

	private double x;;
	private double y;
	private int sp;
	private double yVel;
	private double xVel;
	private int width;
	private int height;
	private int widHb;
	private int heiHb;
	private double oldX;
	private double oldY;
	private double health;
	private double maxHealth;
	private boolean immune;
	private int direction;
	private int spriteDisplay;
	private int xHb;
	private boolean[] move = { false, false, false, false };
	
	public Hero(double x, double y, int sp, int wid, int hei, int wH, int hH, int hp,int xH) {
		this.x = x;
		this.y = y;
		this.sp = sp;
		spriteDisplay=sp;
		widHb = wH;
		heiHb = hH;
		width = wid;
		height = hei;
		yVel = 0;
		xVel = 0;
		oldX = 0;
		oldY = 0;
		health = hp;
		maxHealth = hp;
		immune = false;
		direction=3;
		xHb=xH;
	}

	public void update() {
		// Old Position
		oldX = x;
		oldY = y;
		move();
		// New Position
		y += yVel;
		x += xVel;
		checkBoundries();
	}

	// Getters
	
	public int getXHitBox(){
		return xHb;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public int getSprite() {
		return sp;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getOldX() {
		return (int) oldX;
	}

	public int getOldY() {
		return (int) oldY;
	}

	public int getYVel() {
		return (int) yVel;
	}

	public double getHealth() {
		return health;
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public boolean getImmune() {
		return immune;
	}


	public boolean getMotion() {
		return (xVel != 0)||(yVel!=0);
	}

   public int getDirection(){
	   return direction;
	   
   }

	public int getSpriteDisplay() {
		return spriteDisplay;
	}

	public int getWidthHitBox() {
		return widHb;
	}

	public int getHeightHitBox() {
		return heiHb;
	}
	
	

	// Setters
	
	public void setMove(int x,boolean on){
		if (move[x]==false&&on){
			direction=x+1;
			if (direction==4)direction=0;
			if (direction==1||direction==2){
				spriteDisplay=0;
			}
			else if (direction==3){
				spriteDisplay=8;
			}else{
				spriteDisplay=4;
			}
		}
		move[x]=on;
		
	}

	public void setY(int yNew) {
		y = yNew;
	}

	public void setX(int xNew) {
		x = xNew;
	}

	public void setXVel(int x) {
		xVel = x;
	}

	public void setYVel(int y) {
		yVel = y;
	}

	public void setHealth(double x) {
		health = x;
		if (health > maxHealth)
			health = maxHealth;
	}

	public void setMaxHealth(int x) {
		maxHealth = x;
	}

	public void setImmune(boolean x) {
		immune = x;
	}

	public void setSprite(int x) {
		sp = x;
		if (sp >= spriteDisplay + 4 ) {
			sp = spriteDisplay;
		}
	}
	


	// Move
	private void move() {
		if (move[0])
			xVel -= 1.8;
		if (move[1])
			xVel += 1.8;
		if (move[2])
			yVel -= 1.8;
		if (move[3])
			yVel += 1.8;
		if (xVel > 0)
			xVel -= 0.60 + 10 * Math.abs(xVel) / 100;
		else if (xVel < 0)
			xVel += 0.60 + 10 * Math.abs(xVel) / 100;
		if (yVel > 0)
			yVel -= 0.60 + 10 * Math.abs(yVel) / 100;
		else if (yVel < 0)
			yVel += 0.60 + 10 * Math.abs(yVel) / 100;
		if (Math.abs(xVel) < 0.60)
			xVel = 0;
		if (Math.abs(yVel) < 0.60)
			yVel = 0;
	}

	// Checks Imaginary Walls
	private void checkBoundries() {
		if (x+xHb <0) {
			x =-xHb;
		} else if (x+widHb+xHb > 1280) {
			x = 1280-widHb-xHb;
		}
		if (y < 0) {
			y = 0;
		} else if (y+height > 720) {
			y = 720-height;
		}

	}

	// Check Walls
	public void hitWalls() {
		x = oldX;
		y = oldY;
	}

}
