package entity;

public class LogicSwitch extends LogicPower{

	public LogicSwitch(int x, int y, boolean state, int id) {
		super(x, y, state, id);
		// TODO Auto-generated constructor stub
	}
	
	public void click(){
		output.get(0).setState(!output.get(0).getState());
	}

}
