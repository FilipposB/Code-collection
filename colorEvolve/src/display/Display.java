package display;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

import colorEvolve.Creature;

public class Display extends JPanel {
	private static final long serialVersionUID = 1L;

	private Color objColor = new Color(255,0,255);
	private Creature[] creatures = new Creature[100];
	

	public Display() {
		for (int i = 0; i < creatures.length; i++) {
			creatures[i] = new Creature(objColor);
		}
		bblSort();

	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		for (int y = 0; y < Math.sqrt(creatures.length); y++) {
			for (int x = 0; x < Math.sqrt(creatures.length); x++) {
				
				if (creatures[y * 10 + x].getAlive()){
					g.setColor(creatures[y * 10 + x].getColor());
					g.fillRect(x * 60, y * 60,60, 60);
					g.setColor(objColor);
					g.drawString("" + creatures[y * 10 + x].getScore(), x * 60 + 20, y * 60 + 25);
				}
				g.setColor(Color.BLACK);
				g.drawRect(x*60, y*60, 60, 60);
			}
		}
		g.dispose();
		try {
			update();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
	}

	private void update() throws InterruptedException {
		Thread.sleep(1000);

		killColors();
		reborn();
		bblSort();
	}

	private void killColors() {
		Random rand = new Random();
		int killed = 0;
		for (int i = 0; i < creatures.length *90/100; i++) {
			if (rand.nextInt(100) < 2) {
				creatures[i].setDead();
				killed++;
			}
		}
		for (int i=49+killed;i<creatures.length;i++){
			creatures[i].setDead();
		}
		
	}
	
	private void reborn(){
		int nxtAlive=nextAlive(-1);
		for (int i=0;i<creatures.length;i++){
			if (!creatures[i].getAlive()){
				creatures[i]=new Creature(creatures[nxtAlive],objColor);
				nxtAlive=nextAlive(nxtAlive);

			}
		}
	}

	private void bblSort() {
		for (int x = 1; x < creatures.length; x++) {
			for (int i = 0; i < creatures.length - x; i++) {
				if (creatures[i].getScore() > creatures[i + 1].getScore()) {
					Creature temp = creatures[i];
					creatures[i] = creatures[i + 1];
					creatures[i + 1] = temp;
				}
			}
		}
	}
	
	private int nextAlive(int x){
		for (int i=x+1;i<creatures.length;i++){
			if (creatures[i].getAlive())return i;
		}
		return -1;
	}
}
