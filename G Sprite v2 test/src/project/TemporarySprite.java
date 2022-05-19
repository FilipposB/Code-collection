package project;

public class TemporarySprite {
		private int[][] color;
		private int lineSize;
		
		public TemporarySprite(int line,int[][] col){
			color=new int[line][line];
			for (int i=0;i<line;i++){
				for (int j=0;j<line;j++){
					color[i][j]=col[i][j];
				}
			}
			lineSize = line;	
		}
		
		public int getColor(int i,int j){
			return color[i][j];
		}
		
		public int getLineCount(){
			return lineSize;
		}
		
		public void setColor(int y,int x,int col){
			color[y][x]=col;
		}
}
