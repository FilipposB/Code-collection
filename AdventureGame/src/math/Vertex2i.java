package math;

public class Vertex2i {
	public int x;
	public int y;

	public Vertex2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vertex2i(Vertex2f ver){
		this.x = (int)(ver.x+0.5);
		this.y = (int)(ver.y+0.5);
	}
}
