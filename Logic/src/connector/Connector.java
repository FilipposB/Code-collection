package connector;

public class Connector {
	private boolean state;
	
	public Connector(){
	}
	
	public boolean getState(){
		return state;
	}
	
	public void setState(boolean state){
		this.state=state;
	}
}
