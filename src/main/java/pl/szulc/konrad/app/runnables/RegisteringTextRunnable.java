package pl.szulc.konrad.app.runnables;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

/**
 * Simple runnable, needed to start a new thread and show "Registering an account..." text while registering new user.
 */
public class RegisteringTextRunnable implements Runnable {
    private boolean check ;
    private TextIO textIO = TextIoFactory.getTextIO();
    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public void run() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();

        textTerminal.print("Registering an account");
        while (check!=true){
            textTerminal.print(".");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Thread.currentThread().interrupt();
    }

}
