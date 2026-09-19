package object;

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
		this.description = "A small iron key. It must open something around here.";
		this.image = loadImage("/objects/key.png");
	}

	@Override
	public Action[] getActions() {
		return this._actions;
	}
}
