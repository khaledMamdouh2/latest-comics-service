package khaled.latestcomics.service;

import khaled.latestcomics.model.Comic;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Khaled
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ComicsServiceTest {

    @Autowired
    private ComicsService comicsService;

    @DisplayName("checking that comics list is correct")
    @Test
    public void getLatestComicsTest() {
        assertThat(comicsService).isNotNull();
        List<Comic> comics = comicsService.getLatestComics();
        assertThat(comics).isNotNull();
        assertThat(comics).isNotEmpty();
        List<Comic> comicsTobeSorted = new ArrayList<>(comics);
        Collections.sort(comicsTobeSorted, Collections.reverseOrder());
        assertThat(comicsTobeSorted).isEqualTo(comics);
        assertThat(comics.size()).isEqualTo(20);
    }
}
