package app.v1;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Article(
    String title,
    String content,
    String author,
    @JsonProperty("published_at") String publishedAt
) {}
