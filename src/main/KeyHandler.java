package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.Enum.GameState;
import ui.GamePanel;

public class KeyHandler implements KeyListener {

	// Pressed
	public boolean upPressed, downPressed, leftPressed, rightPressed, inventoryPressed, usePressed;
	
	// Released
	public boolean inventoryReleased = true;

	private GamePanel gamePanel;
	
	public KeyHandler(GamePanel gamePanel) {
		
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {	
	}	

	@Override
	public void keyPressed(KeyEvent e) {
				
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_ESCAPE) {
			this.gamePanel.gameState = this.gamePanel.gameState == GameState.Paused 
				? GameState.Running 
				: GameState.Paused;
		}
		if(code == KeyEvent.VK_I && inventoryReleased) {			
			inventoryPressed = true;
			inventoryReleased = false;
		}
		if(code == KeyEvent.VK_W) {
			this.upPressed = true;
		}
		if(code == KeyEvent.VK_S) {

			this.downPressed = true;
		}
		if(code == KeyEvent.VK_D) {

			this.rightPressed = true;
		}
		if(code == KeyEvent.VK_A) {

			this.leftPressed = true;
		}
		if(code == KeyEvent.VK_E) {
			this.usePressed = true;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_I) {
			inventoryPressed = false;
			inventoryReleased = true;
		}
		if(code == KeyEvent.VK_W) {
			this.upPressed = false;
		}
		if(code == KeyEvent.VK_S) {

			this.downPressed = false;
		}
		if(code == KeyEvent.VK_D) {

			this.rightPressed = false;
		}
		if(code == KeyEvent.VK_A) {

			this.leftPressed = false;
		}
		if(code == KeyEvent.VK_E) {
			this.usePressed = false;
		}
	}
}
