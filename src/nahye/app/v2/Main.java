package nahye.app.v2;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    static final BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {

        NewsThread sportsThread = new NewsThread("Sports", logQueue);
        NewsThread itThread = new NewsThread("IT", logQueue);
        NewsThread economyThread = new NewsThread("Economy", logQueue);

        Thread thread1 = new Thread(sportsThread, "Sports");
        Thread thread2 = new Thread(itThread, "IT");
        Thread thread3 = new Thread(economyThread, "Economy");

        thread1.start();
        thread2.start();
        thread3.start();

        Thread logCleaner = new Thread(new LogCleaner(logQueue), "LogCleaner");
        logCleaner.start();

        Thread.sleep(15000);

        thread1.interrupt();
        thread2.interrupt();
        thread3.interrupt();

        thread1.join();
        thread2.join();
        thread3.join();

        logCleaner.interrupt();
        logCleaner.join();
    }
}





