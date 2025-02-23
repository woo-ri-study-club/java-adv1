package chaewon.app.v2;

import java.util.List;
import java.util.Random;

import static ex.app.v1.util.Logger.errorLog;
import static ex.app.v1.util.Logger.log;

public class NewsCrawler implements Runnable{
    private final Random random = new Random();

    private final String category;
    private final List<String> titles;

    public NewsCrawler(String category, List<String> titles) {
        this.category = category;
        this.titles = titles;
    }

    @Override
    public void run() {
        while(!Thread.interrupted()){
            try {
                //title 리스트 중 한 개만 랜덤으로 크롤링
                int chooseIndex = random.nextInt(titles.size());
                String chooseTitle = titles.get(chooseIndex);
                log(category, chooseTitle);

                Thread.sleep(3000);

                Thread.yield();
            } catch (InterruptedException e) {
                errorLog("뉴스 크롤링 실패: " + e.getMessage());
            }
        }

    }

}