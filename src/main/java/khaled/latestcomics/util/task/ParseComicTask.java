package khaled.latestcomics.util.task;

import khaled.latestcomics.model.Comic;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.concurrent.Callable;

/**
 * @author Khaled
 */

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ParseComicTask implements Callable<Comic> {

    private Integer comicNum;

    @Override
    public Comic call() {
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
