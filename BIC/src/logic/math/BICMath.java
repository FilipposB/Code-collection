package logic.math;

public class BICMath {

	public static int convertToBICNeutral(int number, int key[]) {
		if (number == 0) {
			if (key[0] != 0) {
				number = (number / 10) * 10 + key[0];
			} else if (key[1] != 0) {
				number = (number / 10) * 10 + key[1];
			} else {
				number = (number / 10) * 10 + key[2];
			}
		} else {
			for (int i = 0; i < 3; i++) {
				int distance = distance(number, key[i]);
				if (i == 0 && addEndings(number % 10, 2) == key[0]) {
					number = ((number / 10) ) * 10 + key[0];
					break;
				}
				if (Math.abs(distance) >= 0 && Math.abs(distance) < 2||(Math.abs(distance) >= 0 && Math.abs(distance) < 3&&number%10==0)) {
					if (distance < 0) {
						number = ((number / 10) ) * 10 + key[i];
					} else if (distance > 0) {
						number = (number / 10) * 10 + key[i];
					}
					break;
				}
			}
		}

		if (number >= 255) {
			number = (number / 10) * 10 + key[getMax(key)];
			while (number>=255){
				number-=10;
			}

		}
		return number;
	}

	public static int addEndings(int en1, int en2) {
		return (en1 + en2) % 10;
	}

	public static int distance(int number, int point) {
		if (Math.abs(number % 10 - point) > Math.abs(point - number % 10)) {
			return point - number % 10;
		}
		return number % 10 - point;
	}

	public static int getBitsNeeded(char text[], char alphabet[]) {
		int bits = 0;
		for (int i = 0; i < text.length; i++) {
			if (text[i] == ' ') {
				bits++;
			} else {
				int index = findLetterInAlphabet(text[i],alphabet);
				if (index<alphabet.length){
					if (index==0){
						bits+=2;
					}
					else 
						bits += Math.log(index) + 1 + 1;
				}
			}
		}

		return bits;
	}
	
	public static int findLetterInAlphabet(char letter, char alphabet[]){
		int j;
		for (j=0; j < alphabet.length; j++) {
			if (Character.toUpperCase(letter) == Character.toUpperCase(alphabet[j])){
				return j;
			}
				
		}
		return j;
	}
	
	private static int getMax(int arr[]) {
				int max = arr[0];
				int indexMax = 0;

				for (int i = 1; i < arr.length; i++) {
					if (arr[i] > max) {
						max = arr[i];
						indexMax = i;
					}
				}
				
				return indexMax;
			}
}
