package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import hero.Hero;
import level.Floor;
import level.LevelChooser;
import objects.LootCrate;
import projectile.Bullet;
import sound.Sound;
import weapon.GunSelect;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private static int width = 1200;
	private static int height = width / 16 * 9;
	private static String title = "2P Shooter";

	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	
	private final int TARGET_FPS = 60;
	private final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

	// Hero Class
	private Hero redHero;
	private Hero blueHero;

	// Floor Class
	private Floor floor;
	private List<Integer> colFloor = new ArrayList<Integer>();

	// Level Chooser
	private LevelChooser level = new LevelChooser();

	// LootCrate
	private List<LootCrate> lootBoxes = new ArrayList<LootCrate>();

	// Timer
	private double tickTimerRed = 0;
	private double tickTimerBlue = 0;
	private double tickTimerLoot = 0;
	private double tickTimerHealth = 0;
	private double tickTimerRedBullets = 0;
	private double tickTimerBlueBullets = 0;

	// Bullets
	private List<Bullet> bullets = new ArrayList<Bullet>();

	// Sound
	private Sound sound = new Sound();

	public Game() {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		frame = new JFrame();
		setFocusable(true);
		requestFocusInWindow(false);
		setFocusTraversalKeysEnabled(true);
		level.level0(true);
		redHero = level.getHero(0);
		blueHero = level.getHero(1);

		addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_A) {
					redHero.move[0] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					redHero.move[1] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_W) {
					redHero.move[2] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_T) {
					if (!redHero.move[3] && tickTimerRedBullets == 0) {
						shootProjectile(redHero, true);
						tickTimerRedBullets++;
					}

					redHero.move[3] = true;

				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					blueHero.move[0] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					blueHero.move[1] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					blueHero.move[2] = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!blueHero.move[3] && tickTimerBlueBullets == 0) {
						shootProjectile(blueHero, false);
						tickTimerBlueBullets++;
					}

					blueHero.move[3] = true;

				}
				
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_A) {
					redHero.move[0] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					redHero.move[1] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_W) {
					redHero.move[2] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_T) {
					redHero.move[3] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_L) {
					level.level0(false);
				}

				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					blueHero.move[0] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					blueHero.move[1] = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					blueHero.move[2] = false;

				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					blueHero.move[3] = false;
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});

	}

	private synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	private synchronized void stop() {
		running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void run() {
		long lastTime = System.nanoTime();
		long lastRender = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			long updateLength = now - lastTime;
			lastTime = now;
			double delta = updateLength / ((double) OPTIMAL_TIME);

			lastRender += updateLength;
			frames++;

			if (lastRender >= 1000000000) {
				frame.setTitle("Game FPS: " + frames);
				lastRender = 0;
				frames = 0;
			}

			tick(delta);

			render();

		}
		stop();
	}

	private void tick(double delta) {
		// Check If Game Over
		gameOverCheck();
		gameOverCheckBlue();

		// Hero Checks
		touchFloorRed();
		redHero.update(touchGroundRed(),delta);
		animateHeroRed(delta);

		touchFloorBlue();
		blueHero.update(touchGroundBlue(),delta);
		animateHeroBlue(delta);
		
		shootWeapons(delta);

		// Bullet Check
		bulletInFrame();
		bulletUpdate(delta);

		// LootBox Drop
		lootBoxSpawn(delta);
		lootBoxInFrame();
		touchFloorBox();
		lootBoxUpdate(delta);

	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		double scale=1;
		g.scale(scale,scale);
		int rX=redHero.getX();
		int rY=redHero.getY();
		int bX=blueHero.getX();
		int bY=blueHero.getY();


		// Clear Screen
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.GRAY.brighter());
		g.drawLine(0, 0, getWidth(), 0);

		// Display Floors
		g.setColor(Color.black);
		for (int i = 0; i < level.getFloorSize(); i++) {
			floor = level.getFloor(i);
			for (int x = 0; x < floor.getWidth() / floor.getSprWidth(); x++) {
				for (int y = 0; y < floor.getHeight() / floor.getSprHeight(); y++) {
					g.drawImage(level.getSprite(floor.getSprite()), floor.getX() + x * floor.getSprWidth(),
							floor.getY() + y * floor.getSprHeight(), floor.getSprWidth(), floor.getSprHeight(), null);
				}
			}

		}
		

		// Display LootBoxes
		for (int i = 0; i < lootBoxes.size(); i++) {
			g.drawImage(level.getSprite(lootBoxes.get(i).getSprite()), lootBoxes.get(i).getX(), lootBoxes.get(i).getY(),
					32, 32, null);
		}

		// Display Bullets
		for (int i = 0; i < bullets.size(); i++) {
			if (bullets.get(i).getShooter()) {
				g.setColor(Color.RED);

			} else {
				g.setColor(Color.BLUE);

			}
			g.fillRect(bullets.get(i).getX(), bullets.get(i).getY(), bullets.get(i).getWidth(),
					bullets.get(i).getHeight());
		}

		// Display Health Bars
		g.setColor(Color.RED);
		g.fillRect(redHero.getX()-2, redHero.getY() - 10, (int) (redHero.getMaxHealth() / 5), 5);
		g.fillRect(blueHero.getX()-2, blueHero.getY() - 10, (int) (blueHero.getMaxHealth() / 5), 5);

		g.setColor(Color.GREEN);
		g.fillRect(redHero.getX()-2, redHero.getY() - 10, (int) (redHero.getHealth() / 5), 5);
		g.fillRect(blueHero.getX()-2, blueHero.getY() - 10, (int) (blueHero.getHealth() / 5), 5);
		
		//Reload Animation
		g.setColor(Color.GREEN);
		g.fillArc(redHero.getX()-2, redHero.getY() - 10, (int) (redHero.getHealth() / 5), 5,5,5);
		
		// Display Sprite
				if (!redHero.getLeft()) {
					g.drawImage(level.getSprite(redHero.getSprite()), rX + redHero.getWidthHitBox(), rY,
							-redHero.getWidth(), redHero.getHeight(), null);
				} else {
					g.drawImage(level.getSprite(redHero.getSprite()), rX, rY, redHero.getWidth(),
							redHero.getHeight(), null);
				}

				if (!blueHero.getLeft()) {
					g.drawImage(level.getSprite(blueHero.getSprite()), bX + blueHero.getWidthHitBox(), bY,
							-blueHero.getWidth(), blueHero.getHeight(), null);
				} else {
					g.drawImage(level.getSprite(blueHero.getSprite()), bX, bY, blueHero.getWidth(),
							blueHero.getHeight(), null);
				}
				
		g.dispose();
		bs.show();
		
		

	}
	

	public static void main(String[] args) {

		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.frame.getKeyListeners();
		game.frame.setFocusable(true);
		game.frame.requestFocusInWindow();

		game.start();
	}

	private void touchFloorRed() {
		colFloor.clear();
		Rectangle heroRect = new Rectangle(redHero.getX(), redHero.getY(), redHero.getWidthHitBox(), redHero.getHeightHitBox());
		for (int i = 0; i < level.getFloorSize(); i++) {
			floor = level.getFloor(i);
			Rectangle floorRect = new Rectangle(floor.getX(), floor.getY(), floor.getWidth(), floor.getHeight());
			if (heroRect.intersects(floorRect)) {
				colFloor.add(i);
			}
		}
	}

	private boolean touchGroundRed() {
		Boolean res = false;
		for (int i = 0; i < colFloor.size(); i++) {
			floor = level.getFloor(colFloor.get(i));
			if ((floor.getY() >= redHero.getOldY() + redHero.getHeight() - 1)) {
				redHero.setY(floor.getY() - redHero.getHeight() + 1);
				res = true;
			} else if ((floor.getY() + floor.getHeight() <= redHero.getOldY()) && !floor.getPass()) {
				redHero.setY(floor.getY() + floor.getHeight());
				redHero.setYVel(0);
			} else if (!floor.getPass()) {
				redHero.setX(redHero.getOldX());
				redHero.setXVel(0);

			}
		}

		return res;
	}

	private void animateHeroRed(double delta) {
		
		if (redHero.getMotion() && (delta>12 || tickTimerRed>4.5)) {
			redHero.setRedSprite(redHero.getSprite() + 1);
			tickTimerRed=0;
		} else if (!redHero.getMotion()){
			redHero.setRedSprite(redHero.getSpriteDisplay());
		}
		else{
			tickTimerRed+=delta;
		}
	}

	private void gameOverCheck() {
		if (redHero.getHealth() <= 0) {
			stop();
		}
	}

	private void touchFloorBlue() {
		colFloor.clear();
		Rectangle heroRect = new Rectangle(blueHero.getX(), blueHero.getY(), blueHero.getWidthHitBox(), blueHero.getHeightHitBox());
		for (int i = 0; i < level.getFloorSize(); i++) {
			floor = level.getFloor(i);
			Rectangle floorRect = new Rectangle(floor.getX(), floor.getY(), floor.getWidth(), floor.getHeight());
			if (heroRect.intersects(floorRect)) {
				colFloor.add(i);
			}
		}
	}

	private boolean touchGroundBlue() {
		Boolean res = false;
		for (int i = 0; i < colFloor.size(); i++) {
			floor = level.getFloor(colFloor.get(i));
			if ((floor.getY() >= blueHero.getOldY() + blueHero.getHeight() - 1)) {
				blueHero.setY(floor.getY() - blueHero.getHeight() + 1);
				res = true;
			} else if ((floor.getY() + floor.getHeight() <= blueHero.getOldY()) && !floor.getPass()) {
				blueHero.setY(floor.getY() + floor.getHeight());
				blueHero.setYVel(0);
			} else if (!floor.getPass()) {
				blueHero.setX(blueHero.getOldX());
				blueHero.setXVel(0);
			}
		}

		return res;
	}

	private void animateHeroBlue(double delta) {
		if (blueHero.getMotion() && (delta>12 || tickTimerBlue>5)) {
			blueHero.setBlueSprite(blueHero.getSprite() + 1);
			tickTimerBlue=0;
		} else if (!blueHero.getMotion()){
			blueHero.setBlueSprite(blueHero.getSpriteDisplay()+5);
		}
		else{
			tickTimerBlue+=delta;
		}
	}

	private void gameOverCheckBlue() {
		if (blueHero.getHealth() <= 0) {
			stop();
		}
	}

	private void shootProjectile(Hero hero, boolean red) {
		sound.play(0);
		GunSelect guns = new GunSelect();

		bullets.add(new Bullet(hero.getX() + (hero.getWidth() / 2), hero.getY() + (hero.getHeight() / 2),
				hero.getLeft(), guns.getGun(hero.getSelWeapon()).getBulletWidth(), guns.getGun(hero.getSelWeapon()).getBulletHeight(), red, guns.getGun(hero.getSelWeapon()).getBulletDamage(),
				guns.getGun(hero.getSelWeapon()).getSpeed(),guns.getGun(hero.getSelWeapon()).getDmgAmp()));
		if (red) {
			redHero.setMagazine(redHero.getMagazine() - 1);
		} else {
			blueHero.setMagazine(blueHero.getMagazine() - 1);

		}
	}

	private void shootWeapons(double delta) {
		GunSelect guns = new GunSelect();
		

		if ((int)tickTimerRedBullets < 	guns.getGun(redHero.getSelWeapon()).getFireRate()) {
			tickTimerRedBullets+=delta;
		} else if ((int)tickTimerRedBullets > guns.getGun(redHero.getSelWeapon()).getFireRate()) {
			tickTimerRedBullets = 0;
		} else if (redHero.move[3]) {

			shootProjectile(redHero, true);
			tickTimerRedBullets = 0;
		}

		if ((int)tickTimerBlueBullets < guns.getGun(blueHero.getSelWeapon()).getFireRate()) {
			tickTimerBlueBullets+=delta;
		} else if ((int)tickTimerBlueBullets > guns.getGun(blueHero.getSelWeapon()).getFireRate()) {
			tickTimerBlueBullets = 0;
		} else if (blueHero.move[3]) {
			shootProjectile(blueHero, false);
			tickTimerBlueBullets = 0;
		}

	}

	private void bulletInFrame() {
		Rectangle screen = new Rectangle(0, 0, width, height);
		Rectangle blueHeroRect = new Rectangle(blueHero.getX(), blueHero.getY(), blueHero.getWidthHitBox(),
				blueHero.getHeight());
		Rectangle redHeroRect = new Rectangle(redHero.getX(), redHero.getY(), redHero.getWidthHitBox(),
				redHero.getHeightHitBox());
		for (int i = 0; i < bullets.size(); i++) {
			Rectangle bulletRect = new Rectangle(bullets.get(i).getX(), bullets.get(i).getY(),
					bullets.get(i).getWidth(), bullets.get(i).getHeight());
				boolean ruined = false;
				for (int j = 0; j < level.getFloorSize(); j++) {
					floor = level.getFloor(j);
					Rectangle floorRect = new Rectangle(floor.getX(), floor.getY(), floor.getWidth(),
							floor.getHeight());
					if (bulletBackward(floorRect, bullets.get(i))) {
						bullets.remove(i);
						i--;
						ruined = true;
						break;

					} else if (bulletRect.intersects(floorRect)) {
						bullets.remove(i);
						i--;
						ruined = true;
						break;
					}
				}
				if (!ruined) {
					if (bullets.get(i).getShooter()) {
						if (blueHeroRect.intersects(bulletRect)) {
							blueHero.setHealth(blueHero.getHealth() - bullets.get(i).getDmg());
							bullets.remove(i);
							i--;
							break;
						} else if (bulletBackward(blueHeroRect, bullets.get(i))) {
							blueHero.setHealth(blueHero.getHealth() - bullets.get(i).getDmg());
							bullets.remove(i);
							i--;
							break;
						}
					} else {
						if (redHeroRect.intersects(bulletRect)) {
							redHero.setHealth(redHero.getHealth() - bullets.get(i).getDmg());
							bullets.remove(i);
							i--;
							break;
						} else if (bulletBackward(redHeroRect, bullets.get(i))) {
							redHero.setHealth(redHero.getHealth() - bullets.get(i).getDmg());
							bullets.remove(i);
							i--;
							break;
						}
					}
				}
				else{
					if (!screen.intersects(bulletRect)) {
						bullets.remove(i);
						i--;
					}
				}
			}
	}

	private void bulletUpdate(double delta) {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).bulletUpdate(delta);
			if (bullets.get(i).getDmg()==0){
				bullets.remove(i);
				i--;
			}
		}
	}

	private void lootBoxInFrame() {
		Rectangle screen = new Rectangle(0, 0, width, height);
		for (int i = 0; i < lootBoxes.size(); i++) {
			Rectangle boxRect = new Rectangle(lootBoxes.get(i).getX(), lootBoxes.get(i).getY(), 32, 32);
			if (!screen.intersects(boxRect)) {
				lootBoxes.remove(i);
				i--;
			}
		}
	}

	private void lootBoxSpawn(double delta) {
		GunSelect guns = new GunSelect();
		Random rand = new Random();
		if (rand.nextInt( (8000 - (int)tickTimerLoot)) ==1) {
			sound.play(1);
			lootBoxes.add(new LootCrate(12, guns.getGunSize()));
			tickTimerLoot = 0;
		} else {
			tickTimerLoot+=delta*10;
			if (tickTimerLoot > 7998) {
				tickTimerLoot = 7998;
			}
		}
		if (rand.nextInt((int) (8000 - tickTimerHealth)) == 1) {
			sound.play(1);
				lootBoxes.add(new LootCrate(13));
			tickTimerHealth = 0;
		} else {
			tickTimerHealth+=delta*5;
			if (tickTimerHealth > 7998) {
				tickTimerHealth = 7998;
			}
		}

	}

	private void lootBoxUpdate(double delta) {
		GunSelect guns = new GunSelect();
		Rectangle blueHeroRect = new Rectangle(blueHero.getX(), blueHero.getY(), blueHero.getWidthHitBox(),
				blueHero.getHeight());
		Rectangle redHeroRect = new Rectangle(redHero.getX(), redHero.getY(), redHero.getWidthHitBox(), redHero.getHeightHitBox());
		for (int i = 0; i < lootBoxes.size(); i++) {
			boolean flag = false;
			lootBoxes.get(i).updateCrate(delta);
			Rectangle boxRect = new Rectangle(lootBoxes.get(i).getX(), lootBoxes.get(i).getY(), 32, 32);
			if (redHeroRect.intersects(boxRect) && blueHeroRect.intersects(boxRect)) {
				if (Math.abs(redHero.getX() - lootBoxes.get(i).getX() + (32 / 2)) > Math
						.abs(blueHero.getX() - lootBoxes.get(i).getX() + (32 / 2))) {
					if (lootBoxes.get(i).getSprite() == 12) {
						blueHero.setWeapon(lootBoxes.get(i).getWeapon(),guns.getGun(lootBoxes.get(i).getWeapon()).getSprite());
						tickTimerBlueBullets = guns.getGun(blueHero.getSelWeapon()).getFireRate();
						blueHero.setMagazine(guns.getGun(lootBoxes.get(i).getWeapon()).getMagazine());
					} else {
						blueHero.setHealth(blueHero.getHealth() + 25);
					}

					flag = true;

				} else {
					if (lootBoxes.get(i).getSprite() == 12) {
						redHero.setWeapon(lootBoxes.get(i).getWeapon(),guns.getGun(lootBoxes.get(i).getWeapon()).getSprite());
						tickTimerRedBullets = guns.getGun(redHero.getSelWeapon()).getFireRate();
						redHero.setMagazine(guns.getGun(lootBoxes.get(i).getWeapon()).getMagazine());
					} else {
						redHero.setHealth(redHero.getHealth() + 25);
					}

					flag = true;

				}
			} else if (redHeroRect.intersects(boxRect)) {
				if (lootBoxes.get(i).getSprite() == 12) {
					redHero.setWeapon(lootBoxes.get(i).getWeapon(),guns.getGun(lootBoxes.get(i).getWeapon()).getSprite());
					tickTimerRedBullets = guns.getGun(redHero.getSelWeapon()).getFireRate();
					redHero.setMagazine(guns.getGun(lootBoxes.get(i).getWeapon()).getMagazine());
				} else {
					redHero.setHealth(redHero.getHealth() + 25);
				}

				flag = true;

			} else if (blueHeroRect.intersects(boxRect)) {
				if (lootBoxes.get(i).getSprite() == 12) {
					blueHero.setWeapon(lootBoxes.get(i).getWeapon(),guns.getGun(lootBoxes.get(i).getWeapon()).getSprite());
					tickTimerBlueBullets = guns.getGun(blueHero.getSelWeapon()).getFireRate();
					blueHero.setMagazine(guns.getGun(lootBoxes.get(i).getWeapon()).getMagazine());
				} else {
					blueHero.setHealth(blueHero.getHealth() + 25);
				}

				flag = true;

			}
			if (flag) {
				lootBoxes.remove(i);
				i--;
			}
		}
	}
	private boolean bulletBackward(Rectangle object,Bullet bul){
		if (Math.abs(object.getX()-bul.getX())<=bul.getSpeed()&&(object.getY()<=bul.getY()&&object.getHeight()+object.getY()>=bul.getHeight()+bul.getY())){
			if (bul.getDirection()){
				for (int i=bul.getOldX();i<=bul.getX();i+=bul.getWidth()){
					Rectangle bulletRect = new Rectangle(i, bul.getY(),
							bul.getWidth(), bul.getHeight());
					if (bulletRect.intersects(object))return true;
				}
			}
			else{
				for (int i=bul.getOldX();i>=bul.getX();i-=bul.getWidth()){
					Rectangle bulletRect = new Rectangle(i, bul.getY(),
							bul.getWidth(), bul.getHeight());
					if (bulletRect.intersects(object))return true;
				}
			}
		}
		return false;
	}
	
	private void touchFloorBox() {
		for (int j = 0; j < lootBoxes.size(); j++) {
			if (lootBoxes.get(j).getFalling()) {
				Rectangle lootRect = new Rectangle(lootBoxes.get(j).getX(), lootBoxes.get(j).getY(),
						lootBoxes.get(j).getHeight(), lootBoxes.get(j).getHeight());
				for (int i = 0; i < level.getFloorSize(); i++) {
					floor = level.getFloor(i);
					Rectangle floorRect = new Rectangle(floor.getX(), floor.getY(), floor.getWidth(),
							floor.getHeight());
					if (lootRect.intersects(floorRect)) {
						lootBoxes.get(j).setY(floor.getY() - lootBoxes.get(j).getHeight() + 1);
						lootBoxes.get(j).setFalling(false);
					}
				}
			}
		}
	}
}
