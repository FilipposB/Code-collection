package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import math.Maths;

public class LogicManager {
	private final static int objectWidth = 35;
	private final static int objectHeight = 35;

	private final static int connectorWidth = 14;
	private final static int connectorHeight = 10;
	private final static int connectionDistance = 17;
	
	private static List<LogicObject> logicObjects = new ArrayList<LogicObject>();

	private static List<Integer> selected = new ArrayList<Integer>();

	private static int focus = -1;

	private static int connectFocusID = -1;
	private static int connectFocusConnector = -1;

	private static boolean boxSelect = false;
	private static int selectX = 0;
	private static int selectY = 0;

	public static void draw(Graphics2D g, int mX, int mY) {

		int outputIndex = getIndexWithID(connectFocusID);
		if (outputIndex != -1) {
			if (logicObjects.get(outputIndex).output.get(connectFocusConnector).getState())
				g.setColor(Color.MAGENTA);
			else
				g.setColor(Color.RED);
			LogicObject logic = logicObjects.get(outputIndex);
			g.drawLine((int) logic.getX() + objectWidth / 2 + connectorWidth,
					(int) logic.getY() + objectHeight * connectFocusConnector,
					(int) logic.getX() + objectWidth / 2 + connectorWidth + connectionDistance,
					(int) logic.getY() + objectHeight * connectFocusConnector);
			g.drawLine((int) logic.getX() + objectWidth / 2 + connectorWidth + connectionDistance,
					(int) logic.getY() + objectHeight * connectFocusConnector,
					(int) logic.getX() + objectWidth / 2 + connectorWidth + connectionDistance, mY);
			g.drawLine((int) logic.getX() + objectWidth / 2 + connectorWidth + connectionDistance, mY, mX, mY);
		}
		for (int i = 0; i < logicObjects.size(); i++) {
			LogicObject logic = logicObjects.get(i);
			for (int q = 0; q < logic.getOutputConnectorCount(); q++) {
				for (int x = 0; x < logic.output.get(q).getConnectedObjectsSize(); x++) {
					int index = getIndexWithID(logic.output.get(q).getConnectorID(x));
					if (index != -1) {
						if (logic.output.get(q).getState())
							g.setColor(Color.RED);
						else
							g.setColor(Color.MAGENTA);
						int connector = logic.output.get(q).getConnectedConnectors(x);
						int inputX = (int) logicObjects.get(index).getX() - connectorWidth - objectWidth / 2;
						int inputY = (int) logicObjects.get(index).getY() + objectHeight * connector;
						g.drawLine((int) logic.getX() + objectWidth / 2 + connectorWidth,
								(int) logic.getY() + objectHeight * q,
								(int) logic.getX() + objectWidth / 2 + connectorWidth + connectionDistance,
								(int) logic.getY() + objectHeight * q);
						g.drawLine((int) logic.getX() + objectWidth / 2 + connectorWidth + connectionDistance,
								(int) logic.getY() + objectHeight * q,
								(int) logic.getX() + objectWidth / 2 + connectorWidth + connectionDistance, inputY);
						g.drawLine((int) logic.getX() + objectWidth / 2 + connectorWidth + connectionDistance, inputY,
								inputX, inputY);
					}
				}
			}
			for (int q = 0; q < logic.getInputConnectorCount(); q++) {
				g.setColor(Color.BLUE);
				g.fillRect((int) logic.getX() - connectorWidth - objectWidth / 2,
						(int) logic.getY() + objectHeight * q - connectorHeight / 2, connectorWidth, connectorHeight);
				g.setColor(Color.WHITE);
				g.drawRect((int) logic.getX() - connectorWidth - objectWidth / 2,
						(int) logic.getY() + objectHeight * q - connectorHeight / 2, connectorWidth, connectorHeight);
			}
			for (int q = 0; q < logic.getOutputConnectorCount(); q++) {
				g.setColor(Color.GREEN);

				g.fillRect((int) logic.getX() + objectWidth / 2,
						(int) logic.getY() + objectHeight * q - connectorHeight / 2, connectorWidth, connectorHeight);
				g.setColor(Color.WHITE);
				g.drawRect((int) logic.getX() + objectWidth / 2,
						(int) logic.getY() + objectHeight * q - connectorHeight / 2, connectorWidth, connectorHeight);
			}
			if (selected.contains(logic.getID()))
				g.setColor(Color.YELLOW);
			else
				g.setColor(Color.YELLOW.darker());
			if (logic instanceof LogicLamp) {
				if (((LogicLamp) logic).isOn()) {
					if (selected.contains(logic.getID()))
						g.setColor(Color.RED);
					else
						g.setColor(Color.RED.darker());
				}
			}

			g.fillRect((int) logic.getX() - objectWidth / 2, (int) logic.getY() - objectHeight / 2, objectWidth,
					objectHeight * logic.getSize());
			if (selected.contains(logic.getID()))
				g.setColor(Color.WHITE);
			else
				g.setColor(Color.WHITE.darker());
			g.drawRect((int) logic.getX() - objectWidth / 2, (int) logic.getY() - objectHeight / 2, objectWidth,
					objectHeight * logic.getSize());
		}

		if (boxSelect) {
			int x;
			int y;
			int width = Math.abs(mX - selectX);
			int height = Math.abs(mY - selectY);
			if (selectX < mX) {
				x = selectX;
			} else {
				x = mX;
			}
			if (selectY < mY) {
				y = selectY;
			} else {
				y = mY;
			}
			g.setColor(new Color(255, 255, 255, 100));
			g.fillRect(x, y, width, height);
			g.setColor(new Color(255, 255, 255, 255));
			g.drawRect(x, y, width, height);
		}
	}

	public static void update() {
		for (int i = 0; i < logicObjects.size(); i++) {
			LogicObject Logic = logicObjects.get(i);
			Logic.update();
		}
	}

	public static void addLogicObject(int x, int y, int obj) {
		if (obj == 0) {
			logicObjects.add(new LogicSwitch(x, y, false, generateLogicObjectID()));
		} else if (obj == 1) {
			logicObjects.add(new LogicLamp(x, y, generateLogicObjectID()));
		} else if (obj > 1 && obj < 8) {
			logicObjects.add(new LogicGate(x, y, generateLogicObjectID(), obj - 2));
		} else if (obj == -1) {
			logicObjects.add(new LogicPower(x, y, false, generateLogicObjectID()));
		} else if (obj == -2) {
			logicObjects.add(new LogicPower(x, y, true, generateLogicObjectID()));
		} else if (obj == -3) {
			logicObjects.add(new LogicPower(x, y, 100, false, generateLogicObjectID()));
		}
		selected.clear();
		selected.add(logicObjects.get(logicObjects.size() - 1).getID());
	}

	private static int getIndexWithID(int id) {
		for (int i = 0; i < logicObjects.size(); i++) {
			if (logicObjects.get(i).getID() == id) {
				return i;
			}
		}
		return -1;
	}
	
	protected static int getIndexWithID(int id,List<LogicObject> logicObjects) {
		for (int i = 0; i < logicObjects.size(); i++) {
			if (logicObjects.get(i).getID() == id) {
				return i;
			}
		}
		return -1;
	}

	private static int generateLogicObjectID() {
		int id = 0;
		boolean unique = false;
		Random rand = new Random();
		while (!unique) {
			id = rand.nextInt();
			unique = true;
			for (LogicObject Logic : logicObjects) {
				if (Logic.getID() == id) {
					unique = false;
					break;
				}
			}
		}
		return id;
	}

	public static int getTotalLogicObjects() {
		return logicObjects.size();
	}

	public static void selectClick(int xM, int yM, boolean shift) {
		boxSelect = true;
		selectX = xM;
		selectY = yM;

	}

	public static void boxSelect(int xM, int yM, boolean shift) {
		if (boxSelect) {
			if (!shift) {
				selected.clear();
			}
			int x;
			int y;
			int width = Math.abs(xM - selectX);
			int height = Math.abs(yM - selectY);
			if (width == 0)
				width = 1;
			if (height == 0)
				height = 1;
			if (selectX < xM) {
				x = selectX;
			} else {
				x = xM;
			}
			if (selectY < yM) {
				y = selectY;
			} else {
				y = yM;
			}
			for (LogicObject Logic : logicObjects) {
				if (Maths.rectangleAreConnected((int) (Logic.getX() - objectWidth / 2), (int) (Logic.getY() - objectHeight / 2),  objectWidth,objectHeight * Logic.getSize(),x,y,width ,height)){
					if (!selected.contains(Logic.getID())) {
						selected.add(Logic.getID());
					}
				}
			}
		}
		boxSelect = false;
	}

	public static void establishFocus(int xM, int yM, boolean bind) {
		if (!bind)
			focus = -1;
		else if (focus == -1) {
			for (Integer id : selected) {
				int index = getIndexWithID(id);
				if (index != -1) {
					if (Maths.clickInRectangle(xM, (int) logicObjects.get(index).getX() - objectWidth / 2, yM,
							(int) logicObjects.get(index).getY() - objectHeight / 2, objectWidth,
							objectHeight * logicObjects.get(index).getSize())) {
						focus = id;
						return;
					}
				}
			}
		}
	}

	public static void moveLogic(int xM, int yM, boolean shift) {
		if (focus != -1 && getIndexWithID(focus) != -1) {
			float xOffset = logicObjects.get(getIndexWithID(focus)).getX();
			float yOffset = logicObjects.get(getIndexWithID(focus)).getY();

			logicObjects.get(getIndexWithID(focus)).setXY(xM, yM);

			if (shift) {
				xOffset -= logicObjects.get(getIndexWithID(focus)).getX();
				yOffset -= logicObjects.get(getIndexWithID(focus)).getY();
				for (Integer x : selected) {
					int index = getIndexWithID(x);
					if (index != -1 && x != focus) {
						logicObjects.get(index).moveXY(xOffset, yOffset);
					}
				}
			}
		}
	}

	public static void interact(int xM, int yM, boolean focus) {
		boolean removedConnection=false;
		for (LogicObject logic : logicObjects) {
			if (!focus)
			for (int i = 0; i < logic.getInputConnectorCount(); i++) {
				if (Maths.clickInRectangle(xM, (int) logic.getX() - connectorWidth - objectWidth / 2, yM,
						(int) logic.getY() + objectHeight * i - connectorHeight / 2, connectorWidth,
						connectorHeight)) {
					int index = getIndexWithID(logic.input.get(i).getConnectedObjectID());
					if (index!=-1){
						removedConnection=true;
						logicObjects.get(index).output.get(logic.input.get(i).getConnectedConnector()).clearConnectionWithID(logic.getID());
						logic.input.get(i).clearConnection();
						break;

					}
				}
			}
			if (!focus && Maths.clickInRectangle(xM, (int) logic.getX() - objectWidth / 2, yM,
					(int) logic.getY() - objectHeight / 2, objectWidth, objectHeight * logic.getSize())) {
				if (logic instanceof LogicSwitch) {
					((LogicSwitch) logic).click();
					break;
				}
			} else if (!removedConnection) {
				if (connectFocusID == -1) {
					for (int i = 0; i < logic.getOutputConnectorCount(); i++) {
						if (Maths.clickInRectangle(xM, (int) logic.getX() + objectWidth / 2, yM,
								(int) logic.getY() + objectHeight * i - connectorHeight / 2, connectorWidth,
								connectorHeight)) {
							connectFocusID = logic.getID();
							connectFocusConnector = i;
							break;
						}
					}
				} else {
					for (int i = 0; i < logic.getInputConnectorCount(); i++) {
						if (Maths.clickInRectangle(xM, (int) logic.getX() - connectorWidth - objectWidth / 2, yM,
								(int) logic.getY() + objectHeight * i - connectorHeight / 2, connectorWidth,
								connectorHeight)) {
							connectObjects(connectFocusID, connectFocusConnector, logic.getID(), i);
							connectFocusID = -1;
							connectFocusConnector = -1;
							break;
						}
					}
				}
			}
		}
	}

	public static void deleteSelected() {
		for (Integer x : selected) {
			int index = getIndexWithID(x);
			if (index != -1) {
				removeConnection(x);
				logicObjects.remove(index);
			}
		}
		selected.clear();
	}

	public static void deselectAll() {
		selected.clear();
	}

	public static void connectObjects(int outID, int outNum, int inID, int inNum) {
		if (getIndexWithID(inID) != -1 && getIndexWithID(outID) != -1) {
			int inIndex = getIndexWithID(inID);
			int outIndex = getIndexWithID(outID);

			if (inNum < logicObjects.get(inIndex).input.size() && outNum < logicObjects.get(outIndex).output.size()) {

				if (logicObjects.get(inIndex).input.get(inNum).setUpConnection(outID, outNum)) {
					logicObjects.get(outIndex).output.get(outNum).connectToInputConnector(inID, inNum);

				}
			}
		}
	}

	public static void updateConnections(int outID) {
		int outIndex = getIndexWithID(outID);
		if (outIndex != -1) {
			LogicObject logic = logicObjects.get(outIndex);
			for (int i = 0; i < logic.getOutputConnectorCount(); i++) {
				for (int j = 0; j < logic.output.get(i).getConnectedObjectsSize(); j++) {
					int inIndex = getIndexWithID(logic.output.get(i).getConnectorID(j));
					if (inIndex != -1) {
						logicObjects.get(inIndex).input.get(logic.output.get(i).getConnectedConnectors(j))
								.setState(logic.output.get(i).getState());
					}
				}
			}

		}
	}

	protected static List<LogicObject> updateConnections(int outID,List<LogicObject> logicObjects2) {
		int outIndex = getIndexWithID(outID,logicObjects2);
		if (outIndex != -1) {
			LogicObject logic = logicObjects2.get(outIndex);
			for (int i = 0; i < logic.getOutputConnectorCount(); i++) {
				for (int j = 0; j < logic.output.get(i).getConnectedObjectsSize(); j++) {
					int inIndex = getIndexWithID(logic.output.get(i).getConnectorID(j),logicObjects2);
					if (inIndex != -1) {
						logicObjects2.get(inIndex).input.get(logic.output.get(i).getConnectedConnectors(j))
								.setState(logic.output.get(i).getState());
					}
				}
			}

		}
		return logicObjects2;
	}
	
	protected static void removeConnection(int outID) {
		int outIndex = getIndexWithID(outID);
		if (outIndex != -1) {
			for (int i = 0; i < logicObjects.get(outIndex).output.size(); i++) {
				for (int j = 0; j < logicObjects.get(outIndex).output.get(i).getConnectedObjectsSize(); j++) {
					int inIndex = getIndexWithID(logicObjects.get(outIndex).output.get(i).getConnectorID(j));
					if (inIndex != -1) {
						logicObjects.get(inIndex).input
								.get(logicObjects.get(outIndex).output.get(i).getConnectedConnectors(j))
								.clearConnection();
						;
					}
				}
			}

		}
	}

	public static void unFocusConnector() {
		connectFocusID = -1;
		connectFocusConnector = -1;
	}

	public static void merge(int mX, int mY) {
		if (selected.size() > 0) {
			LogicCompartment obj = new LogicCompartment(mX, mY, generateLogicObjectID());
			for (Integer x : selected) {
				int index = getIndexWithID(x);
				if (index != -1) {
					obj.addCompartment(logicObjects.get(index));
				}
			}
			logicObjects.add(obj);
		}
	}
}
