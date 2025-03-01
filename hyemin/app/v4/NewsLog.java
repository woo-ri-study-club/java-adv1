package app.v4;

public class NewsLog {
    private final String category;
    private final String content;

    public NewsLog(String category, String content) {
        this.category = category;
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Log{" +
                "category='" + category + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
