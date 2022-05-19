package math;

public class BICMath {

	//Modifies a number to fit into BIC (numbers in a chain "-9-0-1-2-3-4-5-6-7-8-9-0-")
	public static int calculate(int number){
		if (number >= 10){
			return lastDigit(number);
		}
		else if (number < 0){
			return lastDigit(10+number);
		}
		return number;
	}
	
	//Returns the last digit of a number
	public static int lastDigit(int number){
		return number % 10;
	}
	
	//Gets an array with the total of all last digits used in the image and returns the base number as a result
	public static int getBase(int numbers[]){
		int index=0;
		int best=weightCalculation(0,numbers);
		for (int i=1;i<10;i++){
			int weight = weightCalculation(i,numbers);
			if (weight<best){
				best = weight;
				index=i;
			}
		}
		return index;
	}
	
	//Returns the weight of the images last digits
	private static int weightCalculation(int n,int numbers[]){
		return (numbers[calculate(n)]+numbers[calculate(n+3)]+numbers[calculate(n+6)])+2*(numbers[calculate(n-1)]+numbers[calculate(n+1)]+numbers[calculate(n+2)]+numbers[calculate(n+4)]+numbers[calculate(n+5)]+numbers[calculate(n+7)])+3*numbers[calculate(n+8)];
	}
	
	public static int compare(int num1,int num2){
		if (calculate(1))
	}
	
	//Rounds number to the encryption
	public static int roundToRGB(int number,int encryption){
		if (number)
	}
	
}
