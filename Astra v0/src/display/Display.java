package display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import project.Project;
import widgets.ProjectButton;
import widgets.Widget;

public class Display extends JPanel {
	private static final long serialVersionUID = 1L;

	// Mouse
	private Point mouse;
	private int selectedButton;

	// Colors
	private final static Color background = new Color(34, 49, 63);
	private final static Color border = new Color(44, 62, 80);
	private final static Color black = Color.BLACK;

	private final static Font RGBFont = new Font("default", Font.BOLD, 16);
	
	// Project
	private List<Project> project = new ArrayList<Project>();
	private int selectedProject;
	private int selectedSprite;
	private int selectedLayer;

	// Frame
	private int width;
	private int height;
	private int spriteX;
	private int spriteY;
	public int rightLine;
	
	// Widgets
	private List<Widget> widgets;

	// Images
	private Image sprite;

	public Display() {
		selectedProject = -1;
		selectedSprite = -1;
		selectedLayer = -1;

		// Frame size
		width = 0;
		height = 0;
		rightLine = 224;
		
		// Initialize Widgets
		initializeWidgets();
		
		// Mouse Listener
		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				// On left mouse click
				if (SwingUtilities.isLeftMouseButton(e)) {
					mouse = e.getPoint();
				}
				// On right mouse click
				else {

				}

			}

			public void mouseReleased(MouseEvent e) {
				// On left mouse release
				if (SwingUtilities.isLeftMouseButton(e)) {
						selectedButton = -1;
				}
				// On right mouse release
				else {

				}
			}

		});

		addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {

			}

			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
				} else {

				}
			}
		});
	}

	public void paint(Graphics g2) {
		super.paintComponent(g2);

		Graphics2D g = (Graphics2D) g2;
		
		checkForResize();
		

		// BackGround Fill
		g.setColor(background);
		g.fillRect(0, 0, width, height);

		// Display Sprite
		if (selectedSprite != -1) {
			g.drawImage(sprite, spriteX, spriteY, this);
		}

		drawGui(g);

		//draw Widgets
		for (Widget w:widgets)w.paint(g);
		
		repaint();
		g.dispose();

	}

	// Make a New Project
	private void newProject() {
		if (openDialogBox("project")){
			selectedProject = project.size() - 1;
			newSprite(selectedProject, "No name", 1200, 700);
		}
	}

	// Make a New Sprite
	private void newSprite(int proj, String name, int wid, int hei) {
		project.get(proj).createSprite(name, wid, hei);
		selectedSprite = project.get(proj).getSpriteSize() - 1;
		sprite = project.get(selectedProject).getSpriteImage(selectedSprite);
		int boxX = 34;
		int boxY = 73;
		int boxW = width + boxX - 224;
		int boxH = height + boxY;
		spriteX = boxW / 2 - project.get(selectedProject).getSpriteWidth(selectedSprite) / 2;
		spriteY = boxH / 2 - project.get(selectedProject).getSpriteHeight(selectedSprite) / 2;
		if (spriteX < boxX)
			spriteX = boxX;
		if (spriteY < boxY)
			spriteY = boxY;

	}

	// Make a New Layer
	private void newLayer(int proj, int spr, String name, int x, int y, int wid, int hei, boolean lock, boolean visible, Color col) {
		project.get(proj).createLayer(spr, x, y, wid, hei, lock, visible, col);
	}

	// Project Dialog Box
	private boolean openDialogBox(String type) {
		if (type.equals("project")) {
			JTextField projectName = new JTextField();
			final JComponent[] inputs = new JComponent[] { new JLabel("Project"), projectName };
			int result = JOptionPane.showConfirmDialog(null, inputs, "Name Project", JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				project.add(new Project("No name", project.size()));
				return true;
			}
		}
		return false;
	}
	
	private void drawGui(Graphics2D g){
		// --> Borders -Start <-

				// -> Fillers -Start <-

				// Default Border Color
				g.setColor(border);

				// Left Bar fill
				g.fillRect(0, 33, 32, height - 33);

				// Right Bar fill
				g.fillRect(width - 224, 33, 224, height - 33);

				// Tool Bar
				g.fillRect(0, 0, width, 30);

				// Project Bar
				g.fillRect(35, 33, width - 261, 25);

				// -> Fillers -End <-

				// -> Outlines -Start <-

				// Outline Color
				g.setColor(black);

				// Top Black Line
				g.drawLine(0, 0, width, 0);

				// Left Bar Line
				g.drawLine(32, 33, 32, height);
				g.drawLine(0, 33, 32, 33);

				// Right Bar Line
				g.drawLine(width - rightLine, 33, width - rightLine, height);
				g.drawLine(width - rightLine, 33, width, 33);

				// Tool Bar Outline
				g.drawLine(0, 30, width, 30);
				g.drawRect(35, 33, width - 261, 25);

				// -> Outlines -End <-

				// --> Borders -End <--

				// --> Sprite Viewing Area -Start <--

				// --> Top Bar
				g.setColor(background);
				g.drawLine(width - 212, height - 400, width - 15, height - 400);

				// -> Fillers -Start <-
				g.setColor(background);
				g.fillRect(width - 217, height - 390, 210, 370);
				// -> Fillers -End <-

				// --> Borders -Start
				g.setColor(black);
				g.drawRect(width - 217, height - 390, 210, 370);
				// --> Borders -End

				// --> Sprite Viewing Area -End <--

				// --> Color Panel -Start <--

				// --> Fillers Start
				g.setColor(background);
				g.fillRect(width - 217, height - 477, 195, 17);
				g.fillRect(width - 217, height - 452, 195, 17);
				g.fillRect(width - 217, height - 427, 195, 17);
				// --> Fillers End

				// --> Borders -Start
				g.setColor(black);
				g.drawRect(width - 217, height - 477, 195, 17);
				g.drawRect(width - 217, height - 452, 195, 17);
				g.drawRect(width - 217, height - 427, 195, 17);
				// --> Borders -End

				// --> Letters
				g.setFont(RGBFont);
				g.drawString("R", getWidth() - 17, getHeight() - 465);
				g.drawString("G", getWidth() - 17, getHeight() - 439);
				g.drawString("B", getWidth() - 17, getHeight() - 412);
	}
	
	private void checkForResize(){
		if (getWidth() != width || getHeight() != height) {
			width = getWidth();
			height = getHeight();
			if (selectedSprite != -1) {
				int boxX = 34;
				int boxY = 75;
				int boxW = width + boxX - 224;
				int boxH = height + boxY;
				spriteX = boxW / 2 - project.get(selectedProject).getSpriteWidth(selectedSprite) / 2;
				spriteY = boxH / 2 - project.get(selectedProject).getSpriteHeight(selectedSprite) / 2;
				if (spriteX < boxX)
					spriteX = boxX;
				if (spriteY < boxY)
					spriteY = boxY;
			}
		}
	}
	
	private void initializeWidgets(){
		widgets = new ArrayList<Widget>();
		widgets.add(new ProjectButton(this,0,0,32,32,"lil"));
	}
}
