package project;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

public class Sprite {
	private String name;
	private List<Layers> layers;
	private int width;
	private int height;

	protected Sprite(String nam, int num, int wid, int hei) {
		if (nam.isEmpty()) {
			name = "Untitled " + num;
		}
		layers = new ArrayList<Layers>();
		width = wid;
		height = hei;
	}

	protected String getName() {
		return name;
	}

	protected void fillNewLayer(int x, int y, int wid, int hei, boolean lock, boolean visible, Color col) {
		layers.add(new Layers(x, y, wid, hei, lock, visible, col));
	}

	protected Image getImage() {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		for (int i = 0; i < layers.size(); i++) {
			if (layers.get(i).isVisible()) {
				int[] layerPixels = layers.get(i).getPixels();
				for (int r = 0; r < layers.get(i).getHeight(); r++) {
					if (r + layers.get(i).getY() < height) {
						for (int c = 0; c < layers.get(i).getWidth(); c++) {
							if (c + layers.get(i).getX() >= width) {
								break;
							}
							pixels[((r + layers.get(i).getY()) * (width) + (c + layers.get(i).getX()))] = layerPixels[r * width + c];

						}
					}
				}
			}
		}
		return image;
	}
	
	protected int getWidth(){
		return width;
	}
	
	protected int getHeight(){
		return height;
	}

}
