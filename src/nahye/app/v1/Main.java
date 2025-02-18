package nahye.app.v1;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    static final BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {

        Thread logThread = new Thread(new LogThread(logQueue), "Logger");
        logThread.setDaemon(true);
        logThread.start();

        Thread logCleaner = new Thread(new LogCleaner(logQueue), "LogCleaner");
        logCleaner.setDaemon(true);
        logCleaner.start();

        NewsThread sportsThread = new NewsThread("Sports", logQueue);
        NewsThread itThread = new NewsThread("IT", logQueue);
        NewsThread economyThread = new NewsThread("Economy", logQueue);

        Thread thread1 = new Thread(sportsThread, "Sports");
        Thread thread2 = new Thread(itThread, "IT");
        Thread thread3 = new Thread(economyThread, "Economy");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        sportsThread.stop();
        itThread.stop();
        economyThread.stop();
    }
}





