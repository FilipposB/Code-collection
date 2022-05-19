package weapon;

public class Gun {
	private double bulletDamage;
	private int bulletWidth;
	private int bulletHeight;
	private int fireRate;
	private int magazine;
	private double speed;
	private int sprite;
	private double dmgAmp;
	
	public Gun(double dmg,int wid, int hei,int fir,int mag,double spee,int spr,double dmAmp){
		setBulletDamage(dmg);
		setBulletWidth(wid);
		setBulletHeight(hei);
		setFireRate(fir);
		setMagazine(mag);
		setSpeed(spee);
		setSprite(spr);
		setDmgAmp(dmAmp);
	}

	public double getBulletDamage() {
		return bulletDamage;
	}

	public void setBulletDamage(double bulletDamage) {
		this.bulletDamage = bulletDamage;
	}

	public int getBulletWidth() {
		return bulletWidth;
	}

	public void setBulletWidth(int bulletWidth) {
		this.bulletWidth = bulletWidth;
	}

	public int getBulletHeight() {
		return bulletHeight;
	}

	public void setBulletHeight(int bulletHeight) {
		this.bulletHeight = bulletHeight;
	}

	public int getFireRate() {
		return fireRate;
	}

	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}

	public int getMagazine() {
		return magazine;
	}

	public void setMagazine(int magazine) {
		this.magazine = magazine;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getSprite() {
		return sprite;
	}

	public void setSprite(int sprite) {
		this.sprite = sprite;
	}

	public double getDmgAmp() {
		return dmgAmp;
	}

	public void setDmgAmp(double dmgAmp) {
		this.dmgAmp = dmgAmp;
	}
}
