package app.v4.task;

import app.v4.log.NewsLogger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LogCleaner {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final NewsLogger logger;

    public LogCleaner(NewsLogger logger) {
        this.logger = logger;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(logger::cleanLogs, 5, 5, TimeUnit.SECONDS);
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
