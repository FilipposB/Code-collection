package logic.decrypt;

import java.awt.Color;
import java.awt.image.BufferedImage;

import logic.key.BICKey;
import logic.math.BICMath;

public class BICDecode {

	public static String decodeStringFromImage(BufferedImage bi, char alphabet[], BICKey key) {
		String text = "";
		String binary = "";
		int bits = 0;
		int stop = 1;
		for (int i = 0; i < bi.getWidth(); i++) {
			for (int j = 0; j < bi.getHeight(); j += key.getStep()) {
				Color sample = new Color(bi.getRGB(i, j));
				char bit[] = new char[3];
				bit[0] = getBinaryOutput(sample.getRed(), key.getRed());
				bit[1] = getBinaryOutput(sample.getGreen(), key.getGreen());
				bit[2] = getBinaryOutput(sample.getBlue(), key.getBlue());
				bits += 3;
				for (int q = 0; q < 3; q++) {
					if (bit[q] == 'e') {
						if (stop == 0) {
							stop = 1;
							text += alphabet[Integer.parseInt(binary, 2)];
							binary = "";
						} else {
							stop++;
							text += " ";
						}
					} else {
						stop = 0;
						binary += bit[q];
					}
					if (stop >= 100) {
						return text;
					}
				}

			}
		}
		return text;
	}

	public static char getBinaryOutput(int number, int key[]) {
		for (int i = 0; i < 3; i++) {
			if (BICMath.distance(number, key[i]) == -1) {
				return '0';
			} else if (BICMath.distance(number, key[i]) == 1) {
				return '1';
			} else if (BICMath.distance(number, key[i]) == 0) {
				return 'e';
			}
		}
		return 'e';
	}

}
