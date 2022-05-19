package project;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import colors.ColorArchive;

public class Project {
	public String name;
	public List<Sprite> sprite = new ArrayList<Sprite>();
	public ColorArchive col;
	public String path;
	
	public Project(String newName,String dir){
		name = newName;
		col = new ColorArchive();
		path = dir;
	}
	
	public void addSprite(int x,String nam){
		col.addColor(Color.decode("0xFF0096"),x*x);
		sprite.add(new Sprite(x,nam));
	}
	
	public void loadSprite(int x,String nam){
		sprite.add(new Sprite(x,nam));
	}

}
