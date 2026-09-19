package object;

import entity.Action;
import entity.Player;

public class Book extends GameObject {

	private final Action[] _actions = new Action[] {
		Action.Pickup,
		Action.Examine
	};

	private final String _text;

	public Book(String title, String text) {

		this.name = title;
		this._text = text;
		this.description = "A worn leather book titled \"" + title + "\".";
		// Placeholder sprite until a book image exists.
		this.image = loadImage("/objects/book.png");
	}

	@Override
	public Action[] getActions() {
		return this._actions;
	}

	@Override
	public boolean use(Player player) {

		player.say(this._text, 5000);
		return true;
	}
}
