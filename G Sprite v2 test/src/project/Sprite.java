package project;

import java.util.List;
import java.util.ArrayList;

public class Sprite {
	public int[][] color;
	public String title;
	public int lineSize;
	private int curHistory=-1;
	private List<String> history = new ArrayList<String>();
	private List<String> colHistory = new ArrayList<String>();
	private int historySize = 20;

	
	public Sprite(int line,String nam){
		color=new int[line][line];
		for (int i=0;i<line;i++){
			for (int j=0;j<line;j++){
				color[i][j]=0;
			}
		}
		lineSize = line;
		title = nam;
	}
	
	public boolean isHistoryEmpty(){
		return history.isEmpty();
	}
	public String getSpriteHistory(){

		return history.get(curHistory);
	}
	
	public void setSpriteHistory(String line, String colors){
		if (history.size()<historySize){
			curHistory++;
			history.add(curHistory, line);
			
			colHistory.add(curHistory, colors);
		}else{
			for(int i=0;i<curHistory;i++){
				history.set(i, history.get(i+1));
				colHistory.set(i, colHistory.get(i+1));
			}
			history.set(curHistory, line);
			colHistory.set(curHistory, colors);
		}
		if (curHistory<history.size()-1){
			clearSpriteHistory();
		}
	}
	
	public void clearSpriteHistory(){
		history.subList(curHistory+1, history.size()).clear();
		colHistory.subList(curHistory+1, colHistory.size()).clear();
	}
	
	public String getColorHistory(){
		return colHistory.get(curHistory);
	}

	public boolean undoRedo(int num) {

		curHistory-=num;

		if (curHistory<0){
			curHistory=0;
			return false;
		}
		else if (curHistory>history.size()-1){
			curHistory=history.size()-1;
			return false;
		}
		return true;
	}
	
}
