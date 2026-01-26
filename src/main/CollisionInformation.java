package main;

import java.util.List;

import entity.Npc;
import object.GameObject;

public class CollisionInformation {

	public final List<Npc> npcs;
	public final List<GameObject> gameObjects;
	
	public CollisionInformation(List<Npc> npcs, List<GameObject> gameObject) {
		this.npcs = npcs;
		this.gameObjects = gameObject;
	}	
}
