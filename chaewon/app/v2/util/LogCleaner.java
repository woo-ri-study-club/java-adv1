package chaewon.app.v2.util;

import static chaewon.app.v2.util.Logger.*;

public class LogCleaner implements Runnable {

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(5000);

                while (getLogQueue().size() > getLogSize()) {
                    String oldLog = removeOldLog();

                    if (oldLog != null) {
                        String deleteLog = logFormatter("DELETE", oldLog);

                        System.out.println(deleteLog);
                    }
                }

            } catch (InterruptedException e) {
                errorLog("클리너 실패: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

}
