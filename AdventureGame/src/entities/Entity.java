package entities;

import math.Vertex2f;
import math.Vertex2i;

public abstract class Entity {
	private Vertex2f position;
	private Vertex2i size;
	private float speed;

	public Entity(int x, int y, int wid, int hei, float speed) {
		position = new Vertex2f(x, y);
		size = new Vertex2i(wid, hei);
		this.speed = speed;
	}

	public abstract void tick();

	public Vertex2f getPosition() {
		return position;
	}

	public float getSpeed() {
		return speed;
	}

	public void addSpeed(float sped) {
		speed += sped;
	}

	public void setPosition(Vertex2f position) {
		this.position = position;
	}

	public Vertex2i getSize() {
		return size;
	}

	public void setSize(Vertex2i size) {
		this.size = size;
	}
}
