package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.Enum.Direction;

public abstract class Npc extends Entity {

	public Npc(GamePanel gp, String imagePrefix) {
		super(gp, imagePrefix);
	}
	
	public abstract void updateAction();
	
	@Override
	public void draw(Graphics2D g) {
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.cameraX &&
		    worldX - gp.tileSize < gp.player.worldX + gp.player.cameraX &&
		    worldY + gp.tileSize > gp.player.worldY - gp.player.cameraY &&
		    worldY - gp.tileSize < gp.player.worldY + gp.player.cameraY) {
		
			int screenX = worldX - gp.player.worldX + gp.player.cameraX;
			int screenY = worldY - gp.player.worldY + gp.player.cameraY;
			
			BufferedImage image = null;		
			
			switch(this.lookDirection) {
				case Direction.Up:
					image = this.up[spriteIndex];
					break;
				case Direction.Down:
					image = this.down[spriteIndex];
					break;
				case Direction.Left:
					image = this.left[spriteIndex];
					break;
				case Direction.Right:
					image = this.right[spriteIndex];
					break;
				default:
					break;
			}
			
			g.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}	
	}
	
	@Override
	protected void checkCollision() {
		super.checkCollision();
		if(this.collisionOn == false) {
			this.gp.collisiongChecker.checkPlayerCollision(this);
		}
	}
}
