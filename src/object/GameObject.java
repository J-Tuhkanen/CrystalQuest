package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public abstract class GameObject {

	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	
	public void draw(Graphics2D graphics, GamePanel gp) {
		
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.cameraX &&
		    worldX - gp.tileSize < gp.player.worldX + gp.player.cameraX &&
		    worldY + gp.tileSize > gp.player.worldY - gp.player.cameraY &&
		    worldY - gp.tileSize < gp.player.worldY + gp.player.cameraY) {
		
			int screenX = worldX - gp.player.worldX + gp.player.cameraX;
			int screenY = worldY - gp.player.worldY + gp.player.cameraY;
			
			graphics.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}		
	}
}
