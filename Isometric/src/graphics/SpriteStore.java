package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteStore {

	public static Sprite getSprite(String imga) {

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("img/"+imga));
		} catch (IOException e) {
		}

		Sprite sprite = new Sprite(img);

		return sprite;
	}

}