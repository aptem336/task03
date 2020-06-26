package lt.vu.mif.jate.tasks.task03.mt.client;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

public class Sender extends Thread {

    private final OutputStream outputStream;
    private final BlockingQueue<Message> send;
    private final ConcurrentMap<Long, Message> receive;
    private boolean alive;

    public Sender(OutputStream outputStream, BlockingQueue<Message> send, ConcurrentMap<Long, Message> receive) {
        this.outputStream = outputStream;
        this.send = send;
        this.receive = receive;
        alive = true;
    }

    @Override
    public void run() {
        while (alive) {
            Message m;
            try {
                m = send.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                alive = false;
                break;
            }
            Client.getLOG().info(String.format("%s: about to send: %s, correlation = %d", this, m.getCode().name(), m.getCorrelation()));

            ByteBuffer bytes = m.toBytes();
            ByteBuffer size = Message.toBytes(bytes.capacity());
            try {
                outputStream.write(size.array());
                outputStream.write(bytes.array());
            } catch (IOException e) {
                e.printStackTrace();
                alive = false;
                break;
            }
            receive.put(m.getCorrelation(), m);
        }
    }
}
