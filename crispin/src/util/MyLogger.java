package util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MyLogger {

    private MyLogger() {
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public static void log(Object obj) {
        String time = LocalTime.now().format(formatter);
        String str = String.format("%s [%9s] %s", time, Thread.currentThread().getName(), obj);
        System.out.println(str);
    }
}
