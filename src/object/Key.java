package object;

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
}
