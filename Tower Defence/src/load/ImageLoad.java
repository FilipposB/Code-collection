package load;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ImageLoad {
	private Translator tran = new Translator();
	private List<Color> color = new ArrayList<Color>();
	private List<LoadedImage> img = new ArrayList<LoadedImage>();
	private BufferedReader b;	
	public ImageLoad(String name) {
		File textFile = new File(name+".goz");
		
		try {
			b = new BufferedReader(new FileReader(textFile));

			String readLine = "";

			int lineCount=-1;
			while ((readLine = b.readLine()) != null) {
				if (lineCount==-1){
					for (int i=0;i<readLine.length();i+=6){
						color.add((Color.decode("0x"+readLine.substring(i, i+6))));
					}
				}else{
					
					int i = 0;
					int totalSize = 0;

					while (!readLine.substring(i, i + 1).equals(".")) {
						String line = "";
						boolean size = false;
						while (!readLine.substring(i, i + 1).equals(",")) {
							line += readLine.substring(i, i + 1);
							if (readLine.substring(i, i + 1).equals("_"))
								size = true;
							i++;
						}
						i++;
						if (size) {
							String amound = "";
							int count = 0;
							while (!line.substring(count, count + 1).equals("_")) {
								amound += line.substring(count, count + 1);
								count++;
							}
							count++;
							while (count < line.length()) {
								count++;
							}
							totalSize += tran.hexToDec(amound) + 1;
						} else {
							totalSize++;
						}
					}
					img.add(new LoadedImage(totalSize));
					i=0;
					int x=0;
					int y=0;
					while (!readLine.substring(i, i+1).equals(".")){
						String line="";
						boolean size=false;
						while (!readLine.substring(i, i+1).equals(",")){
							line+=readLine.substring(i, i+1);
							if (readLine.substring(i, i+1).equals("_"))size=true;
							i++;
						}
						i++;
						if (size){
							String amound="";
							String color="";
							int count=0;
							while (!line.substring(count, count+1).equals("_")){
								amound+=line.substring(count, count+1);
								count++;
							}
							count++;
							while (count<line.length()){
								color+=line.substring(count, count+1);
								count++;
							}
							for (int j=0;j< tran.hexToDec(amound)+1;j++){
								img.get(img.size()-1).addColor(x, y, tran.hexToDec(color));
								x++;
								if (x==(int)Math.sqrt(totalSize)){
									x=0;
									y++;
								}
							}
						}
						else{
							img.get(img.size()-1).addColor(x, y, tran.hexToDec(line));
							x++;
							if (x==(int)Math.sqrt(totalSize)){
								x=0;
								y++;
							}							}
					}
				}
				lineCount++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				b.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public Color getImageColor(int pos,int x,int y){
		return color.get(img.get(pos).getColor(y, x));
		
	}
	
	public int getTotalSize(int x){
		return img.get(x).getTotalSize();
	}
	
}
