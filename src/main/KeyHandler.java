package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.Enum.GameState;
import ui.GamePanel;

public class KeyHandler implements KeyListener {

	// Pressed
	public boolean upPressed, downPressed, leftPressed, rightPressed, inventoryPressed, usePressed, escapePressed, sprintPressed;
	
	// Released
	public boolean inventoryReleased = true;
	public boolean useReleased = true;

	private final GamePanel gamePanel;
	private int menuMovement;
	private boolean pendingUse;
	private boolean pendingEscape;

	public synchronized int consumeMenuMovement() {
		int movement = menuMovement;
		menuMovement = 0;
		return movement;
	}

	public synchronized boolean consumeUsePress() {
		boolean pressed = pendingUse;
		pendingUse = false;
		return pressed;
	}

	// Escape presses meant for an open menu or the inventory; handled by Player.update().
	public synchronized boolean consumeEscapePress() {
		boolean pressed = pendingEscape;
		pendingEscape = false;
		return pressed;
	}
	
	public KeyHandler(GamePanel gamePanel) {
		
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {	
	}	

	@Override
	public synchronized void keyPressed(KeyEvent e) {
				
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_ESCAPE && !escapePressed) {
			escapePressed = true;
			menuMovement = 0;
			pendingUse = false;
			
			var player = this.gamePanel.player;
			
			if(this.gamePanel.gameState == GameState.Paused) {
				this.gamePanel.gameState = GameState.Running;
			}
			else if(player.actionMenu.isOpen() || player.inventoryIsOpen) {
				// Menus and inventory modes step back one level at a time; see Player.handleEscape().
				this.pendingEscape = true;
			}
			else {
				this.gamePanel.gameState = GameState.Paused;
			}
		}
		if(code == KeyEvent.VK_I && inventoryReleased) {			
			inventoryPressed = true;
			inventoryReleased = false;
		}
		if(code == KeyEvent.VK_W) {
			if (!upPressed && gamePanel.player.actionMenu.isOpen()) 
				menuMovement--;
			this.upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			if (!downPressed && gamePanel.player.actionMenu.isOpen()) 
				menuMovement++;
			this.downPressed = true;
		}
		if(code == KeyEvent.VK_D) {

			this.rightPressed = true;
		}
		if(code == KeyEvent.VK_A) {

			this.leftPressed = true;
		}
		if(code == KeyEvent.VK_E && this.useReleased) {
			this.pendingUse = gamePanel.gameState != GameState.Paused;
			this.usePressed = true;
			this.useReleased = false;
		}
		if(code == KeyEvent.VK_SPACE) {
			this.sprintPressed = true;
		}
	}
	
	@Override
	public synchronized void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_ESCAPE) escapePressed = false;
		if(code == KeyEvent.VK_I) {
			this.inventoryPressed = false;
			this.inventoryReleased = true;
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
			this.useReleased = true;
		}
		if(code == KeyEvent.VK_SPACE) {
			this.sprintPressed = false;
		}
	}
}
