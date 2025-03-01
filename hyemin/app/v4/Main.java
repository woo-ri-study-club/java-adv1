package app.v4;

import java.util.List;
import java.util.concurrent.*;

import static app.v4.ConsoleLogger.log;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String currentDir = System.getProperty("user.dir");
        String relativePath = "/hyemin/app/v4/news_log.txt";
        String filePath = currentDir + relativePath;

        NewsLogger newsLogs = new NewsLogger(filePath);
        ScheduledExecutorService cleanerService = Executors.newScheduledThreadPool(1);
        cleanerService.scheduleAtFixedRate(new NewsLogCleaner(newsLogs), 0, 5, TimeUnit.SECONDS);

        ExecutorService crawlerService = Executors.newFixedThreadPool(3);
        List<Crawler> crawlers = List.of(
                new Crawler("sport", newsLogs),
                new Crawler("it", newsLogs),
                new Crawler("economy", newsLogs)
        );

        try {
            List<Future<NewsLog>> futures = crawlerService.invokeAll(crawlers, 3, TimeUnit.SECONDS);
            for (Future<NewsLog> future : futures) {
                try {
                    NewsLog result = future.get();
                    log("크롤링 결과: " + result);
                } catch (ExecutionException e) {
                    log("크롤러 실행 중 오류 발생: " + e.getCause().getMessage());
                }
            }
        } finally {
            crawlerService.shutdown();
            try {
                if (!crawlerService.awaitTermination(15, TimeUnit.SECONDS)) {
                    List<Runnable> notExecutedTasks = crawlerService.shutdownNow();
                    log("크롤러가 강제로 종료되었습니다: " + notExecutedTasks);
                }
            } catch (InterruptedException e) {
                crawlerService.shutdownNow();
            }
        }

        cleanerService.shutdown();
    }
}
