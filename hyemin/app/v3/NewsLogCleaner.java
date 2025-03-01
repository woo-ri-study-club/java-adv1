package app.v3;

import java.util.Deque;

import static app.v3.ConsoleLogger.log;

public class NewsLogCleaner implements Runnable{
    private NewsLogger newsLogger;

    NewsLogCleaner(NewsLogger newsLogger) {
        this.newsLogger = newsLogger;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Deque<NewsLog> logs = newsLogger.getLogs();
                while (logs.size() >= 10) {
                    log("로그 삭제 시작: " + newsLogger);
                    logs.removeFirst();
                    newsLogger.rewrite();
                    log("로그 삭제 종료: " + newsLogger);
                }

                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
