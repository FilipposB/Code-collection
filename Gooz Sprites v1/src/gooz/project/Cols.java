package gooz.project;

import java.awt.Color;
import java.util.ArrayList;

public class Cols {
	public ArrayList<Color> col = new ArrayList<Color>();

	public Cols() {

	}

	public void addColor(Color newColor) {
		if (!colorExist(newColor))col.add(newColor);
	}

	public boolean colorExist(Color newColor) {
		if (col.contains(newColor))
			return true;
		return false;
	}
}
