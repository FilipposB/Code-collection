package engine;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import button.Button;
import button.ButtonLoader;
import project.Project;
import render.ImageRender;

public class Handler {
	// Sprite Loader
	private ImageRender render;

	// Translator
	private Translator tran = new Translator();
	// User
	// User Selection
	public int selSprite;
	public int selProject;

	private Color firstCol = Color.decode("0x000000");
	private Color secondCol = Color.decode("0xFFFFFF");

	private int preX;
	private int preY;

	// Size
	private int width;
	private int height;

	// RGB Color Selector
	public int R = 0;
	public int G = 0;
	public int B = 0;

	// Axis and Box Size
	public int xAxis;
	public int yAxis;
	public int boxSize;
	
	private BufferedImage image;
	private int[] sprPixels;

	// Buttons
	private ButtonLoader button;
	public Button but;
	
	// ss

	// Project
	private List<Project> proj = new ArrayList<Project>();

	// Focus
	private boolean spriteFocus = false;
	private int focus;
	private int selectedAction = 0;

	// Sprite Scroller
	private int scroll = 0;
	public int colScroll = 0;
	
	// TempSprite
		public Image tmpSprite;
		public Image tmpDisSprite[];

	// Cursor
	private Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
	

	public Handler(int wid, int hei) {
		setDimensions(wid, hei);
		render = new ImageRender();
	}

	public void setDimensions(int wid, int hei) {
		width = wid;
		height = hei;
		button = new ButtonLoader(width, height);
		spriteReset();
		button.scrolls(R, G, B);
	}

	// Default Axis position
	private void spriteReset() {
		xAxis = (int) (width * 0.1);
		yAxis = (int) (height * 0.07);
		if (proj.size() > 0) {
			if (proj.get(selProject).sprite.size() != 0) {
				boxSize = (int) (((width * 0.5)) / getLineSize());
				if (boxSize < 1)
					boxSize = 1;
			}
		}
	}

	// send Project values

	// The amount of Projects
	public int getProjectAmount() {
		return proj.size();
	}

	// Name of Project
	public String getProjectName(int i) {
		if (proj.get(i).name.length() > 15) {
			return proj.get(i).name.substring(0, 12) + "...";
		}
		return proj.get(i).name;
	}

	// send Sprite values

	// getScroll
	public int getScroll() {
		return scroll;
	}

	// the amount of Sprite
	public int getSpriteAmount() {
		return proj.get(selProject).sprite.size();
	}

	// name of Sprite
	public String getSpriteName(int i) {
		i += scroll;
		if (proj.get(selProject).sprite.get(i).title.length() > 15) {
			return proj.get(selProject).sprite.get(i).title.substring(0, 12) + "...";
		}
		return proj.get(selProject).sprite.get(i).title;
	}

	// lineSize of open Sprite
	public int getLineSize() {
		return proj.get(selProject).sprite.get(selSprite).lineSize;
	}

	// get box color
	public Color getBoxColor(int i, int j) {
		return proj.get(selProject).col.color.get(proj.get(selProject).sprite.get(selSprite).color[i][j]);
	}

	// send the Coordinates to the proper Method
	public void setCoords(int x, int y, boolean press, boolean release, boolean leftClick) {

		if (press) {
			firstPress(x, y, leftClick);
		} else if (release) {
			releasePress(x, y, leftClick);
		} else {
			dragPress(x, y, leftClick);
		}
		

	}

	// used when mouse is pressed
	private void firstPress(int x, int y, boolean left) {
		preX = x;
		preY = y;
		spriteFocus = false;

		if (x >= (int) (width * 0.0275) && x <= (int) (width * 0.0275) + (int) (width * 0.7932) && y >= (int) (height * 0.056) && y <= (int) (height * 0.056) + (int) (height * 0.936)) {
			if (touchSprite(x, y, left)) {
				spriteFocus = true;
			}
		} else if (touchButton(x, y) != 420) {
			int val = touchButton(x, y);
			focus = val;
		}
	}

	// used when click is released
	private void releasePress(int x, int y, boolean left) {
		if (spriteFocus) {
			if (selectedAction == 3) {
				fillTool(x, y, left);
			}
			setHistory();
			spriteFocus = false;
		} else if (touchButton(x, y) != 420) {
			int val = touchButton(x, y);
			if (val == focus) {
				focus = button.button.get(val).command;
				System.out.println("Button is here with value : " + focus);
				if (focus == 1) {
					makeProject();
				} else if (focus == 2) {
					makeSprite();
				} else if (focus >= 70 && focus < 75) {
					changeSelProject(focus - 70);
				} else if (focus >= 75 && focus < 80) {
					deleteProject(focus - 75);
				} else if (focus >= 80 && focus < 83) {
					changeSelSprite(focus - 80);
				} else if (focus >= 83 && focus < 86) {
					deleteSprite(focus - 83);
				} else if (focus >= 86 && focus < 89) {
					scrollSprite(focus - 87);
				} else if (focus == 3) {
					switchColors(left, R, G, B);
				} else if (focus > 3 && focus <= 8) {
					switchMouseControl(focus - 4);
				} else if (focus >= 100 && focus < 130) {
					pickColorFromArchive(focus - 100, left);
				} else if (focus >= 40 && focus <= 42) {
					colorScroll(focus - 40);
				} else if (focus == 43) {
					selectedColorSwitch();
				}
			}

		}

		focus = 420;
	}

	// used when mouse is dragged
	private void dragPress(int x, int y, boolean left) {

		if (spriteFocus) {
			if (x >= xAxis && x <= xAxis + boxSize * getLineSize()) {
				if (y >= yAxis && y <= yAxis + boxSize * getLineSize()) {
					mouseActionShorter(x, y, left, false);
					preX = x;
					preY = y;
				} else {
					if (y > boxSize * getLineSize() + yAxis && x < boxSize * getLineSize() + xAxis && x > xAxis) {
						preX = x;
						preY = boxSize * (getLineSize() - 1) + yAxis;
					} else if (y < yAxis && x < boxSize * getLineSize() + xAxis && x > xAxis) {
						preX = x;
						preY = yAxis;
					}
				}
			} else {
				if (x > boxSize * getLineSize() + xAxis && y < boxSize * getLineSize() + yAxis && y > yAxis) {
					preX = boxSize * (getLineSize() - 1) + xAxis;
					preY = y;
				} else if (x < xAxis && y < boxSize * getLineSize() + yAxis && y > yAxis) {
					preX = xAxis;
					preY = y;
				}
			}
		}

		if (focus >= 20 && focus <= 22) {

			if (focus == 20) {
				int move = x - button.button.get(20).x;
				R += move;
				if (R < 0)
					R = 0;
				else if (R > 255)
					R = 255;
			} else if (focus == 21) {
				int move = x - button.button.get(21).x;
				G += move;
				if (G < 0)
					G = 0;
				else if (G > 255)
					G = 255;
			} else {
				int move = x - button.button.get(22).x;
				B += move;
				if (B < 0)
					B = 0;
				else if (B > 255)
					B = 255;
			}

			button.scrolls(R, G, B);
		}

	}

	// Check if given Coordinates Touch a Button
	private int touchButton(int x, int y) {
		for (int i = 0; i < button.button.size(); i++) {
			if (button.button.get(i).x <= x && button.button.get(i).x + button.button.get(i).wid >= x) {
				if (button.button.get(i).y <= y && button.button.get(i).y + button.button.get(i).hei >= y) {
					return i;
				}
			}
		}
		return 420;
	}

	// Check if the given Coordinates are in the Sprite Sheet
	private boolean touchSprite(int x, int y, boolean left) {
		if (proj.size() > 0) {
			if (proj.get(selProject).sprite.size() != 0) {
				if (x >= xAxis && x <= xAxis + boxSize * getLineSize()) {
					if (y >= yAxis && y <= yAxis + boxSize * getLineSize()) {
						mouseActionShorter(x, y, left, true);
						return true;
					}
				}
			}
		}
		return false;
	}

	// lineCorrection
	private void line(int y, int x, int y2, int x2, Color col) {
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
			proj.get(selProject).col.addColor(col, 1);
			proj.get(selProject).col.removeColor(proj.get(selProject).sprite.get(selSprite).color[y][x]);
			proj.get(selProject).sprite.get(selSprite).color[y][x] = proj.get(selProject).col.getColorNumber(col);
			refreshSprite(x+y*proj.get(selProject).sprite.get(selSprite).lineSize,proj.get(selProject).col.getColorNumber(col));
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

	// Is used by the display to get the values of a button
	public void getButton(int i) {
		but = new Button(button.button.get(i));
	}

	// Is used by the display to get button size
	public int getButtonSize() {
		return button.button.size();
	}

	// DEV COMMAND
	public void devReset() {
		button = new ButtonLoader(width, height);
	}

	// Button Press Action

	// Make Project
	private void makeProject() {
		if (proj.size() < 5) {
			JTextField projectName = new JTextField();
			final JComponent[] inputs = new JComponent[] { new JLabel("Project Name"), projectName, };
			int result = JOptionPane.showConfirmDialog(null, inputs, "Make Project", JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION && projectName.getText().length() != 0) {
				proj.add(new Project(projectName.getText(),null));
				selProject = proj.size() - 1;
			}
		}

	}

	// Make Sprite
	private void makeSprite() {
		if (proj.size() > 0) {
			JTextField spriteName = new JTextField();
			JTextField spriteSize = new JTextField();
			final JComponent[] inputs = new JComponent[] { new JLabel("Name Sprite"), spriteName, new JLabel("Sprite Size"), spriteSize };
			int result = JOptionPane.showConfirmDialog(null, inputs, "Make Project", JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION && spriteName.getText().length() != 0 && spriteSize.getText().length() != 0) {
				proj.get(selProject).addSprite(Integer.parseInt(spriteSize.getText().replaceAll("\\D+", "")), spriteName.getText());
				selSprite = proj.get(selProject).sprite.size() - 1;
				spriteReset();
				setHistory();
				if (proj.get(selProject).sprite.size() > 3) {
					for (int i = scroll; i < proj.get(selProject).sprite.size() - 3; i++) {
						scroll++;
					}
				}
			}
		}
	}

	// Change Project
	private void changeSelProject(int x) {
		if (proj.size() > x) {
			selProject = x;
			scroll = 0;
			colScroll = 0;
		}
	}

	// Delete Project
	private void deleteProject(int x) {
		if (proj.size() > 0) {
			if (selProject != 0)
				selProject--;
			proj.remove(x);
		}

	}

	// Change Sprite
	private void changeSelSprite(int x) { 
		if (proj.size() > 0) {
			if (proj.get(selProject).sprite.size() > x) {
				selSprite = x + scroll;
				spriteReset();
				if (proj.get(selProject).sprite.get(selSprite).isHistoryEmpty())setHistory();
			}
		}
	}

	// Delete Sprite
	private void deleteSprite(int x) {
		if (proj.size() > 0) {
			if (proj.get(selProject).sprite.size() != 0) {
				for (int i = 0; i < proj.get(selProject).sprite.get(x + scroll).lineSize; i++) {
					for (int j = 0; j < proj.get(selProject).sprite.get(x + scroll).lineSize; j++) {
						proj.get(selProject).col.removeColor(proj.get(selProject).sprite.get(x + scroll).color[i][j]);
					}
				}
				if (selSprite != 0) {
					selSprite--;
					spriteReset();
				}

				proj.get(selProject).sprite.remove(x + scroll);
				if (scroll > 0)
					scroll--;
			}
		}

	}

	// Scroll Sprite
	private void scrollSprite(int x) {
		if (proj.size() > 0) {
			if (proj.get(selProject).sprite.size() != 0) {
				if (x == -1 && scroll > 0) {
					scroll--;
				} else if (x == 1) {
					if (proj.get(selProject).sprite.size() > 3) {
						if (proj.get(selProject).sprite.size() - 3 > scroll)
							scroll++;
					}
				}
			}
		}

	}

	// Take the test color
	private void switchColors(boolean left, int r, int g, int b) {
		if (left)
			firstCol = new Color(r, g, b);
		else
			secondCol = new Color(r, g, b);
	}

	private void switchMouseControl(int x) {
		selectedAction = x;
	}

	private void mouseActionShorter(int x, int y, boolean left, boolean press) {
		if (selectedAction == 0) {
			paintSprite(x, y, left, press);
			


		} else if (selectedAction == 1) {
			eraseSprite(x, y, press);
			//refreshSprite();

		} else if (selectedAction == 2) {
			pickColor(x, y, left);
		}
		tmpSprite=UpdateImage();

	}

	private void fillTool(int x, int y, boolean left) {
		boolean[][] mark = new boolean[proj.get(selProject).sprite.get(selSprite).lineSize][proj.get(selProject).sprite.get(selSprite).lineSize];
		int xS = (x - xAxis) / boxSize;
		int yS = (y - yAxis) / boxSize;
		if (left) {
			floodFill(xS, yS, proj.get(selProject).col.color.get(proj.get(selProject).sprite.get(selSprite).color[(y - yAxis) / boxSize][(x - xAxis) / boxSize]), firstCol, mark);
		} else {
			floodFill(xS, yS, proj.get(selProject).col.color.get(proj.get(selProject).sprite.get(selSprite).color[(y - yAxis) / boxSize][(x - xAxis) / boxSize]), secondCol, mark);
		}
		//refreshSprite();
	}

	private void floodFill(int x, int y, Color targetColor, Color replacementColor, boolean mark[][]) {
		if (x < 0) return;
        if (y < 0) return;
        if (y >= proj.get(selProject).sprite.get(selSprite).lineSize) return;
        if (x >= proj.get(selProject).sprite.get(selSprite).lineSize) return;
		if (mark[x][y])
			return;
		if (proj.get(selProject).col.color.get((proj.get(selProject).sprite.get(selSprite).color[y][x])) != targetColor)
			return;
		
		proj.get(selProject).col.removeColor(proj.get(selProject).col.getColorNumber((proj.get(selProject).col.color.get((proj.get(selProject).sprite.get(selSprite).color[y][x])))));
		proj.get(selProject).col.addColor(replacementColor, 1);
		proj.get(selProject).col.color.get((proj.get(selProject).sprite.get(selSprite).color[y][x]) = proj.get(selProject).col.getColorNumber(replacementColor));
		refreshSprite(x+y*proj.get(selProject).sprite.get(selSprite).lineSize,proj.get(selProject).col.getColorNumber(replacementColor));

		mark[x][y] = true;
			floodFill(x - 1, y, targetColor, replacementColor, mark);
			floodFill(x + 1, y, targetColor, replacementColor, mark);
			floodFill(x, y - 1, targetColor, replacementColor, mark);
			floodFill(x, y + 1, targetColor, replacementColor, mark);

	}

	private void pickColor(int x, int y, boolean left) {
		if (left) {
			firstCol = proj.get(selProject).col.color.get((proj.get(selProject).sprite.get(selSprite).color[(y - yAxis) / boxSize][(x - xAxis) / boxSize]));
			switchRGB(firstCol);
		} else {
			secondCol = proj.get(selProject).col.color.get((proj.get(selProject).sprite.get(selSprite).color[(y - yAxis) / boxSize][(x - xAxis) / boxSize]));
		}
	}

	private void eraseSprite(int x, int y, boolean press) {
		Color col = Color.decode("0xFF0096");
		proj.get(selProject).col.removeColor(proj.get(selProject).sprite.get(selSprite).color[(y - yAxis) / boxSize][(x - xAxis) / boxSize]);

		proj.get(selProject).col.addColor(col, 1);

		proj.get(selProject).sprite.get(selSprite).color[(y - yAxis) / boxSize][(x - xAxis) / boxSize] = proj.get(selProject).col.getColorNumber(col);
		if (!press)
			line((y - yAxis) / boxSize, (x - xAxis) / boxSize, (preY - yAxis) / boxSize, (preX - xAxis) / boxSize, col);

	}

	private void paintSprite(int x, int y, boolean left, boolean press) {
		proj.get(selProject).col.removeColor(proj.get(selProject).sprite.get(selSprite).color[(y - yAxis) / boxSize][(x - xAxis) / boxSize]);
		if (left) {
			proj.get(selProject).col.addColor(firstCol, 1);
			proj.get(selProject).sprite.get(selSprite).color[(y - yAxis) / boxSize][(x - xAxis) / boxSize] = proj.get(selProject).col.getColorNumber(firstCol);
			refreshSprite(((x - xAxis) / boxSize)+((y - yAxis) / boxSize)*proj.get(selProject).sprite.get(selSprite).lineSize,proj.get(selProject).col.getColorNumber(firstCol));
			if (!press)
				line((y - yAxis) / boxSize, (x - xAxis) / boxSize, (preY - yAxis) / boxSize, (preX - xAxis) / boxSize, firstCol);
		} else {
			proj.get(selProject).col.addColor(secondCol, 1);
			proj.get(selProject).sprite.get(selSprite).color[(y - yAxis) / boxSize][(x - xAxis) / boxSize] = proj.get(selProject).col.getColorNumber(secondCol);
			refreshSprite(x+y*proj.get(selProject).sprite.get(selSprite).lineSize,proj.get(selProject).col.getColorNumber(secondCol));
			if (!press)
				line((y - yAxis) / boxSize, (x - xAxis) / boxSize, (preY - yAxis) / boxSize, (preX - xAxis) / boxSize, secondCol);

		}
	}

	private void switchRGB(Color col) {
		R = col.getRed();
		G = col.getGreen();
		B = col.getBlue();
		button.scrolls(R, G, B);
	}

	public void saveSpriteSheet() throws IOException {
		if (proj.size() > 0) {
			if (proj.get(selProject).sprite.size() != 0) {
				// String savePath = fileExplorer();
				if (proj.get(selProject).path==null){
					FileExplorer fileExp = new FileExplorer(true,proj.get(selProject).name + ".goz");
					if (fileExp.active)proj.get(selProject).path=fileExp.path;
				}
				if (proj.get(selProject).path!=null){
				File textFile = new File(proj.get(selProject).path, proj.get(selProject).name + ".goz");
				BufferedWriter out = new BufferedWriter(new FileWriter(textFile));
				try {
					String line = "";
					for (int i = 0; i < proj.get(selProject).col.color.size(); i++) {
						line += tran.colorDecToHex(proj.get(selProject).col.color.get(i));
					}
					out.write(line);
					for (int i = 0; i < proj.get(selProject).sprite.size(); i++) {
						line = "";
						out.newLine();
						int pre = -1;
						int num = 0;
						for (int y = 0; y < proj.get(selProject).sprite.get(i).lineSize; y++) {
							for (int x = 0; x < proj.get(selProject).sprite.get(i).lineSize; x++) {
								if (pre == proj.get(selProject).sprite.get(i).color[y][x]) {
									num++;
								} else if (pre == -1 || pre != proj.get(selProject).sprite.get(i).color[y][x]) {
									if (num > 0) {
										line += tran.decToHex(num) + "_" + tran.decToHex(pre) + ",";
									} else if (pre != -1) {
										line += tran.decToHex(pre) + ",";
									}
									pre = proj.get(selProject).sprite.get(i).color[y][x];
									num = 0;
								}

							}
						}
						if (num != 0)
							line += tran.decToHex(num) + "_" + tran.decToHex(pre) + ",";
						line += "." + proj.get(selProject).sprite.get(i).title;
						out.write(line);
					}
				} finally {
					out.close();
				}
			}
		}
		}
	}

	public void loadSpriteSheet() {
		if (proj.size() < 6) {
			FileExplorer fileExp = new FileExplorer(false,null);
			if (fileExp.active){
				try {
					
					File textFile = new File(fileExp.path, fileExp.name);

					BufferedReader b = new BufferedReader(new FileReader(textFile));

					String readLine = "";

					int lineCount = -1;

					while ((readLine = b.readLine()) != null) {
						if (lineCount == -1) {
							proj.add(new Project(fileExp.name.substring(0, fileExp.name.length()-4),fileExp.path));
							for (int i = 0; i < readLine.length(); i += 6) {
								proj.get(proj.size() - 1).col.addColor(Color.decode("0x" + readLine.substring(i, i + 6)), 0);
							}
							for (int i = 1; i < proj.get(proj.size() - 1).col.color.size(); i++) {
								proj.get(proj.size() - 1).col.removeColor(i);
							}
						} else {
							int i = 0;
							int totalSize = 0;

							while (!readLine.substring(i, i + 1).equals(".")) {
								String line = "";
								boolean size = false;
								while (!readLine.substring(i, i + 1).equals(",")) {
									line += readLine.substring(i, i + 1);
									if (readLine.substring(i, i + 1).equals("_"))
										size = true;
									i++;
								}
								i++;
								if (size) {
									String amound = "";
									String color = "";
									int count = 0;
									while (!line.substring(count, count + 1).equals("_")) {
										amound += line.substring(count, count + 1);
										count++;
									}
									count++;
									while (count < line.length()) {
										color += line.substring(count, count + 1);
										count++;
									}
									totalSize += tran.hexToDec(amound) + 1;
									proj.get(proj.size() - 1).col.addColor(proj.get(proj.size() - 1).col.color.get(tran.hexToDec(color)), tran.hexToDec(amound) + 1);
								} else {
									totalSize++;
									proj.get(proj.size() - 1).col.addColor(proj.get(proj.size() - 1).col.color.get(tran.hexToDec(line)), 1);
								}
							}
							proj.get(proj.size() - 1).loadSprite((int) Math.sqrt(totalSize), readLine.substring(i + 1));
							i = 0;
							int x = 0;
							int y = 0;
							while (!readLine.substring(i, i + 1).equals(".")) {
								String line = "";
								boolean size = false;
								while (!readLine.substring(i, i + 1).equals(",")) {
									line += readLine.substring(i, i + 1);
									if (readLine.substring(i, i + 1).equals("_"))
										size = true;
									i++;
								}
								i++;
								if (size) {
									String amound = "";
									String color = "";
									int count = 0;
									while (!line.substring(count, count + 1).equals("_")) {
										amound += line.substring(count, count + 1);
										count++;
									}
									count++;
									while (count < line.length()) {
										color += line.substring(count, count + 1);
										count++;
									}
									for (int j = 0; j < tran.hexToDec(amound) + 1; j++) {
										proj.get(proj.size() - 1).sprite.get(lineCount).color[y][x] = tran.hexToDec(color);
										x++;
										if (x == (int) Math.sqrt(totalSize)) {
											x = 0;
											y++;
										}
									}
								} else {
									proj.get(proj.size() - 1).sprite.get(lineCount).color[y][x] = tran.hexToDec(line);
									x++;
									if (x == (int) Math.sqrt(totalSize)) {
										x = 0;
										y++;
									}
								}
							}
						}
						lineCount++;
					}
					b.close();
					changeSelProject(proj.size() - 1);
					changeSelSprite(0);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
	

	public BufferedImage getButtonImage(int spr) {
		BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		render.clear();
		render.renderButton(spr, selectedAction);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = render.pixels[i];
		}
		return image;
	}

	public BufferedImage getSpriteImage(int spr) {

		image = new BufferedImage(proj.get(selProject).sprite.get(spr).lineSize, proj.get(selProject).sprite.get(spr).lineSize, BufferedImage.TYPE_INT_ARGB);
		int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		render.clear();
		render.renderSprite(proj.get(selProject).sprite.get(spr), proj.get(selProject).col);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = render.sprPixels[i];
		}
		return image;

	}
	
	private BufferedImage UpdateImage(){
		return image;
	}

	private BufferedImage getCursorImage(int spr) {
		BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		render.clear();
		render.renderCursor(spr, firstCol);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = render.pixels[i];
		}
		return image;
	}

	private void pickColorFromArchive(int i, boolean left) {
		if (proj.get(selProject).col.color.size() > i + colScroll * 6) {
			if (left) {
				firstCol = proj.get(selProject).col.color.get(i + colScroll * 6);
				switchRGB(firstCol);
			} else {
				secondCol = proj.get(selProject).col.color.get(i + colScroll * 6);
			}
		}

	}

	public Color colorFromArchive(int x) {
		return proj.get(selProject).col.color.get(x);
	}

	public int colorAmound() {
		return proj.get(selProject).col.color.size();
	}

	private void colorScroll(int x) {
		colScroll += x - 1;
		if (colScroll < 0)
			colScroll = 0;
		else if (colScroll * 6 >= colorAmound())
			colScroll--;
	}

	public Color getSelectedColor(boolean left) {
		if (left) {
			return firstCol;
		} else {
			return secondCol;
		}
	}

	private void selectedColorSwitch() {
		Color col = firstCol;
		firstCol = secondCol;
		secondCol = col;
		switchRGB(firstCol);
	}

	public Cursor getCursor() {

		return cursor;
	}

	public void resetCursor() {
		cursor = new Cursor(Cursor.DEFAULT_CURSOR);
	}

	public void checkMousePosition(int x, int y) {
		if (x >= (int) (width * 0.0275) && x <= (int) (width * 0.0275) + (int) (width * 0.7932) && y >= (int) (height * 0.056) && y <= (int) (height * 0.056) + (int) (height * 0.936)) {
			if (proj.size() > 0) {
				if (proj.get(selProject).sprite.size() > 0) {
					if (x >= xAxis && x <= xAxis + boxSize * proj.get(selProject).sprite.get(selSprite).lineSize && y >= yAxis && y <= yAxis + boxSize * proj.get(selProject).sprite.get(selSprite).lineSize) {
						int yPos = 31;
						int xPos = 0;
						if (selectedAction == 2) {
							yPos = 25;
							xPos = 17;
						} else if (selectedAction == 3) {
							yPos = 0;
							xPos = 0;
						}
						cursor = Toolkit.getDefaultToolkit().createCustomCursor(getCursorImage(selectedAction), new Point(xPos, yPos), "custom cursor");
					}
				}
			}
		} else if (touchButton(x, y) != 420) {
			cursor = new Cursor(Cursor.HAND_CURSOR);
		}
	}

	public void undo() {
		if (proj.size() > 0) {
			if (proj.get(selProject).sprite.size() > 0) {
				spriteHistory(1);
			}
		}
	}

	public void redo() {
		if (proj.size() > 0) {
			if (proj.get(selProject).sprite.size() > 0) {
				spriteHistory(-1);
			}
		}
	}

	private void setHistory() {
		String line = "";
		String color = "";
		List<Integer> indCol = new ArrayList<Integer>();
		boolean flag = true;

		for (int i = 0; i < proj.get(selProject).sprite.get(selSprite).color.length; i++) {

			for (int j = 0; j < proj.get(selProject).sprite.get(selSprite).color.length; j++) {
				flag = true;
				for (int k = 0; k < indCol.size(); k++) {

					if (indCol.get(k).equals(proj.get(selProject).sprite.get(selSprite).color[i][j])) {
						flag = false;
						break;
					}
				}

				if (flag) {

					indCol.add(proj.get(selProject).sprite.get(selSprite).color[i][j]);
					color += tran.colorDecToHex(proj.get(selProject).col.color.get(indCol.get(indCol.size() - 1)));
				}

			}

		}
			int pre = -1;
			int num = 0;
			for (int y = 0; y < proj.get(selProject).sprite.get(selSprite).lineSize; y++) {
				for (int x = 0; x < proj.get(selProject).sprite.get(selSprite).lineSize; x++) {
					if (pre == proj.get(selProject).sprite.get(selSprite).color[y][x]) {
						num++;
					} else if (pre == -1 || pre != proj.get(selProject).sprite.get(selSprite).color[y][x]) {
						if (num > 0) {
							
							line += tran.decToHex(num) + "_" + tran.decToHex(modColor(pre,indCol)) + ",";
						} else if (pre != -1) {
							line += tran.decToHex(modColor(pre,indCol)) + ",";
						}
						pre = proj.get(selProject).sprite.get(selSprite).color[y][x];
						num = 0;
					}

				}
			}
			if (num != 0)
				line += tran.decToHex(num) + "_" + tran.decToHex(modColor(pre,indCol)) + ",";
		
		indCol.clear();
		line+=".";
		proj.get(selProject).sprite.get(selSprite).setSpriteHistory(line,color);
	}
	
	private int modColor(int x, List<Integer> indCol){
		for (int i=0;i<indCol.size();i++){
			if (indCol.get(i)==x){
				return i;
			}
		}
		return 0;
	}
	
	private void spriteHistory(int num){
		if (proj.get(selProject).sprite.get(selSprite).undoRedo(num)){
			for (int y = 0; y < proj.get(selProject).sprite.get(selSprite).lineSize; y++) {
				for (int x = 0; x < proj.get(selProject).sprite.get(selSprite).lineSize; x++) {
					 proj.get(selProject).col.removeColor( proj.get(selProject).sprite.get(selSprite).color[y][x]);
				}
			}
			List<Color> color = new ArrayList<Color>();
			String line=proj.get(selProject).sprite.get(selSprite).getColorHistory();
			for (int i=0;i<line.length();i+=6){
				color.add(Color.decode("0x"+proj.get(selProject).sprite.get(selSprite).getColorHistory().substring(i, i+6)));
			}
			int totalSize = proj.get(selProject).sprite.get(selSprite).lineSize*proj.get(selProject).sprite.get(selSprite).lineSize;
			int i = 0;
			int x = 0;
			int y = 0;
			line = proj.get(selProject).sprite.get(selSprite).getSpriteHistory();
			while (!line.substring(i, i + 1).equals(".")) {
				String word = "";
				boolean size = false;
				while (!line.substring(i, i + 1).equals(",")) {
					word += line.substring(i, i + 1);
					if (line.substring(i, i + 1).equals("_"))
						size = true;
					i++;
				}
				i++;
				if (size) {
					String amound = "";
					String col = "";
					int count = 0;
					while (!word.substring(count, count + 1).equals("_")) {
						amound += word.substring(count, count + 1);
						count++;
					}
					count++;
					while (count < word.length()) {
						col += word.substring(count, count + 1);
						count++;
					}
					 proj.get(selProject).col.addColor(color.get(tran.hexToDec(col)), tran.hexToDec(amound)+1);
					for (int j = 0; j < tran.hexToDec(amound)+1; j++) {
						proj.get(selProject).sprite.get(selSprite).color[y][x] =  proj.get(selProject).col.getColorNumber(color.get(tran.hexToDec(col)));
						x++;
						if (x == (int) Math.sqrt(totalSize)) {
							x = 0;
							y++;
						}
					}
				} else {
					 proj.get(selProject).col.addColor(color.get(tran.hexToDec(word)), 1);
					proj.get(selProject).sprite.get(selSprite).color[y][x] = proj.get(selProject).col.getColorNumber(color.get(tran.hexToDec(word)));
					x++;
					if (x == (int) Math.sqrt(totalSize)) {
						x = 0;
						y++;
					}
				}
			}
		}
	}
	
	private void refreshSprite(int x, int col){
		int[] sprPixel = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		render.refreshSprite(x, proj.get(selProject).col, col);
		sprPixel[x] = render.sprPixels[x];
	}
	
	
	private void refreshSpriteList(boolean up){
		if (up){
			
		}else{
			
		}
	}

}
