package khaled.latestcomics.service;

import khaled.latestcomics.model.Comic;
import khaled.latestcomics.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Khaled
 */

@Slf4j
@Service
public class ComicsService {
    public List<Comic> getLatestComics() {
        RestTemplate restTemplate = new RestTemplate();
        log.info("Calling RSS feed page to get its latest 10 comics");
        String response = restTemplate.getForObject(Constants.RSS_TO_JSON_API_URL, String.class);
        List<Comic> comics = parseComicsFromRss(response);
        return comics;
    }

    private List<Comic> parseComicsFromRss(String comicsInJson) {
        log.info("parsing json object of comics returned from RSS");
        List<Comic> comics = new ArrayList<>();
        try {
            JSONObject mainJsonObject = new JSONObject(comicsInJson);
            JSONArray comicsJsonArr = mainJsonObject.getJSONArray("items");
            for (int i = 0; i < comicsJsonArr.length(); i++) {
                JSONObject comicJsonObj = comicsJsonArr.getJSONObject(i);
                String comicViewUrl = comicJsonObj.getString("link");
                String comicTitle = comicJsonObj.getString("title");
                String comicPicUrl = comicJsonObj.getString("thumbnail");
                String comicDateStr = comicJsonObj.getString("pubDate").substring(0, 10);
                LocalDate comicPublishingDate = LocalDate.parse(comicDateStr);
                comics.add(new Comic(comicPicUrl, comicTitle, comicViewUrl, comicPublishingDate));
            }
        } catch (JSONException err) {
            log.error(err.getMessage());
        }
        return comics;
    }
}
