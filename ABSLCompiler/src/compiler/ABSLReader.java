package compiler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ABSLReader {

	public static int runScript(String file) {

		boolean comment = false;

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				// Checks to see if what we are reading is comment so we can
				// ignore it
				boolean lastComment = comment;
				//comment = commentCheck(comment, line);

				// If it is a comment it will be ignored
				if (!comment && lastComment == comment) {
					System.out.println(line.trim());
				}

				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;
	}

	private static boolean commentCheck(boolean comment, String line) {
		if (line.trim().equals("/*"))
			return true;
		else if (line.trim().equals("*/"))
			return false;
		else{
			line = line.trim();
			
		}

		return comment;
	}

}
