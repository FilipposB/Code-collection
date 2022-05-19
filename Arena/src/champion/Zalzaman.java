package champion;

import java.awt.Color;
import java.awt.Graphics2D;

import ability.RangedAuto;
import game.ClientGame;
import things.Bullet;

public class Zalzaman extends Champion {

	public Zalzaman(int x, int y) {
		super(x, y, 78, 5);
		autoAttack = new RangedAuto(10, 800, 70, 10, 20, 0);
	}

	@Override
	public void paint(ClientGame game, Graphics2D g) {
		// TODO Auto-generated method stub

		int x = (int) (this.x - 0.5);
		int y = (int) (this.y - 0.5);

		int X = (int) (x + Math.cos(-angle) * (size - 24));
		int Y = (int) (y + Math.sin(-angle) * (size - 24));

		// TODO Auto-generated method stub

		

		g.setColor(Color.BLACK);
		g.drawLine(x - game.camera.x, y - game.camera.y, X - game.camera.x, Y - game.camera.y);
		g.fillOval(x - size / 2 - game.camera.x, y - size / 2 - game.camera.y, size, size);
		g.setColor(Color.MAGENTA);
		g.fillOval(x - size / 2 + 5 - game.camera.x, y - size / 2 + 5 - game.camera.y, size - 10, size - 10);

	}

	@Override
	public void tick(ClientGame game) {
		int mouseX = game.mouseX + game.camera.x;
		int mouseY = game.mouseY + game.camera.y;

		angle = Math.atan2(x - mouseX, +y - mouseY) - Math.toRadians(-90);

		// TODO Auto-generated method stub
		if (autoAttack.useAbility(auto)) {
			autoAttack.doAbility(this,game.map);
		}

		double handic = 1;
		if (handicap > 0) {
			handicap--;
			handic = 3;
		}

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
