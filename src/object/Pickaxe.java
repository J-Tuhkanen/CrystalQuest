package object;

import entity.Action;
import entity.Player;

public class Pickaxe extends GameObject {

	private final Action[] _actions = new Action[] {
		Action.Pickup,
		Action.Examine
	};

	public Pickaxe() {

		this.name = "Pickaxe";
		this.description = "Useful against rock.";
		// Placeholder sprite until a stick image exists.
		this.image = loadImage("/objects/pickaxe.png");
	}

	@Override
	public Action[] getActions() {
		return this._actions;
	}

	@Override
	public CombineResult combineWith(GameObject other, Player player) {

		// if (other instanceof Cloth) {
		// 	return new CombineResult(List.of(this, other), List.of(new Sword()), "I made a torch.");
		// }
		return null;
	}
}
