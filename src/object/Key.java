package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.ActionInformation.ActionInformation;
import main.ActionInformation.ActionType;

public class Key extends GameObject {
		
	public Key() {
		super(new ActionInformation(ActionType.Pickup));
		this.name = "Key";		
		try {
			
			this.image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png")); 
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
