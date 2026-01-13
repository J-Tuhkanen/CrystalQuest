package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.Enum.GameState;
import object.GameObject;

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
		if(this.gamePanel.gameState == GameState.DialogState) {
			drawDialogScreen(graphics);
		}
	}
	
	
	public void drawDialogScreen(Graphics2D graphics) {
		
		int x = this.gamePanel.tileSize * 2;
		int y = this.gamePanel.tileSize / 2;
		int width = this.gamePanel.screenWidth - this.gamePanel.tileSize * 4;
		int height = this.gamePanel.tileSize * 5;
		
		drawSubWindow(x, y, width, height, graphics);
	}
	
	public void drawSubWindow(int x, int y, int width, int height, Graphics2D graphics) {
		Color c = new Color(0,0,0);
		graphics.setColor(c);
		graphics.fillRoundRect(x, y, width, height, width, height);
		
	}
	
	public void drawTextOnCenter(String text, Graphics2D graphics) {
		
		int length = (int)graphics.getFontMetrics().getStringBounds(text, graphics).getWidth();
		
		graphics.drawString(text, 
				this.gamePanel.screenWidth / 2 - length / 2,
				this.gamePanel.screenHeight / 2);
	}
	

}