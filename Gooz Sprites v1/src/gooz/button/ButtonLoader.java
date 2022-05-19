package gooz.button;


public class ButtonLoader {
	public Button[] button = new Button[24];
	private int sWid;
	private int sHei;
	public ButtonLoader(int sW , int sH){
		sWid = sW;
		sHei = sH;
	}
	public ButtonLoader(){
		for (int i = 0;i<button.length;i++){
			button[i]=new Button(true,0,0,0,0,0,0);
		}
	}
	
	// To add a new Button use this format (boolean :visible:,Positions: int :x: , int :y: , Size:  int :wid: , int :hei:, int :sprite:, int :command:
	public void Layout(){
		//test buttons
		button[0]=new Button(true,sWid/500,sHei/100+sHei/100*7,sWid/130*3,sWid/130*3,3,0);
		button[1]=new Button(true,sWid/500,sHei/100+sHei/100*12,sWid/130*3,sWid/130*3,3,0);
		button[2]=new Button(true,sWid/500,sHei/100+sHei/100*17,sWid/130*3,sWid/130*3,3,0);
		//file button
		button[3]=new Button(false,0,0,sWid/90*3,sWid/140*3,0,0);
		//edit button
		button[4]=new Button(false,sWid/90*3,0,sWid/90*3,sWid/140*3,0,0);
		//task bar buttons
		//add a new project
		button[5]=new Button(true,sWid / 500+sWid / 130 * 3,sHei / 26,sWid/180*4,sWid/156*3,0,1);
		//more projects
		button[6]=new Button(true,sWid / 2+ sWid / 100 *27,sHei / 26,sWid/150*3,sWid/156*3,1,2);
		//project 0
		button[7]=new Button(false,sWid / 500+sWid / 130 * 6,sHei / 26,sWid/300*49,sWid/160*3,1,70);
		//project 1
		button[8]=new Button(false,sWid / 500+sWid / 130 * 25,sHei / 26,sWid/300*49,sWid/160*3,1,71);
		//project 2
		button[9]=new Button(false,sWid / 500+sWid / 130 * 44,sHei / 26,sWid/300*49,sWid/160*3,1,72);
		//project 3
		button[10]=new Button(false,sWid / 500+sWid / 130 * 63,sHei / 26,sWid/300*49,sWid/160*3,1,73);
		//project 4
		button[11]=new Button(false,sWid / 500+sWid / 130 * 82,sHei / 26,sWid/300*49,sWid/160*3,1,74);
		//close applet
		button[12]=new Button(false,sWid/5+sWid/2-sWid/130*3 ,sHei/5+sHei/140,sWid/140*3,sWid/140*3,2,4);
		//done applet
		button[13]=new Button(false,sWid/4+sWid/3, sHei /100*66, sWid/12, sHei/15,1,5);
		//add sprite button
		button[14]=new Button(true,sWid*88/100, sHei /100*98, sHei/25, sHei/25,0,3);
		//sprite 0
		button[20]=new Button(false,sWid - sWid / 200 * 43, sHei / 100 * 61, sWid / 100 * 18, sHei / 100 * 37 / 3,0,62);
		//sprite 1
		button[21]=new Button(false,sWid - sWid / 200 * 43, sHei / 100 * 61+sHei / 100 * 37 / 3, sWid / 100 * 18, sHei / 100 * 37 / 3,0,61);
		//sprite 2
		button[22]=new Button(false,sWid - sWid / 200 * 43, sHei / 100 * 61 +2*(sHei / 100 * 37 / 3), sWid / 100 * 18, sHei / 100 * 37 / 3,0,60);
		//sprites up button
		button[18]=new Button(true,sWid*82/100, sHei /100*98, sHei/25, sHei/25,1,50);
		//sprites down button
		button[19]=new Button(true,sWid*94/100, sHei /100*98, sHei/25, sHei/25,1,51);
		//delete sprite 2
		button[17]=new Button(false,sWid - sWid / 200 * 43 +  sWid / 100 * 18/16 *14, sHei / 100 * 61+sHei/ 100 * 18/4, sWid / 100 * 18/16, sWid / 100 * 18/16,2,65);
		//delete sprite 1
		button[16]=new Button(false,sWid - sWid / 200 * 43 +  sWid / 100 * 18/16 *14, sHei / 100 * 61+sHei / 100 * 37 / 3+sWid / 100 * 18/7, sWid / 100 * 18/16, sWid / 100 * 18/16,2,64);
		//delete sprite 0
		button[15]=new Button(false,sWid - sWid / 200 * 43 +  sWid / 100 * 18/16 *14, sHei / 100 * 61 +2*(sHei / 100 * 37 / 3)+sWid / 100 * 18/7, sWid / 100 * 18/16, sWid / 100 * 18/16,2,63);
		//Color Swap Button
		button[23]=new Button(true,sWid/60,sHei/100+sHei/100*70,sWid/200*2,sWid/200*2,3,4);
	}
	
	public int ClickButton(int x, int y){
		int result = 0;
		for (int i = 0; i < button.length; i++){
			if (button[i].Touch(x, y)){
				result = button[i].command;
				break;
			}
		}
		
		return result;
	}
}
