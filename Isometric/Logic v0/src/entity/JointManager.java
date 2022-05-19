package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import math.Maths;

public class JointManager {
	private final static int objectSize = 20;
	private final static int jointSize = 15;

	private static List<JointedObject> jointedObjects = new ArrayList<JointedObject>();

	private static List<Integer> selected = new ArrayList<Integer>();

	private static int focus = -1;

	public static void draw(Graphics2D g) {
		for (int i=0;i<jointedObjects.size();i++){
			JointedObject jointed = jointedObjects.get(i);
			Color nodeColor;
			Color lineColor;
			if (selected.contains(jointed.getID())) {
				nodeColor = Color.RED;
				lineColor = Color.MAGENTA;
			} else {
				nodeColor = Color.BLUE;
				lineColor = Color.GRAY;
			}
			for (int j=0;j<jointed.getJoints().size();j++){
				Joint joint = jointed.getJoints().get(j);
				g.setColor(lineColor);
				g.drawLine((int) joint.getX(), (int) joint.getY(), (int) jointed.getX(), (int) jointed.getY());
				g.setColor(nodeColor);
				g.fillOval((int) joint.getX() - jointSize / 2, (int) joint.getY() - jointSize / 2, jointSize,
						jointSize);
				g.setColor(Color.GREEN);
				g.drawOval((int) joint.getX() - jointSize / 2, (int) joint.getY() - jointSize / 2, jointSize,
						jointSize);
			}
			if (selected.contains(jointed.getID()))
				g.setColor(Color.ORANGE);
			else
				g.setColor(Color.GRAY);
			g.fillOval((int) jointed.getX() - objectSize / 2, (int) jointed.getY() - objectSize / 2, objectSize,
					objectSize);
			g.setColor(Color.WHITE);
			g.drawOval((int) jointed.getX() - objectSize / 2, (int) jointed.getY() - objectSize / 2, objectSize,
					objectSize);
		}
	}

	public static void addJointedObject(int x, int y) {
		for (JointedObject jointed : jointedObjects) {
			if (Maths.clickInCircle(x, (int) jointed.getX(), y, (int) jointed.getY(), objectSize*2)) {
				return;
			}
		}
		jointedObjects.add(new JointedObject(x, y, generateJointedObjectID()));
		selected.clear();
		selected.add(jointedObjects.get(jointedObjects.size() - 1).getID());
	}

	public static void addJoint(int x, int y) {
		if (!selected.isEmpty()) {
			int index = getIndexWithID(selected.get(selected.size() - 1));
			if (index != -1) {
				jointedObjects.get(index).addJoint(x, y, generateJointID());
			}
		}
	}

	private static int getIndexWithID(int id) {
		for (int i = 0; i < jointedObjects.size(); i++) {
			if (jointedObjects.get(i).getID() == id) {
				return i;
			}
		}
		return -1;
	}

	private static int generateJointedObjectID() {
		int id = 0;
		boolean unique = false;
		Random rand = new Random();
		while (!unique) {
			id = rand.nextInt();
			unique = true;
			for (JointedObject jointed : jointedObjects) {
				if (jointed.getID() == id) {
					unique = false;
					break;
				}
			}
		}
		return id;
	}

	private static int generateJointID() {
		int id = 0;
		boolean unique = false;
		Random rand = new Random();
		while (!unique) {
			id = rand.nextInt();
			unique = true;
			for (JointedObject jointed : jointedObjects) {
				for (Joint joint : jointed.getJoints()) {
					if (joint.contains(id)) {
						unique = false;
						break;
					}
				}
			}
		}
		return id;
	}

	public static int getTotalJointedObjects() {
		return jointedObjects.size();
	}

	public static void selectClick(int xM, int yM, boolean shift) {
		for (JointedObject jointed : jointedObjects) {
			if (Maths.clickInCircle(xM, (int) jointed.getX(), yM, (int) jointed.getY(), objectSize)) {
				if (!selected.contains(jointed.getID())) {
					if (!shift) {
						selected.clear();
					}
					selected.add(jointed.getID());
				} else
					selected.remove(Integer.valueOf(jointed.getID()));
				break;
			}
		}
	}

	public static void establishFocus(int xM, int yM, boolean bind) {
		if (!bind)
			focus = -1;
		else if (focus == -1) {
			for (Integer id : selected) {
				int index = getIndexWithID(id);
				if (index != -1) {
					if (Maths.clickInCircle(xM, (int) jointedObjects.get(index).getX(), yM,
							(int) jointedObjects.get(index).getY(), objectSize)) {
						focus = id;
						return;
					}
				}
			}
		}
	}

	public static void moveJoint(int xM, int yM, boolean shift) {
		if (focus != -1 && getIndexWithID(focus) != -1) {
			float xOffset=jointedObjects.get(getIndexWithID(focus)).getX();
			float yOffset=jointedObjects.get(getIndexWithID(focus)).getY();

			jointedObjects.get(getIndexWithID(focus)).setXY(xM, yM);
			
			if (shift) {
				xOffset-=jointedObjects.get(getIndexWithID(focus)).getX();
				yOffset-=jointedObjects.get(getIndexWithID(focus)).getY();
				for (Integer x : selected) {
					int index = getIndexWithID(x);
					if (index != -1&&x!=focus) {
						jointedObjects.get(index).moveXY(xOffset, yOffset);
					}
				}
			}
		}
	}

	public static void deleteSelected() {
		for (Integer x : selected) {
			int index = getIndexWithID(x);
			if (index != -1) {
				jointedObjects.remove(index);
			}
		}
		selected.clear();
	}
	
	public static void deselectAll(){
		selected.clear();
	}

}
