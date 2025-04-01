package object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Key extends GameObject {

	public Key() {
		
		this.name = "Key";
		
		try {
			
			this.image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png")); 
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Key(String name) {
		
		this.name = name;
		
		try {
			
			this.image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png")); 
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Key(String name, BufferedImage image) {
		
		this.name = name;			
		this.image = image;
	}
}
