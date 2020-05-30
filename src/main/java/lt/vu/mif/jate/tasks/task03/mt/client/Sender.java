package lt.vu.mif.jate.tasks.task03.mt.client;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Sender extends Thread {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Message m;
            try {
                m = Client.getSend().take();
            } catch (InterruptedException e) {
                interrupt();
                break;
            }
            Client.getLOG().info(String.format("%s: about to send: %s, correlation = %d", this, m.getCode().name(), m.getCorrelation()));

            ByteBuffer bytes = m.toBytes();
            ByteBuffer size = Message.toBytes(bytes.capacity());
            try {
                Client.getOut().write(size.array());
                Client.getOut().write(bytes.array());
            } catch (IOException e) {
                interrupt();
                break;
            }
            Client.getReceive().put(m.getCorrelation(), m);
        }
    }
}
