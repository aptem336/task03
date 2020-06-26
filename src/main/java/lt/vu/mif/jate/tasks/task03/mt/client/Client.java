package lt.vu.mif.jate.tasks.task03.mt.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

/**
 * Client object: implements Java API for server-side arithmetic operations protocol.
 * <p>
 * Request (client -> server) message format:
 * <p>
 * - message length (int, 4 bytes): length of the remaining message
 * - request code (int, 4 bytes): message (operation) type, @see lt.vu.mif.jate.tasks.task03.mt.common.Request
 * - correlation id (long, 8 bytes): id to correlate request-response pair
 * - delay (long, 8 bytes): request to delay the response in ms
 * - operand1 (long, 8 bytes, optional): first operand
 * - operand2 (long, 8 bytes, optional): second operand
 * <p>
 * Response (server -> client) message format:
 * <p>
 * - message length (int, 4 bytes): length of the remaining message
 * - response code (int, 4 bytes): message type, @see lt.vu.mif.jate.tasks.task03.mt.common.Response
 * - correlation id (long, 8 bytes): id to correlate request-response pair
 * - result (long, 8 bytes, optional): operation result (if code == Success)
 * - message (String, optional): error message (if code == Error)
 *
 * @author valdo
 */
public class Client implements AutoCloseable {


    private static final Logger LOG = Logger.getLogger(Client.class.getName());
    private static boolean isUsed = false;
    private final Socket socket;
    private BlockingQueue<Message> send;
    private ConcurrentMap<Long, Message> receive;

    public Client(InetSocketAddress addr) throws IOException {
        if (isUsed) {
            throw new IOException("Connection is used");
        }
        isUsed = true;

        socket = new Socket(addr.getAddress(), addr.getPort());
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        send = new LinkedBlockingQueue<>();
        receive = new ConcurrentHashMap<>();

        new Sender(outputStream, send, receive).start();
        new Receive(inputStream, receive).start();
        new Pinger(send).start();
    }

    public static Logger getLOG() {
        return LOG;
    }

    public final void ping() throws ServerFunctionException {
        this.exec(new Message(ServerFunction.Ping, 0L));
    }

    public Long addition(Long op1, Long op2) throws ServerFunctionException {
        return addition(op1, op2, 0L);
    }

    public Long addition(Long op1, Long op2, Long delay) throws ServerFunctionException {
        return exec(new MessageWithArgs(ServerFunction.Addition, op1, op2, delay));
    }

    public Long substraction(Long op1, Long op2) throws ServerFunctionException {
        return substraction(op1, op2, 0L);
    }

    public Long substraction(Long op1, Long op2, Long delay) throws ServerFunctionException {
        return exec(new MessageWithArgs(ServerFunction.Substraction, op1, op2, delay));
    }

    public Long multiplication(Long op1, Long op2) throws ServerFunctionException {
        return multiplication(op1, op2, 0L);
    }

    public Long multiplication(Long op1, Long op2, Long delay) throws ServerFunctionException {
        return exec(new MessageWithArgs(ServerFunction.Multiplication, op1, op2, delay));
    }

    public Long division(Long op1, Long op2) throws ServerFunctionException {
        return division(op1, op2, 0L);
    }

    public Long division(Long op1, Long op2, Long delay) throws ServerFunctionException {
        return exec(new MessageWithArgs(ServerFunction.Division, op1, op2, delay));
    }

    public Long function01(Long op1, Long op2) throws ServerFunctionException {
        return function01(op1, op2, 0L);
    }

    public Long function01(Long op1, Long op2, Long delay) throws ServerFunctionException {
        return exec(new MessageWithArgs(ServerFunction.Function01, op1, op2, delay));
    }

    public Long function02(Long op1, Long op2) throws ServerFunctionException {
        return function02(op1, op2, 0L);
    }

    public Long function02(Long op1, Long op2, Long delay) throws ServerFunctionException {
        return exec(new MessageWithArgs(ServerFunction.Function02, op1, op2, delay));
    }

    private Long exec(Message m) throws ServerFunctionException {
        send.add(m);
        if (!m.getCode().equals(ServerFunction.Ping)) {
            while (!m.isReceived()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
            if (m.getResult() != null) {
                return m.getResult();
            }
            throw new ServerFunctionException(m.getError());
        }
        return 0L;
    }

    @Override
    public void close() throws Exception {
        socket.close();
        isUsed = false;
    }
}