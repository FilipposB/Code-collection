package button;

import java.util.ArrayList;
import java.util.List;

public class ButtonLoader {
	public List<Button> button = new ArrayList<Button>();
	private int width;
	private int height;

	public ButtonLoader(int wid, int hei) {
		width = wid;
		height = hei;
		// Make Project
		button.add(new Button(true, w(0.0275), h(0.02), w(0.019), h(0.033), 1, 1));
		// Close Projects
		button.add(new Button(false, w(0.18202), h(0.02), w(0.01936), h(0.033), 0, 75));
		button.add(new Button(false, w(0.33686), h(0.02), w(0.01936), h(0.033), 0, 76));
		button.add(new Button(false, w(0.49170), h(0.02), w(0.01936), h(0.033), 0, 77));
		button.add(new Button(false, w(0.64654), h(0.02), w(0.01936), h(0.033), 0, 78));
		button.add(new Button(false, w(0.80138), h(0.02), w(0.01936), h(0.033), 0, 79));

		// Projects
		button.add(new Button(false, w(0.0465), h(0.02), w(0.15484), h(0.033), 3, 70));
		button.add(new Button(false, w(0.20134), h(0.02), w(0.15484), h(0.033), 3, 71));
		button.add(new Button(false, w(0.35618), h(0.02), w(0.15484), h(0.033), 3, 72));
		button.add(new Button(false, w(0.51102), h(0.02), w(0.15484), h(0.033), 3, 73));
		button.add(new Button(false, w(0.66586), h(0.02), w(0.15484), h(0.033), 3, 74));

		// Make Sprite
		button.add(new Button(true, w(0.9), h(0.949), w(0.02), h(0.036), 1, 2));

		// Close Sprite
		button.add(new Button(false, w(0.960), h(0.849), w(0.022), h(0.05), 0, 83));
		button.add(new Button(false, w(0.960), h(0.702), w(0.022), h(0.05), 0, 84));
		button.add(new Button(false, w(0.960), h(0.555), w(0.022), h(0.05), 0, 85));

		// Sprite Viewer
		button.add(new Button(false, w(0.828), h(0.802), w(0.157), h(0.147), 3, 80));
		button.add(new Button(false, w(0.828), h(0.655), w(0.157), h(0.147), 3, 81));
		button.add(new Button(false, w(0.828), h(0.508), w(0.157), h(0.147), 3, 82));

		// sprite scrolls
		button.add(new Button(true, w(0.85), h(0.949), w(0.02), h(0.036), 2, 88));
		button.add(new Button(true, w(0.95), h(0.949), w(0.02), h(0.036), 3, 86));

		// color sliders
		button.add(new Button(true, w(0.835), h(0.325), w(0.008), h(0.021), 4, 90));
		button.add(new Button(true, w(0.835), h(0.37), w(0.008), h(0.021), 4, 91));
		button.add(new Button(true, w(0.835), h(0.415), w(0.008), h(0.021), 4, 92));

		// color archive scrolls
		button.add(new Button(true, w(0.972), h(0.06), w(0.0165), h(0.03), 2, 40));
		button.add(new Button(true, w(0.972), h(0.256), w(0.0165), h(0.03), 3, 42));

		// color test box
		int side = (int) (w(0.015) + h(0.02));
		button.add(new Button(true, w(0.899), h(0.44), side, side, 5, 3));

		// color archive boxes
		int boxWidth = w(0.013);
		int boxHeight = h(0.024);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 6; j++) {
				button.add(new Button(true, w(0.84) + boxWidth * j + (w(0.01) * j), h(0.075) + boxHeight * i + (h(0.031) * i), boxWidth, boxHeight, 5, 100 + (i * 6) + j));
			}
		}
		
		// color switch button
		button.add(new Button(true, w(0.97), h(0.442), h(0.018), h(0.018), 6, 43));


		// tool buttons
		// draw button
		int sideTool = w(0.022);
		for (int i = 0; i < 4; i++) {
			int sp = i + 8;
			button.add(new Button(true, w(0.004), h(0.053) + sideTool * i + (h(0.002) * i), sideTool, sideTool, sp, 4 + i));
		}

	}

	// easy math
	private int w(double x) {
		return (int) (width * x);
	}

	private int h(double x) {
		return (int) (height * x);
	}

	// moveSliders
	public void scrolls(int r, int g, int b) {
		button.set(20, new Button(true, w(0.835 + (0.1455 / 255 * r)), h(0.325), w(0.008), h(0.021), 4, 90));
		button.set(21, new Button(true, w(0.835 + (0.1455 / 255 * g)), h(0.37), w(0.008), h(0.021), 4, 91));
		button.set(22, new Button(true, w(0.835 + (0.1455 / 255 * b)), h(0.415), w(0.008), h(0.021), 4, 92));
	}
}
