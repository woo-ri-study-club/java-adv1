package nahye.app.v3;

import java.io.*;
import java.util.concurrent.BlockingQueue;

public class LogCleaner implements Runnable {

    private final BlockingQueue<String> queue;

    LogCleaner(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.println("로그 정리 중...");
                cleanQueue();
                cleanLogFile();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("LogCleaner 종료합니다.");
                break;
            }
        }
    }

    private synchronized void cleanQueue() {
        while (queue.size() > 10) {
            String removed = queue.poll();
            if (removed != null) {
                System.out.println("[♻️삭제된 로그]: " + removed);
            }
        }
    }

    private synchronized void cleanLogFile() {
        String logPath = "news_log.txt";

        try (FileWriter writer = new FileWriter(logPath)) {
            for (String log : queue) {
                writer.write(log + "\n");
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}