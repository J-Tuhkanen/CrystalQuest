package main;

import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	private Clip _clip;
	private URL _soundUrl;
	
	public Sound(String resoursePath) {
		_soundUrl = getClass().getResource(resoursePath);
		
		try {			
			_clip = AudioSystem.getClip();
			_clip.open(AudioSystem.getAudioInputStream(_soundUrl));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		_clip.start();
	}
	
	public void loop() {
		_clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		_clip.stop();
	}
}