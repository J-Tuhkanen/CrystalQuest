package main;

import java.util.List;

import entity.Interactable;
import entity.Npc;
import object.GameObject;

public class CollisionInformation {

	public final List<Npc> _npcs;
	public final List<GameObject> _gameObjects;
	
	public CollisionInformation(List<Npc> npcs, List<GameObject> gameObject) {
		_npcs = npcs;
		_gameObjects = gameObject;
	}
	
	public List<Npc> getNpcsThatCanBeInterractedWith(){
				
		return _npcs.stream().filter(n -> n instanceof Interactable).toList();
	}
}
