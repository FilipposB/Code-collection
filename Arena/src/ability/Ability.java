package ability;

import champion.Champion;
import map.Map;

public abstract class Ability {
	public double damage;
	public double range;
	public double speed;
	public double cooldown;
	public double cooldownTimer;
	public double handicap;
	public int manaCost;
	
	public Ability(double dam,double rang,double coold,double spe,double handi,int mana){
		damage=dam;
		range=rang;
		cooldown=coold;
		speed=spe;
		handicap=handi;
		manaCost=mana;
	}
	
	public boolean useAbility(boolean use){
		if (cooldown>cooldownTimer)cooldownTimer++;
		if (use&&cooldown<=cooldownTimer){
			cooldownTimer=0;
			return true;
		}
		return false;
	}
	
	public abstract void doAbility(Champion champion,Map map);
	
}
