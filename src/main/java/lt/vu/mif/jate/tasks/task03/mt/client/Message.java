package lt.vu.mif.jate.tasks.task03.mt.client;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

public class Message {

    private static final AtomicLong CORRELATION_SEQ = new AtomicLong();
    
    private final ServerFunction code;
    private final Long correlation;
    private final Long delay;
    private Long result;
    private String error;

    public Message(ServerFunction code, Long delay) {
        this.code = code;
        this.delay = delay;
        this.correlation = CORRELATION_SEQ.incrementAndGet();
    }
    
    public ByteBuffer toBytes() {
        ByteBuffer buf = ByteBuffer.allocate(4 + 8 + 8);
        buf.putInt(code.getCode());
        buf.putLong(correlation);
        buf.putLong(delay);
        buf.rewind();
        return buf;
    }
    
    public static ByteBuffer toBytes(int i) {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.putInt(i);
        b.rewind();
        return b;
    }

    public ServerFunction getCode() {
        return code;
    }

    public Long getCorrelation() {
        return correlation;
    }

    public Long getDelay() {
        return delay;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isReceived() {
        return result != null || error != null;
    }
}
