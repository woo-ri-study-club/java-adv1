package app.v4.task;

import app.v4.log.NewsLogger;

import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.Callable;

public class NewsCrawler implements Callable<String> {

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
    public String call() {
        String news = fetchNews();
        String log = "[" + LocalTime.now() + "] [" + category + "] " + news;

        logger.log(log);

        return log;
    }

    private String fetchNews() {
        return sampleNews[random.nextInt(sampleNews.length)];
    }
}
