package colors;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ColorArchive {
	public List<Color> color;
	private List<Integer> colorAmount;

	public ColorArchive() {
		color = new ArrayList<Color>();
		colorAmount = new ArrayList<Integer>();
	}

	/*
	 * color will have all the colors used in the program by all projects and
	 * sprites newColor adds a color to the first empty spot it finds , if non e
	 * exist it makes one colorAmound will have the amount of each color if it
	 * reaches 0 the color will be free and other colors will take its place ,
	 * if it is the last -one in the list it will be scraped addColor and
	 * removeColor needs two integers , number is used to tell us which color
	 * will be altered and amount shows the alteration
	 */

	private void newColor(Color col,int x) {
		boolean flag=false;
		if (color.size()==0){
			colorAmount.add(0,x);
			color.add(0, col);
			return;
		}
		for (int i=1;i<colorAmount.size();i++){
			if (colorAmount.get(i)==0){
				colorAmount.set(i,x);
				color.set(i, col);
				flag = true;
				break;
			}
		}
		if (!flag){
			color.add(col);
			colorAmount.add(1);
		}
	}

	public void addColor(Color col,int amount) {
		boolean flag = false;
		for (int i = 0; i < color.size(); i++) {
			if (col.equals(color.get(i))) {
				colorAmount.set(i, colorAmount.get(i) + amount);
				flag = true;
				return;
			}
		}
		if (!flag) {
			newColor(col,amount);
		}
	}

	public void removeColor(int number) {
		colorAmount.set(number, colorAmount.get(number) - 1);
		if (colorAmount.get(number)<0)colorAmount.set(number, 0);
	}
	
	public int  getColorNumber(Color col){
		for (int i = 0; i < color.size(); i++) {
			if (col.equals(color.get(i))) {

				return i;
			}
		}
		return -1;
	}
	
	public void display(){
		for (int i=0;i<color.size();i++){
			System.out.println(color.get(i)+"   :    "+colorAmount.get(i));
		}
	}
	
}
