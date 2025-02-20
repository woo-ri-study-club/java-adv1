package app.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class NewsSerializer {

    public NewsData serialize() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("news_data.json"), NewsData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
