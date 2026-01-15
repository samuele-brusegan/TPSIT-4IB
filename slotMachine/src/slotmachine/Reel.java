package slotmachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reel implements Runnable {
    private final List<Symbol> symbols;
    private Symbol currentSymbol;
    private boolean spinning;
    private final Random random;
    private final ReelListener listener;
    private int spinDuration;

    public interface ReelListener {
        void onSymbolChanged(Reel reel, Symbol symbol);

        void onSpinFinished(Reel reel, Symbol finalSymbol);
    }

    public Reel(ReelListener listener) {
        this.listener = listener;
        this.random = new Random();
        this.symbols = new ArrayList<>();
        this.spinning = false;

        // Populate reel based on weights
        for (Symbol s : Symbol.values()) {
            for (int i = 0; i < s.getWeight(); i++) {
                symbols.add(s);
            }
        }
        // Initialize with a random symbol
        currentSymbol = symbols.get(random.nextInt(symbols.size()));
    }

    public void spin(int duration) {
        this.spinDuration = duration;
        this.spinning = true;
        new Thread(this).start();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        int sleepTime = 50;

        while (System.currentTimeMillis() - startTime < spinDuration || sleepTime < 400) {
            currentSymbol = symbols.get(random.nextInt(symbols.size()));
            if (listener != null) {
                listener.onSymbolChanged(this, currentSymbol);
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            // Gradually slow down after duration.
            if (System.currentTimeMillis() - startTime > spinDuration) {
                sleepTime += 40;
            }
        }

        spinning = false;
        if (listener != null) {
            listener.onSpinFinished(this, currentSymbol);
        }
    }

    public Symbol getCurrentSymbol() {
        return currentSymbol;
    }

    public boolean isSpinning() {
        return spinning;
    }
}
