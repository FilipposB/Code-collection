package level;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import hero.Heart;
import hero.Hero;
import load.ImageRender;

public class LevelChooser {
	private List<Image> sprite = new ArrayList<Image>();
	private ImageRender rend = new ImageRender();
	private Hero hero;
	private List<Floor> floor = new ArrayList<Floor>();
	private List<Heart> heart = new ArrayList<Heart>();
	
	public LevelChooser(){
		
	}
	
	public void level0(boolean hero){
		floor.clear();

		if (hero)hero0();
		floor.add(new Floor(-20,0,32,800,10,32,32,false));
		floor.add(new Floor(1185,0,32,800,10,32,32,false));
		floor.add(new Floor(0,645,1220,32,10,32,32,false));
		floor.add(new Floor(500,549,32,96,10,16,16,false));
		floor.add(new Floor(200,550,128,14,11,32,14,true));
		floor.add(new Floor(800,550,128,14,11,32,14,true));
		floor.add(new Floor(288,450,512,14,11,32,14,true));
		floor.add(new Floor(0,330,384,16,10,32,16,false));




	}
	
	private void hero0(){
		sprite.clear();
		for (int i=0;i<12;i++){
			cookImage(i);
		}


		hero=new Hero(50,650,0,64,64,16,64,100,16);

	}
	
	//Getters
	public Image getSprite(int x){
		return sprite.get(x);
	}
	
	public Hero getHero(){
		return hero;
	}
	
	public Floor getFloor(int x){
		return floor.get(x);
	}
	
	public int getFloorSize(){
		return floor.size();
	}
	

	
	public Heart getHeart(int x){
		return heart.get(x);
	}
	
	public int getHeartSize(){
		return heart.size();
	}
	

	
	
	public void setHeartSprite(int x){
		heart.get(x).setSprite();
	}
	
	public void addHeart(Heart h){
		heart.add(h);
	}
	
	
	private void cookImage(int x){
		rend.clear();
		rend.renderSprite(x);
		BufferedImage image = new BufferedImage(rend.getTotalSize(x), rend.getTotalSize(x), BufferedImage.TYPE_INT_ARGB);
		int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		for (int i=0;i<pixels.length;i++){
			pixels[i]=rend.pixels[i];
		}
		 if(x==12||x==13||x==10){
			
		}else{
			image = image.getSubimage(0, 0, 32, 32);
		}
		sprite.add(image);
	}
}
