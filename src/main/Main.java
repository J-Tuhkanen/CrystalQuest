package main;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import ui.GamePanel;
import ui.UIPanel;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		JLayeredPane containerPanel = new JLayeredPane();
		GamePanel gamePanel = new GamePanel();
		UIPanel uiPanel = new UIPanel(gamePanel);

		containerPanel.setPreferredSize(new Dimension(gamePanel.screenWidth, gamePanel.screenHeight));
		uiPanel.setBounds(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);		
		gamePanel.setBounds(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
		
		window.setTitle("CystalQuest");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		containerPanel.add(gamePanel, Integer.valueOf(0));
		containerPanel.add(uiPanel, Integer.valueOf(1));
		window.add(containerPanel);
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setVisible(true);
		
		LoadSounds(gamePanel);
		gamePanel.setupGame();
		gamePanel.startGameThread();	
	}
	
	public static void LoadSounds(GamePanel gamePanel) {
		
		gamePanel.sounds.put("gamemusic", new Sound("/sound/curious-forest.wav"));
		gamePanel.sounds.put("coin", new Sound("/sound/coin.wav"));
		gamePanel.sounds.put("powerup", new Sound("/sound/powerup.wav"));
		gamePanel.sounds.put("unlock", new Sound("/sound/unlock.wav"));
		gamePanel.sounds.put("fanfare", new Sound("/sound/fanfare.wav"));
	}
}
