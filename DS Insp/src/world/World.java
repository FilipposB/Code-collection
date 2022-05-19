package world;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import load.ImageRender;

public class World {
	private List<Floor> floor = new ArrayList<Floor>();
	private List<Image> sprite = new ArrayList<Image>();
	private ImageRender rend = new ImageRender();
	
	public World(){
		
	}
	
	public void loadWorld(){
		for (int i=0;i<9;i++)cookImage(i);
		floor.add(new Floor(0,0,672,14,1,1,1,false));
		floor.add(new Floor(405,-28,18,36,1,1,1,false));
		floor.add(new Floor(42,-349,17,686,1,1,1,false));
		floor.add(new Floor(15,12,5,64,1,1,1,false));
		floor.add(new Floor(4,59,49,6,1,1,1,false));
		floor.add(new Floor(7,33,48,7,1,1,1,false));
		floor.add(new Floor(34,-337,642,15,1,1,1,false));
		floor.add(new Floor(631,12,10,271,1,1,1,false));
		floor.add(new Floor(838,-352,15,636,1,1,1,false));
		floor.add(new Floor(674,-332,170,5,1,1,1,false));
		floor.add(new Floor(599,447,866,14,1,1,1,false));
		floor.add(new Floor(561,248,147,10,1,1,1,false));
		floor.add(new Floor(585,257,15,240,1,1,1,false));
		floor.add(new Floor(796,335,100,12,1,1,1,false));
		floor.add(new Floor(844,280,3,61,1,1,1,false));
		floor.add(new Floor(797,97,46,8,1,1,1,false));
		floor.add(new Floor(1045,203,15,252,1,1,1,false));
		floor.add(new Floor(1029,214,165,14,1,1,1,false));
		floor.add(new Floor(1163,203,15,252,1,1,1,false));
		floor.add(new Floor(855,459,13,192,1,1,1,false));
		floor.add(new Floor(682,584,561,11,1,1,1,false));
		floor.add(new Floor(865,534,499,6,1,1,1,false));
		floor.add(new Floor(1103,461,4,129,1,1,1,false));
		floor.add(new Floor(1270,-64,9,393,1,1,1,false));
		floor.add(new Floor(845,-44,484,10,1,1,1,false));
		floor.add(new Floor(1249,-75,49,14,1,1,1,false));
		floor.add(new Floor(1289,-62,4,35,1,1,1,false));
		floor.add(new Floor(851,-346,10,41,1,1,1,false));
		floor.add(new Floor(859,-334,493,15,1,1,1,false));
		floor.add(new Floor(860,-321,0,0,1,1,1,false));
		floor.add(new Floor(1346,-346,10,41,1,1,1,false));
		floor.add(new Floor(1273,-326,6,252,1,1,1,false));
		floor.add(new Floor(1255,302,37,2,1,1,1,false));
		floor.add(new Floor(1255,289,37,2,1,1,1,false));
		floor.add(new Floor(1255,2,37,2,1,1,1,false));
		floor.add(new Floor(1255,15,37,2,1,1,1,false));
		floor.add(new Floor(1513,-364,16,936,1,1,1,false));
		floor.add(new Floor(1463,452,53,5,1,1,1,false));
		floor.add(new Floor(1442,153,77,9,1,1,1,false));
		floor.add(new Floor(1467,-241,55,11,1,1,1,false));
		floor.add(new Floor(1956,-366,16,1326,1,1,1,false));
		floor.add(new Floor(1794,-279,13,104,1,1,1,false));
		floor.add(new Floor(1782,-212,178,8,1,1,1,false));
		floor.add(new Floor(1830,-207,9,171,1,1,1,false));
		floor.add(new Floor(1804,-86,156,13,1,1,1,false));
		floor.add(new Floor(1804,-150,156,13,1,1,1,false));
		floor.add(new Floor(3038,-366,16,1326,1,1,1,false));
		floor.add(new Floor(1972,-366,559,20,1,1,1,false));
		floor.add(new Floor(1862,-358,152,5,1,1,1,false));
		floor.add(new Floor(2693,-358,352,8,1,1,1,false));
		floor.add(new Floor(2682,-350,11,156,1,1,1,false));
		floor.add(new Floor(2511,-348,8,25,1,1,1,false));
		floor.add(new Floor(2504,-330,36,5,1,1,1,false));
		floor.add(new Floor(2384,-91,657,16,1,1,1,false));
		floor.add(new Floor(1945,-282,11,15,1,1,1,false));
		floor.add(new Floor(1970,-212,318,12,1,1,1,false));
		floor.add(new Floor(1973,105,911,12,1,1,1,false));
		floor.add(new Floor(2971,-70,4,431,1,1,1,false));
		floor.add(new Floor(2902,358,133,243,1,1,1,false));
		floor.add(new Floor(2951,-77,43,15,1,1,1,false));
		floor.add(new Floor(2092,597,807,18,1,1,1,false));
		floor.add(new Floor(2300,114,6,152,1,1,1,false));
		floor.add(new Floor(2290,242,493,7,1,1,1,false));
		floor.add(new Floor(2766,114,6,152,1,1,1,false));
		floor.add(new Floor(2692,517,52,79,1,1,1,false));
		floor.add(new Floor(2678,465,81,17,1,1,1,false));
		floor.add(new Floor(2684,481,10,39,1,1,1,false));
		floor.add(new Floor(2743,481,10,39,1,1,1,false));
		floor.add(new Floor(2962,162,20,3,1,1,1,false));
		floor.add(new Floor(2962,189,20,3,1,1,1,false));
		floor.add(new Floor(2543,242,5,82,1,1,1,false));
		floor.add(new Floor(2524,319,43,5,1,1,1,false));
		floor.add(new Floor(2520,322,7,20,1,1,1,false));
		floor.add(new Floor(2565,322,7,20,1,1,1,false));
		floor.add(new Floor(1956,1131,1098,15,1,1,1,false));
		floor.add(new Floor(3043,958,6,178,1,1,1,false));
		floor.add(new Floor(2111,1144,5,471,1,1,1,false));
		floor.add(new Floor(2936,1144,5,471,1,1,1,false));
		floor.add(new Floor(1734,246,222,13,1,1,1,false));
		floor.add(new Floor(1834,257,9,400,1,1,1,false));
		floor.add(new Floor(1738,612,219,8,1,1,1,false));
		floor.add(new Floor(1367,1483,1740,18,1,1,1,false));
		floor.add(new Floor(2408,1144,4,293,1,1,1,false));
		floor.add(new Floor(2813,1133,4,293,1,1,1,false));
		floor.add(new Floor(1678,1399,13,93,1,1,1,false));
		floor.add(new Floor(1637,1393,505,9,1,1,1,false));
		floor.add(new Floor(1820,1307,11,187,1,1,1,false));
		floor.add(new Floor(1761,1300,505,9,1,1,1,false));
		floor.add(new Floor(1924,1208,12,279,1,1,1,false));
		floor.add(new Floor(1866,1199,505,9,1,1,1,false));
		floor.add(new Floor(2136,537,9,64,1,1,1,false));
		floor.add(new Floor(2672,1088,117,14,1,1,1,false));
		floor.add(new Floor(2653,1100,153,15,1,1,1,false));
		floor.add(new Floor(2635,1113,191,18,1,1,1,false));
		floor.add(new Floor(2707,1004,6,85,1,1,1,false));
		floor.add(new Floor(2749,1004,6,85,1,1,1,false));
		floor.add(new Floor(147,-30,153,15,1,1,1,false));
		floor.add(new Floor(166,-42,117,14,1,1,1,false));
		floor.add(new Floor(129,-17,191,18,1,1,1,false));
		floor.add(new Floor(201,-126,6,85,1,1,1,false));
		floor.add(new Floor(243,-126,6,85,1,1,1,false));


	}
	
	public Floor getFloor(int x){
		return floor.get(x);
	}
	
	public int getFloorSize(){
		return floor.size();
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
