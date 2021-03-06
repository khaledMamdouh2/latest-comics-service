package khaled.latestcomics.controller;

import khaled.latestcomics.model.Comic;
import khaled.latestcomics.service.ComicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Khaled
 */
@RestController
public class ComicsController {

    private ComicsService comicsService;

    @Autowired
    public ComicsController(ComicsService comicsService) {
        this.comicsService = comicsService;
    }

    @GetMapping("/getLatestComics")
    List<Comic> getLatestComics() {
        try {
            return comicsService.getLatestComics();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
