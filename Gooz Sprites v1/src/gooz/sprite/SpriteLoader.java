package gooz.sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SpriteLoader {
	public int grid = 0;
	public String[] button = new String[5];

	public SpriteLoader() {
		int i = 0;
		for (i = 0; i < button.length; i++) {
			button[i] = spriteLoader(i + 1, "Assets");
		}
	}

	public String spriteLoader(int y, String name) {
		File textFile = new File("src/" + name + ".goz");
		String sprite = "";
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(textFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line;
		String tempSprite = null;
		String decrypt = "";
		try {
			int x = 0;
			while ((line = in.readLine()) != null) {

				if (x == 0) {
					decrypt = line;
				} else if (x == y) {
					tempSprite = line;
				}
				x++;
			}
			int totLen = 0;
			this.grid = 0;

			String[] hex = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
			while (totLen < tempSprite.length()) {
				boolean boolTest = true;
				for (int i = 0; i < 16; i++) {
					if (tempSprite.substring(0 + totLen, 1 + totLen).equals(hex[i])) {
						boolTest = false;
					}
				}
				if (boolTest) {
					char c = tempSprite.charAt(totLen);

					if (c < 'a') {
						sprite += decrypt.substring(6 * (c - 'G'), 6 + 6 * (c - 'G'));
					} else {
						sprite += decrypt.substring(6 * (c - 'a' + 21), 6 + 6 * (c - 'a' + 21));

					}
					totLen++;
				} else {
					sprite += tempSprite.substring(totLen, 6 + totLen);
					totLen += 6;
				}
				grid++;
			}
			grid = (int) Math.sqrt(grid);
			return sprite;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sprite;
	}
}
