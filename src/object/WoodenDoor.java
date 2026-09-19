package object;

import java.awt.image.BufferedImage;

import entity.Action;
import entity.Player;

public class WoodenDoor extends GameObject {

	public boolean locked = true;
	private final BufferedImage _openImage;

	public WoodenDoor(int id) {

		this.id = id;
		this.name = "Door";
		this.description = "A heavy wooden door. It's locked.";
		this.collision = true;
		this.image = loadImage("/objects/door.png");
		this._openImage = loadImage("/objects/door_opened.png");
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

		for (GameObject item : player.inventory.items) {

			if (item instanceof Key key && key.opensId == this.id) {

				this.locked = false;
				this.collision = false;
				this.image = this._openImage;
				this.description = "A heavy wooden door. It stands open.";
				player.consume(key);
				player.playSoundEffect("unlock");
				return true;
			}
		}

		player.say("The door is locked.");
		return false;
	}
}
