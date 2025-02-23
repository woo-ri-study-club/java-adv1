package app.v1;

import app.v1.log.NewsLogger;
import app.v1.task.LogCleaner;
import app.v1.task.NewsCrawler;

public class Main {
    public static void main(String[] args) {
        NewsLogger logger = new NewsLogger("news_log.txt");

        String[] sportsNews = {"축구 경기 결과: 팀 A 2-1 팀 B", "야구: 홈런 3개 폭발!", "배구 결승전 승부처"};
        String[] itNews = {"최신 스마트폰 출시", "AI 기술이 가져올 미래", "자율주행차의 발전"};
        String[] economyNews = {"주식 시장 급등", "환율 급변동 주의", "부동산 가격 변화"};

        NewsCrawler sportsCrawler = new NewsCrawler("SPORTS", logger, sportsNews);
        NewsCrawler itCrawler = new NewsCrawler("IT", logger, itNews);
        NewsCrawler economyCrawler = new NewsCrawler("ECONOMY", logger, economyNews);

        Thread sportsThread = new Thread(sportsCrawler);
        Thread itThread = new Thread(itCrawler);
        Thread economyThread = new Thread(economyCrawler);

        sportsThread.start();
        itThread.start();
        economyThread.start();

        Thread logCleanerThread = new Thread(new LogCleaner(logger));
        logCleanerThread.start();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sportsCrawler.stop();
        itCrawler.stop();
        economyCrawler.stop();

        System.out.println("모든 뉴스 스레드 종료");
    }
}
