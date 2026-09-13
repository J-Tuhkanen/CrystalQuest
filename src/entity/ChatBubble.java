package entity;

public class ChatBubble {

	public final String message;
	private final long expiresAtMillis;

	public ChatBubble(String message, int durationMs) {
		this.message = message;
		this.expiresAtMillis = System.currentTimeMillis() + durationMs;
	}

	public boolean isExpired() {
		return System.currentTimeMillis() >= this.expiresAtMillis;
	}
}
