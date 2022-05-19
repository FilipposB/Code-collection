package entities;

import java.awt.Graphics;

public class EnemyEntity extends Entity {

	public EnemyEntity(int x, int y, int wid, int hei, String img) {
		super(x, y, wid, hei, img);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		sprite.draw(g, (int)(xPos+0.5), (int)(yPos+0.5));
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
