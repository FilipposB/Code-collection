package engine;

import java.awt.Color;

public class Translator {
	public Translator() {

	}

	public String colorDecToHex(Color col) {
		String[] hex = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
		String result = "";
		int[] rgb = { col.getRed(), col.getGreen(), col.getBlue() };
		for (int i = 0; i < 3; i++) {
			String tempres = "";
			int num = rgb[i];
			int rem;
			int ch = 0;
			while (num != 0) {
				rem = num % 16;
				num /= 16;
				tempres = hex[rem] + tempres;
				ch++;
			}
			if (ch == 0) {
				tempres = "00";
			} else if (ch == 1) {
				tempres = "0" + tempres;
			}
			result = result + tempres;
		}
		return result;
	}

	public String decToHex(int num) {
		String result = "";
		String tempres = "";
		String[] hex = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

		int rem;
		int ch = 0;
		while (num != 0) {
			rem = num % 16;
			num /= 16;
			tempres = hex[rem] + tempres;
			ch++;
		}
		if (ch == 0) {
			tempres = "0";
		}
		result = result + tempres;
		return result;
	}

	public int hexToDec(String x) {
		String[] hex = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
		int result = 0;
		int count = 0;
		while (count < x.length()) {

			int dec = 0;

			for (int i = 0; i < 16; i++) {
				if (hex[i].equals(x.substring(count, count + 1))) {
					dec = i;
					break;
				}
			}
			result += Math.pow(16, x.length() - 1 - count) * dec;
			count++;
		}
		return result;
	}

}
