package load;

import java.awt.Color;

public class ImageRender {
	public int[] pixels;
	private int[] RGBcolors = new int[3];
	private ImageLoad sprite = new ImageLoad("2PlayerShoot");

	public ImageRender() {
		pixels = new int[1];
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void renderSprite(int spr) {
		pixels = new int[sprite.getTotalSize(spr)*sprite.getTotalSize(spr)];
		int p;
		for (int i = 0; i < sprite.getTotalSize(spr); i++) {
			for (int j = 0; j < sprite.getTotalSize(spr); j++) {
				p = 255;
				if (sprite.getImageColor(spr, i, j).equals(Color.decode("0xFF0096"))) {
					p = 0;
					RGBcolors[0] = 0;
					RGBcolors[1] = 0;
					RGBcolors[2] = 0;
				} else {

					RGBcolors[0] = sprite.getImageColor(spr, i, j).getRed();
					RGBcolors[1] = sprite.getImageColor(spr, i, j).getGreen();
					RGBcolors[2] = sprite.getImageColor(spr, i, j).getBlue();
				}

				pixels[(int) (j + i * sprite.getTotalSize(spr))] = (p << 24) | (RGBcolors[0] << 16) | (RGBcolors[1] << 8) | RGBcolors[2];
			}
		}
	}
	
	public int getTotalSize(int spr){
		return sprite.getTotalSize(spr);
	}
	

}
