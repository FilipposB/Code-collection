package encryption;

import java.awt.Color;
import java.awt.image.BufferedImage;

import math.BICMath;

public class BICEncryption {

	private static RGBEncryption rgbEncryption;

	public static BufferedImage encodeToImage(BufferedImage bi, int start, int step, String text, char characters[]) {
		int count = start;
		int y = count / bi.getWidth();
		int x = count % bi.getWidth();
		int col = 0;
		char[] charArray = text.toUpperCase().toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			int index = 0;
			if (col > 2) {
				col = 0;
				count += step;
				if (count > bi.getWidth() * bi.getHeight())
					count -= bi.getWidth() * bi.getHeight();
				y = count / bi.getWidth();
				x = count % bi.getWidth();
			}
			if (index == -1) {
				col++;
			} else {
				String binString = Integer.toBinaryString(index);
				for (int q = 0; q < binString.length(); q++) {
					int calc = 0;
					if (binString.charAt(q) == '0') {
						calc = -1;
					} else {
						calc = 1;
					}
					Color c = new Color(bi.getRGB(x, y));
					if (col > 2) {
						col = 0;
						count += step;
						if (count > bi.getWidth() * bi.getHeight())
							count -= bi.getWidth() * bi.getHeight();
						y = count / bi.getWidth();
						x = count % bi.getWidth();
						c = new Color(bi.getRGB(x, y));
					}
					if (col == 0) {
						c = new Color(c.getRed() + calc, c.getGreen(), c.getBlue());
					} else if (col == 1) {
						c = new Color(c.getRed(), c.getGreen() + calc, c.getBlue());
					} else {
						c = new Color(c.getRed(), c.getGreen(), c.getBlue() + calc);
					}
					bi.setRGB(x, y, c.getRGB());
					col++;
				}
			}
		}
		return bi;
	}

	// This method converts an image to BIC
	public static BufferedImage convertBufferedImageToBIC(BufferedImage bi) {

		rgbEncryption = createRGBEncryption(bi);
		
		for (int x = 0; x < bi.getWidth(); x++) {
			for (int y = 0; y < bi.getHeight(); y++) {
				Color c = new Color(bi.getRGB(x, y));
				c = new Color(round(c.getRed(), rgbEncryption.getEncodingRed()),
						round(c.getGreen(), rgbEncryption.getEncodingGreen()),
						round(c.getBlue(), rgbEncryption.getEncodingBlue()));
				bi.setRGB(x, y, c.getRGB());
				System.out.println(c.getRed());
			}
		}

		return bi;
	}

	private static RGBEncryption createRGBEncryption(BufferedImage bi) {
		// Finds the amount of times each last digit is found in the image
		int rEndings[] = new int[10];
		int gEndings[] = new int[10];
		int bEndings[] = new int[10];
		RGBEncryption rgbEncr = null;
		for (int x = 0; x < bi.getWidth(); x++) {
			for (int y = 0; y < bi.getHeight(); y++) {
				// Gets the color of the x,y pixel and checks each color Red, Green, Blue for their last digit
				Color col = new Color(bi.getRGB(x, y));
				rEndings[BICMath.lastDigit(col.getRed())]++;
				gEndings[BICMath.lastDigit(col.getGreen())]++;
				bEndings[BICMath.lastDigit(col.getBlue())]++;
			}
		}
		rgbEncr = new RGBEncryption(BICMath.getBase(rEndings),BICMath.getBase(gEndings),BICMath.getBase(bEndings));
		return rgbEncr;
	}

	public static RGBEncryption getRGBEncryption() {
		return rgbEncryption;
	}

}
