package level;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import hero.Hero;
import load.ImageRender;
import weapon.GunSelect;

public class LevelChooser {
	private List<Image> sprite = new ArrayList<Image>();
	private ImageRender rend = new ImageRender();
	private Hero hero[] = new Hero[2];
	private List<Floor> floor = new ArrayList<Floor>();
	
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
	
	public void level1(){
		floor.add(new Floor(36,600,256,32,10,32,32,false));
		floor.add(new Floor(916,600,256,32,10,32,32,false));
		floor.add(new Floor(363,378,32,256,10,32,32,false));
		floor.add(new Floor(77,461,160,16,11,32,16,true));
		floor.add(new Floor(803,379,32,256,10,32,32,false));
		floor.add(new Floor(955,465,160,16,11,32,16,true));
		floor.add(new Floor(496,597,224,32,10,32,32,false));
		floor.add(new Floor(664,290,64,16,11,32,16,true));
		floor.add(new Floor(472,283,64,16,11,32,16,true));
		floor.add(new Floor(530,450,128,16,11,32,16,true));

		floor.add(new Floor(839,194,192,16,11,32,16,true));
		floor.add(new Floor(86,223,192,16,11,32,16,true));
		sprite.clear();
		for (int i=0;i<64;i++){
			cookImage(i);
		}

		GunSelect guns = new GunSelect();


		hero[0]=new Hero(100,550,0,24,32,16,32,100,0,guns.getGun(0).getSprite(),guns.getGun(0).getMagazine(),true);
		hero[1]=new Hero(1000,550,0,24,32,16,32,100,0,guns.getGun(0).getSprite(),guns.getGun(0).getMagazine(),false);
		hero[1].setLeft(false);	}
	
	private void hero0(){
		sprite.clear();
		for (int i=0;i<64;i++){
			cookImage(i);
		}

		GunSelect guns = new GunSelect();


		hero[0]=new Hero(50,650,0,24,32,16,32,100,0,guns.getGun(0).getSprite(),guns.getGun(0).getMagazine(),true);
		hero[1]=new Hero(1150,650,0,24,32,16,32,100,0,guns.getGun(0).getSprite(),guns.getGun(0).getMagazine(),false);
		hero[1].setLeft(false);

	}
	
	//Getters
	public Image getSprite(int x){
		return sprite.get(x);
	}
	
	public Hero getHero(int x){
		return hero[x];
	}
	
	public Floor getFloor(int x){
		return floor.get(x);
	}
	
	public int getFloorSize(){
		return floor.size();
	}
	

	
	
	
	private void cookImage(int x){
		rend.clear();
		rend.renderSprite(x);
		BufferedImage image = new BufferedImage(rend.getTotalSize(x), rend.getTotalSize(x), BufferedImage.TYPE_INT_ARGB);
		int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		for (int i=0;i<pixels.length;i++){
			pixels[i]=rend.pixels[i];
		}
		if (x==11){
			image = image.getSubimage(0, 0, 32, 8);
		}else if(x==12||x==13||x==10){
			
		}else{
			image = image.getSubimage(4, 0, 12, 16);
		}
		sprite.add(image);
	}
}
