package people;

public class Person {
	protected double xPos;
	protected double yPos;
	protected double oldX;
	protected double oldY;
	private double xHb;
	private double yHb;
	protected double xVel;
	protected double yVel;
	private int xSpawn;
	private int ySpawn;
	private double healthPoints;
	protected double moveSpeed;
	private double attackSpeed;
	protected int sprite;
	private int spriteWidth;
	private int spriteHeight;
	private int hitBoxWidth;
	private int hitBoxHeight;
	boolean flag=true;
	private double animationTimer=0;
	private boolean left;
	
	public Person(int xSp,int ySp,double hp,double moveS,double attackS,int spr){
		xPos=xSp;
		yPos=ySp;
		oldX=xSp;
		oldY=ySp;
		xSpawn=xSp;
		ySpawn=ySp;
		healthPoints=hp;
		moveSpeed=moveS;
		attackSpeed=attackS;
		sprite=spr;
	}
	
	
	public Person(int xSp, int ySp,int xH,int yH,int sprWid,int sprHei,int hitBoxWi,int hitBoxHe,double speed,boolean lef,int spr){
		xPos=xSp;
		yPos=ySp;
		oldX=xSp;
		oldY=ySp;
		xHb=xH;
		yHb=yH;
		xSpawn=xSp;
		ySpawn=ySp;
		spriteWidth=sprWid;
		spriteHeight=sprHei;
		moveSpeed=speed;
		hitBoxWidth=hitBoxWi;
		hitBoxHeight=hitBoxHe;
		left=lef;
		sprite = spr;
	}
	
	public int getSprite(){
		return sprite;
	}
	
	public int getSpriteWidth(){
		return spriteWidth;
	}
	
	public int getSpriteHeight(){
		return spriteHeight;
	}
	
	public int getXPos(){
		return (int) xPos;
	}

	public int getYPos(){
		return (int) yPos;
	}
	
	public int getXHb(){
		return (int) (xPos+xHb);
	}
	
	public int getYHb(){
		return (int) (yPos+yHb);
	}
	
	public int getHitBoxWidth(){
		return hitBoxWidth;
	}
	
	public int getHitBoxHeight(){
		return hitBoxHeight;
	}
	
	public double getOldX(){
		return  oldX;
	}
	
	public double getOldY(){
		return  oldY;
	}
	
	public double getAnimationTimer(){
		return animationTimer;
	}
	
	public boolean getMotion(){
		return xVel!=0;
	}
	public boolean getLeft(){
		return left;
	}
	
	public void setSpeed(double speed){
		moveSpeed=speed;
	}
	
	public void setYPos(double pos){
		yPos=pos;
	}
	
	public void setXPos(double pos){
		xPos=pos;
	}
	
	public void setXVel(double vel){
		xVel=vel;
	}
	
	public void setYVel(double vel){
		yVel=vel;
	}
	
	public void setSprite(int x){
		sprite=x;
	}
	
	public void setLeft(boolean x){
		left=x;
	}
	
	public void setAnimationTimer(double tim){
		animationTimer=tim;
	}
	
	
	protected void gravity(double delta,boolean touch){
		if (touch) {
			if (yVel!=0){
				if (xVel>0)xVel=xVel-(xVel*50/100);
				if (xVel<0)xVel=xVel+(-xVel*50/100);
			}
			yVel = 0;
		} else {
			yVel += 0.8*delta;
		}
	}
	
	protected void slowDown(double delta,double maxSp,boolean touch){
		double drag;
		if (touch){
			drag=moveSpeed*55/100;
		}
		else{
			drag=moveSpeed/10;
		}
		if (xVel > 0){
			xVel -= drag;
			if (xVel>maxSp){
				xVel=maxSp;
			}
		}
		else if (xVel < 0){
			xVel += drag;
			if (xVel<-maxSp){
				xVel=-maxSp;
			}
		}
		if (Math.abs(xVel) < (moveSpeed*45/100))
			xVel = 0;
	}
	
	protected void bounch(boolean touch){
		//if (touch)yVel-=Math.abs(yVel)/2;
	}
}
