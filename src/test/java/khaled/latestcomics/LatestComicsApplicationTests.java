package khaled.latestcomics;

import khaled.latestcomics.controller.ComicsController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest class LatestComicsApplicationTests {

    @Autowired
    private ComicsController comicsController;

    @DisplayName("asserting that Autowiring is ok")
    @Test
    void contextLoads() {
        assertThat(comicsController).isNotNull();
    }

}
