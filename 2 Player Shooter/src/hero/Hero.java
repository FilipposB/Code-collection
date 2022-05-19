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
	private static double gravity = 0.6;
	private double oldX;
	private double oldY;
	private double health;
	private double maxHealth;
	private boolean immune;
	private boolean left;
	private int magazine;
	private int spriteDisplay;
	private int selWeapon;
	public boolean[] move = { false, false, false, false };
	private boolean red;
	
	public Hero(double x, double y, int sp, int wid, int hei, int wH, int hH, int hp, int weapon,int weapSpr,int mag, boolean color) {
		this.x = x;
		this.y = y;
		this.sp = sp;
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
		left = true;
		red = color;
		magazine=mag;
		setWeapon(weapon,weapSpr);
	}

	public void update(boolean collidingBelow) {
		// Old Position
		oldX = x;
		oldY = y;
		gravity(collidingBelow);
		move(collidingBelow);
		// New Position
		y += yVel;
		x += xVel;
		checkBoundries();
	}

	// Getters

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

	public boolean getLeft() {
		return left;
	}

	public boolean getMotion() {
		return xVel != 0;
	}


	public int getMagazine() {
		return magazine;
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
	
	public int getSelWeapon(){
		return selWeapon;
	}
	

	// Setters

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

	public void setRedSprite(int x) {
		sp = x;
		if (sp >= spriteDisplay + 5) {
			sp = spriteDisplay;
		}
	}

	public void setBlueSprite(int x) {

		sp = x;
		if (sp >= spriteDisplay + 10) {
			sp = spriteDisplay + 5;
		}
	}

	public void setLeft(boolean x) {
		left = x;
	}

	public void setMagazine(int x) {
		magazine = x;
		if (magazine == 0)
			setWeapon(0, 0);
		else if (magazine == -2)
			magazine = -1;
	}

	public void setWeapon(int x,int sprDis) {
		int paste = sp - spriteDisplay;
		spriteDisplay = sprDis;
		if (red)
			sp = spriteDisplay + paste;
		else
			sp = spriteDisplay + paste;
		selWeapon=x;
	}

	// Gravity
	private void gravity(boolean touch) {
		if (touch) {
			yVel = 0;
		} else {
			yVel += gravity;
			if (yVel > 50)
				yVel = 50;
		}
	}

	// Move
	private void move(boolean touch) {
		double retarding=0;
		if (move[3]){
			if (xVel!=0)retarding=0.3;
		}
		if (move[0])
			xVel -= 0.90-retarding;
		if (move[1])
			xVel += 0.90-retarding;
		if (move[0])
			left = false;
		if (move[1])
			left = true;
		if (move[2] && touch)
			yVel -= 12.5;
		if (xVel > 0)
			xVel -= 0.30 + 10 * Math.abs(xVel) / 100;
		else if (xVel < 0)
			xVel += 0.30 + 10 * Math.abs(xVel) / 100;
		if (Math.abs(xVel) < 0.30)
			xVel = 0;
	}

	// Checks Imaginary Walls
	private void checkBoundries() {
		if (x < 0) {
			x = 0;
		} else if (x > 1180) {
			x = 1180;
		}

	}

	// Check Walls
	public void hitWalls() {
		x = oldX;
		y = oldY;
	}

}
