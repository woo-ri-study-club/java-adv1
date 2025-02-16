package app.v2.task;

import app.v2.log.NewsLogger;

import java.time.LocalTime;
import java.util.Random;

public class NewsCrawler implements Runnable {

    private final String category;

    private final NewsLogger logger;

    private final Random random = new Random();

    private final String[] sampleNews;

    public NewsCrawler(String category, NewsLogger logger, String[] sampleNews) {
        this.category = category;
        this.logger = logger;
        this.sampleNews = sampleNews;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String news = fetchNews();
                String log = "[" + LocalTime.now() + "] [" + category + "] " + news;
                logger.log(log);

                Thread.sleep(3000);

                Thread.yield();
            } catch (InterruptedException e) {
                System.out.println(category + " 스레드가 인터럽트 되어 종료됩니다.");
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(category + " 스레드 종료");
    }

    private String fetchNews() {
        return sampleNews[random.nextInt(sampleNews.length)];
    }
}
