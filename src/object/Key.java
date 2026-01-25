package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Action;
import entity.Interactable;

public class Key extends GameObject implements Interactable {
	
	private Action[] _actions = new Action[] { 
		Action.Pickup, 
		Action.Examine 
	};
	
	public Key() {
		
		this.name = "Key";		
		try {
			
			this.image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png")); 
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
