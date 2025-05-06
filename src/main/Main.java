package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		GamePanel gamePanel = new GamePanel();
		LoadSounds(gamePanel);
		
		window.setTitle("CystalQuest");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(gamePanel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setVisible(true);

		gamePanel.setupGame();
		gamePanel.startGameThread();
	
	}
	
	public static void LoadSounds(GamePanel gamePanel) {
		
		gamePanel.sounds.put("gamemusic", new Sound("/sound/BlueBoyAdventure.wav"));
		gamePanel.sounds.put("coin", new Sound("/sound/coin.wav"));
		gamePanel.sounds.put("powerup", new Sound("/sound/powerup.wav"));
		gamePanel.sounds.put("unlock", new Sound("/sound/unlock.wav"));
		gamePanel.sounds.put("fanfare", new Sound("/sound/fanfare.wav"));
	}
}
