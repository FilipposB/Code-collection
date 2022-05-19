package entity;

public class LogicGate extends LogicObject {

	/*
	 * Type: 0 AND 1 OR 2 XOR 3 NOR 4 NAND 5 NOT
	 */

	private final int type;

	public LogicGate(int x, int y, int id, int type) {
		super(x, y, id);
		this.type = type;
		if (type == 0) {
			addConnectors(2, 1);
		} else if (type == 1) {
			addConnectors(2, 1);
		} else if (type == 2) {
			addConnectors(2, 1);
		} else if (type == 3) {
			addConnectors(2, 1);
		} else if (type == 4) {
			addConnectors(2, 1);
		} else if (type == 5) {
			addConnectors(1, 1);
		}
	}

	@Override
	protected void update() {
		if (type == 0) {
			output.get(0).setState(input.get(0).getState()&&input.get(1).getState());
		} else if (type == 1) {
			output.get(0).setState(input.get(0).getState()||input.get(1).getState());
		} else if (type == 2) {
			output.get(0).setState(input.get(0).getState()^input.get(1).getState());
		} else if (type == 3) {
			output.get(0).setState(!(input.get(0).getState()||input.get(1).getState()));
		} else if (type == 4) {
			output.get(0).setState(!(input.get(0).getState()&&input.get(1).getState()));
		} else if (type == 5) {
			output.get(0).setState(!input.get(0).getState());
		}
		LogicManager.updateConnections(getID());

	}

}
