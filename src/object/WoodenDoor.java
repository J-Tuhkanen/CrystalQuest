package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Action;

public class WoodenDoor extends GameObject {

	private Action[] _actions = new Action[] { 
		Action.Use, 
		Action.Examine 
	};
	
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

	@Override
	public Action[] getActions() {
		return this._actions;
	}
}
