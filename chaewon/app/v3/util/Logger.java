package chaewon.app.v3.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Deque;

public class Logger {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Path LOG_FILE_PATH = Path.of("log.txt");
    private static final int LOG_SIZE = 10;

    private static BufferedWriter writer;
    private static final Deque<String> LOG_QUEUE = new ArrayDeque<>();

    static {
        try {
            writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH.toFile(), true));
        } catch (IOException e) {
            System.err.println("BufferWriter 정상 생성 실패: " + e.getMessage());
        }
    }

    public static void log(String category, String message) {
        logProcess(logFormatter(category, message));
    }

    public static void errorLog(String message) {
        logProcess(logFormatter("ERROR", message));
    }

    private synchronized static void logProcess(String logText) {
        LOG_QUEUE.add(logText);
        System.out.println(logText);

        logFileWriter(logText);
    }

    static String logFormatter(String category, String message) {
        return String.format("[%s] [%8s] [%8s] %s\n", LocalDateTime.now().format(TIME_FORMATTER), Thread.currentThread().getName(), category, message);
    }

    static synchronized String removeOldLog() {
        return LOG_QUEUE.pollFirst();
    }

    private synchronized static void logFileWriter(String logText) {
        try {
            writer.write(logText);
            writer.newLine();

            writer.flush();
        } catch (IOException e) {
            errorLog("로그 파일 작성 실패: " + e.getMessage());
        }
    }

    public synchronized static void closeWriter() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("BufferWriter 정상 종료 실패: " + e.getMessage());
        }
    }

    static synchronized Deque<String> getLogQueue() {
        return LOG_QUEUE;
    }

    static synchronized int getLogSize() {
        return LOG_SIZE;
    }

}