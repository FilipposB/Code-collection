package gooz.project;

import java.util.ArrayList;

public class Project {
	public String name;
	public ArrayList<Drawing> sprite = new ArrayList<Drawing>();
	
	public Project(String newName){
		name = newName;
	}
	
	public void addSprite(int x,String nam){
		sprite.add(new Drawing(x,nam));
	}

}
