package map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import math.Vertex2i;

public class HexMap {
	private int width;
	private int height;
	private int map[][];
	private boolean mapView[][];
	private Color mapCol[][];

	private Polygon hex;
	private float size;
	private Vertex2i sel = new Vertex2i(-1, -1);

	public HexMap(int wid, int hei, float size, String texture) {
		width = wid;
		height = hei;
		map = new int[wid][hei];
		mapCol = new Color[wid][hei];
		mapView = new boolean[wid][hei];
		this.size = size;
		hex = makeHexagon(size);
		generateMap(texture);
	}

	public void generateMap(String texture) {
		BufferedImage bi;
		try {
			bi = ImageIO.read(new File(texture));
		} catch (IOException e) {
			return;
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				map[i][j] = 1;
				mapCol[i][j] = new Color(bi.getRGB(i, j));
			}
		}
	}

	public void draw(Graphics2D g, float size, int x, int y, int wid, int hei) {
		if (size != this.size) {
			this.size = size;
			hex = makeHexagon(size);
		}
		Rectangle rec = new Rectangle(wid, hei);
		float a = (float) (Math.sqrt(3) * (size / 2));
		for (int i = 0; i < height; i++) {
			boolean over = false;
			for (int j = 0; j < width; j++) {
				Polygon p = movePolygon(hex, j * ((3 * size) / 2) - x, (j % 2) * a + 2 * i * a - y);
				if (rec.intersects(p.getBounds2D())) {
					if (!mapView[j][i])
						g.setColor(mapCol[j][i]);
					else
						g.setColor(Color.GRAY.brighter());
					g.fillPolygon(p);

					if (sel.x == j && sel.y == i) {
						g.setColor(Color.MAGENTA);
						g.fillPolygon(p);
					}

					g.setColor(Color.GRAY);
					//g.drawString(j+" "+i, j * ((3 * size) / 2) - x, (j % 2) * a + 2 * i * a - y);
					g.drawPolygon(p);
				}
				if (p.ypoints[0] > hei) {
					over = true;
				}
				if (p.xpoints[5] > wid) {
					break;
				}

			}

			if (over)
				break;
		}

	}

	private Polygon makeHexagon(float size) {
		Polygon hex = new Polygon();
		float a = (float) (Math.sqrt(3) * (size / 2));
		hex.addPoint((int) (size / 2 + 0.5), (int) -a);
		hex.addPoint((int) (size + 0.5), 0);
		hex.addPoint((int) (size / 2 + 0.5), (int) a);
		hex.addPoint((int) -(size / 2 + 0.5), (int) a);
		hex.addPoint((int) -(size + 0.5), 0);
		hex.addPoint((int) -(size / 2 + 0.5), -(int) a);

		return hex;
	}

	private Polygon movePolygon(Polygon po, float x, float y) {
		Polygon pol = new Polygon();
		for (int i = 0; i < po.npoints; i++) {
			pol.addPoint((int) (po.xpoints[i] + x + 0.5), (int) (po.ypoints[i] + y + 0.5));
		}
		return pol;
	}

	public int getWidth() {
		return (int) (width * ((3 * size) / 2) + 0.5);
	}

	public int getHeight() {
		int a = (int) (Math.sqrt(3) * (size / 2) + 0.5);

		return (int) (height * (2 * a) + 2 * a);
	}

	public int click(int x, int y, int mouseX, int mouseY) {

		float a = (float) (Math.sqrt(3) * (size / 2));
		for (int i = 0; i < height; i++) {
			int j;
			for (j = 0; j < width; j++) {
				if (movePolygon(hex, j * ((3 * size) / 2) - x, ((j % 2) * a + 2 * i * a) - y).contains(mouseX,
						mouseY)) {
					sel = new Vertex2i(j, i);
					break;
				}
			}
			if (j != width) {
				System.out.println(j + " " + i);

				break;
			}
		}

		return mouseY;
	}
	
	public void viewGrid(List<Vertex2i> view){
		for (Vertex2i v: view){
			mapView[v.y][v.x] = true;
		}
	}
}
