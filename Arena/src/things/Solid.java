package things;

public abstract class Solid {
	//Angle
		public double angle;
		//Position
		public double x;
		public double y;
		
		//Size
		public int size;
		
		//Speed
		public double speed;
		
		public Solid(int x,int y,int size,double speed,double angl){
			this.x=x;
			this.y=y;
			this.size=size;
			this.speed=speed;
			this.angle=angl;
		}
}
