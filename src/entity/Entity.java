package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Direction;
import main.GamePanel;

public class Entity {

	protected GamePanel gp;
	public int worldX, worldY, speed;
	public int spriteIndex = 0;
	public final int spriteCount = 2;
	public final String imagePrefix;
	public Direction direction = Direction.Down;
	
	public Rectangle collision;
	public boolean collisionOn = false;
	
	public BufferedImage[] up = new BufferedImage[this.spriteCount];
	public BufferedImage[] left = new BufferedImage[this.spriteCount];
	public BufferedImage[] right = new BufferedImage[this.spriteCount];
	public BufferedImage[] down = new BufferedImage[this.spriteCount];
	
	public Entity(GamePanel gp, String imagePrefix) {
		this.gp = gp;
		this.imagePrefix = imagePrefix;
		loadImages();
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
