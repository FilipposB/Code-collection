package encryption;

public class RGBEncryption {
	
	private int encodingRed;
	private int encodingGreen;
	private int encodingBlue;

	
	public RGBEncryption(int r,int g,int b){
		encodingRed=r;
		encodingGreen=g;
		encodingBlue=b;
	}


	public int getEncodingRed() {
		return encodingRed;
	}


	public int getEncodingGreen() {
		return encodingGreen;
	}


	public int getEncodingBlue() {
		return encodingBlue;
	}
	
	public char result(int color,int encoding){
		int lastDigit = color % 10;
		if (encoding==0)encoding = encodingRed;
		if (encoding==1)encoding = encodingGreen;
		if (encoding==2)encoding = encodingBlue;

		if ((encoding == 0 && (lastDigit == 9 || lastDigit == 8)) || (encoding == 1 && lastDigit == 9)) {
			return '0';
		}
		for (int i = encoding; i <= encoding + 6; i += 3) {
			if (Math.abs(i - lastDigit) == 1) {
				if (lastDigit > i)
					return '1';
				else
					return '0';
			} else if (Math.abs(i - lastDigit) == 0) {
				return 'e';
			}
		}
		return 'e';
	}
}
