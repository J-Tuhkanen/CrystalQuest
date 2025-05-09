package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.Direction;
import main.GamePanel;

public class OldManNpc extends Entity {

	private GamePanel gamePanel;

	public OldManNpc(GamePanel gp) {
		
		super(gp, "/npc/oldman");
		this.gamePanel = gp;
		this.speed = 1;
	}
	
	@Override
	public void update() {
	
	}


	@Override
	public void draw(Graphics2D g) {
		this.spriteIndex = 1;
			
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
		
		g.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
	}
}
