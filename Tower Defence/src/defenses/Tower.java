package defenses;

public class Tower {
	private int x;
	private int y;
	private double range;
	private double damage;
	private int target;
	private double fireRate;
	private double timePassed;
	
	public Tower(int x,int y,double rang,double dmg,double fiRa){
		this.x=x;
		this.y=y;
		range=rang;
		damage=dmg;
		fireRate=fiRa;
		timePassed=fiRa;
		target=-1;
	}
	
	public int getX(){
		return (int) x;
	}
	
	public int getY(){
		return (int) y;
	}
	
	public double getRange(){
		return  range;
	}
	
	public double getDamage(){
		return damage;
	}
	
	public int getTarget(){
		return target;
	}
	
	public void setTarget(int tar){
		target=tar;
	}
	
	public boolean canShoot(){
		if (timePassed>=fireRate){
			timePassed=0;
			return true;
		}
		return false;
	}
	
	public void update(double delta){
		timePassed+=delta;
	}
	
	
}
