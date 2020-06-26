package lt.vu.mif.jate.tasks.task03.mt.client;

import java.util.concurrent.BlockingQueue;

public class Pinger extends Thread {

    private final BlockingQueue<Message> send;
    private boolean alive;

    public Pinger(BlockingQueue<Message> send) {
        this.send = send;
        alive = true;
    }

    @Override
    public void run() {
        while (alive) {
            send.add(new Message(ServerFunction.Ping, 0L));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                alive = false;
                break;
            }
        }
    }
}
