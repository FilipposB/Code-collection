package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
	private int tickTimerRed = 0;
	private int tickTimerBlue = 0;
	private int tickTimerLoot = 0;
	private int tickTimerHealth = 0;
	private int tickTimerRedBullets = 0;
	private int tickTimerBlueBullets = 0;

	// Bullets
	private List<Bullet> bullets = new ArrayList<Bullet>();

	// Sound
	private Sound sound = new Sound();

	public Game() {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		frame = new JFrame();
		setFocusable(true);
		requestFocusInWindow();
		setFocusTraversalKeysEnabled(false);
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
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int ticks = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			boolean shouldRender = false;
			while (delta >= 1) {
				tick();
				ticks++;
				delta--;
				shouldRender = true;
			}

			if (shouldRender) {
				render(frames);
				frames++;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + "  |   " + ticks + " ticks, " + frames + " fps");
				ticks = 0;
				frames = 0;
			}
		}
		stop();
	}

	private void tick() {
		// Check If Game Over
		gameOverCheck();
		gameOverCheckBlue();

		// Hero Checks
		touchFloorRed();
		redHero.update(touchGroundRed());
		animateHeroRed();
		shootWeapons();

		touchFloorBlue();
		blueHero.update(touchGroundBlue());
		animateHeroBlue();

		// Bullet Check
		bulletInFrame();
		bulletUpdate();

		// LootBox Drop
		lootBoxSpawn();
		lootBoxInFrame();
		touchFloorBox();
		lootBoxUpdate();

	}

	private void render(int frames) {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

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
		// Display Sprite
		if (!redHero.getLeft()) {
			g.drawImage(level.getSprite(redHero.getSprite()), redHero.getX() + redHero.getWidthHitBox(), redHero.getY(),
					-redHero.getWidth(), redHero.getHeight(), null);
		} else {
			g.drawImage(level.getSprite(redHero.getSprite()), redHero.getX(), redHero.getY(), redHero.getWidth(),
					redHero.getHeight(), null);
		}

		if (!blueHero.getLeft()) {
			g.drawImage(level.getSprite(blueHero.getSprite()), blueHero.getX() + blueHero.getWidthHitBox(), blueHero.getY(),
					-blueHero.getWidth(), blueHero.getHeight(), null);
		} else {
			g.drawImage(level.getSprite(blueHero.getSprite()), blueHero.getX(), blueHero.getY(), blueHero.getWidth(),
					blueHero.getHeight(), null);
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

		//g.dispose();
		bs.show();
		g.dispose();

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

	private void animateHeroRed() {
		if (redHero.getMotion() && tickTimerRed < 60) {
			tickTimerRed++;
		} else {
			tickTimerRed = 0;
			redHero.setRedSprite(redHero.getSpriteDisplay());
		}
		if (redHero.getMotion() && tickTimerRed % 6 == 0) {
			redHero.setRedSprite(redHero.getSprite() + 1);
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

	private void animateHeroBlue() {
		if (blueHero.getMotion() && tickTimerBlue < 60) {
			tickTimerBlue++;
		} else {
			tickTimerBlue = 0;
			blueHero.setBlueSprite(blueHero.getSpriteDisplay()+5);
		}
		if (blueHero.getMotion() && tickTimerBlue % 6 == 0) {
			blueHero.setBlueSprite(blueHero.getSprite() + 1);
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

	private void shootWeapons() {
		GunSelect guns = new GunSelect();

		if (tickTimerRedBullets < 	guns.getGun(redHero.getSelWeapon()).getFireRate()) {
			tickTimerRedBullets++;
		} else if (tickTimerRedBullets > guns.getGun(redHero.getSelWeapon()).getFireRate()) {
			tickTimerRedBullets = 0;
		} else if (redHero.move[3]) {
			shootProjectile(redHero, true);
			tickTimerRedBullets = 0;
		}

		if (tickTimerBlueBullets < guns.getGun(blueHero.getSelWeapon()).getFireRate()) {
			tickTimerBlueBullets++;
		} else if (tickTimerBlueBullets > guns.getGun(blueHero.getSelWeapon()).getFireRate()) {
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

	private void bulletUpdate() {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).bulletUpdate();
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

	private void lootBoxSpawn() {
		GunSelect guns = new GunSelect();
		Random rand = new Random();
		if (rand.nextInt(1000 - tickTimerLoot / 2) ==1) {
			sound.play(1);
			lootBoxes.add(new LootCrate(12, guns.getGunSize()));
			tickTimerLoot = 0;
		} else {
			tickTimerLoot++;
			if (tickTimerLoot / 2 > 998) {
				tickTimerLoot = 2 * 998;
			}
		}
		if (rand.nextInt(1000 - tickTimerHealth / 2) == 1) {
			sound.play(1);
				lootBoxes.add(new LootCrate(13));
			tickTimerHealth = 0;
		} else {
			tickTimerHealth++;
			if (tickTimerHealth / 2 > 998) {
				tickTimerHealth = 2 * 998;
			}
		}

	}

	private void lootBoxUpdate() {
		GunSelect guns = new GunSelect();
		Rectangle blueHeroRect = new Rectangle(blueHero.getX(), blueHero.getY(), blueHero.getWidthHitBox(),
				blueHero.getHeight());
		Rectangle redHeroRect = new Rectangle(redHero.getX(), redHero.getY(), redHero.getWidthHitBox(), redHero.getHeightHitBox());
		for (int i = 0; i < lootBoxes.size(); i++) {
			boolean flag = false;
			lootBoxes.get(i).updateCrate();
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
						System.out.println(redHero.getMagazine());
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
