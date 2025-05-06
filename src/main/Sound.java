package main;

import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	Clip clip;
	URL soundUrl;
	
	public Sound(String resoursePath) {
		soundUrl = getClass().getResource(resoursePath);
		setFile();
	}
	
	public void setFile() {
		try {			
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(soundUrl));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
}