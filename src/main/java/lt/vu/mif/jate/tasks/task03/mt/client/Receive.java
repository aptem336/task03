package lt.vu.mif.jate.tasks.task03.mt.client;

import lt.vu.mif.jate.tasks.task03.mt.common.Response;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentMap;

public class Receive extends Thread {
    private final InputStream inputStream;
    private final ConcurrentMap<Long, Message> receive;
    private boolean alive;

    public Receive(InputStream inputStream, ConcurrentMap<Long, Message> receive) {
        this.inputStream = inputStream;
        this.receive = receive;
        alive = true;
    }

    @Override
    public void run() {
        while (alive) {
            if (!receive.isEmpty()) {
                ByteBuffer header = ByteBuffer.allocate(4);
                try {
                    inputStream.read(header.array());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    alive = false;
                    break;
                }
                header.rewind();

                ByteBuffer body;
                try {
                    body = ByteBuffer.allocate(header.getInt());
                    inputStream.read(body.array());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    alive = false;
                    break;
                }
                body.rewind();

                Response resp = Response.fromInt(body.getInt());
                long corr = body.getLong();
                Client.getLOG().info(String.format("%s: received body: %s, correlation = %d",
                        this, resp.name(), corr));

                Message m = receive.get(corr);
                switch (resp) {
                    case Success:
                        m.setResult(body.getLong());
                    case Error:
                        byte[] mb = new byte[body.remaining()];
                        body.get(mb);
                        m.setError(new String(mb));
                }
                receive.remove(corr);
            }
        }
    }
}
