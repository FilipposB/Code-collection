package sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Sound {
	List<AudioClip> clip = new ArrayList<AudioClip>();

	public Sound() {
		soundInit("src/sound/sound.wav");
		soundInit("src/sound/pop.wav");

	}

	public void play(int x) {
		try {
			new Thread() {
				public void run() {
					clip.get(x).play();
				}
			}.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	private void soundInit(String path) {
		
		File fileClip = new File(path);

		URL url = null;

		try {
			URI uri = fileClip.toURI();
			url = uri.toURL();
			clip.add(Applet.newAudioClip(url));
		} catch (MalformedURLException e) {
		}

	}

}
