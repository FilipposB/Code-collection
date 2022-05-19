package entity;

public class LogicPower extends LogicObject{
	
	private int frequency;
	private int tick;

	public LogicPower(int x, int y, boolean state,int id) {
		super(x, y, id);
		addConnectors(0, 1);
		frequency=-1;
		output.get(0).setState(state);
	}
	
	public LogicPower(int x, int y,int frequency,boolean state, int id) {
		super(x, y, id);
		addConnectors(0, 1);
		this.frequency=frequency;
		output.get(0).setState(state);
	}

	@Override
	protected void update() {
		if (frequency!=-1){
			tick++;
			if (tick>=frequency){
				output.get(0).setState(!output.get(0).getState());
				tick=0;
			}
		}
		LogicManager.updateConnections(getID());
	}

}
