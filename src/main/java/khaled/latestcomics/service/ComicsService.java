package khaled.latestcomics.service;

import khaled.latestcomics.model.Comic;
import khaled.latestcomics.util.ComicsFetcher;
import khaled.latestcomics.util.task.ParseComicTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    public List<Comic> getLatestComics() throws ExecutionException, InterruptedException {
        List<Comic> comics = comicsFetcher.parseComicsFromRss();
        //now we will start with the xkcd comics
        Integer latestComicNumber = comicsFetcher.getLatestComicNumber();
        if (latestComicNumber != null) {
            ExecutorService threadPool = Executors.newFixedThreadPool(10);
            List<Future<Comic>> futureComicsList = new ArrayList<>();
            log.info("fetching data for latest 10 xkcd comics");
            for (int comicNum = latestComicNumber - 9; comicNum <= latestComicNumber; comicNum++) {
                futureComicsList.add(threadPool.submit(new ParseComicTask(comicNum)));
            }
            for (Future<Comic> futureComic : futureComicsList) {
                comics.add(futureComic.get());
            }
            Collections.sort(comics, Collections.reverseOrder());
        }
        return comics;
    }
}
