package gooz.engine;

public class Translator {
	
	private String[] hex = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
	
	
	// Takes either an R a G or a B value in hex and returns its integer
	public int hexToDec(String x) {
		int sum;
		sum = hexToDecEncr(x.substring(0, 1)) * 16 + hexToDecEncr(x.substring(1, 2));
		return sum;
	}
	
	private int hexToDecEncr(String x) {
		int i = 0;
		boolean flag = true;
		while (flag && i < 16) {
			if (x.equals(hex[i])) {
				flag = false;
			} else {
				i++;
			}
		}
		return i;
	}

	// Takes R G B values and return their hex
	public String decToHex(int rgb[]) {
		String result = "";
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
}
