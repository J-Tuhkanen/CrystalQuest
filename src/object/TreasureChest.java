package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Action;
import entity.Interactable;

public class TreasureChest extends GameObject implements Interactable {

	private Action[] _actions = new Action[] { 
		Action.Use, 
		Action.Examine 
	};

	public TreasureChest() {
		
		this.name = "Treasure chest";
		try {
			
			this.image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png")); 
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Action[] getActions() {
		return this._actions;
	}
}
