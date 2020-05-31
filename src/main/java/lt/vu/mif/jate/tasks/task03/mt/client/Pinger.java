package lt.vu.mif.jate.tasks.task03.mt.client;

import java.io.IOException;

public class Pinger extends Thread {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Client.getInstance().ping();
            } catch (ServerFunctionException | IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                interrupt();
                break;
            }
        }
    }
}
