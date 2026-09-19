package object;

import entity.Action;

public class Sword extends GameObject {

	private final Action[] _actions = new Action[] {
		Action.Pickup,
		Action.Examine
	};

	public Sword() {

		this.name = "Sword";
		this.description = "A good friend if taken care of.";
		// Placeholder sprite until a torch image exists.
		this.image = loadImage("/objects/sword_normal.png");
	}

	@Override
	public Action[] getActions() {
		return this._actions;
	}
}
