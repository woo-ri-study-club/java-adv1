package nahye.app.v2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;


public class NewsThread implements Runnable {
    private final String category;
    private final BlockingQueue<String> queue;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public NewsThread(String category, BlockingQueue<String> queue) {
        this.category = category;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String message = String.format("[%s] [%s] %s: Fetching news...",
                        LocalDateTime.now().format(formatter),
                        Thread.currentThread().getName(),
                        category);

                System.out.println(message);
                queue.put(message);

                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName()+": 쓰레드를 종료합니다.");
                break;
            }
        }
    }
}
