package gooz.sprite;

import java.awt.Color;

import gooz.engine.Translator;

public class SpriteRender {
	public int[] pixels;
	public int[] RGBcolors = new int[3];
	SpriteLoader sprite ;
	Translator translate = new Translator();
	

	public SpriteRender(int width, int height) {
		pixels = new int[width * height];
		sprite = new SpriteLoader();
		
	}
	
	public void clear(){
		for (int i=0 ; i< pixels.length;i++){
			pixels[i] = 0;
		}
	}
	
	public void renderButton(int spr) {
		int p;
		for (int i = 0; i < sprite.grid; i++) {
			for (int j = 0; j < sprite.grid; j++) {
				p = 255;
				if (sprite.button[spr].substring((i * sprite.grid *6 + j*6) , (i * sprite.grid*6 + j*6)+6).equals("FF0096")){
					p = 0;
					RGBcolors[0] = 0;
					RGBcolors[1] = 0;
					RGBcolors[2] = 0;
				}
				else{
					hexToDec(sprite.button[spr].substring((i * sprite.grid *6 + j*6) , (i * sprite.grid*6 + j*6)+6));
				}
				pixels[j+i*sprite.grid] = (p<<24) | (RGBcolors[0]<<16) | (RGBcolors[1]<<8) | RGBcolors[2];
			}
		}
	}
	
	public void makeCanvas(Color hex,int i,int lin){
		pixels[i] = (255<<24) | (hex.getRed()<<16) | (hex.getGreen()<<8) | hex.getBlue();
	}
	
	public void hexToDec(String hexcol) {

		String substr;
		int sum;
		for (int i = 0; i < 3; i++) {
			sum = 0;
			substr = "";
			substr = hexcol.substring(2 * i, 2 + 2 * i);
			sum = hexToDecEncr(substr.substring(0, 1)) * 16 + hexToDecEncr(substr.substring(1, 2));
			setUseRGB(i, sum);
		}

	}

	private int hexToDecEncr(String x) {
		String[] hex = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
		int i = 0;
		boolean flag = true;
		
		while (flag && i < 16) {
			if (x.equals(hex[i])) {
				flag = false;
			} else {
				i++;
			}
		}
		return i;
	}

	public void setUseRGB(int x, int y) {
		this.RGBcolors[x] = y;
	}
}
