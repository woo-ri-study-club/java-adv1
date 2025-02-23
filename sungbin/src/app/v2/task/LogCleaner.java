package app.v2.task;

import app.v2.log.NewsLogger;

public class LogCleaner implements Runnable {

    private final NewsLogger logger;

    public LogCleaner(NewsLogger logger) {
        this.logger = logger;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(5000); // 5초마다 로그 정리
                logger.cleanLogs();
            } catch (InterruptedException e) {
                System.out.println("[LOG CLEANER] 스레드가 인터럽트 되어 종료됩니다.");
                Thread.currentThread().interrupt();
            }
        }
    }
}
