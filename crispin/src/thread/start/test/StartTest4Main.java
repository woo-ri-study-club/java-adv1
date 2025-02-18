package thread.start.test;

import util.MyLogger;

public class StartTest4Main {

    public static void main(String[] args) {
        new Thread(() -> printString("A", 1000), "ThreadA").start();
        new Thread(() -> printString("B", 500), "ThreadB").start();
    }

    private static void printString(String str, int millis) {
        while (true) {
            MyLogger.log(str);
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
