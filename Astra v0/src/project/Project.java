package project;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Project {
	private String name;
	private List<Sprite> sprites;
	
	public Project(String nam,int projectNumber){
		if (nam.isEmpty()){
			name="Untitled "+projectNumber;
		}
		sprites=new ArrayList<Sprite>();
	}
	
	public void createSprite(String name,int wid,int hei){
		sprites.add(new Sprite(name,sprites.size(),wid,hei));
		createLayer(sprites.size()-1,0,0,wid,hei,false,true,Color.WHITE);
	}
	
	public void createLayer(int spr,int x,int y,int wid,int hei,boolean lock,boolean visible,Color col){
		if (sprites.size()>spr){
			sprites.get(spr).fillNewLayer(x, y, wid, hei, lock,visible, col);
		}
		else{
			System.out.println("out of range");
		}
	}
	
	public Image getSpriteImage(int spr){
		return sprites.get(spr).getImage();
	}
	
	public int getSpriteSize(){
		return sprites.size();
	}
	
	public String getName(){
		return name;
	}
	
	public int getSpriteWidth(int i){
		return sprites.get(i).getWidth();
	}
	
	public int getSpriteHeight(int i){
		return sprites.get(i).getHeight();
	}
	
}
