package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class UI {
	
	GamePanel gamePanel;
	final Font arial_plain_40 = new Font("Arial", Font.PLAIN, 40);
	BufferedImage inventoryImage;
	
	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		try {
			
			inventoryImage = ImageIO.read(getClass().getResourceAsStream("/ui/inventory.png")); 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D graphics) {
		
		graphics.setFont(arial_plain_40);
		graphics.setColor(Color.white);
		
		if(this.gamePanel.gameState == GameState.Paused) {
			
			graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 80));
			this.drawTextOnCenter("PAUSE", graphics);
		}
		else if(this.gamePanel.player.inventoryIsOpen) {
			drawInventory(graphics);
		}
	}
	
	public void drawInventory(Graphics2D graphics) {
		graphics.drawImage(
				this.inventoryImage, 
				this.gamePanel.screenWidth / 2 - 200, 
				this.gamePanel.screenHeight / 2 - 200,
				400, 
				200, 
				null);
	}
	
	private void drawTextOnCenter(String text, Graphics2D graphics) {
		
		int length = (int)graphics.getFontMetrics().getStringBounds(text, graphics).getWidth();
		
		graphics.drawString(text, 
				this.gamePanel.screenWidth / 2 - length / 2,
				this.gamePanel.screenHeight / 2);
	}
}