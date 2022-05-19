package entity;

public class LogicLamp extends LogicObject{
	private boolean on;

	public LogicLamp(int x, int y, int id) {
		super(x, y, id);
		addConnectors(1, 0);
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		on=input.get(0).getState();
	}
	
	public boolean isOn(){
		return on;
	}
	
}
