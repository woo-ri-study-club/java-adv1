package app.v1;

import java.util.Deque;

import static app.v1.ConsoleLogger.log;

public class NewsLogCleaner implements Runnable{
    private NewsLogger newsLogger;
    private boolean isCleaning = true;

    NewsLogCleaner(NewsLogger newsLogger) {
        this.newsLogger = newsLogger;
    }

    @Override
    public void run() {
        while (isCleaning) {
            Deque<NewsLog> logs = newsLogger.getLogs();
            while (logs.size() >= 10) {
                log("로그 삭제 시작: " + newsLogger);
                logs.removeFirst();
                newsLogger.clear();
                newsLogger.writeAll();
                log("로그 삭제 종료: " + newsLogger);
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stop(){
        this.isCleaning = false;
    }
}
