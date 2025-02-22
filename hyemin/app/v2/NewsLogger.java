package app.v2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import static app.v1.ConsoleLogger.log;

public class NewsLogger {
    private Deque<NewsLog> logs = new ArrayDeque<>();
    private final String filePath;

    public NewsLogger(String filePath) {
        this.filePath = filePath;
    }

    public void add(NewsLog log) {
        logs.addLast(log);
    }

    public Deque<NewsLog> getLogs() {
        return logs;
    }

    public void write(NewsLog log) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("[" + log.getCategory() + "]\n");
            writer.write(log.getContent() + "\n");
            writer.write("==============================\n");
            writer.newLine();
            Thread.sleep(10);
            log("로그 추가 성공: " + this);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeAll() {
        for (NewsLog log : logs) {
            write(log);
        }
    }

    public void clear() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("");
            Thread.sleep(10);
            log("로그 초기화 성공: " + this);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "NewsLogger{" +
                "logs=" + logs +
                '}';
    }
}
