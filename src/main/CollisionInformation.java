package main;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import entity.Action;
import entity.Npc;
import object.GameObject;

public class CollisionInformation {

	public final List<Npc> npcs;
	public final List<GameObject> gameObjects;
	
	public CollisionInformation(List<Npc> npcs, List<GameObject> gameObject) {
		this.npcs = npcs;
		this.gameObjects = gameObject;
	}
	
	public Dictionary<String, Action[]> getAsActions(){
	
		Dictionary<String, Action[]> dict = new Hashtable<String, Action[]>();
		
		for (GameObject go : this.gameObjects)
			dict.put(go.name, go.getActions());
		
		for(Npc npc : this.npcs)
			dict.put(npc.getName(), npc.getActions());
		
		return dict;
	}
}
