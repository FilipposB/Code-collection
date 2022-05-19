package display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import engine.Handler;

public class Display extends JPanel {
	private static final long serialVersionUID = 1L;

	// Size Of JFrame
	private int width;
	private int height;

	// Handler
	private Handler handler = new Handler(0, 0);

	// Change sprite display
	private boolean showGrid = true;
	private boolean showRender = false;

	// Color
	private Color c;
	
	
	
	//Buttons
	private Image buttons[];

	public Display() {
		
		setFocusable(true);
		requestFocusInWindow();
		addMouseListener(new MouseAdapter() {
			
			
			
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					handler.setCoords(e.getX(), e.getY(), true, false, true);
				} else {
					handler.setCoords(e.getX(), e.getY(), true, false, false);
				}
				
				
			}

			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					handler.setCoords(e.getX(), e.getY(), false, true, true);
				} else {
					handler.setCoords(e.getX(), e.getY(), false, true, false);
				}
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				handler.resetCursor();

				handler.checkMousePosition(e.getX(), e.getY());
				
			    }
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					handler.setCoords(e.getX(), e.getY(), false, false, true);

				} else {
					handler.setCoords(e.getX(), e.getY(), false, false, false);
				}
			}
		});

		addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_R) {
					handler.devReset();
				}

				else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_LEFT) {
					handler.xAxis -= (int) (width * 0.007);
				} else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_RIGHT) {
					handler.xAxis += (int) (width * 0.007);
				} else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_UP) {
					handler.yAxis -= (int) (height * 0.007);
				} else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_DOWN) {
					handler.yAxis += (int) (height * 0.007);
				} else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_CLOSE_BRACKET) {
					handler.boxSize += (int) (height * 0.0007) + (int) (width * 0.0007);
				} else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_OPEN_BRACKET) {
					handler.boxSize -= (int) (height * 0.0007) + (int) (width * 0.0007);
				} else if (e.getKeyCode() == KeyEvent.VK_G) {
					showGrid = !showGrid;
				} else if (e.getKeyCode() == KeyEvent.VK_R) {
					showGrid = showRender;
					showRender = !showRender;
				} else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S) {
					try {
						handler.saveSpriteSheet();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_L) {
					handler.loadSpriteSheet();
				} else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
					handler.undo();
				} else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y) {
					handler.redo();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});
		buttons = new Image[handler.getButtonSize()];
		handler.tmpDisSprite = new Image[3];
		for (int i=0;i<12;i++){
			buttons[i] =handler.getButtonImage(i);
		}
		
	}

	public void paint(Graphics g) {
		long startTime = System.currentTimeMillis();

		setCursor(handler.getCursor());

		
		if (width != getWidth() || height != getHeight()) {
			handler.setDimensions(getWidth(), getHeight());
			width = getWidth();
			height = getHeight();
		}

		// Draw Blank Space For Open Sprite
		c = Color.decode("0x" + "818181");
		g.setColor(c);
		g.fillRect((int) (width * 0.0275), (int) (height * 0.056), (int) (width * 0.7932), (int) (height * 0.936));
		c = Color.decode("0x" + "A6A6A6");
		g.setColor(c);
		g.drawRect((int) (width * 0.0275), (int) (height * 0.056), (int) (width * 0.7932), (int) (height * 0.936));

		// Draw Open Sprite
		if (handler.getProjectAmount() > 0) {
			if (handler.getSpriteAmount() > 0) {
				if (handler.tmpSprite==null)handler.tmpSprite=handler.getSpriteImage(handler.selSprite);
				g.drawImage(handler.tmpSprite,handler.xAxis, handler.yAxis, handler.boxSize*handler.getLineSize(), handler.boxSize*handler.getLineSize(), this);

				if (showGrid) {
					for (int i = 0; i < handler.getLineSize()+1; i++) {
						g.setColor(Color.BLACK);
						g.drawLine(handler.xAxis, handler.boxSize * i + handler.yAxis, handler.boxSize*handler.getLineSize()+handler.xAxis, handler.boxSize * i + handler.yAxis);
					}
					for (int j = 0; j < handler.getLineSize()+1; j++) {
						g.setColor(Color.BLACK);
						g.drawLine(handler.boxSize * j + handler.xAxis, handler.yAxis, handler.boxSize * j + handler.xAxis,handler.boxSize *handler.getLineSize() + handler.yAxis);
					}
					
				}
			}
		}

		// Draw Background
		g.setColor(Color.white);
		g.fillRect(0, 0, (int) (width * 0.0275), height);
		g.fillRect(0, 0, width, (int) (height * 0.056));
		g.fillRect((int) (width * 0.0275), (int) (height * 0.992), width, (int) (height * 0.01));
		g.fillRect((int) (width * 0.8207), (int) (height * 0.056), width, height);

		// Draw Project Bar
		c = Color.decode("0x" + "B9B9B9");
		g.setColor(c);
		g.fillRect((int) (width * 0.0275), (int) (height * 0.02), (int) (width * 0.7932), (int) (height * 0.033));
		c = Color.decode("0x" + "A6A6A6");
		g.setColor(c);
		g.drawRect((int) (width * 0.0275), (int) (height * 0.02), (int) (width * 0.7932), (int) (height * 0.033));

		// Draw Tool Panel
		c = Color.decode("0x" + "B9B9B9");
		g.setColor(c);
		g.fillRoundRect((int) (width * 0.004), (int) (height * 0.02), (int) (width * 0.022), (int) (height * 0.97), (int) (width * 0.004), (int) (width * 0.004));
		c = Color.decode("0x" + "A6A6A6");
		g.setColor(c);
		g.drawRoundRect((int) (width * 0.004), (int) (height * 0.02), (int) (width * 0.022), (int) (height * 0.97), (int) (width * 0.004), (int) (width * 0.004));

		// Draw Sprite&Color Panel
		c = Color.decode("0x" + "B9B9B9");
		g.setColor(c);
		g.fillRoundRect((int) (width * 0.822), (int) (height * 0.02), (int) (width * 0.17), (int) (height * 0.97), (int) (width * 0.004), (int) (width * 0.004));
		c = Color.decode("0x" + "A6A6A6");
		g.setColor(c);
		g.drawRoundRect((int) (width * 0.822), (int) (height * 0.02), (int) (width * 0.17), (int) (height * 0.97), (int) (width * 0.004), (int) (width * 0.004));

		// Draw Sprite, Panel0
		c = Color.decode("0x" + "818181");
		g.setColor(c);
		g.fillRoundRect((int) (width * 0.824), (int) (height * 0.50), (int) (width * 0.165), (int) (height * 0.485), (int) (width * 0.004), (int) (width * 0.004));
		c = Color.decode("0x" + "A6A6A6");
		g.setColor(c);
		g.drawRoundRect((int) (width * 0.824), (int) (height * 0.50), (int) (width * 0.165), (int) (height * 0.485), (int) (width * 0.004), (int) (width * 0.004));

		// Draw Sprite, Panel1 Refresh
		c = Color.decode("0x" + "5B5B5B");
		g.setColor(c);
		g.fillRect((int) (width * 0.828), (int) (height * 0.508), (int) (width * 0.157), (int) (height * 0.44));
		c = Color.decode("0x" + "444444");
		g.setColor(c);
		g.drawRect((int) (width * 0.828), (int) (height * 0.508), (int) (width * 0.157), (int) (height * 0.44));

		// Draw Color Archive Box
		c = Color.decode("0x" + "818181");
		g.setColor(c);
		g.fillRoundRect((int) (width * 0.824), (int) (height * 0.056), (int) (width * 0.165), (int) (height * 0.234), (int) (width * 0.004), (int) (width * 0.004));
		c = Color.decode("0x" + "5B5B5B");
		g.setColor(c);
		g.fillRect((int) (width * 0.827), (int) (height * 0.06), (int) (width * 0.144), (int) (height * 0.225));
		c = Color.decode("0x" + "444444");
		g.setColor(c.brighter().brighter());
		g.drawRoundRect((int) (width * 0.824), (int) (height * 0.056), (int) (width * 0.165), (int) (height * 0.234), (int) (width * 0.004), (int) (width * 0.004));
		g.setColor(Color.BLACK);
		g.drawRect((int) (width * 0.827), (int) (height * 0.06), (int) (width * 0.144), (int) (height * 0.225));

		// Draw Color Switch Environment
		c = Color.decode("0x" + "A0A0A0");
		g.setColor(c);
		g.fillRoundRect((int) (width * 0.824), (int) (height * 0.295), (int) (width * 0.165), (int) (height * 0.2), (int) (width * 0.004), (int) (width * 0.004));
		c = Color.decode("0x" + "444444");
		g.setColor(c.brighter().brighter());
		g.drawRoundRect((int) (width * 0.824), (int) (height * 0.295), (int) (width * 0.165), (int) (height * 0.2), (int) (width * 0.004), (int) (width * 0.004));
		g.setColor(Color.BLACK);
		g.setFont(new Font("default", Font.PLAIN, (int) (height * 0.01 + width * 0.009)));
		g.drawString("R:", (int) (width * 0.825), (int) (height * 0.343));
		g.drawString("G:", (int) (width * 0.825), (int) (height * 0.388));
		g.drawString("B:", (int) (width * 0.825), (int) (height * 0.434));

		// Draw Test Color && Bars
		g.setColor(Color.BLACK);
		g.drawRoundRect((int) (width * 0.895), (int) (height * 0.309), (int) (width * 0.03), (int) (height * 0.03), (int) (width * 0.01), (int) (width * 0.01));
		g.drawRoundRect((int) (width * 0.895), (int) (height * 0.354), (int) (width * 0.03), (int) (height * 0.03), (int) (width * 0.01), (int) (width * 0.01));
		g.drawRoundRect((int) (width * 0.895), (int) (height * 0.399), (int) (width * 0.03), (int) (height * 0.03), (int) (width * 0.01), (int) (width * 0.01));
		c = Color.decode("0x" + "FFFFFF");
		g.setColor(c);
		g.fillRect((int) (width * 0.84), (int) (height * 0.325), (int) (width * 0.145), (int) (height * 0.02));
		g.fillRect((int) (width * 0.84), (int) (height * 0.37), (int) (width * 0.145), (int) (height * 0.02));
		g.fillRect((int) (width * 0.84), (int) (height * 0.415), (int) (width * 0.145), (int) (height * 0.02));
		// fill open space with color
		c = new Color(handler.R, 0, 0);
		g.setColor(c);
		g.fillRect((int) (width * 0.84), (int) (height * 0.325), (int) (width * 0.145 / 255 * handler.R), (int) (height * 0.02));
		c = new Color(0, handler.G, 0);
		g.setColor(c);
		g.fillRect((int) (width * 0.84), (int) (height * 0.37), (int) (width * 0.145 / 255 * handler.G), (int) (height * 0.02));
		c = new Color(0, 0, handler.B);
		g.setColor(c);
		g.fillRect((int) (width * 0.84), (int) (height * 0.415), (int) (width * 0.145 / 255 * handler.B), (int) (height * 0.02));
		c = Color.decode("0x" + "000000");
		g.setColor(c);
		g.drawRect((int) (width * 0.84), (int) (height * 0.325), (int) (width * 0.145), (int) (height * 0.02));
		g.drawRect((int) (width * 0.84), (int) (height * 0.37), (int) (width * 0.145), (int) (height * 0.02));
		g.drawRect((int) (width * 0.84), (int) (height * 0.415), (int) (width * 0.145), (int) (height * 0.02));
		// number of RGB colors
		g.setColor(Color.BLACK);
		g.setFont(new Font("default", Font.PLAIN, (int) (height * 0.001 + width * 0.01)));
		g.drawString(handler.R + "", (int) (width * 0.901), (int) (height * 0.325));
		g.drawString(handler.G + "", (int) (width * 0.901), (int) (height * 0.37));
		g.drawString(handler.B + "", (int) (width * 0.901), (int) (height * 0.415));
		// test Box
		c = new Color(handler.R, handler.G, handler.B);
		g.setColor(c);
		handler.getButton(25);
		g.fillRect(handler.but.x, handler.but.y, handler.but.wid, handler.but.hei);
		
		//Fill Color Boxes
		if (handler.getProjectAmount()>0){
			for (int i=6*handler.colScroll;i<handler.colorAmound();i++){
				if (i>handler.colorAmound())break;
				if (i==24+6*handler.colScroll)break;
				g.setColor(handler.colorFromArchive(i));
				handler.getButton(i-6*handler.colScroll+26);
				g.fillRect( handler.but.x, handler.but.y, handler.but.wid, handler.but.hei);
			}
		}

		//Fill Selected Colors
		g.setColor(handler.getSelectedColor(false));
		g.fillRect((int) (width * 0.962), (int) (height * 0.463), (int) (width * 0.016), (int) (height * 0.03));
		g.drawImage(handler.getButtonImage(5), (int) (width * 0.962), (int) (height * 0.463), (int) (width * 0.016), (int) (height * 0.03),this);

		g.setColor(handler.getSelectedColor(true));
		g.fillRect((int) (width * 0.953), (int) (height * 0.45), (int) (width * 0.016), (int) (height * 0.03));
		g.drawImage(handler.getButtonImage(5), (int) (width * 0.953), (int) (height * 0.45), (int) (width * 0.016), (int) (height * 0.03), this);

		

		// Draw Buttons
				for (int i = 0; i < handler.getButtonSize(); i++) {
					handler.getButton(i);
					if (handler.but.visible) {
						g.drawImage(buttons[handler.but.sprite], handler.but.x, handler.but.y, handler.but.wid, handler.but.hei, this);
					}

				}

		// Draw Projects
		for (int i = 0; i < handler.getProjectAmount(); i++) {
			handler.getButton(6);
			int space = (int) (handler.but.x * 0.1);
			handler.getButton(i + 6);
			if (i == 0) {

				g.setFont(new Font("default", Font.PLAIN, (int) (height * 0.01 + width * 0.009)));
			}
			if (handler.selProject == i) {
				c = Color.decode("0x" + "818181");
				g.setColor(c);
				g.fillRect(handler.but.x, handler.but.y, handler.but.wid, handler.but.hei);
			}
			g.setColor(Color.black);
			g.drawRect(handler.but.x, handler.but.y, handler.but.wid, handler.but.hei);

			g.drawString(handler.getProjectName(i), handler.but.x + space, (int) (handler.but.y + handler.but.hei * 0.8));

			handler.getButton(i + 1);
			g.drawImage(buttons[handler.but.sprite], handler.but.x, handler.but.y, handler.but.wid, handler.but.hei, this);
			
		}

		// Draw Sprite display
		if (handler.getProjectAmount() > 0) {
			for (int i = 0; i < handler.getSpriteAmount(); i++) {
				if (i == 3)
					break;
				handler.getButton(15);
				int space = (int) (handler.but.y * 0.1);
				handler.getButton(i + 15);
				if (i == 0) {

					g.setFont(new Font("default", Font.PLAIN, (int) (height * 0.004 + width * 0.008)));
				}
				if (handler.selSprite == i + handler.getScroll()) {

					c = Color.decode("0x" + "818181");
					g.setColor(c.brighter());
					g.fillRect(handler.but.x, handler.but.y, handler.but.wid, handler.but.hei);
				} else {
					c = Color.decode("0x" + "818181");
					g.setColor(c);
					g.fillRect(handler.but.x, handler.but.y, handler.but.wid, handler.but.hei);
				}
				g.setColor(Color.black);
				g.drawRect(handler.but.x, handler.but.y, handler.but.wid, handler.but.hei);

				g.drawString(handler.getSpriteName(i), handler.but.x + (int) (handler.but.wid * 0.38), handler.but.y + space);

				int box = (int) (height * 0.04 + width * 0.03);

				//if (handler.tmpDisSprite[i] == null)handler.tmpDisSprite[i]=handler.getSpriteImage(i + handler.getScroll());
				g.drawImage(handler.tmpDisSprite[i], (int) (handler.but.x * 1.002), (int) (handler.but.y * 1.05), box, box, this);
				g.drawRect((int) (handler.but.x * 1.002), (int) (handler.but.y * 1.05), box, box);


				handler.getButton(i + 12);
				g.drawImage(buttons[handler.but.sprite], handler.but.x, handler.but.y, handler.but.wid, handler.but.hei, this);

			}
		}
		System.out.println("RENDERED ! : " + ((System.currentTimeMillis() - startTime)));

		g.dispose();
		repaint();
	}

}
