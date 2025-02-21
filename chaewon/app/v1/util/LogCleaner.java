package ex.app.v1.util;

import static ex.app.v1.util.Logger.*;

public class LogCleaner implements Runnable {
    private boolean running = true;

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(5000);

                while (getLogQueue().size() > getLogSize()) {
                    String oldLog = removeOldLog();

                    if(oldLog != null){
                        String deleteLog = logFormatter("DELETE",oldLog);

                        System.out.println(deleteLog);
                        logFileWriter(deleteLog);
                    }
                }

            } catch (InterruptedException e) {
                errorLog("클리너 실패: " + e.getMessage());
            }
        }
    }

    public void stop(){
        running = false;
    }
}
