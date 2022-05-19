package load;

public class LoadedImage {
	private int[][] cols;
	
	public LoadedImage(int x){
		x=(int)Math.sqrt(x);
		cols=new int[x][x];
	}
	
	public void addColor(int x,int y,int col){
		cols[y][x]=col;

	}
	
	public int getColor(int x,int y){
		return cols[y][x];
	}
	
	public int getTotalSize(){
		return cols.length;
	}
}
