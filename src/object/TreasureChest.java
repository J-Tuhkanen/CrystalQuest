package object;

import entity.Action;

public class TreasureChest extends GameObject {

	private final Action[] _actions = new Action[] { 
		Action.Use, 
		Action.Examine 
	};

	public TreasureChest() {
		
		this.name = "Treasure chest";
		this.description = "A sturdy wooden chest with iron bands.";
		this.image = loadImage("/objects/chest.png");
	}

	@Override
	public Action[] getActions() {
		return this._actions;
	}
}
