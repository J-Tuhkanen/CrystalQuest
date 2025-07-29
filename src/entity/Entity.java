package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.CollisionInformation;
import main.GamePanel;
import main.Enum.Direction;

public abstract class Entity  {

	private int _spriteCounter = 0;
	public final boolean canPickupItems;
	
	protected GamePanel gp;
	protected int spriteCount = 2;
	protected int spriteIndex = 0;
	protected int actionLockCounter = 0;
	
	public int worldX, worldY, speed;
	public final String imagePrefix;
	public Direction movementDirection = Direction.Down;
	public Direction lookDirection = Direction.Down;
	
	public Rectangle collision;
	public boolean collisionOn = false;
	
	public Rectangle hitBox;
	
	public BufferedImage[] up = new BufferedImage[this.spriteCount];
	public BufferedImage[] left = new BufferedImage[this.spriteCount];
	public BufferedImage[] right = new BufferedImage[this.spriteCount];
	public BufferedImage[] down = new BufferedImage[this.spriteCount];
	
	public Entity(boolean canPickupItems, GamePanel gp, String imagePrefix) {
		
		this.canPickupItems = canPickupItems;
		this.gp = gp;
		this.imagePrefix = imagePrefix;
		loadImages();
		setupCollision();
	}
	
	public Entity(boolean canPickupItems, GamePanel gp, String imagePrefix, int spriteCount) {
		
		this.canPickupItems = canPickupItems;
		this.gp = gp;
		this.imagePrefix = imagePrefix;
		this.spriteCount = spriteCount;
		loadImages();
	}
	
	public abstract void update();	
	public abstract void draw(Graphics2D g);
	
	public void setupCollision() {
		
		this.collision = new Rectangle();
		this.collision.height = 10;
		this.collision.width = gp.tileSize;
		this.collision.x = 0;
		this.collision.y = gp.tileSize - this.collision.height;
		
		this.hitBox = new Rectangle();
		this.hitBox.x = 8;
		this.hitBox.y = 16;
		this.hitBox.width = 32;
		this.hitBox.height = 32;
	}
	
	protected CollisionInformation checkCollision() {
		
		this.collisionOn = false;
		this.gp.collisiongChecker.checkTile(this);		
				
		// Check object collision
		var gameObjectCollidedWith = this.gp.collisiongChecker.checkObjectCollision(this);
		var npcsCollidedWith = this.gp.collisiongChecker.checkEntityCollision(this);
	
		return new CollisionInformation(npcsCollidedWith, gameObjectCollidedWith);
	}
	
	protected void move() {
		
		int lookDirectionPenalty = this.lookDirection != this.movementDirection	? 2 : 0;
		
		int currentSpeed = this.speed - lookDirectionPenalty;
		
		if (this.movementDirection == Direction.Down) {
		    worldY += currentSpeed;
		}
		if (this.movementDirection == Direction.Left) {
		    worldX -= currentSpeed;
		}
		if (this.movementDirection == Direction.Right) {
		    worldX += currentSpeed;
		}
		if (this.movementDirection == Direction.Up) {
		    worldY -= currentSpeed;
		}
	}
	
	protected void updateSprite() {
		
		this._spriteCounter++;
		
		if(this._spriteCounter > 13) {
			
			if(this.spriteIndex + 1 >= this.spriteCount) {
				this.spriteIndex = 0;
			}
			else {			
				this.spriteIndex++;
			}
			this._spriteCounter = 0;
		}
	}
	
	private void loadImages() {
		try {			
			
			for(int i = 0; i < up.length; i++) {						
				up[i] = ImageIO.read(getClass().getResourceAsStream(String.format("%s_up_%s.png", this.imagePrefix, i + 1)));				
			}
			for(int i = 0; i < down.length; i++) {
				down[i] = ImageIO.read(getClass().getResourceAsStream(String.format("%s_down_%s.png", this.imagePrefix, i + 1)));
			}
			for(int i = 0; i < left.length; i++) {
				left[i] = ImageIO.read(getClass().getResourceAsStream(String.format("%s_left_%s.png", this.imagePrefix, i + 1)));
			}
			for(int i = 0; i < right.length; i++) {
				right[i] = ImageIO.read(getClass().getResourceAsStream(String.format("%s_right_%s.png", this.imagePrefix, i + 1)));
			}	
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
