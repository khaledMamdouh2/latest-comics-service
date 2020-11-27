package khaled.latestcomics.service;

import khaled.latestcomics.model.Comic;
import khaled.latestcomics.util.ComicsFetcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Khaled
 */

@Slf4j
@Service
public class ComicsService {

    private final ComicsFetcher comicsFetcher;

    @Autowired
    public ComicsService(ComicsFetcher comicsFetcher) {
        this.comicsFetcher = comicsFetcher;
    }

    public List<Comic> getLatestComics() {
        List<Comic> comics = comicsFetcher.parseComicsFromRss();
        //now we will start with the xkcd comics
        log.info("reading json file from xkcd for the latest comic");
        Integer latestComicNumber = comicsFetcher.getLatestComicNumber();
        if (latestComicNumber != null) {
            log.info("fetching data for latest 10 xkcd comics");
            for (int comicNum = latestComicNumber - 9; comicNum <= latestComicNumber; comicNum++) {
                comics.add(comicsFetcher.getComicFromXKCD(comicNum));
            }
            Collections.sort(comics, Collections.reverseOrder());
        }
        return comics;
    }
}
