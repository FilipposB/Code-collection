package render;

import java.awt.Color;

import colors.ColorArchive;
import project.Sprite;

public class ImageRender {
	public int[] pixels;
	private int[] RGBcolors = new int[3];
	private ImageLoad sprite = new ImageLoad("Buttons");
	private ImageLoad cursors = new ImageLoad("Cursors");

	public ImageRender() {
		pixels = new int[32 * 32];

	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void renderButton(int spr, int sel) {
		pixels = new int[32 * 32];
		int p;
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 32; j++) {
				p = 255;
				if (sprite.getImageColor(spr, i, j).equals(Color.decode("0xFF0096"))) {
					p = 0;
					RGBcolors[0] = 0;
					RGBcolors[1] = 0;
					RGBcolors[2] = 0;
				} else {
					if (sel + 8 != spr) {
						RGBcolors[0] = sprite.getImageColor(spr, i, j).getRed();
						RGBcolors[1] = sprite.getImageColor(spr, i, j).getGreen();
						RGBcolors[2] = sprite.getImageColor(spr, i, j).getBlue();
					} else {
						if (sprite.getImageColor(spr, i, j).getRed() == 175 && sprite.getImageColor(spr, i, j).getGreen() == 175 && sprite.getImageColor(spr, i, j).getBlue() == 175) {
							RGBcolors[0] = RGBcolors[1] = RGBcolors[2] = 150;
						} else {
							RGBcolors[0] = sprite.getImageColor(spr, i, j).getRed();
							RGBcolors[1] = sprite.getImageColor(spr, i, j).getGreen();
							RGBcolors[2] = sprite.getImageColor(spr, i, j).getBlue();
						}
					}

				}
				pixels[j + i * 32] = (p << 24) | (RGBcolors[0] << 16) | (RGBcolors[1] << 8) | RGBcolors[2];
			}
		}
	}

	public void renderSprite(Sprite sprit, ColorArchive color) {
		pixels = new int[sprit.lineSize * sprit.lineSize];
		int p;
		for (int i = 0; i < sprit.lineSize; i++) {
			for (int j = 0; j < sprit.lineSize; j++) {
				p = 255;
				if (color.color.get(sprit.color[i][j]).equals(Color.decode("0xFF0096"))) {
					p = 50;
				}
				RGBcolors[0] = color.color.get(sprit.color[i][j]).getRed();
				RGBcolors[1] = color.color.get(sprit.color[i][j]).getGreen();
				RGBcolors[2] = color.color.get(sprit.color[i][j]).getBlue();

				pixels[j + i * sprit.lineSize] = (p << 24) | (RGBcolors[0] << 16) | (RGBcolors[1] << 8) | RGBcolors[2];
			}
		}
	}

	public void renderCursor(int curs, Color selected) {
		pixels = new int[32 * 32];
		int p;
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 32; j++) {
				p = 255;
				if (cursors.getImageColor(curs, i, j).equals(Color.decode("0xFF0096"))) {
					p = 0;
					RGBcolors[0] = 0;
					RGBcolors[1] = 0;
					RGBcolors[2] = 0;
				} else {
					if (cursors.getImageColor(curs, i, j).getRed() == 45 && cursors.getImageColor(curs, i, j).getGreen() == 45 && cursors.getImageColor(curs, i, j).getBlue() == 45) {
						RGBcolors[0] = selected.getRed();
						RGBcolors[1] = selected.getGreen();
						RGBcolors[2] = selected.getBlue();
					} else {
						RGBcolors[0] = cursors.getImageColor(curs, i, j).getRed();
						RGBcolors[1] = cursors.getImageColor(curs, i, j).getGreen();
						RGBcolors[2] = cursors.getImageColor(curs, i, j).getBlue();
					}

				}
				pixels[j + i * 32] = (p << 24) | (RGBcolors[0] << 16) | (RGBcolors[1] << 8) | RGBcolors[2];
			}
		}
	}
}
