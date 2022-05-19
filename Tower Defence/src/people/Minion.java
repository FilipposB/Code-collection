package people;

import world.Coordinates;

public class Minion {
	private double x;
	private double y;
	private double xSpeed;
	private double ySpeed;
	private double speed;
	private Coordinates cord;
	private int cordNum;
	private boolean arrived;
	private double health;
	
	public Minion(Coordinates start,double spe,double hp){
		x=start.getX();
		y=start.getY();
		speed=spe;
		cordNum=0;
		health=hp;
		arrived=true;
	}
	
	public int getX(){
		return (int) x;
	}
	
	public int getY(){
		return (int) y;
	}
	
	public boolean getArrived(){
		return arrived;
	}
	
	public int getCoordNumber(){
		return cordNum;
	}
	
	public boolean isAlive(){
		if (health<=0)return false;
		return true;
	}
	
	public void doDamage(double dmg){
		health-=dmg;
	}
	
	public void setCord(Coordinates cor){
		double xSp = 0;
		double ySp = 0;
		double x = this.x;
		double y = this.y;
		double maxDif;
		
		cord=cor;
		cordNum++;
		arrived=false;
		
		xSp = Math.abs(x - cord.getX());
		ySp = Math.abs(y - cord.getY());

			xSpeed = (xSp);
			ySpeed = (ySp );
		
		 double pythagoras = ((xSpeed * xSpeed) + (ySpeed * ySpeed));
		 if (pythagoras > (speed * speed))
		 {
		     double magnitude = Math.sqrt(pythagoras);
		     double multiplier = speed / magnitude;
		     xSpeed *= multiplier;
		     ySpeed *= multiplier;
		 }

	}
	
	public void update(double delta){
		if (!arrived){
			if (Math.abs(x-cord.getX())<=speed*delta){
				x=cord.getX();
			}
			else{
				if (x>cord.getX()){
					x-=xSpeed*delta*speed;
				}
				else if (x<cord.getX()){
					x+=xSpeed*delta*speed;
				}
			}
			if (Math.abs(y-cord.getY())<=speed*delta)y=cord.getY();
			else{
				if (y>cord.getY()){
					y-=ySpeed*delta*speed;
				}
				else if (y<cord.getY()){
					y+=ySpeed*delta*speed;
				}
				
			}
			if (x==cord.getX()&&y==cord.getY())arrived=true;
		}
	}
}
