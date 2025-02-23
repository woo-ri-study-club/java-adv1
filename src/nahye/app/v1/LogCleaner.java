package nahye.app.v1;

import java.io.FileWriter;
import java.io.IOException;
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
                System.out.println("로그 정리 중...");
                cleanQueue();
                cleanLogFile();
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void cleanQueue() {
        while (queue.size() > 10) {
            String removed = queue.poll();
            if (removed != null) {
                System.out.println("삭제된 로그: " + removed);
            }
        }
    }

    private void cleanLogFile() {
        String logPath = "news_log.txt";

        try (FileWriter writer = new FileWriter(logPath, false)) {
            for (String log : queue) {
                writer.write(log + "\n");
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
