package chaewon.app.v1;

import chaewon.app.v1.util.LogCleaner;

import java.util.List;

public class NewsMain {
    public static void main(String[] args) {
        List<String> sports = List.of("\'ㅇㅇㅇ 포함 4명이 10점 ↑\' 진격의 ㅇㅇㅇㅇ", "ㅇㅇㅇ \'주전 2루수\' 스타트, MVP 유격수가 훌륭하고 멋진 선수", "힘든 재활 이겨낸 ㅇㅇㅇ, 정규 리그 최종전서 깜짝 복귀");
        List<String> its = List.of("아이폰 사용자 절반, 안드로이드 쓰다 바꿨다.", "오징어게임과 스포티지 콜라보…", "ㅇㅇㅇㅇ의 게임, 스토어 인기 1위 올라");
        List<String> economies = List.of("ㅇㅇㅇ, 얼라인 추천 사외이사 후보자, 법률상 결격 주장", "\'1% 성장\' 전망까지 나와… 기준금리 향방은?", "딥시크 최대 수혜주 ㅇㅇㅇㅇ");

        NewsCrawler sportCrawler = new NewsCrawler("SPORT", sports);
        NewsCrawler itCrawler = new NewsCrawler("IT", its);
        NewsCrawler economyCrawler = new NewsCrawler("ECONOMY", economies);
        LogCleaner logCleaner = new LogCleaner();

        Thread sportThread = new Thread(sportCrawler, "sport");
        Thread itThread = new Thread(itCrawler, "it");
        Thread economyThread = new Thread(economyCrawler, "economy");
        Thread cleanerThread = new Thread(logCleaner, "logCleaner");

        sportThread.start();
        itThread.start();
        economyThread.start();
        cleanerThread.start();

        try {
            Thread.sleep(15000);

            sportCrawler.stop();
            itCrawler.stop();
            economyCrawler.stop();
            logCleaner.stop();
        } catch (InterruptedException e) {
            e.getStackTrace();
        }

        Logger.closeWriter();
    }
}
