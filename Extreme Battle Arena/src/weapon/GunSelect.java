package weapon;

public class GunSelect {

	//Guns
	Gun guns[] ;
	private static int gunAm=6;
	
	public GunSelect() {
		guns=new Gun[gunAm];
		// (Damage,Width,Height,FireRate,Magazine,BulletSpeed,Sprite,DmgAmp)
				// Default Weapon
				guns[0]=new Gun(5, 5, 5, 35, -1, 12, 0, 0);
				// Sniper
				guns[1]=new Gun(15, 8, 3, 75, 3, 72, 24, 1.4);
				// Gattling Gun
				guns[2]=new Gun(1.4, 4, 4, 1, 120, 9, 14, 0);
				// Blob Gun
				guns[3]=new Gun(5, 15, 15, 60, 3, 2, 34, 0.5);
				// Shot Gun
				guns[4]=new Gun(55, 10, 5, 90, 2, 25, 44, -10);
				// Laser Gun
				guns[5]=new Gun(20, 20, 5, 50, 5, 30, 54, -0.10);

	}

	public Gun getGun(int x) {
		return guns[x];
	}
	
	public int getGunSize(){
		return gunAm;
	}
}
