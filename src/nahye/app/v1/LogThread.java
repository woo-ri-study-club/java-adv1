package nahye.app.v1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class LogThread implements Runnable {

    private final BlockingQueue<String> queue;

    public LogThread(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("news_log.txt", true))) {
            while (true) {
                String logMessage = queue.take();
                writer.println(logMessage);
                writer.flush();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
