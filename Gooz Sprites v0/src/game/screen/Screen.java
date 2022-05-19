package game.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Screen extends JPanel {
	private static final long serialVersionUID = 1L;

	public void setPressed(boolean newpressed) {
		this.pressed = newpressed;
		if (false) {
			this.timeSprite[timezone] = this.selectedSprite;
			if (timezone > 0) {
				boolean test = false;
				for (int i = 0; i < grid; i++) {
					for (int j = 0; j < grid; j++) {
						if (this.past[timezone][i][j].color != this.pixels[(i * this.grid + j)
								+ (grid * grid) * this.timeSprite[timezone]]) {
							test = true;
							break;
						}
					}
				}
				if (test) {
					for (int i = 0; i < grid; i++) {
						for (int j = 0; j < grid; j++) {
							this.past[timezone][i][j].color = this.pixels[(i * this.grid + j)
									+ (grid * grid) * this.timeSprite[timezone]];
						}
					}
					this.timezone++;
				}
			} else {
				for (int i = 0; i < grid; i++) {
					for (int j = 0; j < grid; j++) {
						this.past[timezone][i][j].color = this.pixels[(i * this.grid + j)
								+ (grid * grid) * this.timeSprite[timezone]];
					}
				}
				this.timezone++;
			}
		}
	}

	public void setMsclick(boolean newms) {
		this.msclick = newms;
		this.click = true;
	}

	Scanner input = new Scanner(System.in);

	public boolean shouldLoad = loadOrNew();
	public String fileName = nameFile(shouldLoad);
	public int sprite = spriteAmound(shouldLoad);
	public int grid = sizeOfGrid(shouldLoad);
	public int selectedSprite = 0;
	public int ghostSprite = 0;
	public int viewedSprite = 0;
	public int boxsiz = 780 / grid;
	public int layerBoxSize = 300 / grid;

	// fill button
	boolean[][] mark = new boolean[grid][grid];

	// Start of program
	public boolean start = true;

	// Undo Redo System
	int[] timeSprite = new int[20];
	Past[][][] past = new Past[20][grid][grid];
	int timezone = 0;

	// colors RGB
	int[] RGB = new int[3];
	int[] useRGB = { 0, 0, 0 };
	int[] userx = { 0, 0, 0 };
	int[] ghostRGB = { 0, 0, 0 };
	boolean ghostDrawn = false;
	// x component spaces
	public int xaxis = 400;

	// y component spaces
	public int yaxis = 40;

	// selected colors
	public String[] selcolor = { "0x000000", "0xFFFFFF" };

	// equal color y movement
	public int ycolor = 250;

	// equal button space
	public int xbutton = 0;

	// button pressed
	private boolean pressed = false;
	public boolean[] button = { true, false, false, true, false, false, false };

	// sprite array
	public String[] pixels = new String[(grid * grid) * sprite];

	Color c;

	// mouse
	private int x = 0;
	private int y = 0;
	private boolean msclick = true;
	public boolean click = false;

	public Screen() {
		for (int k = 0; k < 20; k++) {
			timeSprite[k] = 0;
			for (int i = 0; i < grid; i++) {
				for (int j = 0; j < grid; j++) {
					past[k][i][j] = new Past();
				}

			}
		}
		for (int i = 0; i < (grid * grid) * sprite; i++) {
			pixels[i] = "0xFF0096";
		}
		HandlerClass handler = new HandlerClass();
		addMouseListener(handler);
		addMouseMotionListener(handler);

	}

	private class HandlerClass implements MouseMotionListener, MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			setPressed(true);
			setXY(e.getX(), e.getY());
			if (SwingUtilities.isLeftMouseButton(e)) {
				setMsclick(true);
			} else {
				setMsclick(false);
			}
			repaint();

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			setPressed(false);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			setXY(e.getX(), e.getY());
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
		}

	}

	public void paint(Graphics g) {
		// click on tool
		if (pressed) {
			if (click) {
				// paint button
				if (x >= 30 + xbutton && x <= 80 && y >= 60 && y <= 110) {
					button[0] = true;
					button[1] = false;
					button[2] = false;
					button[3] = true;
					button[4] = false;
					button[5] = false;
					// eraser button
				} else if (x >= 110 + xbutton && x <= 160 && y >= 60 && y <= 110) {
					button[0] = false;
					button[1] = true;
					button[2] = false;
					button[3] = true;
					button[4] = false;
					button[5] = false;
					// color button

				} else if (x >= 190 + xbutton && x <= 240 && y >= 60 && y <= 110) {
					button[0] = false;
					button[1] = false;
					button[2] = true;
					button[3] = true;
					button[4] = false;
					button[5] = false;
					//
					// fill box
				} else if (x >= 270 + xbutton && x <= 320 && y >= 60 && y <= 110) {
					button[0] = false;
					button[1] = false;
					button[2] = false;
					button[3] = true;
					button[4] = false;
					button[5] = true;
				} else if (x >= 170 && x <= 320 && y >= 150 + ycolor && y <= 200 + ycolor) {
					selcolor[0] = decToHex(useRGB);
				}
				// color swap button
				else if (x >= 350 && x <= 370 && y >= 490 + ycolor && y <= 510 + ycolor) {
					String tempc = selcolor[0];
					selcolor[0] = selcolor[1];
					selcolor[1] = tempc;
					hexToDec(selcolor[0]);

				}
				// grid button
				else if (x >= 1200 + xbutton && x <= 1250 && y >= 60 && y <= 110) {
					button[0] = true;
					button[3] = true;
					button[4] = false;
					button[5] = false;
					// render button
				} else if (x >= 1280 + xbutton && x <= 1330 && y >= 60 && y <= 110) {
					button[0] = false;
					button[1] = false;
					button[2] = false;
					button[3] = false;
					button[4] = true;
					button[5] = false;
					// Save Button
				} else if (x >= 1360 + xbutton && x <= 1410 && y >= 60 && y <= 110) {
					try {
						saveSpriteSheet();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// Ghost Button
				} else if (x >= 1440 + xbutton && x <= 1490 && y >= 60 && y <= 110) {
					button[3] = true;
					button[4] = false;
					button[6] = !button[6];
					ghostSprite = viewedSprite;
					// right arrow
				} else if (x >= 1380 + xbutton && x <= 1440 && y >= 700 && y <= 760) {
					viewedSprite++;
					if (viewedSprite == sprite) {
						viewedSprite = 0;
					}
					// left arrow
				} else if (x >= 1230 + xbutton && x <= 1290 && y >= 700 && y <= 760) {
					viewedSprite--;
					if (viewedSprite < 0) {
						viewedSprite = sprite - 1;
					}
				}
			}
			// click on box
			if (x > 400 && x < grid * boxsiz + 400 && y > 40 && y < 40 + grid * boxsiz) {
				int xpos = (x - 400) / boxsiz;
				int ypos = (y - 40) / boxsiz;
				if (button[0]) {
					if (msclick) {
						pixels[(ypos * grid + xpos) + (grid * grid) * selectedSprite] = selcolor[0];
					} else {
						pixels[(ypos * grid + xpos) + (grid * grid) * selectedSprite] = selcolor[1];
					}
				} else if (button[1]) {
					pixels[(ypos * grid + xpos) + (grid * grid) * selectedSprite] = "0xFF0096";
				} else if (button[2]) {
					if (msclick) {
						selcolor[0] = pixels[(ypos * grid + xpos) + (grid * grid) * selectedSprite];
						hexToDec(selcolor[0]);
					} else {
						selcolor[1] = pixels[(ypos * grid + xpos) + (grid * grid) * selectedSprite];
					}

				} else if (button[5]) {
					for (int i = 0; i < grid; i++) {
						for (int j = 0; j < grid; j++) {
							mark[i][j] = false;
						}
					}
					if (msclick) {
						floodFill(xpos, ypos, pixels[(ypos * grid + xpos) + (grid * grid) * selectedSprite],
								selcolor[0], mark);
					} else {
						floodFill(xpos, ypos, pixels[(ypos * grid + xpos) + (grid * grid) * selectedSprite],
								selcolor[1], mark);
					}
				}

			} else if (x >= 1305 + xbutton && x <= 1365 && y >= 700 && y <= 760) {
				selectedSprite = viewedSprite;
			} else if (x >= 25 + userx[0] && x <= 60 + userx[0] && y >= 210 + ycolor && y <= 235 + ycolor) {
				userx[0] = x - 35;
				if (userx[0] > 340) {
					userx[0] = (int) (340);
				} else if (userx[0] < 0) {
					userx[0] = 0;
				}
			} else if (x >= 25 + userx[1] && x <= 60 + userx[1] && y >= 280 + ycolor && y <= 305 + ycolor) {
				userx[1] = x - 35;
				if (userx[1] > 340) {
					userx[1] = (int) (340);
				} else if (userx[1] < 0) {
					userx[1] = 0;
				}
			} else if (x >= 25 + userx[2] && x <= 60 + userx[2] && y >= 350 + ycolor && y <= 375 + ycolor) {
				userx[2] = x - 35;
				if (userx[2] > 340) {
					userx[2] = (int) (340);
				} else if (userx[2] < 0) {
					userx[2] = 0;
				}
			}
		}
		if (start) {
			// draw background
			g.setColor(Color.GRAY.brighter());
			g.fillRect(0, 0, getWidth(), getHeight());

			// draw tool box
			g.setColor(Color.WHITE);
			g.fillRoundRect(5, 40, xaxis - 20, getHeight() - yaxis - 10, 20, 30);
			g.setColor(Color.BLACK);
			g.drawRoundRect(5, 40, xaxis - 20, getHeight() - yaxis - 10, 20, 30);

			// draw utility box
			g.setColor(Color.WHITE);
			g.fillRoundRect(1180, 40, xaxis - 85, getHeight() - yaxis - 10, 20, 30);
			g.setColor(Color.BLACK);
			g.drawRoundRect(1180, 40, xaxis - 85, getHeight() - yaxis - 10, 20, 30);

			// draw save button
			g.setColor(Color.lightGray);
			g.fillRoundRect(1360 + xbutton, 60, 50, 50, 10, 10);
			g.setColor(Color.BLACK);
			g.drawRoundRect(1360 + xbutton, 60, 50, 50, 10, 10);
			g.setFont(new Font("default", Font.BOLD, 30));
			g.drawString("S", 1375, 95);

			// draw left arrow layer box
			g.setColor(Color.lightGray);
			g.fillRoundRect(1230, 700, 60, 60, 10, 10);
			g.setColor(Color.BLACK);
			g.drawRoundRect(1230, 700, 60, 60, 10, 10);
			g.setFont(new Font("default", Font.BOLD, 40));
			g.drawString("<", 1250, 745);

			// draw right arrow layer box
			g.setColor(Color.lightGray);
			g.fillRoundRect(1380, 700, 60, 60, 10, 10);
			g.setColor(Color.BLACK);
			g.drawRoundRect(1380, 700, 60, 60, 10, 10);
			g.setFont(new Font("default", Font.BOLD, 40));
			g.drawString(">", 1400, 745);

			// draw load button
			g.setColor(Color.lightGray);
			g.fillRoundRect(1305, 700, 60, 60, 10, 10);
			g.setColor(Color.BLACK);
			g.drawRoundRect(1305, 700, 60, 60, 10, 10);
			g.setFont(new Font("default", Font.BOLD, 40));
			g.drawString("L", 1325, 745);

			// RGB bars
			g.setFont(new Font("default", Font.BOLD, 40));
			g.drawString("R", 10, 280 + ycolor);
			g.drawString("G", 10, 350 + ycolor);
			g.drawString("B", 10, 420 + ycolor);
			g.drawRect(40, 250 + ycolor, 340, 30);
			g.drawRect(40, 320 + ycolor, 340, 30);
			g.drawRect(40, 390 + ycolor, 340, 30);
			for (int i = 0; i < 256; i++) {
				RGB[0] = i;
				RGB[1] = 0;
				RGB[2] = 0;
				c = Color.decode(decToHex(RGB));
				g.setColor(c);
				g.drawRect(40 + i * 340 / 255, 250 + ycolor, 340 / 255, 30);
				RGB[0] = 0;
				RGB[1] = i;
				RGB[2] = 0;
				c = Color.decode(decToHex(RGB));
				g.setColor(c);
				g.drawRect(40 + i * 340 / 255, 320 + ycolor, 340 / 255, 30);
				RGB[0] = 0;
				RGB[1] = 0;
				RGB[2] = i;
				c = Color.decode(decToHex(RGB));
				g.setColor(c);
				g.drawRect(40 + i * 340 / 255, 390 + ycolor, 340 / 255, 30);

				// draw color swap button
				g.setColor(Color.CYAN);
				g.fillRoundRect(350, 490 + ycolor, 20, 20, 10, 10);
				g.setColor(Color.BLACK);
				g.drawRoundRect(350, 490 + ycolor, 20, 20, 10, 10);
			}
		}
		// draw null color for grid
		if (!button[4]) {
			Color c = Color.decode("0xFF0096");
			g.setColor(c);
			g.fillRect(xaxis, yaxis, grid * boxsiz, grid * boxsiz);
		} else {
			g.setColor(Color.gray.brighter());
			g.fillRect(xaxis, yaxis, grid * boxsiz + 1, grid * boxsiz + 1);
		}

		// draw sprite
		for (int i = 0; i < grid; i++) {
			for (int j = 0; j < grid; j++) {
				if (pixels[(i * grid + j) + (grid * grid) * selectedSprite] != "0xFF0096") {
					c = Color.decode(pixels[(i * grid + j) + (grid * grid) * selectedSprite]);
					g.setColor(c);
					g.fillRect(boxsiz * j + xaxis, boxsiz * i + yaxis, boxsiz, boxsiz);
				}
			}

		}

		// draw Ghost Sprite
		if (button[6] && !button[4]) {
			ghostDrawn = true;
			for (int i = 0; i < grid; i++) {
				for (int j = 0; j < grid; j++) {
					if (pixels[(i * grid + j) + (grid * grid) * ghostSprite] != "0xFF0096") {
						hexToDec(pixels[(i * grid + j) + (grid * grid) * ghostSprite]);
						c = new Color(ghostRGB[0], ghostRGB[1], ghostRGB[2], 125);
						g.setColor(c);
						g.fillRect(boxsiz * j + xaxis, boxsiz * i + yaxis, boxsiz, boxsiz);
					}
				}

			}
		}
		ghostDrawn = false;

		// draw grid
		if (button[3]) {
			for (int i = 0; i < grid; i++) {
				for (int j = 0; j < grid; j++) {
					g.setColor(Color.BLACK);
					g.drawRect(boxsiz * j + xaxis, boxsiz * i + yaxis, boxsiz, boxsiz);
				}

			}
		}

		// draw button for paint
		if (button[0]) {
			g.setColor(Color.GRAY);
		} else {
			g.setColor(Color.lightGray);
		}
		g.fillRoundRect(30 + xbutton, 60, 50, 50, 10, 10);
		g.setColor(Color.BLACK);
		g.drawRoundRect(30 + xbutton, 60, 50, 50, 10, 10);
		c = Color.decode(selcolor[0]);
		g.setColor(c);
		g.drawLine(35 + xbutton, 65, 75, 105);

		// draw button for eraser
		if (button[1]) {
			g.setColor(Color.GRAY);
		} else {
			g.setColor(Color.lightGray);
		}
		g.fillRoundRect(110 + xbutton, 60, 50, 50, 10, 10);
		c = Color.decode("0xFF0096");
		g.setColor(c);
		g.fillOval(115 + xbutton, 65, 40, 40);
		g.setColor(Color.BLACK);
		g.drawRoundRect(110 + xbutton, 60, 50, 50, 10, 10);
		g.drawOval(115 + xbutton, 65, 40, 40);

		// draw button for color duplicate
		if (button[2]) {
			g.setColor(Color.GRAY);
		} else {
			g.setColor(Color.lightGray);
		}
		g.fillRoundRect(190 + xbutton, 60, 50, 50, 10, 10);
		g.setColor(Color.BLACK);
		g.drawRoundRect(190 + xbutton, 60, 50, 50, 10, 10);
		g.drawLine(200 + xbutton, 70, 215 + xbutton, 105);
		g.drawLine(215 + xbutton, 105, 230 + xbutton, 70);
		g.drawLine(200 + xbutton, 70, 230 + xbutton, 70);

		// draw button for color fill
		if (button[5]) {
			g.setColor(Color.GRAY);
		} else {
			g.setColor(Color.lightGray);
		}
		g.fillRoundRect(270 + xbutton, 60, 50, 50, 10, 10);
		g.setColor(Color.BLACK);
		g.drawRoundRect(270 + xbutton, 60, 50, 50, 10, 10);
		g.drawRect(280, 75, 25, 25);
		g.drawLine(305, 75, 310, 85);

		// draw grid off/on button
		if (button[3]) {
			g.setColor(Color.GRAY);
		} else {
			g.setColor(Color.lightGray);
		}
		g.fillRoundRect(1200 + xbutton, 60, 50, 50, 10, 10);
		g.setColor(Color.BLACK);
		g.drawRoundRect(1200 + xbutton, 60, 50, 50, 10, 10);
		g.drawRect(1210, 70, 10, 10);
		g.drawRect(1220, 70, 10, 10);
		g.drawRect(1230, 70, 10, 10);
		g.drawRect(1210, 80, 10, 10);
		g.drawRect(1220, 80, 10, 10);
		g.drawRect(1230, 80, 10, 10);
		g.drawRect(1210, 90, 10, 10);
		g.drawRect(1220, 90, 10, 10);
		g.drawRect(1230, 90, 10, 10);

		// draw render button
		if (button[4]) {
			g.setColor(Color.GRAY);
		} else {
			g.setColor(Color.lightGray);
		}
		g.fillRoundRect(1280 + xbutton, 60, 50, 50, 10, 10);
		g.setColor(Color.BLACK);
		g.drawRoundRect(1280 + xbutton, 60, 50, 50, 10, 10);
		g.setFont(new Font("default", Font.BOLD, 30));
		g.drawString("R", 1295, 95);

		// Ghost Button
		if (button[6]) {
			g.setColor(Color.GRAY);
		} else {
			g.setColor(Color.lightGray);
		}
		g.fillRoundRect(1440 + xbutton, 60, 50, 50, 10, 10);
		g.setColor(Color.BLACK);
		g.drawRoundRect(1440 + xbutton, 60, 50, 50, 10, 10);
		g.setFont(new Font("default", Font.BOLD, 30));
		g.drawString("G", 1455, 95);

		// draw layer name
		g.setColor(Color.DARK_GRAY);
		g.fillRoundRect(1230, 365, 210, 60, 10, 10);
		g.setColor(Color.BLACK);
		g.drawRoundRect(1230, 700, 60, 60, 10, 10);
		g.setColor(Color.WHITE);
		g.setFont(new Font("default", Font.BOLD, 40));
		String line = "Sprite " + viewedSprite;
		g.drawString(line, 1250, 395);

		// fill layer box
		for (int i = 0; i < grid; i++) {
			for (int j = 0; j < grid; j++) {
				c = Color.decode(pixels[(i * grid + j) + (grid * grid) * viewedSprite]);
				g.setColor(c);
				g.fillRect(layerBoxSize * j + 1187, layerBoxSize * i + 400, layerBoxSize, layerBoxSize);

			}

		}

		// draw undo and redo buttons
		if (timezone == 0) {
			g.setColor(Color.GRAY);
		} else {
			g.setColor(Color.lightGray);
		}
		g.fillRoundRect(1270, 320, 40, 40, 10, 10);
		if (timezone == 19) {
			g.setColor(Color.lightGray);
		} else {
			g.setColor(Color.GRAY);
		}
		g.fillRoundRect(1350, 320, 40, 40, 10, 10);
		g.setColor(Color.BLACK);
		g.drawLine(1280, 340, 1300, 340);
		g.drawLine(1280, 340, 1290, 330);
		g.drawLine(1280, 340, 1290, 350);
		g.drawLine(1360, 340, 1380, 340);
		g.drawLine(1380, 340, 1370, 330);
		g.drawLine(1380, 340, 1370, 350);
		g.drawRoundRect(1270, 320, 40, 40, 10, 10);
		g.drawRoundRect(1350, 320, 40, 40, 10, 10);

		// draw layer box
		g.setColor(Color.black);
		g.drawRect(1187, 400, layerBoxSize * grid, layerBoxSize * grid);

		// draw selected colors
		// back color
		c = Color.decode(selcolor[1]);
		g.setColor(c);
		g.fillRect(300, 510 + ycolor, 50, 50);
		g.setColor(Color.black);
		g.drawRect(300, 510 + ycolor, 50, 50);
		// front color
		c = Color.decode(selcolor[0]);
		g.setColor(c);
		g.fillRect(280, 490 + ycolor, 50, 50);
		g.setColor(Color.black);
		g.drawRect(280, 490 + ycolor, 50, 50);

		// draw RGB controlers

		// CLEAR PREVIOUS
		g.setColor(Color.WHITE);
		g.fillRect(20, 210 + ycolor, 380, 40);
		g.fillRect(20, 350 + ycolor, 380, 40);
		g.fillRect(20, 280 + ycolor, 380, 40);

		g.setColor(Color.GRAY.brighter());
		g.fillRect(xaxis - 15, 210 + ycolor, 15, 180);

		g.setColor(Color.black);
		g.drawLine(xaxis - 15, 210 + ycolor, xaxis - 15, 390 + ycolor);

		g.setColor(Color.black);
		g.fillRect(40 + userx[0], 235 + ycolor, 2, 15);
		g.fillRect(40 + userx[1], 305 + ycolor, 2, 15);
		g.fillRect(40 + userx[2], 375 + ycolor, 2, 15);
		g.setColor(Color.WHITE);
		g.fillRect(25 + userx[0], 210 + ycolor, 35, 25);
		g.fillRect(25 + userx[2], 350 + ycolor, 35, 25);
		g.fillRect(25 + userx[1], 280 + ycolor, 35, 25);
		g.setColor(Color.black);
		g.drawRect(25 + userx[0], 210 + ycolor, 35, 25);
		g.drawRect(25 + userx[2], 350 + ycolor, 35, 25);
		g.drawRect(25 + userx[1], 280 + ycolor, 35, 25);
		g.setFont(new Font("default", Font.BOLD, 22));
		useRGB[0] = 255 * userx[0] / 340;
		useRGB[1] = 255 * userx[1] / 340;
		useRGB[2] = 255 * userx[2] / 340;
		g.drawString(useRGB[0] + "", 25 + userx[0], 230 + ycolor);
		g.drawString(useRGB[1] + "", 25 + userx[1], 300 + ycolor);
		g.drawString(useRGB[2] + "", 25 + userx[2], 370 + ycolor);

		// test color
		g.setColor(Color.WHITE);
		g.fillRect(170, 150 + ycolor, 51, 51);
		c = Color.decode(decToHex(useRGB));
		g.setColor(c);
		g.fillRect(170, 150 + ycolor, 50, 50);
		if (selcolor[0].equals(decToHex(useRGB))) {
			g.setColor(Color.green);
			g.drawRect(170, 150 + ycolor, 50, 50);
		}
		g.dispose();

		click = false;
		if (start) {
			// start = false;
		}

		if (pressed) {
			repaint();
		}
	}

	public void setXY(int newx, int newy) {
		this.x = newx;
		this.y = newy;
	}

	public boolean loadOrNew() {
		System.out.println("1)New Sprite Sheet\n2)Load Sprite Sheet");
		char cho = input.nextLine().charAt(0);
		if (cho == '2') {
			return true;
		}
		return false;
	}

	public int spriteAmound(boolean y) {
		int x = 0;
		if (y) {
			File desktop = new File(System.getProperty("user.home"), "Desktop");
			File textFile = new File(desktop, fileName + ".goz");
			BufferedReader in = null;
			try {
				in = new BufferedReader(new FileReader(textFile));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String line;
			try {
				while((line = in.readLine()) != null)
				{
					x++;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.print("Amound of sprites : ");
			x = input.nextInt();
		}
		return x;
	}

	public int sizeOfGrid(boolean y) {
		System.out.print("Rows and Colums of pixels in each sprite : ");
		int x = input.nextInt();
		return x;
	}

	public String nameFile(boolean y) {
		if (y)
			System.out.print("Name of the Sprite Sheet : ");
		else
			System.out.print("Name the Sprite Sheet : ");
		String x = input.nextLine();
		return x;
	}

	public String decToHex(int[] rgb) {
		String[] hex = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
		String result = "";
		for (int i = 0; i < 3; i++) {
			String tempres = "";
			int num = rgb[i];
			int rem;
			int ch = 0;
			while (num != 0) {
				rem = num % 16;
				num /= 16;
				tempres = hex[rem] + tempres;
				ch++;
			}
			if (ch == 0) {
				tempres = "00";
			} else if (ch == 1) {
				tempres = "0" + tempres;
			}
			result = result + tempres;
		}
		return "0x" + result;
	}

	public void hexToDec(String hexcol) {

		String substr;
		int sum;
		for (int i = 0; i < 3; i++) {
			sum = 0;
			substr = "";
			substr = hexcol.substring(2 + 2 * i, 4 + 2 * i);
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
		if (this.ghostDrawn) {

			this.ghostRGB[x] = y;
		} else {
			this.userx[x] = (340 * y) / 255;
		}
	}

	public void saveSpriteSheet() throws IOException {
		File desktop = new File(System.getProperty("user.home"), "Desktop");
		File textFile = new File(desktop, fileName + ".goz");
		BufferedWriter out = new BufferedWriter(new FileWriter(textFile));
		try {

			// MAKES AN ARRAY LIST WITH ALL COLORS IN SPRITE SHEET AND GETS HOW
			// MANY THEY ARE
			List<ColorsSearch> allColors = new ArrayList<>();
			int colorCount = 0;
			for (int k = 0; k < sprite; k++) {
				for (int i = 0; i < grid; i++) {
					for (int j = 0; j < grid; j++) {
						boolean test = true;
						for (int q = 0; q < colorCount; q++) {

							if (allColors.get(q).getNames().equals(pixels[(i * grid + j) + (grid * grid) * k])) {
								allColors.get(q).setCount(allColors.get(q).getCount() + 1);
								test = false;
								break;
							}
						}
						if (test) {
							colorCount++;
							allColors.add(new ColorsSearch(pixels[(i * grid + j) + (grid * grid) * k], 1));
						}
					}
				}
			}

			for (int i = 0; i < colorCount; i++) {
				if (allColors.get(i).getCount() == 1) {
					allColors.remove(i);
					colorCount--;
					i--;
				}
			}

			int n = allColors.size();

			for (int i = 0; i < n; i++) {
				for (int j = 1; j < (n - i); j++) {

					if (allColors.get(j - 1).getCount() < allColors.get(j).getCount()) {
						int temp = allColors.get(j - 1).getCount();
						String temps = allColors.get(j - 1).getNames();
						allColors.set(j - 1,
								new ColorsSearch(allColors.get(j).getNames(), allColors.get(j).getCount()));
						allColors.set(j, new ColorsSearch(temps, temp));
					}

				}
			}
			if (colorCount > 46) {
				colorCount = 46;
			}
			String[] finalComp = new String[colorCount];

			for (int i = 0; i < colorCount; i++) {
				finalComp[i] = allColors.get(i).getNames().substring(2, 8);
				out.write(finalComp[i]);
			}
			out.newLine();
			for (int k = 0; k < sprite; k++) {
				for (int i = 0; i < grid; i++) {
					for (int j = 0; j < grid; j++) {
						boolean test = true;
						int num = 0;
						for (int q = 0; q < colorCount; q++) {
							if (finalComp[q].equals(pixels[(i * grid + j) + (grid * grid) * k].substring(2, 8))) {
								test = false;
								num = q;
								break;
							}
						}
						if (test) {
							out.write(pixels[(i * grid + j) + (grid * grid) * k].substring(2, 8));
						} else {
							if (num <= 20) {
								out.write((char) ((int) 'G' + num));
							} else {
								out.write((char) ((int) 'a' + num - 21));
							}
						}

					}

				}
				out.newLine();
			}
		} finally

		{
			out.close();
		}
	}

	public void floodFill(int x, int y, String targetColor, String replacementColor, boolean mark[][]) {
		if (mark[x][y])
			return;
		if (this.pixels[(y * this.grid + x) + (this.grid * this.grid) * this.selectedSprite] != targetColor)
			return;
		this.pixels[(y * this.grid + x) + (this.grid * this.grid) * this.selectedSprite] = replacementColor;
		mark[x][y] = true;
		if (x > 0)
			floodFill(x - 1, y, targetColor, replacementColor, mark);
		if (x < this.grid - 1)
			floodFill(x + 1, y, targetColor, replacementColor, mark);
		if (y > 0)
			floodFill(x, y - 1, targetColor, replacementColor, mark);
		if (y < this.grid - 1)
			floodFill(x, y + 1, targetColor, replacementColor, mark);

	}

}
