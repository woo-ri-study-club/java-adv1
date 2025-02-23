package app.v1;

import java.util.Map;

import static app.v1.ConsoleLogger.log;

public class Crawler implements Runnable {
    private final String category;
    private boolean isRunning = true;
    private NewsLogger newsLogger;

    Map<String, String> crawledData = Map.of(
            "sport","스포츠 크롤링 데이터",
            "it","IT 크롤링 데이터",
            "economy","경제 크롤링 데이터"
    );

    Crawler(String category, NewsLogger newsLogger) {
        this.category = category;
        this.newsLogger = newsLogger;
    }

    @Override
    public void run() {
        while (isRunning) {
            String crawlingData = crawling(category);
            NewsLog log = new NewsLog(category, crawlingData);
            newsLogger.add(log);
            newsLogger.write(log);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stop() {
        this.isRunning = false;
    }

    private String crawling(String category) {
        String data = crawledData.get(category);
        log("뉴스 크롤링 : " + data);
        return data;
    }
}
