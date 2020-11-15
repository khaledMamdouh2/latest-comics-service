package khaled.latestcomics.service;

import khaled.latestcomics.model.Comic;
import khaled.latestcomics.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
        //now we will start with the xkcd comics
        log.info("reading json file from xkcd for the latest comic");
        Integer latestComicNumber = getLatestComicNumber();
        if (latestComicNumber != null) {
            log.info("fetching data for latest 10 xkcd comics");
            for (int comicNum = latestComicNumber - 9; comicNum <= latestComicNumber; comicNum++) {
                comics.add(getComicFromXKCD(comicNum));
            }
            Collections.sort(comics, Collections.reverseOrder());
        }
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

    private Integer getLatestComicNumber() {
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

    private Comic getComicFromXKCD(Integer comicNum) {
        try {
            URL currentComicURL = new URL("https://xkcd.com/" + comicNum + "/info.0.json");
            JSONTokener tokener = new JSONTokener(currentComicURL.openStream());
            JSONObject comicJsonObj = new JSONObject(tokener);
            String comicTitle = comicJsonObj.getString("title");
            String comicViewUrl = comicJsonObj.getString("link");
            String comicPicUrl = comicJsonObj.getString("img");
            Integer comicYear = Integer.parseInt(comicJsonObj.getString("year"));
            Integer comicMonth = Integer.parseInt(comicJsonObj.getString("month"));
            Integer comicDayOfMonth = Integer.parseInt(comicJsonObj.getString("day"));
            LocalDate comicPublishingDate = LocalDate.of(comicYear, comicMonth, comicDayOfMonth);
            return new Comic(comicPicUrl, comicTitle, comicViewUrl, comicPublishingDate);
        } catch (IOException | JSONException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
