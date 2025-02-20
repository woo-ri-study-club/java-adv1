package app.v1;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import util.MyLogger;

public class Main {

    public static void main(String[] args) {
        NewsSerializer newsSerializer = new NewsSerializer();
        NewsData data = newsSerializer.serialize();
        Deque<Article> articles = new LinkedList<>();

        new Thread(() -> {
            while (true) {
                data.news().culture().forEach(article -> {
                    try {
                        articles.add(article);
                        MyLogger.log(article);
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }, "culture").start();

        new Thread(() -> {
            while (true) {
                data.news().economy().forEach(article -> {
                    try {
                        articles.add(article);
                        MyLogger.log(article);
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }, "economy").start();

        new Thread(() -> {
            while (true) {
                data.news().politics().forEach(article -> {
                    try {
                        articles.add(article);
                        MyLogger.log(article);
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }, "politics").start();

        new Thread(() -> {
            while (true) {
                data.news().sports().forEach(article -> {
                    try {
                        articles.add(article);
                        MyLogger.log(article);
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }, "sports").start();

        new Thread(() -> {
            while (true) {
                data.news().science().forEach(article -> {
                    try {
                        articles.add(article);
                        MyLogger.log(article);
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }, "science").start();

        new Thread(() -> {
            try (FileWriter fileWriter = new FileWriter("news_log.txt")) {
                while (true) {
                    if (articles.iterator().hasNext()) {
                        String article = articles.pop().toString();
                        fileWriter.write(article + System.lineSeparator());
                        fileWriter.flush();
                        MyLogger.log(article);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, "writer").start();

        new Thread(() -> {
            Path filePath = Paths.get("news_log.txt");
            try {
                while (true) {
                    if (Files.exists(filePath)) {
                        List<String> lines = Files.readAllLines(filePath);
                        if (lines.size() > 10) {
                            MyLogger.log(lines.removeFirst());
                            Files.write(filePath, lines,
                                StandardOpenOption.CREATE,
                                StandardOpenOption.TRUNCATE_EXISTING
                            );
                        }
                    }
                    Thread.sleep(5000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }, "watcher").start();
    }
}
