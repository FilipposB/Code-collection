package weapon;

import java.util.ArrayList;
import java.util.List;

public class GunSelect {

	List<Gun> guns = new ArrayList<Gun>();
	
	public GunSelect() {
		// (Damage,Width,Height,FireRate,Magazine,BulletSpeed,Sprite,DmgAmp)
				// Default Weapon
				guns.add(new Gun(5, 5, 5, 35, -1, 12, 0, 0));
				// Sniper
				guns.add(new Gun(15, 8, 3, 75, 3, 36, 24, 1.4));
				// Gattling Gun
				guns.add(new Gun(1.4, 4, 4, 1, 100, 9, 14, 0));
				// Blob Gun
				guns.add(new Gun(5, 15, 15, 60, 3, 2, 34, 0.5));
				// Shot Gun
				guns.add(new Gun(55, 10, 5, 90, 2, 25, 44, -10));
				// Laser Gun
				guns.add(new Gun(20, 20, 5, 65, 5, 30, 54, -0.10));

	}

	public Gun getGun(int x) {
		return guns.get(x);
	}
	
	public int getGunSize(){
		return guns.size();
	}
}
