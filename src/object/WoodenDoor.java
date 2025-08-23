package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.ActionInformation.ActionInformation;
import main.ActionInformation.ActionType;

public class WoodenDoor extends GameObject {

	public WoodenDoor() {		
		super(new ActionInformation(ActionType.Open));
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
