package app.v1.task;

import app.v1.log.NewsLogger;

public class LogCleaner implements Runnable {

    private final NewsLogger logger;

    public LogCleaner(NewsLogger logger) {
        this.logger = logger;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000); // 5초마다 로그 정리
                logger.cleanLogs();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
