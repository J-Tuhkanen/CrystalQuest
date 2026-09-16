import java.util.List;

import entity.Action;
import entity.Npc;
import main.ActionMenu;
import main.CollisionInformation;
import object.GameObject;

/** Run with assertions enabled; no test framework required. */
public class ActionMenuTest {

    private static GameObject object(String displayName, Action... actions) {
        return new GameObject() {
            { this.name = displayName; }
            @Override public Action[] getActions() { return actions; }
        };
    }

    private static Npc npc() {
        return new Npc(null, "Old man", new Action[] { Action.Talk, Action.Examine }, false, "/npc/oldman") {
            @Override public void setupCollision() {}
            @Override public void updateAction() {}
            @Override public void update() {}
        };
    }

    public static void main(String[] args) {
        var menu = new ActionMenu();
        var key = object("Key", Action.Pickup, Action.Examine);
        menu.open(new CollisionInformation(List.of(), List.of(key)));
        assert menu.labels().equals(List.of("Pickup", "Examine"));
        menu.move(-1);
        assert menu.select() == Action.Examine;
        menu.move(1);
        assert menu.select() == Action.Pickup;
        assert menu.selectedTarget().object() == key;

        var otherKey = object("Key", Action.Use);
        var npc = npc();
        var otherNpc = npc();
        menu.open(new CollisionInformation(List.of(npc), List.of(key, otherKey)));
        assert menu.labels().equals(List.of("Key", "Key", "Old man"));
        assert menu.freezes(npc) && !menu.freezes(otherNpc);
        menu.move(1);
        assert menu.select() == null;
        assert menu.selectedTarget().object() == otherKey;
        assert menu.labels().equals(List.of("Use", "Back"));
        menu.move(1);
        assert menu.select() == null;
        assert menu.selectedIndex() == 1 && menu.selectedTarget() == null;
        menu.move(1);
        menu.select();
        assert menu.select() == Action.Talk;
        assert menu.selectedTarget().npc() == npc;
        menu.close();
        assert !menu.freezes(npc) && menu.select() == null;

        menu.open(new CollisionInformation(List.of(npc), List.of()));
        assert menu.labels().equals(List.of("Talk", "Examine"));
        assert menu.select() == Action.Talk;
        menu.open(new CollisionInformation(List.of(), List.of(object("Empty"))));
        menu.move(1);
        assert menu.labels().isEmpty() && menu.select() == null;
        menu.open(new CollisionInformation(List.of(), List.of()));
        assert !menu.isOpen();
        System.out.println("ActionMenu checks passed");
    }
}
