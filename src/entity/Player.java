package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Direction;
import main.GamePanel;
import main.KeyHandler;
import object.GameObject;
import abstraction.IUpdateable;

public class Player extends Entity implements IUpdateable {
	
	KeyHandler keyH;
	int tileSize = 0;
	int spriteCounter = 0;
	public int cameraX, cameraY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp, "/player/boy");
		this.gp = gp;
		this.keyH = keyH;
		this.tileSize = gp.tileSize;
		this.collision = new Rectangle();
		
		this.collision.height = 10;
		this.collision.width = gp.tileSize;
		this.collision.x = 0;
		this.collision.y = gp.tileSize - this.collision.height;
		
		this.cameraX = gp.screenWidth/2;
		this.cameraY = gp.screenHeight/2;
		
		this.solidArea = new Rectangle();
		this.solidArea.x = 8;
		this.solidArea.y = 16;
		this.solidArea.width = 32;
		this.solidArea.height = 32;
		
		this.solidAreaDefaultX = this.solidArea.x;
		this.solidAreaDefaultY = this.solidArea.y;
		
		setDefaultValues();
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 5;
		worldY = gp.tileSize * 16;
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
		}
		if(keyH.downPressed) {
			direction = Direction.Down;			
		}
		if(keyH.leftPressed) {
			direction = Direction.Left;			
		}
		if(keyH.rightPressed) {
			direction = Direction.Right;			
		}
		
		// Check collision
		collisionOn = false;
		this.gp.collisiongChecker.checkTile(this);
		
		// Check object collision
		this.gp.collisiongChecker.checkObject(this, true);
		
		//if(objectIndex >= 0) {
			//pickUpObject(objectIndex);
		
		if (collisionOn == false) {
			if (this.direction == Direction.Down) {
			    worldY += speed;
			}
			if (this.direction == Direction.Left) {
			    worldX -= speed;
			}
			if (this.direction == Direction.Right) {
			    worldX += speed;
			}
			if (this.direction == Direction.Up) {
			    worldY -= speed;
			}

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
	
	public void pickUpObject(int objIndex) {
		
		String itemName = gp.objects.get(objIndex).name;
		System.out.println("pick up " + itemName);
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
		
		g.drawImage(image, cameraX, cameraY, gp.tileSize, gp.tileSize, null);
	}
}
