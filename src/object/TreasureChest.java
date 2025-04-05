package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class TreasureChest extends GameObject {

	public TreasureChest() {
		
		this.name = "Treasure chest";
		try {
			
			this.image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png")); 
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
