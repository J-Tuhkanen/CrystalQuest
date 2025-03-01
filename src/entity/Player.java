package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.Direction;
import main.GamePanel;
import main.KeyHandler;
import abstraction.IUpdateable;

public class Player extends Entity implements IUpdateable {
	
	KeyHandler keyH;
	int tileSize = 0;
	int spriteCounter = 0;
	int cameraX, cameraY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp, "/player/boy");
		this.gp = gp;
		this.keyH = keyH;
		this.tileSize = gp.getTileSize();
		
		cameraX = gp.screenWidth/2;
		cameraY= gp.screenHeight/2;
				
		setDefaultValues();
	}
	
	public void setDefaultValues() {
		worldX = 200;
		worldY = 200;
		speed = 4;
	}
	
	public void update() {
		
		if(keyH.upPressed == false && 
		   keyH.downPressed == false && 
		   keyH.leftPressed == false && 
		   keyH.rightPressed == false) {
			return;
		}
		
		if(keyH.upPressed) {
			direction = Direction.Up;
			worldY -= speed;
		}
		if(keyH.downPressed) {
			direction = Direction.Down;
			worldY += speed;
		}
		if(keyH.leftPressed) {
			direction = Direction.Left;
			worldX -= speed;
		}
		if(keyH.rightPressed) {
			direction = Direction.Right;
			worldX += speed;
		}
		
		this.spriteCounter++;
		
		if(this.spriteCounter > 13) {
			
			if(this.spriteIndex + 1 >= this.spriteCount) {
				this.spriteIndex = 0;
			}
			else {			
				this.spriteIndex++;
			}
			this.spriteCounter = 0;
		}
	}
	
	public void draw(Graphics2D g) {
		
		BufferedImage image = null;		
		
		switch(this.direction) {
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
		
		g.drawImage(image, cameraX, cameraY, gp.getTileSize(), gp.getTileSize(), null);
	}
}
