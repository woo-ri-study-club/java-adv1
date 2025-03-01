package app.v4;

import java.util.Deque;

import static app.v4.ConsoleLogger.log;

public class NewsLogCleaner implements Runnable {
    private final NewsLogger newsLogger;

    NewsLogCleaner(NewsLogger newsLogger) {
        this.newsLogger = newsLogger;
    }

    @Override
    public void run() {
        Deque<NewsLog> logs = newsLogger.getLogs();
        while (logs.size() >= 10) {
            log("로그 삭제 시작: " + newsLogger);
            logs.removeFirst();
            newsLogger.rewrite();
            log("로그 삭제 종료: " + newsLogger);
        }
    }
}
