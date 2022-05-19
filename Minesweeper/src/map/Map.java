package map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Map {
	public int visibleMap[][];
	private int map[][];
	private int nonBombs;
	private final static Font gameFont = new Font("default", Font.BOLD, 12);
	
	public boolean hitBomb=false;
	public boolean win=false;

	public Map(int size,int bombs){
		newMap(size,bombs);
	}
	
	public void newMap(int size,int bombs){
		map=new int[size][size];
		nonBombs=size*size-bombs;
		hitBomb=false;
		win=false;

		visibleMap=new int[size][size];
		for (int i=0;i<size;i++){
			for (int j=0;j<size;j++){
				map[i][j]=0;
				visibleMap[i][j]=0;
			}
		}
		
		Random rand = new Random();
		while (bombs>0){
			int x=rand.nextInt(size);
			int y=rand.nextInt(size);
			if (map[x][y]!=-1){
				map[x][y]=-1;
				addAround(x,y);
				bombs--;
			}
		}
		
		
	}
	
	private void addAround(int x,int y){
		addTo(x-1,y);
		addTo(x+1,y);
		addTo(x-1,y-1);
		addTo(x-1,y+1);
		addTo(x,y+1);
		addTo(x,y-1);
		addTo(x+1,y+1);
		addTo(x+1,y-1);

	}
	
	private void addTo(int x,int y){
		if (x<0||x>=map.length||y<0||y>=map.length)return;
		if (map[x][y]!=-1)map[x][y]++;
	}
	
	public void paint(Graphics g,int tileSize){
		for (int i=0;i<map.length;i++){
			for (int j=0;j<map.length;j++){
				if (visibleMap[i][j]==0)g.setColor(Color.BLUE);
				else if (visibleMap[i][j]==1)g.setColor(Color.yellow);
				else if (map[i][j]==-1)g.setColor(Color.RED);
				else g.setColor(Color.WHITE);
				g.fillRect(i*tileSize, j*tileSize, tileSize, tileSize);
				g.setColor(Color.black);
				if (map[i][j]>0&&visibleMap[i][j]==-1){
					g.setFont(gameFont);
					g.drawString(""+map[i][j], i*tileSize+tileSize/2, j*tileSize+tileSize/2);
				}
				g.drawRect(i * tileSize, j * tileSize, tileSize, tileSize);
			}
		}
		
	}
	
	public void leftClickOnMap(int x,int y){
		if (x>=map.length||y>=map.length)return;
		if (visibleMap[x][y]!=-1){
			visibleMap[x][y]=-1;
			nonBombs--;
			if (map[x][y]==0)clearEmpty(x,y);
			if (nonBombs==0)win=true;

		}
		if (map[x][y]==-1)hitBomb=true;
	}
	
	
	public void rightClickOnMap(int x,int y){
		if (x>=map.length||y>=map.length)return;
		if (visibleMap[x][y]==1){
			visibleMap[x][y]=0;
			return;
		}
		if (visibleMap[x][y]==-1)return;
		visibleMap[x][y]=1;
	}
	
	private void clearEmpty(int x,int y){
		checkEmpty(x-1,y);
		checkEmpty(x+1,y);
		checkEmpty(x-1,y-1);
		checkEmpty(x-1,y+1);
		checkEmpty(x,y+1);
		checkEmpty(x,y-1);
		checkEmpty(x+1,y+1);
		checkEmpty(x+1,y-1);
	}
	
	private void checkEmpty(int x,int y){
		if (x<0||x>=map.length||y<0||y>=map.length)return;
		if (visibleMap[x][y]!=-1){
			visibleMap[x][y]=-1;
			nonBombs--;
			if (map[x][y]==0)clearEmpty(x,y);
		}
	}
}
