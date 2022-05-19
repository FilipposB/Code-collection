package connector;

import java.util.ArrayList;
import java.util.List;

public class OutputConnector extends Connector {
	public OutputConnector() {
		super();
	}

	private List<Integer> connectedObjectsID = new ArrayList<Integer>();
	private List<Integer> connectedConnectors = new ArrayList<Integer>();

	public void connectToInputConnector(int id, int connector) {
		connectedObjectsID.add(id);
		connectedConnectors.add(connector);
	}

	public int getConnectedObjectsSize() {
		return connectedObjectsID.size();
	}

	public int getConnectorID(int x) {
		return connectedObjectsID.get(x);
	}

	public int getConnectedConnectors(int x) {
		return connectedConnectors.get(x);
	}

	public void clearConnection(int i) {
		if (i < connectedObjectsID.size()) {
			connectedObjectsID.remove(i);
			connectedConnectors.remove(i);
		}
	}
	
	public void clearConnectionWithID(int id){
		int index = connectedObjectsID.indexOf(id);
		if (index!=-1){
			connectedObjectsID.remove(index);
			connectedConnectors.remove(index);
		}
	}
}
