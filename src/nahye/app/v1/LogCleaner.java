package nahye.app.v1;

import java.util.concurrent.BlockingQueue;

public class LogCleaner implements Runnable {

    private final BlockingQueue<String> queue;

    LogCleaner(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        try {
            while (true) {
                if (queue.size() >= 10) {
                    int itemsToRemove = queue.size() - 10;
                    for (int i = 0; i < itemsToRemove; i++) {
                        queue.poll();
                    }
                }
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
