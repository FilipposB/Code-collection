package world;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import load.ImageRender;

public class World {
	private List<Image> sprite = new ArrayList<Image>();
	private ImageRender rend = new ImageRender();
	
	public World(){
		
	}
	
	public void loadWorld(){
		

	}
	
	
	public Image getSprite(int x){
		return sprite.get(x);
	}

	
	private void cookImage(int x){
		rend.clear();
		rend.renderSprite(x);
		BufferedImage image = new BufferedImage(rend.getTotalSize(x), rend.getTotalSize(x), BufferedImage.TYPE_INT_ARGB);
		int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		for (int i=0;i<pixels.length;i++){
			pixels[i]=rend.pixels[i];
		}
		sprite.add(image);
	}
	
}
