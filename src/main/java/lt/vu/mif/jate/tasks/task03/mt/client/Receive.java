package lt.vu.mif.jate.tasks.task03.mt.client;

import lt.vu.mif.jate.tasks.task03.mt.common.Response;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Receive extends Thread {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (!Client.getReceive().isEmpty()) {
                boolean success = false;
                ByteBuffer header = ByteBuffer.allocate(4);
                do {
                    try {
                        Client.getIn().read(header.array());
                        success = true;
                    } catch (IOException ignored) {
                    }
                } while (!success);
                header.rewind();

                success = false;
                ByteBuffer body = null;
                do {
                    try {
                        body = ByteBuffer.allocate(header.getInt());
                        Client.getIn().read(body.array());
                        success = true;
                    } catch (IOException ignored) {
                    }
                } while (!success);
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
