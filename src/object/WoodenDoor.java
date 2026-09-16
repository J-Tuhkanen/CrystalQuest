package object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Action;
import entity.Player;

public class WoodenDoor extends GameObject {

	public boolean locked = true;
	private BufferedImage openImage;

	public WoodenDoor(int id) {

		this.id = id;
		this.name = "Door";
		this.collision = true;
		try {

			this.image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
			this.openImage = ImageIO.read(getClass().getResourceAsStream("/objects/door_opened.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Action[] getActions() {
		return this.locked
			? new Action[] { Action.Use, Action.Examine }
			: new Action[] { Action.Examine };
	}

	@Override
	public boolean use(Player player) {

		if (!this.locked) {
			return false;
		}

		for (int i = 0; i < player.inventory.items.length; i++) {

			GameObject item = player.inventory.items[i];

			if (item instanceof Key key && key.opensId == this.id) {

				this.locked = false;
				this.collision = false;
				this.image = this.openImage;
				player.inventory.RemoveAt(i);
				return true;
			}
		}

		player.say("The door is locked.");
		return false;
	}
}
