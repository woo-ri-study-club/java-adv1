package app.v2;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String currentDir = System.getProperty("user.dir");
        String relativePath = "/hyemin/app/v2/news_log.txt";
        String filePath = currentDir + relativePath;

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

        sportThread.interrupt();
        itThread.interrupt();
        economyThread.interrupt();
        cleanerThread.interrupt();

        cleanerThread.join();
        sportThread.join();
        itThread.join();
        economyThread.join();
    }
}
