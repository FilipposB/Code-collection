package game.screen;

public class ColorsSearch {
	private int count;
	private String names;
	
	ColorsSearch(){
		count = 0;
		names = null;
	}
	ColorsSearch(String x,int y){
		count = y;
		names = x;
	}
	
	int getCount(){
		return count;
	}
	
	String getNames(){
		return names;
	}
	
	void setCount(int x){
		this.count = x;
	}
	
	void setNames(String x){
		this.names = x;
	}
}
