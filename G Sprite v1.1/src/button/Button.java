package button;

public class Button {
	
    
	public boolean visible;
	public int x;
	public int y;
	public int wid;
	public int hei;
	public int sprite;
	public int command;
	
	public Button(){
		x = 0;
		y = 0;
		wid = 0;
		hei = 0;
		sprite =0;
		command = 0;
		visible = false;

	}
	
	public Button(boolean b, int i, int j, int k, int l, int m, int n) {
		visible = b;
		x = i;
		y=j;
		wid = k;
		hei = l;
		sprite = m;
		command = n;
	}
	
	public Button(Button b){
		visible = b.visible;
		x = b.x;
		y=b.y;
		wid = b.wid;
		hei = b.hei;
		sprite = b.sprite;
		command = b.command;
	}
	
	public boolean Touch(int mX,int mY){
		if (mX >= x && mX <= x + wid){
			if (mY >= y && mY <= y+hei){
				return true;
			}
		}
		return false;
	}
	
}
