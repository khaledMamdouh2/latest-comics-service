package khaled.latestcomics.util;

/**
 * @author Khaled
 */
public class Constants {

    private static final String RSS_SOURCE_URL = "http://feeds.feedburner.com/PoorlyDrawnLines";

    public static final String RSS_TO_JSON_API_URL = "https://api.rss2json.com/v1/api.json?rss_url=" + RSS_SOURCE_URL;

    public static final String XKCD_CURRENT_COMIC_URL = "https://xkcd.com/info.0.json";
}
