package app.v4;


import app.v4.log.NewsLogger;
import app.v4.task.LogCleaner;
import app.v4.task.NewsCrawler;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        NewsLogger logger = new NewsLogger("news_log.txt");

        String[] sportsNews = {"축구 경기 결과: 팀 A 2-1 팀 B", "야구: 홈런 3개 폭발!", "배구 결승전 승부처"};
        String[] itNews = {"최신 스마트폰 출시", "AI 기술이 가져올 미래", "자율주행차의 발전"};
        String[] economyNews = {"주식 시장 급등", "환율 급변동 주의", "부동산 가격 변화"};

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        List<Callable<String>> crawlers = Arrays.asList(
                new NewsCrawler("SPORTS", logger, sportsNews),
                new NewsCrawler("IT", logger, itNews),
                new NewsCrawler("ECONOMY", logger, economyNews)
        );

        LogCleaner logCleaner = new LogCleaner(logger);
        logCleaner.start();

        try {
            for (int i = 0; i < 5; i++) {
                List<Future<String>> results = executorService.invokeAll(crawlers, 3, TimeUnit.SECONDS);
                for (Future<String> result : results) {
                    try {
                        result.get(1, TimeUnit.SECONDS);
                    } catch (TimeoutException | ExecutionException e) {
                        System.out.println("크롤링 중 오류 발생: " + e.getMessage());
                    }
                }
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
        logCleaner.shutdown();

        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

        System.out.println("모든 뉴스 크롤링 종료");
    }
}
