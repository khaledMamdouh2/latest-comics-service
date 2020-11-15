package khaled.latestcomics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author Khaled
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comic {
    private String pictureUrl;

    private String title;

    private String browserViewUrl;

    private LocalDate publishingDate;
}
