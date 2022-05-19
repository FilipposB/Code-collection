package runner;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import logic.decrypt.BICDecode;
import logic.encrypt.BICEncode;
import logic.key.BICKey;

public class Runner {

	private static char alphabet[] = { 'E', 'T', 'A', 'O', 'I', 'N', 'S', 'R', 'H', 'D', 'L', 'U', 'C', 'M', 'F', 'Y',
			'W', 'G', 'P', 'B', 'V', 'K', 'X', 'Q', 'J', 'Z','{','}','\n',';','(',')',',','=','+'};

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File("place.png"));
		} catch (IOException e) {
			return;
		}

		int step = 1;
		BICKey key = BICEncode.generateBICKey(bi, step);
		//BICKey key = new BICKey(4,8,7,1,0);
		bi = BICEncode.encodeTextToImage(bi,readLineByLineJava8("C:/Users/Pogan/Desktop/ex2.txt").toCharArray(),alphabet, key);
		//bi = BICEncode.encodeTextToImage(bi,"Hey this is a secret messenge".toCharArray(),alphabet, key);

		System.out.println();

		System.out.println(BICDecode.decodeStringFromImage(bi, alphabet, key));

		System.out.println(key.getBits() + " Bit");
		System.out.println((double) ((double) (key.getBits() / 8) / 1024) + "KB");

		File outputfile = new File("output.png");
		try {
			ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	 //Read file content into string with - Files.lines(Path path, Charset cs)
	 
    private static String readLineByLineJava8(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
 
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
 
        return contentBuilder.toString();
    }
}
