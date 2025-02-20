package app.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record News(
    @JsonProperty("정치") List<Article> politics,
    @JsonProperty("경제") List<Article> economy,
    @JsonProperty("스포츠") List<Article> sports,
    @JsonProperty("과학") List<Article> science,
    @JsonProperty("문화") List<Article> culture
) {}
