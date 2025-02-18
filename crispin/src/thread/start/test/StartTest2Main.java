package thread.start.test;

import util.MyLogger;

public class StartTest2Main {

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                MyLogger.log(String.format("value: %d", i + 1));
                if (i == 4) {
                    return;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "counter").start();
    }
}
