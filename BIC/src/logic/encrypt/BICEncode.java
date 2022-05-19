package logic.encrypt;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import logic.key.BICKey;
import logic.math.BICMath;

public class BICEncode {

	public static BICKey generateBICKey(BufferedImage bi, int step) {
		int R[] = new int[10];
		int G[] = new int[10];
		int B[] = new int[10];
		int bits = 0;

		for (int i = 0; i < bi.getWidth(); i++) {
			for (int j = 0; j < bi.getHeight(); j += step) {
				Color sample = new Color(bi.getRGB(i, j));
				R[sample.getRed() % 10]++;
				G[sample.getGreen() % 10]++;
				B[sample.getBlue() % 10]++;
				bits += 3;
			}
		}

		BICKey key = new BICKey(getMin(R), getMin(G), getMin(B), step, bits);
		return key;
	}

	public static BufferedImage encodeTextToImage(BufferedImage bi, char text[], char alphabet[], BICKey key) {
		if (BICMath.getBitsNeeded(text, alphabet) > key.getBits()) {
			System.err.println("ERROR TOO MANY BITS - USE A SMALLER STEP OR A LARGER IMAGE");
			return bi;
		}

		bi = neutralizeImage(bi, key, BICMath.getBitsNeeded(text, alphabet));

		List<Character> binary = new ArrayList<Character>();

		System.out.println("Amount of characters : " + text.length);
		for (int i = 0; i < text.length; i++) {
			int index = BICMath.findLetterInAlphabet(text[i], alphabet);
			char bin[];

			if (index == alphabet.length) {
				binary.add('e');
			} else {
				bin = Integer.toBinaryString(index).toCharArray();
				for (Character c : bin) {
					binary.add(c);
				}
				binary.add('e');
			}
		}
		int index = 0;
		int x = 0;
		int y = 0;

		int changes[] = new int[3];
		for (Character c : binary) {

			if (c == '0') {
				changes[index] = -1;
			} else if (c == '1') {
				changes[index] = 1;
			} else {
				changes[index] = 0;
			}
			index++;
			if (index == 3) {
				bi.setRGB(x, y, getAlteredRGB(bi.getRGB(x, y), changes));
				y += key.getStep();
				if (y >= bi.getHeight()) {
					y = 0;
					x++;
				}
				index = 0;
				changes = new int[3];
			}
		}
		if (index != 0) {
			bi.setRGB(x, y, getAlteredRGB(bi.getRGB(x, y), changes));
		}

		return bi;
	}

	private static int getAlteredRGB(int rgb, int changes[]) {
		Color col = new Color(rgb);
		return new Color(col.getRed() + changes[0], col.getGreen() + changes[1], col.getBlue() + changes[2]).getRGB();
	}

	private static BufferedImage neutralizeImage(BufferedImage bi, BICKey key, int bitsNeeded) {
		for (int i = 0; i < bi.getWidth(); i++) {
			for (int j = 0; j < bi.getHeight(); j += key.getStep()) {
				Color sample = new Color(bi.getRGB(i, j));
				int rgb = new Color(BICMath.convertToBICNeutral(sample.getRed(), key.getRed()),
						BICMath.convertToBICNeutral(sample.getGreen(), key.getGreen()),
						BICMath.convertToBICNeutral(sample.getBlue(), key.getBlue())).getRGB();
				bi.setRGB(i, j, rgb);
			}
		}
		return bi;
	}

	private static int getMin(int arr[]) {
		// 799 460
		int min = arr[0];
		int indexMin = 0;

		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < min) {
				min = arr[i];
				indexMin = i;
			}
		}

		return indexMin;
	}
}
