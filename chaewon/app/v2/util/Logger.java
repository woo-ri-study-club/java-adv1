package chaewon.app.v2.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Deque;

public class Logger {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Path LOG_FILE_PATH = Path.of("log.txt");
    private static final int LOG_SIZE = 10;
    private static final Deque<String> LOG_QUEUE = new ArrayDeque<>();

    public static void log(String category, String message) {
        logProcess(logFormatter(category, message));
    }

    public static void errorLog(String message) {
        logProcess(logFormatter("ERROR", message));
    }

    private static void logProcess(String logText) {
        String oldLog = removeOldLog();
        if(oldLog != null){
            String deleteLog = logFormatter("DELETE",oldLog);

            System.out.println(deleteLog);
            logFileWriter(deleteLog);
        }

        LOG_QUEUE.add(logText);
        System.out.println(logText);

        logFileWriter(logText);
    }

    static String logFormatter(String category, String message) {
        return String.format("[%s] [%8s] [%8s] %s\n", LocalDateTime.now().format(TIME_FORMATTER), Thread.currentThread().getName(), category, message);
    }

    static String removeOldLog() {
        if (LOG_QUEUE.size() >= LOG_SIZE) {
            return LOG_QUEUE.pollFirst();
        }

        return null;
    }

    static void logFileWriter(String logText) {
        try {
            Files.writeString(LOG_FILE_PATH, logText,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("파일 저장 오류: " + e.getMessage());
        }
    }

     static Deque<String> getLogQueue(){
        return LOG_QUEUE;
    }

     static int getLogSize(){
        return LOG_SIZE;
    }
}
