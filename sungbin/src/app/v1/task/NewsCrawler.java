package app.v1.task;

import app.v1.log.NewsLogger;

import java.time.LocalTime;
import java.util.Random;

public class NewsCrawler implements Runnable {

    private final String category;

    private final NewsLogger logger;

    private final Random random = new Random();

    private final String[] sampleNews;

    private boolean running = true;

    public NewsCrawler(String category, NewsLogger logger, String[] sampleNews) {
        this.category = category;
        this.logger = logger;
        this.sampleNews = sampleNews;
    }

    @Override
    public void run() {
        while (running) {
            try {
                String news = fetchNews();
                String log = "[" + LocalTime.now() + "] [" + category + "] " + news;
                logger.log(log);

                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(category + " 스레드 종료");
    }

    public void stop() {
        running = false;
    }

    private String fetchNews() {
        return sampleNews[random.nextInt(sampleNews.length)];
    }
}
