package ability;

import champion.Champion;
import map.Map;
import things.Bullet;

public class RangedAuto extends Ability{

	public RangedAuto(double dam, double rang, double coold,double spe,double handi, int mana) {
		super(dam, rang, coold,spe,handi, mana);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAbility(Champion champion,Map map) {
		// TODO Auto-generated method stub
		champion.handicap=handicap;
		map.bullet.add(new Bullet((int)champion.x,(int)champion.y,20,champion.angle,speed,range,damage));
	}
	
	
}
