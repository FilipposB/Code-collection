package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.Game;

public class World {
	private int mapSize=1000;
	protected Tile map[][];
	
	public World(Game game){
		map=new Tile[mapSize][256];
		for (int i=0;i<mapSize;i++){
			for(int j=100;j>=0;j--){
				map[i][j]=new SolidTile(i,j,2,Color.BLUE,game);
			}
		}
	}
	
	public void drawMap(double worldX,double worldY,int width,int height,Graphics2D g){
		int xSt=(int) ((worldX/32)-1);
		int ySt=(int) ((worldY/32)-1);
		int totW=(int) (width/32+1);
		int totH=(int) (height/32+3);
		
		for (int i=xSt;i<xSt+totW+1&&i<mapSize;i++){
			for(int j=ySt;j<ySt+totH;j++){
				if (i>=0&&j>=0&&map[i][j]!=null)
				map[i][j].draw(g);
			}
		} 
	}
	
	public int checkCollision(Rectangle e){
		for (int i=0;i<mapSize;i++){
			for(int j=100;j>=0;j--){
				if (map[i][j]!=null){
					if (e.contains(map[i][j].getHitBox()))return 1;
				}
			}
		}
		return 0;
	}
	
	
}
