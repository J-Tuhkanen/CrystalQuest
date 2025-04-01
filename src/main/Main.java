package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		GamePanel gamePanel = new GamePanel();
		
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
}
