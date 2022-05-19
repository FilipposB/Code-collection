package main;

import java.util.Scanner;

import converter.DictionaryConverter;
import reader.Solver;

public class Runner {

	public static void main(String[] args) {
		
		//DictionaryConverter.convertDictionaryToPowerList("greek.txt", "unicode","greekResult");
		
		Scanner in = new Scanner(System.in);
		String order="";
		System.out.println("Welcome !");
		while(!order.equals("stop")){
			order=in.nextLine();
			System.out.println("Tryign to solve "+order);
			String sol = Solver.anagram(order, "greekResult.txt", "unicode");
			if (sol==null){
				System.out.println("Couldnt find it :(");
			}
			System.out.println("Over !\n");

		}
		in.close();
	}

}
