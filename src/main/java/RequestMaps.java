import java.util.HashMap;
import java.util.Map;

public class RequestMaps {

    public Map<String, Object> titleAndYearMap(String title) {
        Map<String, Object> params = new HashMap<>();
        params.put("t", title);
        params.put("y", 2011);
        params.put("apiKey", "52cd129d");
        return params;
    }

    public Map<String, Object> imdbIdMap(String imdbId) {
        Map<String, Object> bySearchParams = new HashMap<>();
        bySearchParams.put("i", imdbId);
        bySearchParams.put("apiKey", "52cd129d");
        return bySearchParams;
    }

    public Map<String, Object> titleMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("t", "Harry Potter");
        return params;
    }
}
