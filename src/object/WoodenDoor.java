package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class WoodenDoor extends GameObject {

	public WoodenDoor() {
		
		this.name = "Door";
		this.collision = true;

		try {
			
			this.image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png")); 
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
