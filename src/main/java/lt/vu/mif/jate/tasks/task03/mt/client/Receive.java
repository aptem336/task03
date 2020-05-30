package lt.vu.mif.jate.tasks.task03.mt.client;

import lt.vu.mif.jate.tasks.task03.mt.common.Response;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Receive extends Thread {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (!Client.getReceive().isEmpty()) {
                ByteBuffer header = ByteBuffer.allocate(4);
                try {
                    Client.getIn().read(header.array());
                } catch (IOException e) {
                    interrupt();
                    e.printStackTrace();
                    break;
                }
                header.rewind();

                ByteBuffer body = ByteBuffer.allocate(header.getInt());
                try {
                    Client.getIn().read(body.array());
                } catch (IOException e) {
                    interrupt();
                    break;
                }
                body.rewind();

                Response resp = Response.fromInt(body.getInt());
                long corr = body.getLong();
                Client.getLOG().info(String.format("%s: received body: %s, correlation = %d",
                        this, resp.name(), corr));
                Message m = Client.getReceive().get(corr);
                switch (resp) {
                    case Success:
                        m.setResult(body.getLong());
                    case Error:
                        byte[] mb = new byte[body.remaining()];
                        body.get(mb);
                        m.setError(new String(mb));
                }
                Client.getReceive().remove(corr);
            }
        }
    }
}
