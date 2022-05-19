package converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DictionaryConverter {
	
	private static List<Word> dic = new ArrayList<Word>();

	public static void convertDictionaryToPowerList(String path, String encoding,String output) {
		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), encoding));
			try {
				String word = in.readLine();
				do{
					word=word.toUpperCase();
					int code=0;
					for	(int i=0;i<word.length();i++){
						int dec=(int)word.charAt(i);
						if (dec==902)dec=913;
						else if(dec==904)dec=917;
						else if(dec==905)dec=919;
						else if(dec==906||dec==912||dec==938)dec=921;
						else if(dec==908)dec=927;
						else if(dec==910||dec==939||dec==978||dec==980)dec=933;
						else if(dec==911)dec=937;
						if (dec!=(int)word.charAt(i))
							word = word.replace(word.charAt(i),(char)dec);
						code+=dec;
					}
					boolean flag = false;
					int i=dic.size()-20;
					if (i<0)i=0;
					for (;i<dic.size();i++){
						if (dic.get(i).word.equals(word)){
							flag=true;
							break;
						}
					}
					if (!flag)
						dic.add(new Word(code,word));
					word = in.readLine();

				}while (word!=null);
				Collections.sort(dic, new Sortbyroll()); 
				Writer out = new BufferedWriter(new OutputStreamWriter(
					    new FileOutputStream(output+".txt"), encoding));
					try {
						for (int i = 0; i < dic.size(); i++) {
							Word w = dic.get(i);
							out.write(w.word+"-"+w.code+"\n");
						}
					} finally {
					    out.close();
					}
				
				out.close();
				in.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
	}
}
