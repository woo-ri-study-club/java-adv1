package app.v3;


import app.v3.log.NewsLogger;
import app.v3.task.LogCleaner;
import app.v3.task.NewsCrawler;

public class Main {
    public static void main(String[] args) {
        NewsLogger logger = new NewsLogger("news_log.txt");

        // 샘플 뉴스 데이터
        String[] sportsNews = {"축구 경기 결과: 팀 A 2-1 팀 B", "야구: 홈런 3개 폭발!", "배구 결승전 승부처"};
        String[] itNews = {"최신 스마트폰 출시", "AI 기술이 가져올 미래", "자율주행차의 발전"};
        String[] economyNews = {"주식 시장 급등", "환율 급변동 주의", "부동산 가격 변화"};

        // 뉴스 크롤러 스레드 실행
        Thread sportsThread = new Thread(new NewsCrawler("SPORTS", logger, sportsNews));
        Thread itThread = new Thread(new NewsCrawler("IT", logger, itNews));
        Thread economyThread = new Thread(new NewsCrawler("ECONOMY", logger, economyNews));

        sportsThread.start();
        itThread.start();
        economyThread.start();

        // 로그 정리 스레드 실행
        Thread logCleanerThread = new Thread(new LogCleaner(logger));
        logCleanerThread.start();

        // 15초 후 모든 스레드 종료
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 모든 스레드 interrupt
        sportsThread.interrupt();
        itThread.interrupt();
        economyThread.interrupt();
        logCleanerThread.interrupt();

        // join()을 사용하여 모든 스레드가 종료될 때까지 대기
        try {
            sportsThread.join();
            itThread.join();
            economyThread.join();
            logCleanerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("모든 뉴스 스레드 종료");
    }
}
