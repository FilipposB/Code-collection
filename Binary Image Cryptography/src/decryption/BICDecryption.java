package decryption;

import java.awt.Color;
import java.awt.image.BufferedImage;

import encryption.RGBEncryption;

public class BICDecryption {

	public static String decryptBIC(BufferedImage bi, RGBEncryption rgbEnc, int start, int step, char characters[]) {

		String text = "";
		String word = "";
		int count = start;
		int col = 0;
		int countE = 0;

		int y = count / bi.getWidth();
		int x = count % bi.getWidth();

		Color color = new Color(bi.getRGB(x, y));

		while (countE < 3) {
			char result;

			if (col > 2) {
				col = 0;
				count += step;
				if (count > bi.getWidth() * bi.getHeight())
					count -= bi.getWidth() * bi.getHeight();
				y = count / bi.getWidth();
				x = count % bi.getWidth();
				color = new Color(bi.getRGB(x, y));
			}
			if (col == 0) {
				result = rgbEnc.result(color.getRed(), 0);
			} else if (col == 1) {
				result = rgbEnc.result(color.getGreen(), 1);
			} else {
				result = rgbEnc.result(color.getBlue(), 2);
			}

			if (result == 'e') {
				if (countE == 0&&word!="") {
					System.out.println();
					text = text + characters[Integer.parseInt(word, 2)];
					word="";
				} else if (countE == 1) {
					text = text + " ";
				} else if (countE>2){
					return text;
				}
				countE++;
			} else {
				word = word + result;
				System.out.print(result);
				countE=0;
			}

			col++;

		}
		return text;
	}

}
