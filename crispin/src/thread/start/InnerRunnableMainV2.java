package thread.start;

import util.MyLogger;

public class InnerRunnableMainV2 {

    public static void main(String[] args) {
        MyLogger.log("main() start");

        Thread thread = new Thread(() -> MyLogger.log("run()"));
        thread.start();

        MyLogger.log("main() end");
    }
}
