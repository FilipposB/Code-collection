package connector;

public class InputConnector extends Connector{
	
	public InputConnector() {
		super();
		connectedObjectID=-1;
		connectedConnector=-1;
	}



	private int connectedObjectID;
	private int connectedConnector;
	
	
	
	public boolean setUpConnection(int id,int connector){
		if (connectedObjectID==-1){
			connectedObjectID=id;
			connectedConnector=connector;
			return true;
		}
		return false;
	}
	
	public int getConnectedObjectID(){
		return connectedObjectID;
	}
	
	public int getConnectedConnector(){
		return connectedConnector;
	}
	
	public void clearConnection(){
		connectedObjectID=-1;
		connectedConnector=-1;
		setState(false);
	}
}
