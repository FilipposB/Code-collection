package gui.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import gui.widgets.ButtonWidget;
import gui.widgets.ToolButton;
import gui.widgets.Widget;
import logic.key.BICKey;

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

	// Frame
	private int width;
	private int height;

	// BIC
	// InputImage
	private BufferedImage input = null;
	// OutputImage
	private BufferedImage output = null;
	// Key
	private BICKey key=new BICKey(8,8,8,1,1);

	// Widgets
	private List<Widget> widgets;

	public Display() {

		// Frame size
		width = 900;
		height = 600;

		// Initialize Widgets
		initializeWidgets();

		// Mouse Listener
		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				// On left mouse click
				if (SwingUtilities.isLeftMouseButton(e)) {
					mouse = e.getPoint();
					for (int i = 0; i < widgets.size(); i++) {
						if (widgets.get(i) instanceof ToolButton) {
							if (((ToolButton) widgets.get(i)).isClicked(mouse.x, mouse.y)) {
								selectedButton = i;
							}
						}
					}
				}
				// On right mouse click
				else {

				}

			}

			public void mouseReleased(MouseEvent e) {
				// On left mouse release
				if (SwingUtilities.isLeftMouseButton(e)) {
					mouse = e.getPoint();
					if (selectedButton != -1)
						if (widgets.get(selectedButton) instanceof ToolButton) {
							if (((ToolButton) widgets.get(selectedButton)).isClicked(mouse.x, mouse.y)) {
								int signal = ((ToolButton) widgets.get(selectedButton)).getSignal();
								if (signal == 0) {
									FileExplorer fe = new FileExplorer(false, "", "");
									try {
										input = ImageIO.read(new File(fe.path + "/" + fe.name));
									} catch (IOException q) {
										return;
									}
								} else if (signal == 1) {

								}
							}
						}
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

		// BackGround Fill
		g.setColor(background);
		g.fillRect(0, 0, width, height);

		drawGui(g);

		if (input != null) {

			g.drawImage(input, 601, 22, 279, 200, this);
		}

		if (output != null) {
			g.drawImage(output, 601, 330, 279, 200, this);
		}

		initializeWidgets();
		// draw Widgets
		for (Widget w : widgets)
			w.paint(g);

		repaint();
		g.dispose();

	}

	// Project Dialog Box
	private boolean openDialogBox(String type) {
		if (type.equals("project")) {
			JTextField projectName = new JTextField();
			final JComponent[] inputs = new JComponent[] { new JLabel("Project"), projectName };
			int result = JOptionPane.showConfirmDialog(null, inputs, "Name Project", JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				// project.add(new Project("No name", project.size()));
				return true;
			}
		}
		return false;
	}

	private void drawGui(Graphics2D g) {
		
		g.setColor(Color.GRAY);
		g.fillRect(600, 21, 280, 201);
		g.fillRect(600, 329, 280, 201);
		g.setColor(Color.black);
		g.drawRect(600, 21, 280, 201);
		g.drawRect(600, 329, 280, 201);

		// key digits
		g.setColor(border);
		g.fillRect(270, 5, 180, 140);
		
		g.setColor(Color.WHITE);
		g.fillRect(320, 40, 30, 30);
		g.fillRect(360, 40, 30, 30);
		g.fillRect(400, 40, 30, 30);

		g.setColor(Color.BLACK);

		g.drawRect(320, 40, 30, 30);
		g.drawRect(360, 40, 30, 30);
		g.drawRect(400, 40, 30, 30);

	}

	private void initializeWidgets() {
		widgets = new ArrayList<Widget>();
		// inports an image
		widgets.add(new ToolButton(this, 500, 110, 70, 40, 0));
		// writes data in image
		widgets.add(new ToolButton(this, 700, 260, 100, 40, 1));

	}
}
