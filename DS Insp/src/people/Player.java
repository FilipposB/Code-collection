package people;

public class Player extends Person {
	public boolean[] move = { false, false, false,false };
	public boolean jump = false;
	private final double maxSpeed = 8;
	private boolean punchAnimation = false;

	public Player(int xP, int yP, int xH, int yH, int spWid, int spHei, int widHb, int heiHb, double speed ,boolean left,int sprite) {
		super(xP, yP, xH, yH, spWid, spHei, widHb, heiHb, speed ,left, sprite);
		
	}

	public void update(double delta,boolean touch) {
		// Old Position
		oldX = xPos;
		oldY = yPos;
		gravity(delta, touch);
		move(delta,touch);
		xPos += delta * xVel;
		yPos += delta * yVel;
	}
	
	public void setPunchAnimation(boolean x){
		punchAnimation=x;
	}
	
	public boolean getPunchAnimation(){
		return punchAnimation;
	}
	
	private void move(double delta,boolean touch) {
		if (move[3]&&!punchAnimation){
			punchAnimation=true;
			sprite=5;
			setAnimationTimer(0);
		}
		else if (!punchAnimation){
		if (move[2] && touch){
			yVel -= 15;
			jump=true;
		}
		if (move[0]&&touch) {
			xVel -= moveSpeed;
		}
		else if (move[0])
			xVel -= (moveSpeed*80)/100;;
		if (move[1]&&touch) {
			xVel += moveSpeed;
		}
		else if (move[1]){
			xVel+=(moveSpeed*80)/100;
		}
		if (move[2]&&yVel>-9.7&&jump){
			yVel-=5;
			jump=false;
		}
		bounch(touch);
		}
		slowDown(delta, maxSpeed,touch);

	}
	
	public void animate(){
		sprite++;
		if (sprite>4&&!punchAnimation)sprite=0;
		if (sprite>8){
			sprite=0;
			punchAnimation=false;
		}
		}

}
