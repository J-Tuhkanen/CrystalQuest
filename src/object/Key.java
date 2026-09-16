package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Action;

public class Key extends GameObject {
	
	private Action[] _actions = new Action[] {
		Action.Pickup,
		Action.Examine
	};

	// The id of the WoodenDoor (or other lockable object) this key opens.
	public final int opensId;

	public Key(int opensId) {

		this.opensId = opensId;
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
