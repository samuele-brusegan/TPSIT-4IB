package slotmachine;

import java.util.Scanner;
import java.util.Map;

public class SlotMachineTUI implements Reel.ReelListener {
    private final SlotMachine slotMachine;
    private final Scanner scanner;
    private double currentBet;
    private int finishedReels;
    private final Object lock = new Object();

    public SlotMachineTUI() {
        this.slotMachine = new SlotMachine(100.0, 3, this);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("========================================");
        System.out.println("       🎰 ROYAL SLOTS TERMINAL 🎰       ");
        System.out.println("========================================");
        displayPayouts();

        while (slotMachine.getBalance() > 0) {
            System.out.printf("\nSaldo Attuale: $%.2f\n", slotMachine.getBalance());
            System.out.print("Inserisci puntata (o 0 per uscire): ");

            try {
                String input = scanner.nextLine();
                currentBet = Double.parseDouble(input);
            } catch (Exception e) {
                System.out.println("Per favore, inserisci un numero valido.");
                continue;
            }

            if (currentBet == 0)
                break;
            if (currentBet < 0 || currentBet > slotMachine.getBalance()) {
                System.out.println("Puntata non valida!");
                continue;
            }

            spin();
        }

        System.out
                .println("\nGrazie di aver giocato! Saldo finale: $" + String.format("%.2f", slotMachine.getBalance()));
    }

    private void displayPayouts() {
        System.out.println("\nTABELLA PREMI (x Puntata):");
        for (Map.Entry<Symbol, Double> entry : slotMachine.getPayouts().entrySet()) {
            System.out.printf("%s x3 = %.1fx\n", entry.getKey().getEmoji(), entry.getValue());
        }
        System.out.println("----------------------------------------");
    }

    private void spin() {
        finishedReels = 0;
        slotMachine.spin(currentBet);

        // Aspetta che i thread finiscano
        synchronized (lock) {
            while (finishedReels < 3) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        System.out.println(); // Nuova riga dopo i risultati dei rulli
        double win = slotMachine.checkWin(currentBet);
        if (win > 0) {
            System.out.printf("✨ VINCITA! Hai vinto $%.2f ✨\n", win);
        } else {
            System.out.println("❌ Ritenta, sarai più fortunato!");
        }
    }

    @Override
    public void onSymbolChanged(Reel reel, Symbol symbol) {
        // Nella TUI non aggiorniamo dinamicamente per evitare confusione nel terminale
    }

    @Override
    public void onSpinFinished(Reel reel, Symbol finalSymbol) {
        synchronized (lock) {
            finishedReels++;
            // Mostriamo il risultato del rullo man mano che si ferma
            System.out.print("[" + finalSymbol.getEmoji() + "] ");
            if (finishedReels == 3) {
                lock.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        new SlotMachineTUI().start();
    }
}
