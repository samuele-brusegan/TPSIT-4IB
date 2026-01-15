package slotmachine;

public enum Symbol {
    CHERRY("🍒", 12, 2.0),
    LEMON("🍋", 10, 5.0),
    ORANGE("🍊", 8, 10.0),
    BELL("🔔", 5, 25.0),
    DIAMOND("💎", 2, 100.0),
    SEVEN("7️⃣", 1, 500.0);

    private final String emoji;
    private final int weight;
    private final double baseMultiplier; // Will be adjusted for RTP

    Symbol(String emoji, int weight, double baseMultiplier) {
        this.emoji = emoji;
        this.weight = weight;
        this.baseMultiplier = baseMultiplier;
    }

    public String getEmoji() {
        return emoji;
    }

    public int getWeight() {
        return weight;
    }

    public double getBaseMultiplier() {
        return baseMultiplier;
    }
}
