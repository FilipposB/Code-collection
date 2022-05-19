package logic.key;

import logic.math.BICMath;

public class BICKey {
	private int red[] = new int[3];
	private int green[] = new int[3];
	private int blue[] = new int[3];
	private int step;
	private int bits;

	
	public BICKey(int r,int g,int b,int step,int bits) {
		System.out.println("  _KEY_\n---------");
		System.out.println("R   G   B");
		for (int i=0;i<3;i++) {
			red[i]=BICMath.addEndings(BICMath.addEndings(r, 2), i*3);
			green[i]=BICMath.addEndings(BICMath.addEndings(g, 2), i*3);
			blue[i]=BICMath.addEndings(BICMath.addEndings(b, 2), i*3);
			System.out.println(red[i]+" | "+green[i]+" | "+blue[i]);
			this.step=step;
			this.bits=bits;
		}
		System.out.println("---------\n\n");
	}


	public int[] getRed() {
		return red;
	}


	public int[] getGreen() {
		return green;
	}


	public int[] getBlue() {
		return blue;
	}
	
	public int getStep(){
		return step;
	}


	public int getBits() {
		return bits;
	}
	
}
