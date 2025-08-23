package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.ActionInformation.ActionInformation;
import main.ActionInformation.ActionType;

public class TreasureChest extends GameObject {

	public TreasureChest() {
		super(new ActionInformation(ActionType.Open));
		this.name = "Treasure chest";
		try {
			
			this.image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png")); 
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
