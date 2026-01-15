package slotmachine;

import java.util.HashMap;
import java.util.Map;

public class SlotMachine {
    private final Reel[] reels;
    private double balance;
    private final double targetRTP = 0.65;
    private final Map<Symbol, Double> payouts;

    public SlotMachine(double initialBalance, int numReels, Reel.ReelListener listener) {
        this.balance = initialBalance;
        this.reels = new Reel[numReels];
        for (int i = 0; i < numReels; i++) {
            reels[i] = new Reel(listener);
        }
        this.payouts = calculatePayouts();
    }

    private Map<Symbol, Double> calculatePayouts() {
        Map<Symbol, Double> calcPayouts = new HashMap<>();
        int totalWeight = 0;
        for (Symbol s : Symbol.values()) {
            totalWeight += s.getWeight();
        }

        double totalExpectedBaseReturn = 0;
        for (Symbol s : Symbol.values()) {
            double probSingle = (double) s.getWeight() / totalWeight;
            double probTriple = Math.pow(probSingle, reels.length);
            totalExpectedBaseReturn += probTriple * s.getBaseMultiplier();
        }

        double scaleFactor = targetRTP / totalExpectedBaseReturn;

        for (Symbol s : Symbol.values()) {
            // Round to nearest 0.5 for cleaner prizes
            double payout = Math.round(s.getBaseMultiplier() * scaleFactor * 2) / 2.0;
            calcPayouts.put(s, payout);
        }

        return calcPayouts;
    }

    public void spin(double bet) {
        if (bet > balance)
            return;
        balance -= bet;

        for (int i = 0; i < reels.length; i++) {
            // Each reel stops at a different time
            reels[i].spin(1000 + i * 500);
        }
    }

    public double checkWin(double bet) {
        Symbol first = reels[0].getCurrentSymbol();
        boolean allSame = true;
        for (int i = 1; i < reels.length; i++) {
            if (reels[i].getCurrentSymbol() != first) {
                allSame = false;
                break;
            }
        }

        if (allSame) {
            double prize = bet * payouts.get(first);
            balance += prize;
            return prize;
        }
        return 0;
    }

    public double getBalance() {
        return balance;
    }

    public Map<Symbol, Double> getPayouts() {
        return payouts;
    }

    public Reel[] getReels() {
        return reels;
    }
}
