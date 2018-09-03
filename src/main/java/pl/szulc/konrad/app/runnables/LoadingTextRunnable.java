package pl.szulc.konrad.app.runnables;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

/**
 * Simple runnable, needed to start a new thread and show "Sending..." text while sending a message to database.
 */
public class LoadingTextRunnable implements Runnable {

    private boolean check ;
    private TextIO textIO = TextIoFactory.getTextIO();

    public LoadingTextRunnable() {
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }

    @Override
    public void run() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();

        textTerminal.print("Sending");
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
