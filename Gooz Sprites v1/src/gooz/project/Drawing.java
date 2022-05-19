package gooz.project;

import java.awt.Color;

public class Drawing {
	public Color[][] spr;
	public String title;
	public int lineSize;
	public int sX;
	public int sY;

	public Drawing(int line, String nam) {
		lineSize = line;
		title = nam;
		spr = new Color[line][line];
		for (int i = 0; i < line; i++) {
			for (int j = 0; j < line; j++) {
				spr[i][j] = Color.decode("0x"+"FF0096");
			}
		}
	}

	public void TouchCheck(int x, int y, Color col,int boxSize) {
		boolean flag = false;
		if (!(x > lineSize * boxSize || y > boxSize*lineSize || x < 0 || y < 0)) {
			for (int i = 0; i < lineSize; i++) {
				for (int j = 0; j < lineSize; j++) {
					if (x >= boxSize * j && x <= boxSize * j + boxSize && y >= boxSize * i && y <= boxSize * i + boxSize) {
						spr[i][j] = col;
						flag = true;
						sX=j;
						sY=i;
						break;
					}

				}
				if (flag) {
					break;
				}
			}
		}
	}
	
	public boolean touch(int x, int y,int boxSize){
		if (!(x > lineSize * boxSize || y > boxSize*lineSize || x < 0 || y < 0)) {
			return true;
		}
		return false;
	}
}

