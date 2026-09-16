package main;

import java.util.ArrayList;
import java.util.List;

import entity.Action;
import entity.Npc;
import object.GameObject;

/** Keeps target identity and the two menu levels together. */
public class ActionMenu {

	public record Target(String name, Action[] actions, GameObject object, Npc npc) {}
	private List<Target> targets = List.of();
	private int targetIndex = -1;
	private int selectedIndex;
	private boolean open;

	public void open(CollisionInformation collisions) {
		var entries = new ArrayList<Target>();
		for (var object : collisions.gameObjects) {
			entries.add(new Target(object.name, object.getActions().clone(), object, null));
		}
		for (var npc : collisions.npcs) {
			entries.add(new Target(npc.getName(), npc.getActions().clone(), null, npc));
		}
		targets = List.copyOf(entries);
		targetIndex = targets.size() == 1 ? 0 : -1;
		selectedIndex = 0;
		open = !targets.isEmpty();
	}

	public boolean isOpen() { return open; }
	public void close() { open = false; }
	public int selectedIndex() { return selectedIndex; }
	public Target selectedTarget() { return targetIndex < 0 ? null : targets.get(targetIndex); }
	public boolean freezes(Npc npc) {
		return open && targets.stream().anyMatch(target -> target.npc() == npc);
	}

	public List<String> labels() {
		
		if (targetIndex < 0) 
			return targets.stream().map(Target::name).toList();
		
		var labels = new ArrayList<String>();
		for (var action : selectedTarget().actions()) 
			labels.add(action.toString());
		
		if (targets.size() > 1) 
			labels.add("Back");

		return labels;
	}

	public void move(int direction) {
		
		int count = labels().size();
		if (open && count > 0) 
			selectedIndex = Math.floorMod(selectedIndex + direction, count);
	}

	/** Returns an action only when an action row is confirmed. */
	public Action select() {

		if (!open) 
			return null;

		if (targetIndex < 0) {
			targetIndex = selectedIndex;
			selectedIndex = 0;
			return null;
		}

		var actions = selectedTarget().actions();

		if (selectedIndex < actions.length) 
			return actions[selectedIndex];
		
		if (targets.size() > 1) {
			selectedIndex = targetIndex;
			targetIndex = -1;
		}
		return null;
	}
}
