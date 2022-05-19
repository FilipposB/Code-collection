package reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import converter.Word;

public class Solver {
	public static String anagram(String word,String pathCode,String encoding){
		List<String> possible = new ArrayList<String>();
		
		String result=null;
		int code=0;
		word = word.toUpperCase();
		for	(int i=0;i<word.length();i++){
			int dec=(int)word.charAt(i);
			if (dec==902)dec=913;
			else if(dec==904)dec=917;
			else if(dec==905)dec=919;
			else if(dec==906||dec==912||dec==938)dec=921;
			else if(dec==908)dec=927;
			else if(dec==910||dec==939||dec==978||dec==980)dec=933;
			else if(dec==911)dec=937;
			code+=dec;
		}
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(pathCode), encoding));
			String codeFile;
			try {
				codeFile = in.readLine();
				String[] parts = codeFile.split("-");
				Word w = new Word(Integer.parseInt(parts[1]),parts[0]);
				boolean flag = false;
				do{
					if(w.code==code){
						flag = true;
						possible.add(w.word);
					}
					else if (flag){
						break;
					}
					codeFile = in.readLine();
					parts = codeFile.split("-");
					w = new Word(Integer.parseInt(parts[1]),parts[0]);
				}while (codeFile!=null&&w.code<=code);
				
				long longCode=1;
				for	(int i=0;i<word.length();i++){
					int dec=(int)word.charAt(i);
					dec-=900;
					longCode*=dec;
				}
				
				for(int i=0;i<possible.size();i++){
					long tempCode=1;
					for	(int j=0;j<possible.get(i).length();j++){
						int dec=(int)possible.get(i).charAt(j);
						dec-=900;
						tempCode*=dec;
					}
					if (longCode==tempCode){
						System.out.println(possible.get(i));
						result = possible.get(i);
					}
				}
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
