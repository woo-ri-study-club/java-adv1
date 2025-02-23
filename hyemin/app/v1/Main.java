package app.v1;

public class Main {
    public static void main(String[] args) {
        String filePath = "/Users/hmkim199/Develop/study/java-adv1/hyemin/app/v1/news_log.txt";

        NewsLogger newsLogs = new NewsLogger(filePath);
        NewsLogCleaner logCleaner = new NewsLogCleaner(newsLogs);

        Crawler sportCrawler = new Crawler("sport", newsLogs);
        Crawler itCrawler = new Crawler("it", newsLogs);
        Crawler economyCrawler = new Crawler("economy", newsLogs);

        Thread sportThread = new Thread(sportCrawler, "SPORT");
        Thread itThread = new Thread(itCrawler, "IT");
        Thread economyThread = new Thread(economyCrawler, "ECONOMY");
        Thread cleanerThread = new Thread(logCleaner, "CLEANER");

        cleanerThread.start();
        sportThread.start();
        itThread.start();
        economyThread.start();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        logCleaner.stop();
        sportCrawler.stop();
        itCrawler.stop();
        economyCrawler.stop();
    }
}
