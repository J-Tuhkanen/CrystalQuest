package main;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		JLayeredPane mainPane = new JLayeredPane();
		GamePanel gamePanel = new GamePanel();
		JPanel uiPanel = new ActionHintPanel(gamePanel);

		mainPane.setPreferredSize(new Dimension(gamePanel.screenWidth, gamePanel.screenHeight));
		uiPanel.setBounds(0, (int)(gamePanel.screenHeight * 0.8), gamePanel.screenWidth, (int)(gamePanel.screenHeight * 0.2));
		gamePanel.setBounds(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
		
		window.setTitle("CystalQuest");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainPane.add(gamePanel, Integer.valueOf(0));
		mainPane.add(uiPanel, Integer.valueOf(1));
		window.add(mainPane);
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setVisible(true);
		
		LoadSounds(gamePanel);
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
