package camera;

public class Camera {
	private double x;
	private double y;
	private double xAc;
	private double yAc;
	
	public Camera(double x,double y){
		this.x=x;
		this.y=y;
	}
	
	public void cameraMove(int x, int y,double delta){
		
		this.x=x-800;
		this.y=y-800;

	}
	
	public int getX(){
		return (int) x;
	}
	
	public int getY(){
		return (int) y;
	}
	
}
