package champion;

import java.awt.Graphics2D;

import ability.Ability;
import game.ClientGame;
import game.ServerGame;
import things.Solid;

public abstract class Champion extends Solid{
	
	
	//Move Directions
	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	
	//Attack
	public boolean auto;
	
	//Cooldown
	public double handicap;
	
	//Auto attack
	public Ability autoAttack;
	public double autoCooldown;
	public double autoDamage;
	
	

	
	public Champion(int x,int y,int size,double speed){
		super(x,y,size,speed,90);
	}
	
	public abstract void paint(ClientGame game,Graphics2D g);
	public abstract void tick(ClientGame game);

	public void tick(ServerGame game) {
		int handic=1;
		if (up) {
			y -= speed / handic;
		}

		if (down) {
			y += speed / handic;
		}
		if (left) {
			x -= speed / handic;
		}
		if (right) {
			x += speed / handic;
		}
		
		game.map.isInBounds(this);

	}
	
}
