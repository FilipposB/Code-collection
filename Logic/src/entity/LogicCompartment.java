package entity;

import java.util.ArrayList;
import java.util.List;

import connector.InputConnector;
import connector.OutputConnector;

public class LogicCompartment extends LogicObject {

	private List<LogicObject> logicObjects = new ArrayList<LogicObject>();
	private List<Integer> inID = new ArrayList<Integer>();
	private List<Integer> outID = new ArrayList<Integer>();

	public LogicCompartment(int x, int y, int id) {
		super(x, y, id);
		// TODO Auto-generated constructor stub
	}

	public void addCompartment(LogicObject obj) {
		if (obj instanceof LogicPower) {
			addConnectors(1, 0);
			inID.add(obj.getID());
		} else if (obj instanceof LogicLamp) {
			addConnectors(0, 1);
			outID.add(obj.getID());
		}
		logicObjects.add(obj);

	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		for (LogicObject obj : logicObjects) {
			for (int i = 0; i < obj.input.size(); i++) {
				for (int j = 0; j < inID.size(); j++) {
					if (obj.input.get(i).getConnectedObjectID() == inID.get(j)) {
						obj.input.get(i).setState(input.get(j).getState());
						logicObjects=LogicManager.updateConnections(obj.getID(),logicObjects);
					}
				}
			}

			
		}
		for (LogicObject obj : logicObjects) {
			obj.update();
			logicObjects=LogicManager.updateConnections(obj.getID(),logicObjects);
		}
		for (int i=0;i<outID.size();i++){
			int index=LogicManager.getIndexWithID(outID.get(i), logicObjects);
			if (index!=-1){
				LogicObject log = logicObjects.get(index);
				if (log instanceof LogicLamp) {
					output.get(i).setState(((LogicLamp) log).isOn());
					logicObjects=LogicManager.updateConnections(log.getID(),logicObjects);

				}
			}
		}
		
		LogicManager.updateConnections(getID());

		
	}

}
