package nahye.app.v1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
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
                cleanMemoryQueue();
                cleanLogFile();
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void cleanMemoryQueue() {
        if (queue.size() > 10) {
            int itemsToRemove = queue.size() - 10;
            for (int i = 0; i < itemsToRemove; i++) {
                queue.poll();
            }
        }
    }

    private void cleanLogFile() {

        try {
            List<String> allLines = Files.readAllLines(Paths.get("news_log.txt"));

            if (allLines.size() > 10) {
                List<String> newLines = new ArrayList<>();
                for (int i = allLines.size() - 10; i < allLines.size(); i++) {
                    newLines.add(allLines.get(i));
                }
                Files.write(Paths.get("news_log.txt"), newLines, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
