package object;

import java.util.List;

// Outcome of combining two items: which items go away, which new items appear, and an optional message.
public record CombineResult(List<GameObject> removed, List<GameObject> added, String message) {}
