package app.v4.log;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewsLogger implements Logger {

    private final String logFilePath;

    private final List<String> logs = new ArrayList<>();

    private static final int MAX_LOG_SIZE = 10;

    public NewsLogger(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @Override
    public synchronized void log(String message) {
        logs.add(message);
        System.out.println(message);
        writeToFile();
    }

    @Override
    public void cleanLogs() {
        if (logs.size() > MAX_LOG_SIZE) {
            logs.removeFirst();
            rewriteFile();
            log("[LOG CLEANER] 로그 정리 완료 (10개 초과 삭제)");
        }
    }

    private void writeToFile() {
        try (FileWriter writer = new FileWriter(logFilePath, true)) {
            writer.write(logs.getLast() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void rewriteFile() {
        synchronized (this) {
            try (FileWriter writer = new FileWriter(logFilePath)) {
                for (String log : logs) {
                    writer.write(log + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
