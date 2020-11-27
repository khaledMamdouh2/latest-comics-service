package khaled.latestcomics.util;

import khaled.latestcomics.model.Comic;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Khaled
 */
@Slf4j
@Component
public class ComicsFetcher {

    public List<Comic> parseComicsFromRss() {
        RestTemplate restTemplate = new RestTemplate();
        log.info("Calling RSS feed page to get its latest 10 comics");
        String comicsInJson = restTemplate.getForObject(Constants.RSS_TO_JSON_API_URL, String.class);
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

    public Integer getLatestComicNumber() {
        log.info("reading json file from xkcd for the latest comic");
        try {
            URL currentComicURL = new URL(Constants.XKCD_CURRENT_COMIC_URL);
            JSONTokener tokener = new JSONTokener(currentComicURL.openStream());
            JSONObject comicJsonObj = new JSONObject(tokener);
            return comicJsonObj.getInt("num");
        } catch (IOException | JSONException e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
