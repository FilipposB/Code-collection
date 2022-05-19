package gooz.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import gooz.button.ButtonLoader;
import gooz.engine.SelectedColors;
import gooz.project.Project;
import gooz.sprite.SpriteRender;

public class Screen extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;

	private BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	ArrayList<Project> proj = new ArrayList<Project>();
	ButtonLoader but = new ButtonLoader();
	SelectedColors selCol = new SelectedColors();
	SpriteRender render = new SpriteRender(32, 32);

	// width and height are constant values for the size of the JPanel
	int width;
	int height;
	int xaxis;
	int yaxis;

	// mouse
	// mouse position
	int x;
	int y;
	int oldX;
	int oldY;
	int com;
	public int boxsiz;

	// mouse states
	boolean pressed = false;
	boolean leftClick = true;
	boolean click = false;

	// priority
	boolean free = true;
	boolean inputBox = false;
	boolean prevBox = false;

	// Project Box
	String name = "";
	boolean newProject = false;

	// Sprite Box
	String line = "";
	boolean newSprite = false;

	// Projects
	int[] projNum = new int[5];
	int selProj = -1;

	// Sprites
	int[] spriNum = new int[3];
	int selSpr = -1;
	int prevSpr = -1;

	// init
	boolean init = true;

	Color c;

	public Screen() {
		for (int i = 0; i < 5; i++) {
			if (i < 3)
				spriNum[i] = -1;
			projNum[i] = -1;
		}
		addKeyListener(this);
		setFocusable(true);
		requestFocusInWindow();
		setFocusTraversalKeysEnabled(false);
		HandlerClass handler = new HandlerClass();
		addMouseListener(handler);
		addMouseMotionListener(handler);
	}

	public void paint(Graphics g) {

		if (init) {
			// add values to width and Height
			width = getWidth();
			height = width / 16 * 9;

			xaxis = width / 500 + width / 300 * 50;
			yaxis = height / 13;
			but = new ButtonLoader(getWidth(), getHeight());
			but.Layout();

			// add background color
			{
				c = Color.decode("0x" + "D0DDE2");
				g.setColor(c);
				g.fillRect(0, 0, width, height);
			}

			// add blank space for sprite to be drawn on
			{
				c = Color.decode("0x" + "696969");
				g.setColor(c);
				g.fillRect(width / 500 + width / 300 * 4, height / 14, width - width / 250 * 55, height / 100 * 96);
			}

		}

		but = new ButtonLoader(getWidth(), getHeight());
		but.Layout();

		if (pressed) {

		}

		// add blank space for sprite to be drawn on
		if (prevSpr != selSpr || prevBox != inputBox) {
			c = Color.decode("0x" + "696969");
			g.setColor(c);
			g.fillRect(width / 500 + width / 300 * 4, height / 14, width - width / 250 * 55, height / 100 * 96);
		}

		// add open projects bar
		{
			c = Color.decode("0x" + "fcfdfd");
			g.setColor(c);

			g.fillRect(width / 500 + width / 300 * 7, height / 27, width - width / 200 * 50, height / 110 * 4);
			g.setColor(Color.black);
			g.drawRect(width / 500 + width / 300 * 7, height / 27, width - width / 200 * 50, height / 110 * 4);
		}

		// add the 2 menu panels
		{

			c = Color.decode("0x" + "B5B5B5");
			g.setColor(c);
			g.fillRoundRect(width / 500, height / 27, width / 130 * 3, height / 100 * 100, width / 300, width / 300);
			c = Color.decode("0x" + "fcfdfd");
			g.setColor(c);
			g.fillRoundRect(width - width / 200 * 46, height / 27, width / 200 * 45, height / 100 * 100, width / 200,
					width / 200);

			g.setColor(Color.BLACK);
			g.drawRoundRect(width / 500, height / 27, width / 130 * 3, height / 100 * 100, width / 300, width / 300);
			g.drawRoundRect(width - width / 200 * 46, height / 27, width / 200 * 45, height / 100 * 100, width / 200,
					width / 200);
		}

		// addSelectedColors
		{
			g.setColor(Color.BLACK);
			g.drawRect(width / 150, height / 120 + height / 100 * 73, width / 60, width / 60);
			g.setColor(selCol.secondary);
			g.fillRect(width / 150, height / 120 + height / 100 * 73, width / 60, width / 60);
			g.setColor(selCol.primary);
			g.fillRect(width / 500, height / 55 + height / 100 * 71, width / 60, width / 60);
			g.setColor(Color.BLACK);
			g.drawRect(width / 500, height / 55 + height / 100 * 71, width / 60, width / 60);
		}

		// add the panel for other sprite viewing
		{
			c = Color.decode("0x" + "B5B5B5");
			g.setColor(c);
			g.fillRect(width - width / 200 * 44, height / 100 * 60, width / 100 * 19, height / 100 * 42);
			g.setColor(Color.black);
			g.drawRect(width - width / 200 * 44, height / 100 * 60, width / 100 * 19, height / 100 * 42);
			c = Color.decode("0x" + "696969");
			g.setColor(c);
			g.fillRect(width - width / 200 * 43, height / 100 * 61, width / 100 * 18, height / 100 * 37);
			g.setColor(Color.black);
			g.drawRect(width - width / 200 * 43, height / 100 * 61, width / 100 * 18, height / 100 * 37);
		}

		// add RGB color selector
		{
			c = Color.decode("0x" + "B5B5B5");
			g.setColor(c);
			g.fillRect(width - width / 200 * 44, height / 100 * 45, width / 100 * 19, height / 100 * 14);
			g.setColor(Color.black);
			g.drawRect(width - width / 200 * 44, height / 100 * 45, width / 100 * 19, height / 100 * 14);

			g.setColor(Color.black);
			g.setFont(new Font("default", Font.PLAIN, width / 95));
			g.drawString("R:", width - width / 200 * 43, height / 100 * 49);
			g.drawString("G:", width - width / 200 * 43, height / 100 * 53);
			g.drawString("B:", width - width / 200 * 43, height / 100 * 57);
			g.setColor(Color.WHITE);
			g.fillRect(width - width / 200 * 40, height / 130 * 61, width / 100 * 12, height / 100 * 2);
			g.fillRect(width - width / 200 * 40, height / 130 * 66, width / 100 * 12, height / 100 * 2);
			g.fillRect(width - width / 200 * 40, height / 130 * 71, width / 100 * 12, height / 100 * 2);
			g.setColor(Color.black);
			g.drawRect(width - width / 200 * 40, height / 130 * 61, width / 100 * 12, height / 100 * 2);
			g.drawRect(width - width / 200 * 40, height / 130 * 66, width / 100 * 12, height / 100 * 2);
			g.drawRect(width - width / 200 * 40, height / 130 * 71, width / 100 * 12, height / 100 * 2);

			// Color box
			c = selCol.test;
			g.setColor(c);
			g.fillRect(width - width / 200 * 12, height / 100 * 49, width / 100 * 3, height / 100 * 6);
			g.setColor(Color.black);
			g.drawRect(width - width / 200 * 12, height / 100 * 49, width / 100 * 3, height / 100 * 6);
			g.setColor(Color.white);

			g.drawLine(width - width / 200 * 12, height / 100 * 49 + height / 100 * 6,
					width - width / 200 * 12 + width / 100 * 3, height / 100 * 49 + height / 100 * 6);
			g.drawLine(width - width / 200 * 12 + width / 100 * 3, height / 100 * 49 + height / 100 * 6,
					width - width / 200 * 12 + width / 100 * 3, height / 100 * 49);
		}

		// menu options
		g.setColor(Color.black);
		g.setFont(new Font("default", Font.PLAIN, width / 95));
		g.drawString("File     Edit", width / 110, height / 35);

		// buttons
		for (int i = 0; i < but.button.length; i++) {
			if (but.button[i].visible) {

				/*
				 * render.clear(); render.renderButton(but.button[i].sprite);
				 * importImage(i); g.drawImage(image, but.button[i].x,
				 * but.button[i].y, but.button[i].wid, but.button[i].hei, null);
				 */
				g.fillRect(but.button[i].x, but.button[i].y, but.button[i].wid, but.button[i].hei);
			}
		}

		// draw open projects
		for (int i = 7; i < 12; i++) {
			if (projNum[i - 7] != -1) {
				c = Color.decode("0x" + "B5B5B5");
				if (projNum[i - 7] == selProj) {
					c = Color.decode("0x" + "8F8F8F");
				}
				g.setColor(c);
				g.fillRect(but.button[i].x, but.button[i].y, but.button[i].wid, but.button[i].hei);
				g.setColor(Color.black);
				g.drawRect(but.button[i].x, but.button[i].y, but.button[i].wid, but.button[i].hei);
				g.drawString(proj.get(projNum[i - 7]).name, but.button[i].x + but.button[7].x * 10 / 100,
						but.button[i].y + but.button[i].hei / 10 * 8);
			} else {
				break;
			}
		}

		// draw open sprites
		for (int k = 22; k >= 20; k--) {
			if (spriNum[22 - k] != -1) {
				if (spriNum[22 - k] == selSpr) {
					g.setColor(Color.white);
					g.fillRect(but.button[k].x, but.button[k].y, but.button[k].wid, but.button[k].hei);
				}

				render.clear();
				render.renderButton(but.button[22 - k + 15].sprite);
				importImage(22 - k + 15);
				g.drawImage(image, but.button[22 - k + 15].x, but.button[17 - k + 20].y, but.button[22 - k + 15].wid,
						but.button[22 - k + 15].hei, null);

				g.setColor(Color.BLACK);
				g.drawRect(but.button[k].x, but.button[k].y, but.button[k].wid, but.button[k].hei);
				g.drawString(proj.get(projNum[selProj]).sprite.get(spriNum[22 - k]).title,
						but.button[k].x + but.button[k].x * 10 / 100, but.button[k].y + but.button[k].hei / 100 * 90);
				BufferedImage image = new BufferedImage(proj.get(selProj).sprite.get(spriNum[22 - k]).lineSize,
						proj.get(selProj).sprite.get(spriNum[22 - k]).lineSize, BufferedImage.TYPE_INT_ARGB);
				int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
				SpriteRender spr = new SpriteRender(proj.get(selProj).sprite.get(spriNum[22 - k]).lineSize,
						proj.get(selProj).sprite.get(spriNum[22 - k]).lineSize);
				for (int i = 0; i < proj.get(selProj).sprite.get(spriNum[22 - k]).lineSize; i++) {
					for (int j = 0; j < proj.get(selProj).sprite.get(spriNum[22 - k]).lineSize; j++) {
						c = proj.get(selProj).sprite.get(spriNum[22 - k]).spr[i][j];
						spr.makeCanvas(c, i * proj.get(selProj).sprite.get(spriNum[22 - k]).lineSize + j,
								proj.get(selProj).sprite.get(spriNum[22 - k]).lineSize);
					}
				}
				for (int i = 0; i < pixels.length; i++) {
					pixels[i] = spr.pixels[i];
				}
				g.drawImage(image, but.button[k].x, but.button[k].y, but.button[k].hei, but.button[k].hei, null);
				g.drawRect(but.button[k].x, but.button[k].y, but.button[k].hei, but.button[k].hei);

			}

		}

		// SPRITE !!
		if (!proj.isEmpty() && selSpr != -1) {
			if (!proj.get(selProj).sprite.isEmpty()) {
				// width - width / 250 * 55
				boxsiz = ((width - width / 250 * 60) - (width / 500 + width / 300 * 100))
						/ proj.get(selProj).sprite.get(selSpr).lineSize;

				if (free) {
					if (leftClick) {
						proj.get(selProj).sprite.get(selSpr).TouchCheck(x - xaxis, y - yaxis, selCol.primary, boxsiz);
						int k = proj.get(selProj).sprite.get(selSpr).sY;
						int f = proj.get(selProj).sprite.get(selSpr).sX;

						System.out.println(oldX + " " + oldY);
						System.out.println(f + " " + k);

						line(f, k, oldX, oldY, selCol.primary);

					} else {
						proj.get(selProj).sprite.get(selSpr).TouchCheck(x - xaxis, y - yaxis, selCol.secondary, boxsiz);
						int k = proj.get(selProj).sprite.get(selSpr).sY;
						int f = proj.get(selProj).sprite.get(selSpr).sX;
						line(f, k, oldX, oldY, selCol.secondary);
					}
				}
				for (int i = 0; i < proj.get(selProj).sprite.get(selSpr).lineSize; i++) {
					for (int j = 0; j < proj.get(selProj).sprite.get(selSpr).lineSize; j++) {
						c = proj.get(selProj).sprite.get(selSpr).spr[i][j];
						g.setColor(c);
						g.fillRect(boxsiz * j + xaxis, boxsiz * i + yaxis, boxsiz, boxsiz);
						g.setColor(Color.BLACK);
						g.drawRect(boxsiz * j + xaxis, boxsiz * i + yaxis, boxsiz, boxsiz);
					}
				}
			}

		}

		// input box
		if (inputBox) {
			g.setColor(Color.WHITE);
			g.fillRect(width / 5, height / 5, width / 2, height / 2);
			c = Color.decode("0x" + "B5B5B5");
			g.setColor(c);
			g.fillRect(but.button[13].x, but.button[13].y, but.button[13].wid, but.button[13].hei);
			g.fillRect(width / 5, height / 5, width / 2, height / 25);
			g.setColor(Color.BLACK);
			g.drawRect(width / 5, height / 5, width / 2, height / 2);
			g.drawRect(but.button[13].x, but.button[13].y, but.button[13].wid, but.button[13].hei);
			render.clear();
			render.renderButton(but.button[12].sprite);
			importImage(12);
			g.drawImage(image, but.button[12].x, but.button[12].y, but.button[12].wid, but.button[12].hei, null);
			g.drawString("Done", width / 100 * 62, height / 100 * 70);
			if (newProject) {
				g.drawString("Name the new Sprite Sheet !", width / 5 + width / 50, height / 5 + height / 40);
				g.drawRect(width / 100 * 25, height / 100 * 40, width / 100 * 40, height / 20);
				g.setFont(new Font("default", Font.PLAIN, width / 95 * 2));
				g.drawString(name, width / 100 * 25, height / 100 * 44);
			} else if (newSprite) {
				g.drawString("Initialize the sprite !", width / 5 + width / 50, height / 5 + height / 40);
				g.drawString("Pixels per line (128 max)  :", width / 100 * 21, height / 100 * 49);
				g.drawRect(width / 100 * 35, height / 100 * 45, width / 100 * 5, height / 20);
				g.setFont(new Font("default", Font.PLAIN, width / 95 * 2));
				g.drawString(line + "", width / 100 * 35, height / 100 * 49);
			}
		}

		g.dispose();
		if (init)
			init = false;
		if (click)
			click = false;
		if (pressed)
			repaint();
	}

	void input() {
		// if (proj.size()>0)if
		// (proj.get(0).sprite.size()>0)System.out.println(proj.get(0).sprite.get(0).spr[0][0]);
		/*
		 * --COMMAND LIST-- ___ FREE COMANDS : 1)OPENS THE NEW PROJECT WINDOW
		 * 2)MOVE PROJECT BAR 3)OPENS THE NEW SPRITE WINDOW 4)Swap colors
		 * 7x)SELECT x PROJECT 6x)SELECT x SPRTIE ___ !FREE COMMANDS : 4)X
		 * BUTTON IN A NEW WINDOW 5)DONE BUTTON IN A NEW WINDOW
		 * 
		 * ___
		 */
		if (click) {
			com = but.ClickButton(x, y);
			if (but.ClickButton(x, y) != 0) {
				if (free) {
					if (com == 1) {
						free = false;
						inputBox = true;
						newProject = true;
					} else if (com == 2) {
						fixProjectBar();
					} else if (com == 3 && proj.size() > 0) {

						free = false;
						inputBox = true;
						newSprite = true;
					} else if (com == 4) {
						selCol.swapColors();
					} else if (com / 10 == 7 && projNum[com - 70] != -1) {
						com -= 70;
						selProj = projNum[com];
						for (int i = 0; i < proj.get(selProj).sprite.size() && i < 3; i++) {
							spriNum[i] = i;
						}
						if (proj.get(selProj).sprite.size() - 1 < 3) {
							for (int i = proj.get(selProj).sprite.size(); i < 3; i++) {
								spriNum[i] = -1;
							}
						}
					} else if (com / 10 == 6) {
						com -= 60;
						boolean flag = true;
						if (com >= 3) {
							com -= 3;
							flag = false;
						}
						if (flag) {
							if (spriNum[com] != -1) {
								selSpr = spriNum[com];
							}
						} else if (spriNum.length > 0) {
							proj.get(selProj).sprite.remove(com);
							spriNum[com] = -1;
							if (com == 2) {
								selSpr = spriNum[1];
							} else if (com == 1) {
								selSpr = spriNum[0];
							} else {
								selSpr = -1;
							}
							if (proj.get(selProj).sprite.size() != 0) {
								while (spriNum[0] == -1) {
									fixSpriteDisplay();
								}
							}

						}

					}
				} else {
					if (com == 4) {
						closeWindow(false);
					} else if (com == 5
							&& (name.length() > 0 || (Integer.parseInt(line) <= 128 && line.length() > 0))) {
						closeWindow(true);

					}
				}

			}

		}
	}

	public void line(int x, int y, int x2, int y2, Color col) {
		int w = x2 - x;
		int h = y2 - y;
		int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
		if (w < 0)
			dx1 = -1;
		else if (w > 0)
			dx1 = 1;
		if (h < 0)
			dy1 = -1;
		else if (h > 0)
			dy1 = 1;
		if (w < 0)
			dx2 = -1;
		else if (w > 0)
			dx2 = 1;
		int longest = Math.abs(w);
		int shortest = Math.abs(h);
		if (!(longest > shortest)) {
			longest = Math.abs(h);
			shortest = Math.abs(w);
			if (h < 0)
				dy2 = -1;
			else if (h > 0)
				dy2 = 1;
			dx2 = 0;
		}
		int numerator = longest >> 1;
		for (int i = 0; i <= longest; i++) {
			proj.get(selProj).sprite.get(selSpr).TouchCheck(x, y, col, 1);
			numerator += shortest;
			if (!(numerator < longest)) {
				numerator -= longest;
				x += dx1;
				y += dy1;
			} else {
				x += dx2;
				y += dy2;
			}
		}
	}

	public void setPressed(boolean x) {
		pressed = x;
	}

	public void setXY(int newX, int newY) {
		if (!proj.isEmpty() && selSpr != -1) {
			if (!proj.get(selProj).sprite.isEmpty()) {
				oldX = x - xaxis;
				oldY = y - yaxis;
				if (proj.get(selProj).sprite.get(selSpr).touch(oldX,oldY, boxsiz)){
					oldX/=boxsiz;
					oldY/=boxsiz;
				}
				
			}
		}
		x = newX;
		y = newY;

	}

	public void setMouseClick(boolean x) {
		leftClick = x;
		click = true;
		prevSpr = selSpr;
		prevBox = inputBox;
		input();
	}

	void closeWindow(boolean x) {

		// close and save
		if (x) {
			if (newProject) {
				free = true;
				inputBox = false;
				newProject = false;
				proj.add(new Project(name));
				fixProjectBar();
				selProj = proj.size() - 1;
				for (int i = 0; i < proj.get(selProj).sprite.size() && i < 3; i++) {
					spriNum[i] = i;
				}
				if (proj.get(selProj).sprite.size() - 1 < 3) {
					for (int i = proj.get(selProj).sprite.size(); i < 3; i++) {
						spriNum[i] = -1;
					}
				}
				name = "";
			} else if (newSprite) {
				free = true;
				inputBox = false;
				newSprite = false;
				proj.get(selProj).addSprite(Integer.parseInt(line), "Sprite " + proj.get(selProj).sprite.size());
				selSpr = proj.get(selProj).sprite.size() - 1;
				fixSpriteDisplay();
				line = "";
			}
			repaint();
		}
		// close
		else {
			if (newProject) {
				free = true;
				inputBox = false;
				newProject = false;
				name = "";
			} else if (newSprite) {
				free = true;
				inputBox = false;
				newSprite = false;
				line = "";
			}
			repaint();
		}

	}

	void fixSpriteDisplay() {
		if (spriNum[0] == -1) {
			if (proj.get(selProj).sprite.size() > 0)
				spriNum[0] = 0;
		}
		if (spriNum[1] == -1) {
			if (proj.get(selProj).sprite.size() > 1)
				spriNum[1] = 1;
		}
		if (spriNum[2] == -1) {
			if (proj.get(selProj).sprite.size() > 2)
				spriNum[2] = 2;
		} else {
			for (int i = 0; i < 3; i++) {
				spriNum[i]++;
				if (spriNum[i] >= proj.get(selProj).sprite.size()) {
					spriNum[i] -= proj.get(selProj).sprite.size();
				}
			}

		}
	}

	void fixProjectBar() {
		if (projNum[0] == -1) {
			if (proj.size() > 0)
				projNum[0] = 0;
			return;
		} else if (projNum[1] == -1) {
			if (proj.size() > 1)
				projNum[1] = 1;
			return;
		} else if (projNum[2] == -1) {
			if (proj.size() > 2)
				projNum[2] = 2;
			return;
		} else if (projNum[3] == -1) {
			if (proj.size() > 3)
				projNum[3] = 3;
			return;
		} else if (projNum[4] == -1) {
			if (proj.size() > 4)
				projNum[4] = 4;
			return;
		} else {
			for (int i = 0; i < 5; i++) {
				projNum[i]++;
				if (projNum[i] >= proj.size()) {
					projNum[i] -= proj.size();
				}
			}
		}
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
				setMouseClick(true);
			} else {
				setMouseClick(false);
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
			setXY(e.getX(), e.getY());

		}

	}

	public void importImage(int index) {
		for (int i = 0; i < pixels.length; i++) {
			this.pixels[i] = render.pixels[i];
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if (newProject) {

			if ((int) e.getKeyChar() == 8 && name.length() > 0) {
				name = name.substring(0, name.length() - 1);
				repaint();
			} else if (e.getKeyChar() == (KeyEvent.VK_ENTER) && name.length() > 0) {
				closeWindow(true);
			} else if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
				closeWindow(false);
			} else if (name.length() < 20 && e.getKeyChar() != KeyEvent.VK_SPACE && (int) e.getKeyChar() != 8
					&& e.getKeyChar() != (KeyEvent.VK_ENTER)) {
				name += e.getKeyChar();
				repaint();
			}
		}
		if (newSprite) {
			if ((int) e.getKeyChar() == 8 && line.length() > 0) {
				line = line.substring(0, line.length() - 1);
				repaint();
			} else if (e.getKeyChar() == (KeyEvent.VK_ENTER) && line.length() > 0) {
				if (Integer.parseInt(line) <= 128) {
					closeWindow(true);
				}

			} else if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
				closeWindow(false);
			} else if (line.length() <= 2) {
				if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
					line += e.getKeyChar();
					repaint();
				}

			}
		}
	}

}
