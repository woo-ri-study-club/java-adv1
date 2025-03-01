package app.v4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.ReentrantLock;

import static app.v4.ConsoleLogger.log;

public class NewsLogger {
    private final Deque<NewsLog> logs = new ArrayDeque<>();
    private final String filePath;
    private final ReentrantLock lock = new ReentrantLock();

    public NewsLogger(String filePath) {
        this.filePath = filePath;
    }

    public void add(NewsLog log) {
        logs.addLast(log);
    }

    public Deque<NewsLog> getLogs() {
        return logs;
    }

    public synchronized void write(NewsLog log) {
        lock.lock();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("[" + log.getCategory() + "]\n");
            writer.write(log.getContent() + "\n");
            writer.write("==============================\n");
            writer.newLine();
            log("로그 추가 성공: " + this);
        } catch (IOException e) {
            log("로그 기록 실패: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public synchronized void rewrite() {
        lock.lock();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (NewsLog log : logs) {
                writer.write("[" + log.getCategory() + "]\n");
                writer.write(log.getContent() + "\n");
                writer.write("==============================\n");
                writer.newLine();
            }
            log("로그 재작성 성공: " + this);
        } catch (IOException e) {
            log("로그 재작성 실패: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "NewsLogger{" + "logs=" + logs + '}';
    }
}
