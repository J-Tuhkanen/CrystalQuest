package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		GamePanel gamePanel = new GamePanel();
		
		window.setTitle("CystalQuest");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(gamePanel);
		window.add(gamePanel);
		gamePanel.startGameThread();
		window.pack();
		window.setLocationRelativeTo(null);
		window.setResizable(true);
		window.setVisible(true);
	}

}
